package com.tsuiraku.hospservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsuiraku.modelservice.model.hosp.HospitalSet;

public interface HospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hoscode);
}
