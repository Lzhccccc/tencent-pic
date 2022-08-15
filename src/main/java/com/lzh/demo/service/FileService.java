package com.lzh.demo.service;


import com.lzh.demo.model.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    String upload(MultipartFile file);

    //批量上传
    String upload(List<MultipartFile> files);

    Response<Object> checkPic(String url);
}
