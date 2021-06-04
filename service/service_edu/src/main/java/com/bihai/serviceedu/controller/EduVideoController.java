package com.bihai.serviceedu.controller;


import com.bihai.common_utils.ResultData;
import com.bihai.serviceedu.entity.pojo.EduVideo;
import com.bihai.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author bihai_ui
 * @since 2020-12-11
 */
@RestController
@RequestMapping("/serviceedu/edu-video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    //增
    @PutMapping("video")
    public ResultData insertVideo(@RequestBody EduVideo eduVideo){
        boolean flag = eduVideoService.save(eduVideo);
        if(flag){
            return ResultData.success();
        }
        return ResultData.error();
    }

    // 改
    @PostMapping("video")
    public ResultData updateVideo(@RequestBody EduVideo eduVideo){
        boolean flag = eduVideoService.updateById(eduVideo);
        if(flag){
            return ResultData.success();
        }
        return ResultData.error();
    }

    //删
    @DeleteMapping("video/{id}")
    public ResultData deleteVideo(@PathVariable("id") String id){
        eduVideoService.deleteVideo(id);
        return ResultData.success();
    }

    //查
    @GetMapping("video/{id}")
    public ResultData selectById(@PathVariable("id") String id){
        EduVideo video = eduVideoService.getById(id);
        if(video != null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("video",video);
            return ResultData.success().data(map);
        }
        return ResultData.error();
    }


}

