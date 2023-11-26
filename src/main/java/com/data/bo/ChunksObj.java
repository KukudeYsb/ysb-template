package com.data.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2023/11/20 20:43
 * @description 上传文件分片
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChunksObj {
    private String name;
    private String[] chunksName;
}
