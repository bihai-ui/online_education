package com.bihai.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.serviceedu.entity.condition.CourseQuery;
import com.bihai.serviceedu.entity.pojo.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bihai.serviceedu.entity.pojo.EduTeacher;
import com.bihai.serviceedu.entity.vo.CoursePublishVo;
import com.bihai.serviceedu.entity.vo.CourseWebVo;
import com.bihai.serviceedu.form.CourseInfoForm;
import com.bihai.serviceedu.quey.FrontCourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseAndDescription(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseForm(String id);

    boolean updateCourseForm(CourseInfoForm courseInfoForm);

    CoursePublishVo getCoursePublishVoById(String id);

    //发布课程
    boolean publishCourseById(String id);

    //课程的分页查询
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    //删除课程，包含课程信息、Oss图片，点播视频
    void deleteAll(String courseId);

    //根据教师id查询讲师列表
    List<EduCourse> selectByTeacherId(String teacherId);

    //查询课程信息
    Map<String,Object> pageListWeb(Page<EduCourse> page, FrontCourseQuery query);

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    CourseWebVo selectInfoWebById(String id);

    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);


}
