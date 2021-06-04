package com.bihai.serviceunceter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bihai.common_utils.JWTUtils;
import com.bihai.common_utils.MD5;
import com.bihai.common_utils.exception.OlineEducationException;
import com.bihai.serviceunceter.entity.UcenterMember;
import com.bihai.serviceunceter.mapper.UcenterMemberMapper;
import com.bihai.serviceunceter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bihai.serviceunceter.vo.LoginVo;
import com.bihai.serviceunceter.vo.RegisterVo;
import com.bihai.serviceunceter.vo.info.LoginInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-28
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public String login(LoginVo loginVo) {
        System.out.println(loginVo);

        String password = loginVo.getPassword();
        String mobile = loginVo.getMobile();

        if(password.isEmpty()){
            throw new OlineEducationException(20001,"账号或密码为空");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);
        if(Objects.isNull(member)){
            throw new OlineEducationException(20001,"手机号输入错误");
        }

        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("password", MD5.encrypt(password));
        queryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(queryWrapper);

        if(count != 1){
            throw new OlineEducationException(20001,"密码错误");
        }

        return JWTUtils.getJwtToken(member.getId(),member.getNickname());
    }

    @Override
    public LoginInfoVo getLoginInfo(String memberId) {
        UcenterMember member = baseMapper.selectById(memberId);
        System.out.println("------------member" + member);
        LoginInfoVo infoVo = new LoginInfoVo();
        BeanUtils.copyProperties(member,infoVo);
        return infoVo;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if(StringUtils.isEmpty(mobile) ||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            throw new OlineEducationException(20001,"您的账号、手机号或密码未输入");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count != 0){
            throw new OlineEducationException(20001,"手机号已经注册");
        }

        //加密
        registerVo.setPassword(MD5.encrypt(password));

        UcenterMember member = new UcenterMember();
        BeanUtils.copyProperties(registerVo,member);

        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getByOpenid(String openId) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openId);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public com.bihai.common_utils.vo.UcenterMember getmemberById(String memberId) {
        UcenterMember member = baseMapper.selectById(memberId);
        com.bihai.common_utils.vo.UcenterMember ucenterMember = new com.bihai.common_utils.vo.UcenterMember();

        BeanUtils.copyProperties(member,ucenterMember);

        return ucenterMember;
    }

    @Override
    public int getDailyRegister(String date) {
        int count = baseMapper.getRegisterCount(date);
        return count;
    }
}
