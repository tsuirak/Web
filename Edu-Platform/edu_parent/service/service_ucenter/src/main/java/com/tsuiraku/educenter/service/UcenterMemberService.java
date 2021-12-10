package com.tsuiraku.educenter.service;

import com.tsuiraku.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tsuiraku.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-11-04
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}
