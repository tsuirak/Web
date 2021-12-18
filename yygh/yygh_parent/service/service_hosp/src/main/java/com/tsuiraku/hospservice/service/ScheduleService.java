package com.tsuiraku.hospservice.service;

import com.tsuiraku.modelservice.model.hosp.Schedule;
import com.tsuiraku.modelservice.vo.hosp.ScheduleOrderVo;
import com.tsuiraku.modelservice.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    /* 上传排班接口 */
    void save(Map<String, Object> paramMap);

    /* 查询排班接口 */
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    /* 删除排班 */
    void remove(String hoscode, String hosScheduleId);

    /* 根据医院编号、科室编号查询排班规则数据 */
    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

    /* 根据医院编号、科室编号、工作日期查询排班详细信息 */
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);

    /* 获取可预约的排班数据 */
    Map<String,Object> getBookingScheduleRule(int page, int limit, String hoscode, String depcode);

    /* 获取排班id获取排班数据 */
    Schedule getScheduleId(String scheduleId);

    /* 根据排班id获取预约下单数据 */
    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    /* 更新排班数据 */
    void update(Schedule schedule);
}
