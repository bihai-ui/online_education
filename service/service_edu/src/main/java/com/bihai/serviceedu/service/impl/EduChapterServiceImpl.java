package com.bihai.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bihai.serviceedu.entity.pojo.EduChapter;
import com.bihai.serviceedu.entity.pojo.EduVideo;
import com.bihai.serviceedu.entity.vo.ChapterVo;
import com.bihai.serviceedu.entity.vo.VideoVo;
import com.bihai.serviceedu.exception.DeleteException;
import com.bihai.serviceedu.mapper.EduChapterMapper;
import com.bihai.serviceedu.mapper.EduVideoMapper;
import com.bihai.serviceedu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired(required = false)
    private EduVideoMapper eduVideoMapper;

    @Override
    public List<ChapterVo> selectChapterAndVideoById(String id) {
        //查询所有的目录
        QueryWrapper<EduChapter> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id",id);
        List<EduChapter> chapterList = baseMapper.selectList(wrapper1);


        //查询所有的小节
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id",id);
        List<EduVideo> eduVideoList = eduVideoMapper.selectList(wrapper2);

        ArrayList<ChapterVo> chapterVos = new ArrayList<>();

        for(EduChapter chapter:chapterList){

            ArrayList<VideoVo> videoVos = new ArrayList<>();

            for(EduVideo video:eduVideoList){
                if(chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVos.add(videoVo);
                }

            }

            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            chapterVo.setList(videoVos);

            chapterVos.add(chapterVo);
        }


        return chapterVos;
    }

    @Override
    public String insertChapter(EduChapter eduChapter) {
        baseMapper.insert(eduChapter);

        return eduChapter.getId();
    }

    @Override
    public boolean deleteChapter(String id) {
        //查询小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        List<EduVideo> eduVideos = eduVideoMapper.selectList(wrapper);

        if(eduVideos == null){
            throw new DeleteException("不能间接删除小节");
        }

        int i = baseMapper.deleteById(id);
        return i > 0 ? true:false;
    }

    @Override
    public boolean updateChapter(EduChapter eduChapter) {
        int i = baseMapper.updateById(eduChapter);
        return i > 0 ? true:false;
    }

    @Override
    public EduChapter getChapterById(String id) {
        EduChapter eduChapter = baseMapper.selectById(id);

        return eduChapter;
    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
