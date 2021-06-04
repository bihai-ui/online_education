package com.bihai.serviceedu.service;

import com.bihai.serviceedu.entity.pojo.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bihai.serviceedu.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> selectChapterAndVideoById(String id);

    //增
    String insertChapter(EduChapter eduChapter);

    //删
    boolean deleteChapter(String id);

    //改
    boolean updateChapter(EduChapter eduChapter);
    //查
    EduChapter getChapterById(String id);

    //通过课程id删除所有章节
    void deleteChapterByCourseId(String courseId);
}
