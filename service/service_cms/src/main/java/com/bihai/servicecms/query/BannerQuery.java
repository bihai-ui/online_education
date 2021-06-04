package com.bihai.servicecms.query;
/*
 *@author bihai-ui
 *@create 2020-12-25 12:25
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
@Data
public class BannerQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "banner名称")
    private String title;

}