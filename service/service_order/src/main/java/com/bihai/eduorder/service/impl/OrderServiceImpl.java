package com.bihai.eduorder.service.impl;

import com.bihai.common_utils.form.CourseInfoForm;
import com.bihai.common_utils.vo.UcenterMember;
import com.bihai.eduorder.client.EduClient;
import com.bihai.eduorder.client.UcenterClient;
import com.bihai.eduorder.entity.Order;
import com.bihai.eduorder.mapper.OrderMapper;
import com.bihai.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bihai.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author bihai_ui
 * @since 2021-01-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String saveOrder(String courseId, String memberId) {
        CourseInfoForm course = eduClient.getCourseInfoDto(courseId);

        UcenterMember member = ucenterClient.getInfo(memberId);

        Order order = new Order();

        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(course.getTitle());
        order.setCourseCover(course.getCover());
        order.setTeacherName(course.getName());

        order.setMemberId(memberId);
        order.setNickname(member.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
