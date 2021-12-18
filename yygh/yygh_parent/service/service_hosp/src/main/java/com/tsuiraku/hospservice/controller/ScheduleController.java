package com.tsuiraku.hospservice.controller;

import com.tsuiraku.commonutil.result.Result;
import com.tsuiraku.hospservice.service.ScheduleService;
import com.tsuiraku.modelservice.model.hosp.Schedule;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/schedule")
//@CrossOrigin
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/14 上午10:50
     *  @Description: 根据医院编号、科室编号查询排班规则数据
     */
    @GetMapping("getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable long page,
                                  @PathVariable long limit,
                                  @PathVariable String hoscode,
                                  @PathVariable String depcode) {
        Map<String,Object> map = scheduleService.getRuleSchedule(page, limit, hoscode, depcode);
        return Result.ok(map);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/14 上午10:50
     *  @Description: 根据医院编号、科室编号和工作日期查询排班详细信息
     */
    @GetMapping("getScheduleDetail/{hoscode}/{depcode}/{workDate}")
    public Result getScheduleDetail( @PathVariable String hoscode,
                                     @PathVariable String depcode,
                                     @PathVariable String workDate) {
        List<Schedule> list = scheduleService.getDetailSchedule(hoscode,depcode,workDate);
        return Result.ok(list);
    }
}
