package com.bihai.serviceedu.controller;


import com.bihai.common_utils.ResultData;
import com.bihai.serviceedu.entity.vo.ResultSubject;
import com.bihai.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/serviceedu/edu-subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("subject")
    public ResultData ExcelToSubject(MultipartFile file){
        boolean b = eduSubjectService.saveEduSubjectQuery(file);
        if(b){
            return ResultData.success();
        }
        else{
            return ResultData.error();
        }
    }

    @GetMapping("subjects")
    public ResultData selectToTree(){
        List<ResultSubject> list = eduSubjectService.nextedList();

        if(list != null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("list",list);
            return ResultData.success().data(map);
        }

        return ResultData.error();
    }

}

