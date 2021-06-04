package com.bihai.servicecms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.common_utils.ResultData;
import com.bihai.servicecms.entity.CrmBanner;
import com.bihai.servicecms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-25
 */
@RestController
@RequestMapping("/servicecms/crm-banner")
//@CrossOrigin
public class CrmBannerController {
    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("首页数据查询")
    @GetMapping("{page}/{limit}")
    public ResultData index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable("page")  Long page,
            @ApiParam(name = "limit", value = "每页条数", required = true)
            @PathVariable("limit") Long limit){

        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        crmBannerService.pageBanner(bannerPage,null);
        long total = bannerPage.getTotal();
        List<CrmBanner> bannerList = bannerPage.getRecords();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("bannerList",bannerList);
        return ResultData.success().data(map);
    }

    @ApiOperation("通过bannerId查询数据")
    @GetMapping("{id}")
    public ResultData get(@PathVariable("id") String bannerId){
        CrmBanner banner = crmBannerService.getBannerById(bannerId);

        HashMap<String, Object> map = new HashMap<>();
        map.put("banner",banner);
        return ResultData.success().data(map);
    }

    @ApiOperation("插入一条CrmBanner数据")
    @PostMapping("save")
    public ResultData save(@RequestBody CrmBanner crmBanner){
        crmBannerService.saveBanner(crmBanner);

        return ResultData.success();
    }


    @ApiOperation("通过bannerId删除一条数据")
    @DeleteMapping("delete/{id}")
    public ResultData delete(@PathVariable("id") String bannerId){
        crmBannerService.removeBannerById(bannerId);

        return ResultData.success();
    }

    @ApiOperation("通过id更新数据")
    @PutMapping("update")
    public ResultData update(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateBannerById(crmBanner);

        return ResultData.success();
    }



}

