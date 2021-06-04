package com.bihai.eduorder.service;

import com.bihai.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author bihai_ui
 * @since 2021-01-07
 */
public interface PayLogService extends IService<PayLog> {

    Map<String,Object> createNative(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrdersStatus(Map<String, String> map);
}
