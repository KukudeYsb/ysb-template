package com.manager.file;

import com.data.bo.ChunksObj;
import com.data.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFile {

    /**
     * 上传文件
     * @param file 文件
     */
    String upload(MultipartFile file) throws Exception;

    /**
     * 分片上传文件
     * @param file 文件
     * @param name 文件名
     * @param shardSize 分页符号
     * @param shardTotal 分页总数
     * @return 文件地址
     */
    String resumeUpload(MultipartFile file, String name, int shardSize, int shardTotal);

    /**
     * 判断文件是否存在
     * @param filename 文件名
     * @return 是否存在
     */
    FileVo checkFile(String filename, String extName, ChunksObj chunksObj);

    /**
     * 检查文件分片是否存在
     * @param folderName 文件夹名
     * @return 需要下载的文件分片
     */
    Boolean checkFilesExists(String folderName);


    Integer uploadChunk(MultipartFile file, String chunkName, String name, Integer index);

    void mergeFile(String fileMd5, String filename, String extName);
}
