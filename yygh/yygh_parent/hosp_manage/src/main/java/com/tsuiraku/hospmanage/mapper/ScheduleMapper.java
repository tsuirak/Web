package com.tsuiraku.hospmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsuiraku.hospmanage.model.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleMapper extends BaseMapper<Schedule> {

}
