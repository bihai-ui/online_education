package com.bihai.serviceunceter.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.bihai.common_utils.JWTUtils;
import com.bihai.common_utils.ResultData;
import com.bihai.common_utils.exception.OlineEducationException;
import com.bihai.common_utils.vo.UcenterMember;
import com.bihai.serviceunceter.service.UcenterMemberService;
import com.bihai.serviceunceter.vo.LoginVo;
import com.bihai.serviceunceter.vo.RegisterVo;
import com.bihai.serviceunceter.vo.info.LoginInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-28
 */
@RestController
@RequestMapping("/serviceunceter/ucenter-member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService service;

    @PostMapping("login")
    public ResultData login(@RequestBody LoginVo loginVo){
        String token = service.login(loginVo);
        HashMap<String, Object> map = new HashMap<>();
        map.put("token",token);

        return ResultData.success().data(map);
    }


    @PostMapping("register")
    public ResultData register(@RequestBody RegisterVo registerVo){
        try {
            service.register(registerVo);
        }
        catch (Exception e){

        }
        return ResultData.success();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("auth/getLoginInfo")
    public ResultData getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JWTUtils.getMemberIdByJwtToken(request);

            System.out.println("-----memberId" + memberId);
            LoginInfoVo loginInfo = service.getLoginInfo(memberId);
            HashMap<String, Object> map = new HashMap<>();
            map.put("loginInfo",loginInfo);

            return ResultData.success().data(map);
        }catch (Exception e){
            throw new OlineEducationException(20001,"error");
        }
    }


    @GetMapping("member/{id}")
    public ResultData getMember(@PathVariable("id") String memberId){
        UcenterMember member = service.getmemberById(memberId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("member",member);

        System.out.println("ucenter:"+member);

        return ResultData.success().data(map);
    }


    @GetMapping("getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable("id") String memberId){
        com.bihai.serviceunceter.entity.UcenterMember ucenterMember = service.getById(memberId);
        UcenterMember member = new UcenterMember();

        BeanUtils.copyProperties(ucenterMember,member);
        return member;
    }


    //每日注册数
    @GetMapping("countregister/{day}")
    public ResultData dailyregister(@PathVariable(name = "day") String date){
        int count = service.getDailyRegister(date);
        HashMap<String, Object> map = new HashMap<>();
        map.put("registerCount",count);
        return ResultData.success().data(map);
    }

}

