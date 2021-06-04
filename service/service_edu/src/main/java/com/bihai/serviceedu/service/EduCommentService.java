package com.bihai.serviceedu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.serviceedu.entity.pojo.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
public interface EduCommentService extends IService<EduComment> {

    //获取课程的评论列表
    Map<String,Object> getCommentMap(Page<EduComment> page, String courseId);


    //添加课程评论
    boolean addComment(EduComment comment, HttpServletRequest request);

}
