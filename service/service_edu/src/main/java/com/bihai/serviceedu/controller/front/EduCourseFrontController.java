package com.bihai.serviceedu.controller.front;
/*
 *@author bihai-ui
 *@create 2021-01-03 18:26
 */


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.common_utils.JWTUtils;
import com.bihai.common_utils.ResultData;
import com.bihai.serviceedu.client.OrderClient;
import com.bihai.serviceedu.entity.pojo.EduChapter;
import com.bihai.serviceedu.entity.pojo.EduCourse;
import com.bihai.serviceedu.entity.pojo.EduTeacher;
import com.bihai.serviceedu.entity.vo.ChapterVo;
import com.bihai.serviceedu.entity.vo.CourseWebVo;
import com.bihai.serviceedu.quey.FrontCourseQuery;
import com.bihai.serviceedu.service.EduChapterService;
import com.bihai.serviceedu.service.EduCourseService;
import com.bihai.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "前端课程管理")
@RestController
@RequestMapping("/serviceedu/educourse/front")
//@CrossOrigin
public class EduCourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;


    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "{page}/{limit}")
    public ResultData pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false)FrontCourseQuery query){
        Page<EduCourse> pageParam = new Page<>(page, limit);

        Map<String, Object> map = courseService.pageListWeb(pageParam, query);

        return  ResultData.success().data(map);
    }



    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "{courseId}")
    public ResultData getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId, HttpServletRequest request){

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVos = chapterService.selectChapterAndVideoById(courseId);

        //查询课程状态
        boolean flag = orderClient.isBuyCourse(JWTUtils.getMemberIdByJwtToken(request), courseId);

        HashMap<String, Object> map = new HashMap<>();
        map.put("course",courseWebVo);
        map.put("chapterVoList",chapterVos);
        map.put("flag",flag);

        return ResultData.success().data(map);
    }




}