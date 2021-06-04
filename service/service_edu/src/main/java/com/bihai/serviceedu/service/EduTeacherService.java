package com.bihai.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.serviceedu.entity.pojo.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bihai.serviceedu.entity.condition.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-01
 */
public interface EduTeacherService extends IService<EduTeacher> {
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    //前端获取所有教师数据
    Map<String,Object> pageListWeb(Page<EduTeacher> page);
}
