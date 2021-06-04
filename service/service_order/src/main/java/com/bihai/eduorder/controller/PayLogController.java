package com.bihai.eduorder.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.bihai.common_utils.ResultData;
import com.bihai.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author bihai_ui
 * @since 2021-01-07
 */
@RestController
@RequestMapping("/eduorder/pay-log")
//@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //生成二维码
    @GetMapping("/createNative/{orderNo}")
    public ResultData createNative(@PathVariable String orderNo) {

        Map<String,Object> map = payLogService.createNative(orderNo);

        return ResultData.success().data(map);
    }

    //查询支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public ResultData queryPayStatus(@PathVariable String orderNo) {
        //调用查询接口
        Map<String, String> map = payLogService.queryPayStatus(orderNo);

        if (map == null) {//出错
            return ResultData.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态
            payLogService.updateOrdersStatus(map);
            System.out.println("----------支付成功---------------");
            return ResultData.success().message("支付成功");
        }

        return ResultData.success().code(25000).message("支付中");
    }


}

