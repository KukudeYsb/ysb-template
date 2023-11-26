package com.data.dto;

import com.data.bo.ChunksObj;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 杨世博
 * @date 2023/11/20 22:11
 * @description 文件分片传输接口
 */
@Data
public class FileCheckDto implements Serializable {

    String fileName;
    String extName;
    ChunksObj chunksObj;
}
