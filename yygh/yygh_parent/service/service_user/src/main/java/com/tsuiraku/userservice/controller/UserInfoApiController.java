package com.tsuiraku.userservice.controller;

import com.tsuiraku.commonutil.result.Result;
import com.tsuiraku.modelservice.vo.user.LoginVo;
import com.tsuiraku.userservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
//@CrossOrigin
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * @Author: tsuiraku
     * @Date: 2021/12/17 下午1:45
     * @Description: 登陆/注册
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        Map<String, Object> info = userInfoService.loginUser(loginVo);

        return Result.ok(info);
    }
}

