package com.tsuiraku.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsuiraku.modelservice.model.user.UserInfo;
import com.tsuiraku.modelservice.vo.user.LoginVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {
    Map<String, Object> loginUser(LoginVo loginVo);

}
