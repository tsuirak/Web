package com.tsuiraku.statisticsservice.controller;


import com.tsuiraku.statisticsservice.service.StatisticsDailyService;
import com.tsuiraku.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author tsuiraku
 * @since 2021-12-06
 */
@RestController
@RequestMapping("/edusta/")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/6 上午10:12
     *  @Description: 统计某一天注册人数
     */
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day) {
        staService.registerCount(day);

        return R.success();
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/7 下午1:49
     *  @Description: 返回查询数据
     */
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin,
                      @PathVariable String end) {
        Map<String, Object> map = staService.getShowData(type, begin, end);

        return R.success().data(map);
    }

}

