package com.bihai.eduorder.service;

import com.bihai.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author bihai_ui
 * @since 2021-01-07
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberId);

}
