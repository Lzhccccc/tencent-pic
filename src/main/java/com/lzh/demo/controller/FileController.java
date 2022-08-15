package com.lzh.demo.controller;

import com.lzh.demo.model.Response;
import com.lzh.demo.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("admin/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Response uploadFile(MultipartFile file){
        String url = fileService.upload(file);
        return Response.success(url,"文件上传成功");
    }

    @ApiOperation("批量上传")
    @PostMapping("/uploadMost")
    public Response uploadFiles(@RequestParam(name = "file") List<MultipartFile> files){
        String url = fileService.upload(files);
        return Response.success(url,"文件上传成功");
    }

    @ApiOperation("图片审核")
    @GetMapping("/checkPic")
    public Response<Object> checkPic(String url){
        Response<Object> result = fileService.checkPic(url);
        return result;
    }
}
