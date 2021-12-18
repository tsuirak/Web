package com.tsuiraku.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsuiraku.educenter.entity.UcenterMember;
import com.tsuiraku.educenter.entity.vo.RegisterVo;
import com.tsuiraku.educenter.mapper.UcenterMemberMapper;
import com.tsuiraku.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsuiraku.servicebase.exceptionhandler.MyException;
import com.tsuiraku.utils.JwtUtils;
import com.tsuiraku.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-11-04
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public String login(UcenterMember member) {
        String phoneNumber = member.getMobile();
        String password = member.getPassword();

        if(StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(password)) {
            throw new MyException(20001, "登陆失败");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", phoneNumber);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);

        if(ucenterMember == null) {
            throw new MyException(20001, "登陆失败");
        }

        if(!MD5.encrypt(password).equals(ucenterMember.getPassword())) {
            throw new MyException(20001, "登陆失败");
        }

        if(ucenterMember.getIsDisabled()) {
            throw new MyException(20001, "登陆失败");
        }

        // 登陆成功；返回token值

        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new MyException(20001, "注册失败");
        }

        String redisCode = redisTemplate.opsForValue().get(mobile);

        if(!redisCode.equals(code)) {
            throw new MyException(20001, "短信已经过期，注册失败");
        }

        // 手机号不重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer integer = baseMapper.selectCount(wrapper);

        if(integer > 0) {
            throw new MyException(20001, "手机号重复，注册失败");
        }

        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://tsuiraku.oss-cn-chengdu.aliyuncs.com/edu/image/default.jpeg"); // 默认头像
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
