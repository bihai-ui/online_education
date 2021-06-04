package com.bihai.oss.service;
/*
 *@author bihai-ui
 *@create 2020-12-08 16:41
 */

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

        /**
         * 文件上传至阿里云
         * @param file
         * @return
         */
        String upload(MultipartFile file);

        void deleteFile(String pathUrl);

}