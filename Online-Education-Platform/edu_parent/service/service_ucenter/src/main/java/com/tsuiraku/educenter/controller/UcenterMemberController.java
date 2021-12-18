package com.tsuiraku.educenter.controller;


import com.tsuiraku.educenter.entity.UcenterMember;
import com.tsuiraku.educenter.entity.vo.RegisterVo;
import com.tsuiraku.educenter.service.UcenterMemberService;
import com.tsuiraku.utils.JwtUtils;
import com.tsuiraku.utils.R;
import com.tsuiraku.utils.vo.UcenterMemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author tsuiraku
 * @since 2021-11-04
 */
@Api(tags = "登陆和注册模块")
@RestController
@RequestMapping("/educenter/")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/4 下午11:45
     *  @Description: 登陆方法
     */
    @ApiOperation("登陆接口")
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {

        String token = memberService.login(member); // 返回jwt生成的token值
        return R.success().data("token", token).msg("登陆成功");
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/5 上午10:30
     *  @Description: 注册方法
     */
    @ApiOperation("注册接口")
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.success().msg("注册成功");
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/5 上午10:52
     *  @Description: 根据token值获取用户信息
     */
    @ApiOperation("根据token获取用户信息")
    @GetMapping("getUserInfoByToken")
    public R getUserInfoByToken(HttpServletRequest request) {
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println("userID：" + userId);
        UcenterMember member = memberService.getById(userId);
        return R.success().data("userInfo", member);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/29 下午1:50
     *  @Description: 根据用户id获取用户信息
     */
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberVo getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);

        UcenterMemberVo ucenterMember = new UcenterMemberVo();
        BeanUtils.copyProperties(member, ucenterMember);
        return ucenterMember;
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/6 上午9:59
     *  @Description: 查询某一个天的注册人数
     */
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable("id") String day) {
        Integer count = memberService.countRegisterDay(day);

        return R.success().data("count", count);
    }

}

