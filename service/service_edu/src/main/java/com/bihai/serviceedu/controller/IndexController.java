package com.bihai.serviceedu.controller;
/*
 *@author bihai-ui
 *@create 2020-12-25 13:02
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.bihai.common_utils.ResultData;
import com.bihai.serviceedu.entity.pojo.EduCourse;
import com.bihai.serviceedu.entity.pojo.EduTeacher;
import com.bihai.serviceedu.service.EduCourseService;
import com.bihai.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/serviceedu/index")
//@CrossOrigin
public class IndexController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("index")
    public ResultData index(){
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);

        //查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        HashMap<String, Object> map = new HashMap<>();
        map.put("eduList",eduList);
        map.put("teacherList",teacherList);
        return ResultData.success().data(map);
    }


}