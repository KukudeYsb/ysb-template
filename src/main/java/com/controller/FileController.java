package com.controller;

import com.commen.Result;
import com.data.dto.FileCheckDto;
import com.data.dto.FileMergeDto;
import com.data.vo.FileVo;
import com.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杨世博
 * @date 2023/11/26 16:14
 * @description 文件接口
 */
@RestController
@RequestMapping("/file")
@Validated
public class FileController {

    @Autowired
    IFileService fileService;

    /**
     * 分片上传-检查（秒传）
     * @param fileCheckDto 检查文件分片传输类
     * @return 分片上传信息
     */
    @PostMapping("/check")
    public Result<FileVo> check(@RequestBody FileCheckDto fileCheckDto){
        FileVo fileVo = fileService.check(fileCheckDto.getFileName(), fileCheckDto.getExtName(), fileCheckDto.getChunksObj());
        return Result.success(fileVo);
    }

    /**
     * 分片上传-上传分片
     * @param file 分片文件
     * @param chunkName 分片名
     * @param name 文件md5
     * @param fileName 文件名
     * @param index 分片序号
     * @return 返回分片序号
     */
    @PostMapping("/upload/chunk")
    public Result<Integer> uploadChunk(@RequestBody MultipartFile file,
                                        @RequestParam("chunkName")String chunkName,
                                        @RequestParam("name")String name,
                                        @RequestParam("fileName")String fileName,
                                        @RequestParam("index")Integer index){
        Integer i = fileService.uploadChunk(file, chunkName, name, fileName, index);
        return Result.success(i);
    }

    /**
     * 合并分片
     * @param fileMergeDto 合并传输类
     * @return
     */
    @PostMapping("/merge")
    public Result merge(@RequestBody FileMergeDto fileMergeDto){
        fileService.merge(fileMergeDto.getFileMd5(), fileMergeDto.getFilename(), fileMergeDto.getExtName());
        return Result.success();
    }
}
