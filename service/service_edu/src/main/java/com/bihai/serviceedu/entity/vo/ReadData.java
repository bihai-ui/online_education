package com.bihai.serviceedu.entity.vo;
/*
 *@author bihai-ui
 *@create 2020-12-08 21:53
 */

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ReadData {

    @ExcelProperty(index = 0)
    private String currentSubject;

    @ExcelProperty(index = 1)
    private String parentSubject;
}