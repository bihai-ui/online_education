package com.bihai.servicecms.controller;
/*
 *@author bihai-ui
 *@create 2020-12-25 12:52
 */

import com.baomidou.mybatisplus.extension.api.R;
import com.bihai.common_utils.ResultData;
import com.bihai.servicecms.entity.CrmBanner;
import com.bihai.servicecms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/educms/banner")
@Api(description = "网站首页Banner列表")
//@CrossOrigin 跨域
public class BannerApiController {


    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public ResultData index() {
        List<CrmBanner> crmBanners = bannerService.selectIndexList();
        HashMap<String, Object> map = new HashMap<>();
        map.put("banners",crmBanners);
        return ResultData.success().data(map);
    }
}