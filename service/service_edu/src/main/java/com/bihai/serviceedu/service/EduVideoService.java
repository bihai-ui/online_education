package com.bihai.serviceedu.service;

import com.bihai.serviceedu.entity.pojo.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
public interface EduVideoService extends IService<EduVideo> {

   void deleteVideo(String id);

   void deleteVideoListByCourseId(String courseId);
}
