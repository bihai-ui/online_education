package com.bihai.serviceedu.entity.parameter;
/*
 *@author bihai-ui
 *@create 2020-12-08 21:42
 */

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bihai.serviceedu.entity.pojo.EduSubject;
import com.bihai.serviceedu.service.EduSubjectService;
import com.bihai.serviceedu.entity.vo.ReadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EasyExecleLitener extends AnalysisEventListener<ReadData> {

    @Autowired
    private EduSubjectService eduSubjectService;


    @Override
    public void invoke(ReadData readData, AnalysisContext analysisContext) {
        if(readData == null){
            try {
                throw new Exception("read为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //获取第一列的数据
        String one = readData.getCurrentSubject();
        EduSubject judge = judge(one, "0");

        if(judge == null){
            judge = new EduSubject();
            judge.setTitle(one);
            judge.setParentId("0");

            eduSubjectService.save(judge);
        }

        //获取第二列的数据
        String two = readData.getParentSubject();
        EduSubject judge1 = judge(two, judge.getId());


        if(judge1 == null){
            judge1 = new EduSubject();
            judge1.setTitle(two);
            judge1.setParentId(judge.getId());

            eduSubjectService.save(judge1);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    private EduSubject judge(String subjectName, String parentSubjectId){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",subjectName);
        wrapper.eq("parent_id",parentSubjectId);

        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }
}