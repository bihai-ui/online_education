package com.bihai.serviceedu.service;

import com.bihai.serviceedu.entity.pojo.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bihai.serviceedu.entity.vo.ResultSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-08
 */

public interface EduSubjectService extends IService<EduSubject> {

    boolean saveEduSubjectQuery(MultipartFile file);

    List<ResultSubject> nextedList();
}
