package com.tsuiraku.statisticsservice.utils;

import com.tsuiraku.statisticsservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *  @Author: tsuiraku
 *  @Date: 2021/12/7 上午10:33
 *  @Description: 自动化生成每日注册数据
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService staService;

    /* 每天凌晨1点执行前一天数据进行数据查询添加 */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task() {
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
