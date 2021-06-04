package com.bihai.serviceedu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.common_utils.ResultData;
import com.bihai.serviceedu.entity.pojo.EduTeacher;
import com.bihai.serviceedu.entity.condition.TeacherQuery;
import com.bihai.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-01
 */
@RestController
@RequestMapping("/serviceedu/edu-teacher") //必须加/
@Api(description = "讲师管理")
@Slf4j
//@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("teachers/{page}/{limit}")
    @ApiOperation(value="查询所有的讲师")
    public ResultData getLists(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit
    )
    {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        eduTeacherService.pageQuery(teacherPage,null);
        long total = teacherPage.getTotal();



        List<EduTeacher> records = teacherPage.getRecords();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("records",records);

        return ResultData.success().data(map);
    }

    @ApiOperation(value="根据条件查询讲师")
    @PostMapping("teachers/{page}/{limit}")
    public ResultData getListsByQuery(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                                      @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                                      @ApiParam(name = "teacherQuery", value = "查询对象", required = false) @RequestBody TeacherQuery teacherQuery){

        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        eduTeacherService.pageQuery(teacherPage,teacherQuery);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("records",records);
        return ResultData.success().data(map);

    }

    @ApiOperation(value="查询所有讲师")
    @GetMapping("teachers")
    public ResultData getLists(){
        List<EduTeacher> list = eduTeacherService.list(null);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",list);
        return ResultData.success().data(map);
    }

    @ApiOperation(value="根据id查询讲师")
    @GetMapping("teacher/{id}")
    public ResultData getTeacherById(@PathVariable("id") Long id){
        EduTeacher teacher = eduTeacherService.getById(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacher",teacher);
        return ResultData.success().data(map);
    }

    @ApiOperation("添加讲师")
    @PostMapping("teacher")
    public ResultData insert(@ApiParam(name="eduTeacher",value = "插入对象") @RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
           return ResultData.success();
        }
        else{
           return ResultData.error();
        }

    }

    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("teacher/{id}")
    public ResultData deleteEduTeacherById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable("id") String id){
        boolean b = eduTeacherService.removeById(id);

        if(b){
            return ResultData.success();
        }
        else{
            return ResultData.error();
        }
    }

    @ApiOperation(value = "根据id更新讲师")
    @PutMapping("teacher/{id}")
    public ResultData updateEduTeacherById(
            @ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable("id") String id,
            @ApiParam(name = "eduTeacher", value = "更新的讲师数据", required = true) @RequestBody EduTeacher eduTeacher
    ){
        eduTeacher.setId(id);
        boolean update = eduTeacherService.updateById(eduTeacher);
        if(update)
            return ResultData.success();
        else
            return ResultData.error();

    }







}

