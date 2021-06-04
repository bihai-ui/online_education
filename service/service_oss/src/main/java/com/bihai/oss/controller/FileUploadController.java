package com.bihai.oss.controller;
/*
 *@author bihai-ui
 *@create 2020-12-08 16:43
 */

import com.bihai.common_utils.ResultData;
import com.bihai.oss.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Api(description="阿里云文件管理")
@RestController
@RequestMapping("/oss/images")
//@CrossOrigin
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public ResultData upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file){

        String upload = fileUploadService.upload(file);
        HashMap<String, Object> map = new HashMap<>();
        map.put("url",upload);
        return ResultData.success().data(map);
    }


    @ApiOperation(value = "文件删除")
    @DeleteMapping("delete")
    public ResultData delete(@RequestParam("filePathUrl") String filePathUrl){
        fileUploadService.deleteFile(filePathUrl);
        return ResultData.success().message("删除成功");
    }
}