package com.service;

import com.data.bo.ChunksObj;
import com.data.vo.FileVo;
import com.manager.file.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杨世博
 * @date 2023/11/26 16:15
 * @description IFileService
 */
public interface IFileService {

    /**
     * 分片上传-检查（秒传）
     * @param fileName 文件名
     * @param extName 文件后缀名
     * @param chunksObj 分片
     * @return 文件分片返回类
     */
    FileVo check(String fileName, String extName, ChunksObj chunksObj);

    /**
     * 分片上传-上传分片
     * @param file 分片文件
     * @param chunkName 分片名
     * @param name 文件md5
     * @param fileName 文件名
     * @param index 分片序号
     * @return
     */
    Integer uploadChunk(MultipartFile file, String chunkName, String name, String fileName, Integer index);

    /**
     * 分片上传-合并
     * @param fileMd5 文件Md5
     * @param filename 文件名
     * @param extName 文件后缀
     */
    void merge(String fileMd5, String filename, String extName);
}
