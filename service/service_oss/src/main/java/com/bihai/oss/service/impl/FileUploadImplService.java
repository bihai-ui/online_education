package com.bihai.oss.service.impl;
/*
 *@author bihai-ui
 *@create 2020-12-08 16:42
 */

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectResult;
import com.bihai.oss.config.OssMessageConfigure;
import com.bihai.oss.service.FileUploadService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileUploadImplService implements FileUploadService {

    @Autowired
    private OssMessageConfigure configure;

    @Override
    public String upload(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = configure.getEndpoint();

        String accessKeyId = configure.getKeyid();
        String accessKeySecret = configure.getKeysecret();

        String accessBucketname = configure.getBucketname();


        // 上传文件流。
        InputStream inputStream = null;
        OSS ossClient = null;
        String uploadUrl = "";
        try {

            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //判断对应的桶是否存在,不能存在则创建这个Bucket
            if(!ossClient.doesBucketExist(accessBucketname)){
                //创建Bucket
                ossClient.createBucket(accessBucketname);

                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(accessBucketname, CannedAccessControlList.PublicRead);

            }

            inputStream = file.getInputStream();

            //构建日期路径：avatar/2019/02/26/文件名
            String filePath = new DateTime().toString("yyyy/MM/dd");

            //文件名：uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            String fileUrl = filePath + "/" + newName;


            PutObjectResult putObjectResult = ossClient.putObject(accessBucketname, fileUrl, inputStream);

            //获取url地址
            uploadUrl = "http://" + accessBucketname + "." + endpoint + "/" + fileUrl;
            //http://onlineducation01.http//oss-cn-shenzhen.aliyuncs.com/2020/12/08/2ef38204-5f74-4da2-9ee2-e117de92b726.png

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            // 关闭OSSClient。
            ossClient.shutdown();
            return uploadUrl;
        }

    }

    @Override
    public void deleteFile(String pathUrl) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = configure.getEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = configure.getKeyid();
        String accessKeySecret = configure.getKeysecret();
        String bucketName = configure.getBucketname();

        String objectName = pathUrl.substring(pathUrl.indexOf("com") + 4, pathUrl.length());
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}