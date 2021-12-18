package com.tsuiraku.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsuiraku.commonutil.exception.YyghException;
import com.tsuiraku.commonutil.helper.JwtHelper;
import com.tsuiraku.commonutil.result.ResultCodeEnum;
import com.tsuiraku.modelservice.model.user.UserInfo;
import com.tsuiraku.modelservice.vo.user.LoginVo;
import com.tsuiraku.userservice.mapper.UserInfoMapper;
import com.tsuiraku.userservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Map<String, Object> loginUser(LoginVo loginVo) {
        String code = loginVo.getCode();
        String phone = loginVo.getPhone();

        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phone)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }

        /* 判断手机验证码和输入的验证码是否一致 */
        String redisCode = redisTemplate.opsForValue().get(phone);
        if(!code.equals(redisCode)) {
            throw new YyghException(ResultCodeEnum.CODE_ERROR);
        }

        /* 根据phone查询
         * 如果数据库中已经存在，则登陆，否则注册 */
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        UserInfo userInfo = this.baseMapper.selectOne(wrapper);
        if (StringUtils.isEmpty(userInfo)) {
            userInfo = new UserInfo();
            userInfo.setName("");
            userInfo.setPhone(phone);
            userInfo.setStatus(1);
            baseMapper.insert(userInfo);
        }

        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name", name);

        /* Jwt */
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token",token);

        return map;
    }


}
