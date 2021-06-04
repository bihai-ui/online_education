package com.bihai.serviceedu.controller;


import com.bihai.common_utils.ResultData;
import com.bihai.serviceedu.entity.pojo.EduChapter;
import com.bihai.serviceedu.entity.vo.ChapterVo;
import com.bihai.serviceedu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
@RestController
@RequestMapping("/serviceedu/edu-chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("chapterAndVideo/{id}")
    public ResultData getChapterAndVideo(@PathVariable("id") String id){
        List<ChapterVo> chapterVos = eduChapterService.selectChapterAndVideoById(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("chapters",chapterVos);
        return ResultData.success().data(map);
    }

    @GetMapping("chapter/{id}")
    public ResultData getChapter(@PathVariable("id") String id){
        EduChapter chapter = eduChapterService.getChapterById(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("chapter",chapter);

        return ResultData.success().data(map);
    }

    @PostMapping("chapter")
    public ResultData updateChapter(@RequestBody EduChapter eduChapter){
        boolean b = eduChapterService.updateChapter(eduChapter);
        if(b){
            return ResultData.success();
        }
        return ResultData.error();
    }

    @PutMapping("chapter")
    public ResultData insertChapter(@RequestBody EduChapter eduChapter){
        String id = eduChapterService.insertChapter(eduChapter);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        return ResultData.success().data(map);
    }

    @DeleteMapping("chapter/{id}")
    public ResultData deleteChapterByid(@PathVariable("id") String id){
        boolean b = eduChapterService.deleteChapter(id);
        if(b){
            return ResultData.success();
        }
        return ResultData.error();
    }





}

