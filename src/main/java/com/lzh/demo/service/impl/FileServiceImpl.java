package com.lzh.demo.service.impl;


import com.alibaba.fastjson.JSON;
import com.lzh.demo.model.Response;
import com.lzh.demo.service.FileService;
import com.lzh.demo.utils.ConstantPropertiesUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ims.v20201229.ImsClient;
import com.tencentcloudapi.ims.v20201229.models.ImageModerationRequest;
import com.tencentcloudapi.ims.v20201229.models.ImageModerationResponse;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    //文件上传
    @Override
    public String upload(MultipartFile file) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String secretKey = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(ConstantPropertiesUtil.END_POINT);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        // 对象键(Key)是对象在存储桶中的唯一标识。(存放路径加名称)
        String key = UUID.randomUUID().toString().replaceAll("-","")
                +file.getOriginalFilename();
        //对上传文件进行分组处理 //当前日期2022/8/14
        String dateTime = new DateTime().toString("yyyy/MM/dd");
        key = dateTime + "/"+ key;
        try {
            // 这里创建一个 ByteArrayInputStream 来作为示例，实际中这里应该是您要上传的 InputStream 类型的流
            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);

            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

           System.out.println(JSON.toJSONString(putObjectResult));

            String url = "https://"+bucketName+"."+"cos"+"."+region+".myqcloud.com"+"/"+key;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }



        return null;
    }


    @Override
    public String upload(List<MultipartFile> files) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String secretKey = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(ConstantPropertiesUtil.END_POINT);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        for(MultipartFile file : files){
            // 对象键(Key)是对象在存储桶中的唯一标识。(存放路径加名称)
            String key = UUID.randomUUID().toString().replaceAll("-","")
                    +file.getOriginalFilename();
            //对上传文件进行分组处理 //当前日期2022/8/14
            String dateTime = new DateTime().toString("yyyy/MM/dd");
            key = dateTime + "/"+ key;
            try {
                // 这里创建一个 ByteArrayInputStream 来作为示例，实际中这里应该是您要上传的 InputStream 类型的流
                InputStream inputStream = file.getInputStream();
                ObjectMetadata objectMetadata = new ObjectMetadata();

                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);

                PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

                //返回文件上传路径
                System.out.println(JSON.toJSONString(putObjectResult));

//                String url = "https://"+bucketName+"."+"cos"+"."+ConstantPropertiesUtil.END_POINT+".myqcloud.com"+"/"+key;
//                return url;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




        return "批量上传成功";
    }


    @Override
    public Response<Object> checkPic(String url) {
        ImageModerationResponse resp = null;
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(ConstantPropertiesUtil.ACCESS_KEY_ID
                    , ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ims.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            ImsClient client = new ImsClient(cred, ConstantPropertiesUtil.END_POINT, clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ImageModerationRequest req = new ImageModerationRequest();
            req.setFileUrl(url);
            // 返回的resp是一个ImageModerationResponse的实例，与请求对象对应
            resp = client.ImageModeration(req);
            // 输出json格式的字符串回包
            System.out.println(ImageModerationResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }


        return Response.success(resp);
    }
}
