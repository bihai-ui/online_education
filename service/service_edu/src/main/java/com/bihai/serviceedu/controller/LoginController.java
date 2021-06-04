package com.bihai.serviceedu.controller;
/*
 *@author bihai-ui
 *@create 2020-12-06 16:16
 */

import com.bihai.common_utils.ResultData;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
//@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class LoginController {

    @PostMapping("login")
    public ResultData login(){
        HashMap<String, Object> response = new HashMap<>();
        response.put("token",1);
        ResultData data = ResultData.success().data(response);
        return data;
    }

    @GetMapping("info")
    public ResultData info(){
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("roles","admin");
        responseData.put("name","Super admin");
        responseData.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return ResultData.success().data(responseData);
    }


}