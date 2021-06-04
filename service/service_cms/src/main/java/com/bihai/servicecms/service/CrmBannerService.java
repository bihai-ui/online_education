package com.bihai.servicecms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.servicecms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bihai.servicecms.query.BannerQuery;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-25
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectIndexList();

    void pageBanner(Page<CrmBanner> page, BannerQuery bannerQuery);

    CrmBanner getBannerById(String bannerId);

    void saveBanner(CrmBanner crmBanner);

    void updateBannerById(CrmBanner crmBanner);

    void removeBannerById(String bannerId);

}
