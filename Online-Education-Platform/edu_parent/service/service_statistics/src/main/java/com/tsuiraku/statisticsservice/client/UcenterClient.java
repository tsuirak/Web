package com.tsuiraku.statisticsservice.client;

import com.tsuiraku.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @GetMapping("/educenter/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
