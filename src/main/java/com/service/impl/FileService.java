package com.service.impl;

import com.data.bo.ChunksObj;
import com.data.vo.FileVo;
import com.manager.file.UploadFile;
import com.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杨世博
 * @date 2023/11/26 16:16
 * @description FileService
 */
@Service
public class FileService implements IFileService {

    @Qualifier("local")
    @Autowired
    UploadFile uploadFile;


    @Override
    public FileVo check(String fileName, String extName, ChunksObj chunksObj) {
        if (uploadFile.checkFilesExists(fileName)){
            return new FileVo();
        }else {
            return uploadFile.checkFile(fileName, extName, chunksObj);
        }
    }

    @Override
    public Integer uploadChunk(MultipartFile file, String chunkName, String name, String fileName, Integer index) {
        return uploadFile.uploadChunk(file, chunkName, name, index);
    }

    @Override
    public void merge(String fileMd5, String filename, String extName) {
        uploadFile.mergeFile(fileMd5, filename, extName);
    }
}
