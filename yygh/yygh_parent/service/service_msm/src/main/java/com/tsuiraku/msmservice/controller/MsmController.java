package com.tsuiraku.msmservice.controller;


import com.tsuiraku.commonutil.result.Result;
import com.tsuiraku.msmservice.config.Sms;
import com.tsuiraku.msmservice.utlis.RandomUtil;
import com.tsuiraku.msmservice.utlis.SendSmsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 *  @Author: tsuiraku
 *  @Date: 2021/11/4 下午7:35
 *  @Description: 腾讯云
 */
@RestController
@RequestMapping("/api/msm")
//@CrossOrigin
public class MsmController {
    @Autowired
    private SendSmsUtils sendSmsUtils;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/4 下午12:51
     *  @Description: 腾讯云发送短信
     */
    @GetMapping("/send/{phoneNumber}")
    public Result sendMsm(@PathVariable String phoneNumber) {
        /*
         *  首先判断redis中是否存在验证码
         *  如果存在验证码则返回，否则通过腾讯云发送 */
        String code = redisTemplate.opsForValue().get(phoneNumber);
        if(!StringUtils.isEmpty(code)) {
            return Result.ok().message("已经发送短信，5分钟内有效");
        }

        /* 生产随机数 */
        code = RandomUtil.getFourBitRandom();

        Sms sms = new Sms();
        sms.setPhoneNumber(new String[]{phoneNumber});
        sms.setSignName("tsuiraku"); // 签名
        sms.setTemplateId("1243319"); // 短信注册模版
        sms.setParams(new String[]{code}); // 参数

        boolean flag = sendSmsUtils.send(sms);// 发送短信

        if(flag) { /* 发送成功 */
            /* 把发送成功的验证码存到redis
             * 设置有效时间（redis） */
            redisTemplate.opsForValue().set(phoneNumber ,code,5, TimeUnit.MINUTES);
            return Result.ok().message("发送短信成功");
        }

        return Result.fail().message("发送短信失败");
    }
}
