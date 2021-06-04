package com.bihai.serviceedu.entity.vo;
/*
 *@author bihai-ui
 *@create 2020-12-15 20:08
 */

import lombok.Data;

@Data
public class CoursePublishVo {
    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示

}