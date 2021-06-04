package com.bihai.serviceedu.client;
/*
 *@author bihai-ui
 *@create 2021-01-07 18:58
 */

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-order")
public interface OrderClient {

    //根据用户id与课程id查询
    @GetMapping("/eduorder/order/isBuyCourse/{memberid}/{id}")
    boolean isBuyCourse(@PathVariable(name = "memberid") String memberid, @PathVariable(name ="id") String id);

}
