package com.bihai.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bihai.common_utils.ResultData;
import com.bihai.common_utils.exception.OlineEducationException;
import com.bihai.serviceedu.client.VideoClient;
import com.bihai.serviceedu.entity.pojo.EduVideo;
import com.bihai.serviceedu.mapper.EduVideoMapper;
import com.bihai.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VideoClient videoClient;

    @Override
    public void deleteVideo(String id) {
        //删除阿里云视频
        EduVideo video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();

        ResultData data = videoClient.removeVideo(videoSourceId);

        if(!data.getSuccess()){
            throw new OlineEducationException(20001,"阿里云视频删除失败");
        }

        //删除数据库信息
        int i = baseMapper.deleteById(id);

        if(i <= 0){
            throw new OlineEducationException(20001,"数据库数据删除失败");
        }
    }

    @Override
    public void deleteVideoListByCourseId(String courseId) {
        //获取所有视频的id,并删除
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideos = baseMapper.selectList(videoWrapper);
        List<String> idList = eduVideos.stream().map(e -> e.getVideoSourceId()).collect(Collectors.toList());

        if(idList.size() > 0) {
            videoClient.removeVideoList(idList);
        }

        //删除数据库信息
        QueryWrapper<EduVideo> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("course_id",courseId);
        baseMapper.delete(deleteWrapper);

    }


}
