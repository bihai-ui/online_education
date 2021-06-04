package com.bihai.serviceedu.entity.vo;
/*
 *@author bihai-ui
 *@create 2020-12-10 22:09
 */

import lombok.Data;

import java.util.List;

@Data
public class ResultSubject {

    private String title;
    private String id;
    private String parentId;

    private List<ResultSubject> list;


}