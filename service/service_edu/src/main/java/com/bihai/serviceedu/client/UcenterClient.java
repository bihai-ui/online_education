package com.bihai.serviceedu.client;
/*
 *@author bihai-ui
 *@create 2021-01-05 19:29
 */


import com.bihai.common_utils.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter", fallback = UcenterFileDegradeFeginClient.class)
@Component
public interface UcenterClient {

    @GetMapping("/serviceunceter/ucenter-member/member/{id}")
    ResultData getMember(@PathVariable(name = "id") String memberId);

}