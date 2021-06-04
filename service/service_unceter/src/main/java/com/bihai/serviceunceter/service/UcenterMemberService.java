package com.bihai.serviceunceter.service;

import com.bihai.serviceunceter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bihai.serviceunceter.vo.LoginVo;
import com.bihai.serviceunceter.vo.RegisterVo;
import com.bihai.serviceunceter.vo.info.LoginInfoVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //登录
    String login(LoginVo loginVo);

    //获取用户信息
    LoginInfoVo getLoginInfo(String memberId);

    //注册
    void register(RegisterVo registerVo);

    //获取用户信息
    UcenterMember getByOpenid(String openId);


    //通过memberid获取用户信息
    com.bihai.common_utils.vo.UcenterMember getmemberById(String memberId);

    //每日注册人数
    int getDailyRegister(String date);

}
