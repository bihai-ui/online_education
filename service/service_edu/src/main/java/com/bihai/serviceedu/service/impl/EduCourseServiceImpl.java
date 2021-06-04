package com.bihai.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bihai.serviceedu.client.OssClient;
import com.bihai.serviceedu.client.VideoClient;
import com.bihai.serviceedu.entity.condition.CourseQuery;
import com.bihai.serviceedu.entity.pojo.EduChapter;
import com.bihai.serviceedu.entity.pojo.EduCourse;
import com.bihai.serviceedu.entity.pojo.EduCourseDescription;
import com.bihai.serviceedu.entity.pojo.EduVideo;
import com.bihai.serviceedu.entity.vo.CoursePublishVo;
import com.bihai.serviceedu.entity.vo.CourseWebVo;
import com.bihai.serviceedu.form.CourseInfoForm;
import com.bihai.serviceedu.mapper.EduChapterMapper;
import com.bihai.serviceedu.mapper.EduCourseDescriptionMapper;
import com.bihai.serviceedu.mapper.EduCourseMapper;
import com.bihai.serviceedu.mapper.EduVideoMapper;
import com.bihai.serviceedu.quey.FrontCourseQuery;
import com.bihai.serviceedu.service.EduChapterService;
import com.bihai.serviceedu.service.EduCourseDescriptionService;
import com.bihai.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bihai.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired(required = false)
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveCourseAndDescription(CourseInfoForm courseInfoForm) {
        //存储课程信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,course);
        baseMapper.insert(course);



        //获取课程id
        String courseId = course.getId();

        //存储课程简介
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoForm.getDescription());
        description.setId(courseId);
        eduCourseDescriptionMapper.insert(description);

        return courseId;
    }

    //获取课程信息：包括描述，封面
    @Override
    public CourseInfoForm getCourseForm(String id) {
        //获取edu_course信息
        QueryWrapper<EduCourse> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("id",id);
        EduCourse eduCourse = baseMapper.selectOne(wrapper1);

        //获取edu_course_description
        QueryWrapper<EduCourseDescription> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("id",id);
        EduCourseDescription courseDescription = eduCourseDescriptionMapper.selectOne(wrapper2);


        CourseInfoForm form = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,form);
        BeanUtils.copyProperties(courseDescription,form);
        return form;
    }

    //更新课程
    @Override
    public boolean updateCourseForm(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);

        int i = baseMapper.updateById(eduCourse);

        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoForm,description);

        int i1 = eduCourseDescriptionMapper.updateById(description);

        if(i >= 0 && i1 >= 0){
            return true;
        }

           return false;

    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourseById(String id) {

        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        Integer count = baseMapper.updateById(course);
        return (count != null && count > 0);
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam, queryWrapper);

    }

    @Override
    public void deleteAll(String courseId) {
        //删除小节信息
        eduVideoService.deleteVideoListByCourseId(courseId);

        //删除章节信息
        eduChapterService.deleteChapterByCourseId(courseId);

        //删除描述信息
        eduChapterService.removeById(courseId);



        //删除课程信息
        baseMapper.deleteById(courseId);
    }

    @Override
    public List<EduCourse> selectByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);

        List<EduCourse> eduCourses = baseMapper.selectList(wrapper);
        return eduCourses;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam, FrontCourseQuery query) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(query.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", query.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(query.getSubjectId())) {
            queryWrapper.eq("subject_id", query.getSubjectId());
        }

        if (!StringUtils.isEmpty(query.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(query.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(query.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, queryWrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;

    }

    @Override
    public CourseWebVo selectInfoWebById(String courseId) {
        this.updatePageViewCount(courseId);
        return baseMapper.selectInfoWebById(courseId);
    }

    @Override
    public void updatePageViewCount(String courseId) {
        EduCourse course = baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }

}
