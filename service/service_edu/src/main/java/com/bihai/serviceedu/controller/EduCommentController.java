package com.bihai.serviceedu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.common_utils.ResultData;
import com.bihai.serviceedu.entity.pojo.EduComment;
import com.bihai.serviceedu.service.EduCommentService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
@RestController
@RequestMapping("/serviceedu/edu-comment")
//@CrossOrigin
public class EduCommentController {

        @Autowired
        private EduCommentService commentService;

        @GetMapping("{page}/{limit}")
        public ResultData getComment(
                @ApiParam(name = "page", value = "当前页码", required = true)
                @PathVariable
                        Long page,

                @ApiParam(name = "limit", value = "每页记录数", required = true)
                @PathVariable
                        Long limit,

                @ApiParam(name = "courseId", value = "查询对象", required = false)
                        String courseId){

            Page<EduComment> commentPage = new Page<>(page, limit);

            Map<String, Object> map = commentService.getCommentMap(commentPage, courseId);

            return ResultData.success().data(map);
        }


    @PostMapping("comment")
    public ResultData addComment(@RequestBody EduComment comment, HttpServletRequest request){
        boolean b = commentService.addComment(comment, request);

        if(b){
            System.out.println("添加评论成功");
            return ResultData.success();
        }
        return ResultData.error();
    }

}

