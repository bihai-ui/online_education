package com.bihai.serviceedu.entity.vo;
/*
 *@author bihai-ui
 *@create 2020-12-13 19:52
 */

import lombok.Data;

import java.util.List;

@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> list;


}