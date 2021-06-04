package com.bihai.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bihai.common_utils.JWTUtils;
import com.bihai.common_utils.ResultData;
import com.bihai.eduorder.entity.Order;
import com.bihai.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author bihai_ui
 * @since 2021-01-07
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;


    //生成订单
    @PostMapping("order/{courseId}")
    public ResultData createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request){
        String memberId = JWTUtils.getMemberIdByJwtToken(request);

        String orderNo = orderService.saveOrder(courseId,memberId);

        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId",orderNo);
        return ResultData.success().data(map);
    }

    //获取订单
    @GetMapping("order/{orderId}")
    public ResultData getOrderById(@PathVariable("orderId") String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);

        HashMap<String, Object> map = new HashMap<>();
        map.put("order",order);

        return ResultData.success().data(map);
    }

    //根据用户id与课程id查询
    @GetMapping("isBuyCourse/{memberid}/{id}")
    public boolean isBuyCourse(@PathVariable String memberid,
                               @PathVariable String id) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<Order>().eq("member_id", memberid).eq("course_id", id).eq("status", 1));
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }



}

