package com.bihai.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bihai.serviceedu.entity.pojo.EduSubject;
import com.bihai.serviceedu.entity.parameter.EasyExecleLitener;
import com.bihai.serviceedu.entity.vo.ResultSubject;
import com.bihai.serviceedu.mapper.EduSubjectMapper;
import com.bihai.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bihai.serviceedu.entity.vo.ReadData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    private EasyExecleLitener easyExecleLitener;

    @Override
    public boolean saveEduSubjectQuery(MultipartFile file) {
        try {

            EasyExcel.read(file.getInputStream(), ReadData.class,easyExecleLitener).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public List<ResultSubject> nextedList() {
        //获取一级目录
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id",0);
        List<EduSubject> lists1 = baseMapper.selectList(wrapper1);

        //获取二级目录
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id",0);
        List<EduSubject> lists2 = baseMapper.selectList(wrapper2);

        //结果集合
        ArrayList<ResultSubject> resultSubjects = new ArrayList<>();

        for(EduSubject eduSubject:lists1){
            ResultSubject subject = new ResultSubject();
            BeanUtils.copyProperties(eduSubject,subject);
            //二级目录集合
            ArrayList<ResultSubject> arrayList = new ArrayList<>();

            for(EduSubject eduSubject1:lists2){
                if(eduSubject.getId().equals(eduSubject1.getParentId())){
                    ResultSubject subject2 = new ResultSubject();
                    BeanUtils.copyProperties(eduSubject1,subject2);
                    arrayList.add(subject2);
                }
            }
            subject.setList(arrayList);

            resultSubjects.add(subject);
        }

        return resultSubjects;
    }
}
