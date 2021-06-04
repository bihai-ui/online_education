package com.bihai.serviceedu.controller.front;
/*
 *@author bihai-ui
 *@create 2021-01-03 19:05
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.common_utils.ResultData;
import com.bihai.serviceedu.entity.pojo.EduCourse;
import com.bihai.serviceedu.entity.pojo.EduTeacher;
import com.bihai.serviceedu.service.EduCourseService;
import com.bihai.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "前端教师管理")
@RestController
@RequestMapping("/serviceedu/eduteacher/front")
//@CrossOrigin
public class EduTeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService courseService;

    @GetMapping("{page}/{limit}")
    public ResultData getAllListPages(
            @PathVariable("page") Long page,
            @PathVariable("limit") Long limit){

        Page<EduTeacher> teacherPage = new Page<>();

        Map<String, Object> map = eduTeacherService.pageListWeb(teacherPage);

        return ResultData.success().data(map);
    }

    @GetMapping("course/{teacherId}")
    public ResultData getListByCourseId(
            @ApiParam(name = "teacherId", value = "教师id" ,required = true)
            @PathVariable("teacherId") String teacherId){

        //获取教师信息
        EduTeacher teacher = eduTeacherService.getById(teacherId);
        //获取课程信息
        List<EduCourse> courses = courseService.selectByTeacherId(teacherId);

        HashMap<String, Object> map = new HashMap<>();
        map.put("courses",courses);
        map.put("teacher",teacher);

        return ResultData.success().data(map);
    }






}