package com.tsuiraku.eduservice.controller;


import com.tsuiraku.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录模块")
@RestController
@RequestMapping("/eduservice/userLogin")
@CrossOrigin
public class EduLoginController {

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/26 下午9:13
     *  @Description: 登录
     */
    @ApiOperation("登录接口返回token")
    @PostMapping("login")
    public R login() {
        return R.success().data("token","admin");
    }
    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/26 下午9:13
     *  @Description:
     */
    @ApiOperation("登录接口返回info")
    @GetMapping("info")
    public R info() {
        return R.success().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
