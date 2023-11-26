package com.manager.file.Local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.commen.SystemException;
import com.constants.ResultCode;
import com.data.bo.ChunksObj;
import com.data.vo.FileVo;
import com.manager.file.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 杨世博
 * @date 2023/11/26 16:18
 * @description 文件上传至服务器本地
 */
@Slf4j
@Service("local")
public class LocalUpload implements UploadFile {
    //文件磁盘路径
    @Value("D:\\test\\file\\")
    private String UPLOAD_DIR;

    //分片文件缓存路径
    @Value("D:\\test\\file\\tmp\\")
    private String TMP_DIR;

    @Override
    public String upload(MultipartFile file) throws Exception {
        return null;
    }

    public String resumeUpload(MultipartFile file, String name, int shardSize, int shardTotal){

        String type = FileUtil.extName(name);
        //
        String uuid = UUID.randomUUID().toString();
        //新文件名称
        final String newFileName = UPLOAD_DIR + File.separator + uuid + StrUtil.DOT + type;

        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String fileName = uuid + "-check-" + shardSize;
            File chunkFile = new File(uploadDir, fileName);

            try (InputStream inputStream = file.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(chunkFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            if (shardSize == shardTotal - 1) {
                File finalFile = new File(newFileName);
                for (int i = 0; i < shardTotal; i++) {
                    File part = new File(uploadDir, uuid + "-check-" + i);
                    try (FileInputStream partStream = new FileInputStream(part);
                         FileOutputStream finalStream = new FileOutputStream(finalFile, true)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = partStream.read(buffer)) != -1) {
                            finalStream.write(buffer, 0, bytesRead);
                        }
                    }
                    part.delete();
                }
                uploadDir.delete();
            }

            return newFileName;
        } catch (IOException e) {
            throw new SystemException(ResultCode.UPLOAD_ERROR);
        }
    }

    @Override
    public FileVo checkFile(String filename, String extName, ChunksObj chunksObj) {
        FileVo fileVo = new FileVo(filename, FileUtil.extName(filename));

        if (!chunksObj.getName().isEmpty()){
            String[] files = getFiles(chunksObj.getName());
            if (files == null && chunksObj.getChunksName() != null){
                fileVo.setNotUploadedChunks(chunksObj.getChunksName());
            }
            if (files != null && chunksObj.getChunksName() != null){
                Set<String> set = new HashSet<>(Arrays.asList(chunksObj.getChunksName()));

                // 创建一个新的ArrayList来存储结果
                List<String> result = new ArrayList<>();

                // 遍历array1中的元素，如果不在set中，则添加到result中
                for (String element : files) {
                    if (!set.contains(element)) {
                        result.add(element);
                    }
                }
                //转换回String[]
                String[] resultArray = result.toArray(new String[0]);
                fileVo.setNotUploadedChunks(resultArray);
            }
            fileVo.setUploadedChunks(files);
        }
        return fileVo;
    }

    @Override
    public Boolean checkFilesExists(String folderName){
        File file = new File(UPLOAD_DIR + folderName);
        return file.exists();
    }

    @Override
    public Integer uploadChunk(MultipartFile file, String chunkName, String name, Integer index) {
        try {
            File uploadChunks = new File(TMP_DIR + name);
            if (!uploadChunks.exists()){
                uploadChunks.mkdirs();
            }

            File chunkFile = new File(uploadChunks, chunkName);

            try (InputStream inputStream = file.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(chunkFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        }catch (IOException e){
            throw new SystemException(ResultCode.UPLOAD_ERROR);
        }
        return index;
    }

    @Override
    public void mergeFile(String fileMd5, String filename, String extName) {
        String sourceFiles = TMP_DIR + fileMd5;
        String targetFile = UPLOAD_DIR + fileMd5 + StrUtil.DOT + extName;

        thunkStreamMerge(sourceFiles, targetFile);
    }

    /**
     * 判断分片是否存在
     * @param name MD5
     * @return 分片文件路径
     */
    private String[] getFiles(String name){
        File file = new File(TMP_DIR + name);
        if (file.isDirectory()){
            return file.list();
        }else {
            return null;
        }
    }

    /**
     * 文件合并
     * @param sourceFiles 源文件目录
     * @param targetFile 目标文件目录
     */
    private void thunkStreamMerge(String sourceFiles, String targetFile){
        String sourceFilesDir = Paths.get(sourceFiles).toString();
        String targetFileDir = Paths.get(targetFile).toString();

        try {
            List<Path> files = Files.list(Paths.get(sourceFilesDir))
                    .filter(Files::isRegularFile)
                    .sorted((a, b) -> Integer.parseInt(a.getFileName().toString().split("@")[1]) - Integer.parseInt(b.getFileName().toString().split("@")[1]))
                    .collect(Collectors.toList());

            try (Stream<Path> fileStream = (Stream<Path>) files) {
                fileStream.forEach(file -> {
                    try {
                        Files.copy(file, Paths.get(targetFileDir), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new SystemException(ResultCode.COMMON_FAIL);
                    }
                });
            }
        } catch (IOException e) {
            throw new SystemException(ResultCode.COMMON_FAIL);
        } finally {
            try {
                Files.deleteIfExists(Paths.get(sourceFilesDir));
            } catch (IOException e) {
                throw new SystemException(ResultCode.COMMON_FAIL);
            }
        }
    }
}
