package com.bihai.eduorder.client;
/*
 *@author bihai-ui
 *@create 2021-01-07 14:06
 */

import com.bihai.common_utils.form.CourseInfoForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "service-edu")
@Component
public interface EduClient {

    //微服务获取课程信息
    @GetMapping("/serviceedu/edu-course/dto/{courseId}")
    CourseInfoForm getCourseInfoDto(@PathVariable("courseId") String courseId);
}