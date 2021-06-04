package com.bihai.serviceunceter.mapper;

import com.bihai.serviceunceter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-28
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer getRegisterCount(String date);

}
