package com.bihai.serviceedu.mapper;

import com.bihai.serviceedu.entity.pojo.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bihai.serviceedu.entity.vo.CoursePublishVo;
import com.bihai.serviceedu.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo selectCoursePublishVoById(String id);


    CourseWebVo selectInfoWebById(String courseId);

}
