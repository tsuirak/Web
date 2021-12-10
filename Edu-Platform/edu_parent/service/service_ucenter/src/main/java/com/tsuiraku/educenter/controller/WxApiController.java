package com.tsuiraku.educenter.controller;

import com.google.gson.Gson;
import com.tsuiraku.educenter.entity.UcenterMember;
import com.tsuiraku.educenter.service.UcenterMemberService;
import com.tsuiraku.educenter.utils.ConstantPropertiesUtil;
import com.tsuiraku.educenter.utils.HttpClientUtils;
import com.tsuiraku.servicebase.exceptionhandler.MyException;
import com.tsuiraku.utils.JwtUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 *  @Author: tsuiraku
 *  @Date: 2021/11/6 下午1:58
 *  @Description:
 *  1. 注册开发者资质
 *      1. 支持企业类型
 *      2. 微信id和微信密钥
 *  2. 申请网站应用名称
 *  3. 需要域名地址
 *  4. 生成微信二维码（微信提供固定的地址，向地址中拼接参数）
 */
@Api(tags = "微信二维码登陆模块")
@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/6 下午3:12
     *  @Description:
     * @Params: code 随机唯一的值
     * @Params: state 原值传递
     */
    @GetMapping("callback")
    public String callback(String code, String state) {
        try {
            // 1. 获取code值，临时票据，类似于验证码
            // 2. 拿着code请求 微信固定的地址，得到两个值 accsess_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code
            );
            // 请求这个拼接好的地址，得到返回两个值 accsess_token 和 openid
            // 使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            System.out.println("accessTokenInfo：" + accessTokenInfo);

            // 从accessTokenInfo字符串获取出来两个值 accsess_token 和 openid
            // 把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            // 使用json转换工具 Gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");

            // 根据openid判断该用户是否第一次使用微信登陆
            UcenterMember member = memberService.getOpenIdMember(openid);
            if(member == null) { // 当前用户未使用微信登陆

                // 3. 使用 accsess_token 和 openid，请求微信提供固定的地址，获取到扫描人信息
                // 访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                // 拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                // 发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);

                // 获取返回userinfo字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String)userInfoMap.get("nickname"); // 昵称
                String headimgurl = (String)userInfoMap.get("headimgurl");// 头像

                member = new UcenterMember();
                member.setOpenid(openid);

                String memberNickname = member.getNickname();
                String memberAvatar = member.getAvatar();

                /* 如果当前用户已经注册，姓名和头像不需要覆盖
                 * 否则，需要将微信的姓名和头像添加*/
                if(StringUtils.isEmpty(memberNickname)) {
                    member.setNickname(nickname);
                }

                if(StringUtils.isEmpty(memberAvatar)) {
                    member.setAvatar(headimgurl);
                }

                memberService.save(member);
            }

            // 使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            // 返回首页面，通过路径传递token字符串
            return "redirect:http://localhost:3000?token=" + jwtToken;

        } catch(Exception e) {
            throw new MyException(20001, "登录失败");
        }
    }

    @GetMapping("/login")
    public String getWxCode() {
        /* 固定地址
         * 拼接参数 */
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        /* 回调地址 */
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; // 获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); // url 编码
        } catch (UnsupportedEncodingException e) {
            throw new MyException(20001, e.getMessage());
        }

        /* 防止csrf攻击(跨站请求伪造攻击)
         * String state = UUID.randomUUID().toString().replaceAll("-", ""); // 一般情况下会使用一个随机数 */

        /* 生成 qrcodeUrl */
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "tsuiraku"); // 原样传递的值


        /* 重定向到请求微信的地址 */
        return "redirect:" + qrcodeUrl;
    }
}