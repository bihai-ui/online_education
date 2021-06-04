package com.bihai.common_utils.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-28
 */
@Data
public class UcenterMember implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;


    private String openid;


    private String mobile;

    private String password;

    private String nickname;

    private Integer sex;

    private Integer age;

    private String avatar;

    private String sign;


    private Integer isDisabled;


    private Integer isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

}
