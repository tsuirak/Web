package com.tsuiraku.eduorder.client;

import com.tsuiraku.utils.R;
import com.tsuiraku.utils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface Uclient {
    @PostMapping("/educenter/getUserInfoOrder/{id}")
    public UcenterMemberVo getUserInfoOrder(@PathVariable("id") String id);
}
