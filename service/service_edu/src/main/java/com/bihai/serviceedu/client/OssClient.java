package com.bihai.serviceedu.client;
/*
 *@author bihai-ui
 *@create 2020-12-19 12:48
 */

import com.bihai.common_utils.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-oss")
@Component
public interface OssClient {

    @DeleteMapping("/oss/images/delete")
    ResultData delete(@RequestParam(name = "filePathUrl") String filePathUrl);

}