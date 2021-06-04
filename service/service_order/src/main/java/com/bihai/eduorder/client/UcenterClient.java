package com.bihai.eduorder.client;
/*
 *@author bihai-ui
 *@create 2021-01-07 14:01
 */

import com.bihai.common_utils.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {

    @GetMapping("/serviceunceter/ucenter-member/getInfoUc/{id}")
    UcenterMember getInfo(@PathVariable("id") String memberId);
}