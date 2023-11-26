package com.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2023/11/20 21:28
 * @description 文件分片返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVo {
    private String[] uploadedChunks;
    private String[] notUploadedChunks;
    private String filename;
    private String fileType;

    public FileVo(String filename, String fileType) {
        this.filename = filename;
        this.fileType = fileType;
    }
}
