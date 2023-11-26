package com.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author 杨世博
 * @date 2023/11/20 23:07
 * @description 文件分片传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileChunkDto implements Serializable {
    private MultipartFile file;
    private String chunkName;
    private String name;
    private String fileName;
    private Integer index;
}
