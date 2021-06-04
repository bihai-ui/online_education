package com.bihai.serviceedu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.common_utils.JWTUtils;
import com.bihai.common_utils.ResultData;
import com.bihai.common_utils.exception.OlineEducationException;
import com.bihai.common_utils.vo.UcenterMember;
import com.bihai.serviceedu.client.UcenterClient;
import com.bihai.serviceedu.entity.pojo.EduComment;
import com.bihai.serviceedu.mapper.EduCommentMapper;
import com.bihai.serviceedu.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public Map<String, Object> getCommentMap(Page<EduComment> pageParam, String courseId) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);

        System.out.println(courseId);

        baseMapper.selectMapsPage(pageParam,wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("items", pageParam.getRecords());
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam);
        map.put("hasPrevious", pageParam.hasPrevious());


        return map;
    }

    @Override
    public boolean addComment(EduComment comment, HttpServletRequest request) {
        String memberId = JWTUtils.getMemberIdByJwtToken(request);

        if(StringUtils.isEmpty(memberId)) {
            throw new OlineEducationException(28004,"请登录");
        }
        comment.setMemberId(memberId);//会员id


        ResultData resultData = ucenterClient.getMember(memberId);

        Object object = resultData.getData().get("member");

        // 将list中的数据转成json字符串
        String jsonObject= JSON.toJSONString(object);
        //将json转成需要的对象
        UcenterMember member = JSONObject.parseObject(jsonObject, UcenterMember.class);

        comment.setNickname(member.getNickname());//昵称
        comment.setAvatar(member.getAvatar());//头像

        baseMapper.insert(comment);

        return true;
    }
}
