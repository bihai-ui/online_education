package com.bihai.servicecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bihai.servicecms.entity.CrmBanner;
import com.bihai.servicecms.mapper.CrmBannerMapper;
import com.bihai.servicecms.query.BannerQuery;
import com.bihai.servicecms.service.CrmBannerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-25
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'indexList'")
    @Override
    public List<CrmBanner> selectIndexList() {
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }

    @Override
    public void pageBanner(Page<CrmBanner> page, BannerQuery bannerQuery) {
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        if(bannerQuery == null){
            baseMapper.selectPage(page,queryWrapper);
            return;
        }

        String title = bannerQuery.getTitle();

        if(title != null){
            queryWrapper.eq("title",title);
        }

        baseMapper.selectPage(page,queryWrapper);

    }

    @Override
    public CrmBanner getBannerById(String bannerId) {
        CrmBanner crmBanner = baseMapper.selectById(bannerId);
        return crmBanner;
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void saveBanner(CrmBanner crmBanner) {
        baseMapper.insert(crmBanner);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void updateBannerById(CrmBanner crmBanner) {
        System.out.println("bannerId" + crmBanner.getId());
        baseMapper.updateById(crmBanner);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void removeBannerById(String bannerId) {
        baseMapper.deleteById(bannerId);
    }
}
