package com.bihai.serviceedu.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.common_utils.ResultData;
import com.bihai.serviceedu.entity.condition.CourseQuery;
import com.bihai.serviceedu.entity.pojo.EduCourse;
import com.bihai.serviceedu.entity.pojo.EduTeacher;
import com.bihai.serviceedu.entity.vo.CoursePublishVo;
import com.bihai.serviceedu.form.CourseInfoForm;
import com.bihai.serviceedu.service.EduCourseService;
import com.bihai.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
@Api(description="课程管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/serviceedu/edu-course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "新增课程")
    @PostMapping("save-course-info")
    public ResultData saveCourseInfo(
        @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
        @RequestBody CourseInfoForm courseInfoForm){

        String courseId = courseService.saveCourseAndDescription(courseInfoForm);

        if(!StringUtils.isEmpty(courseId)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("courseId",courseId);
            return ResultData.success().data(map);
        }else{
            return ResultData.error().message("信息错误");
        }
    }

    @ApiOperation(value = "通过id获取课程信息")
    @GetMapping("courseForm/{id}")
    public ResultData getCourseById(
            @ApiParam(name = "id", value = "课程id", required = true)
            @PathVariable(name = "id") String id){

        CourseInfoForm courseForm = courseService.getCourseForm(id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("course",courseForm);
        return ResultData.success().data(map);
    }


    @ApiOperation(value = "通过id修改课程信息")
    @PostMapping("courseForm/{id}")
    public ResultData saveCourseById(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm){
        boolean flag = courseService.updateCourseForm(courseInfoForm);

        if(flag){
            return ResultData.success();
        }
        return ResultData.error();
    }


    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public ResultData getCoursePublishVoById(
        @ApiParam(name = "id", value = "课程ID", required = true)
        @PathVariable String id){

        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("course",courseInfoForm);
        return ResultData.success().data(map);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish-course/{id}")
    public ResultData publishCourseById(
        @ApiParam(name = "id", value = "课程ID", required = true)
        @PathVariable String id){
        courseService.publishCourseById(id);
        return ResultData.success();
    }


    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public ResultData pageQuery(
        @ApiParam(name = "page", value = "当前页码", required = true)
        @PathVariable Long page,

        @ApiParam(name = "limit", value = "每页记录数", required = true)
        @PathVariable Long limit,

        @ApiParam(name = "courseQuery", value = "查询对象", required = false)
        CourseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<>(page, limit);

        courseService.pageQuery(pageParam, courseQuery);

        List<EduCourse> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("list",records);
        return  ResultData.success().data(map);
    }

    @ApiOperation(value = "删除课程所有信息")
    @DeleteMapping("deleteAll/{courseId}")
    public ResultData deleteAllMessage(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable("courseId") String courseId){
        courseService.deleteAll(courseId);
        return ResultData.success().message("删除成功");
    }

    @GetMapping("getCourseList/{teacherId}")
    public ResultData getListByCourseId(
            @ApiParam(name = "teacherId", value = "教师id" ,required = true)
            @PathVariable("teacherId") String teacherId){
        List<EduCourse> courses = courseService.selectByTeacherId(teacherId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courses",courses);
        return ResultData.success().data(map);
    }


    //微服务获取课程信息
    @GetMapping("dto/{courseId}")
    public com.bihai.common_utils.form.CourseInfoForm getCourseInfoDto(@PathVariable("courseId") String courseId){
        CourseInfoForm courseForm = courseService.getCourseForm(courseId);
        EduTeacher teacher = teacherService.getById(courseForm.getTeacherId());

        com.bihai.common_utils.form.CourseInfoForm infoForm = new com.bihai.common_utils.form.CourseInfoForm();
        BeanUtils.copyProperties(courseForm,infoForm);
        infoForm.setName(teacher.getName());
        return infoForm;
    }


}

