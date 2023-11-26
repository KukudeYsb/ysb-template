package com.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 杨世博
 * @date 2023/11/21 1:06
 * @description 文件合并Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileMergeDto implements Serializable {
    /**
     * 文件的md5
     */
    private String fileMd5;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 文件类型
     */
    private String extName;
}
