package com.manager.file.Oss;

import com.data.bo.ChunksObj;
import com.data.vo.FileVo;
import com.manager.file.UploadFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杨世博
 * @date 2023/11/26 16:37
 * @description OssUpload
 */
public class OssUpload implements UploadFile {
    @Override
    public String upload(MultipartFile file) throws Exception {
        return null;
    }

    @Override
    public String resumeUpload(MultipartFile file, String name, int shardSize, int shardTotal) {
        return null;
    }

    @Override
    public FileVo checkFile(String filename, String extName, ChunksObj chunksObj) {
        return null;
    }

    @Override
    public Boolean checkFilesExists(String folderName) {
        return null;
    }

    @Override
    public Integer uploadChunk(MultipartFile file, String chunkName, String name, Integer index) {
        return null;
    }

    @Override
    public void mergeFile(String fileMd5, String filename, String extName) {

    }
}
