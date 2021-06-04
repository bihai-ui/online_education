package com.bihai.statistics_daily.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bihai.common_utils.ResultData;
import com.bihai.statistics_daily.client.UcenterClient;
import com.bihai.statistics_daily.entity.StatisticsDaily;
import com.bihai.statistics_daily.mapper.StatisticsDailyMapper;
import com.bihai.statistics_daily.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author bihai_ui
 * @since 2021-01-08
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void createStatisticsByDay(String day) {
        //删除数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        ResultData resultData = ucenterClient.dailyregister(day);
        int registerCount = (int) resultData.getData().get("registerCount");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        StatisticsDaily daily = new StatisticsDaily();
        daily.setDateCalculated(day);
        daily.setCourseNum(courseNum);
        daily.setLoginNum(loginNum);
        daily.setRegisterNum(registerCount);
        daily.setVideoViewNum(videoViewNum);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String beginTime, String endTime, String type) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",beginTime,endTime);
        wrapper.select("date_calculated",type);

        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> dataList = new ArrayList<>();



        dailyList.stream().forEach(e ->{
            dateList.add(e.getDateCalculated());

            switch (type) {
                case "register_num":
                    dataList.add(e.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(e.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(e.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(e.getCourseNum());
                    break;
                default:
                    break;
            }
        });

        map.put("dataList",dataList);
        map.put("dateList",dateList);

        return map;
    }
}
