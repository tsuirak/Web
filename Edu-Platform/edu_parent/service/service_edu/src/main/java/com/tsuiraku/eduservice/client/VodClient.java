package com.tsuiraku.eduservice.client;

import com.tsuiraku.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    @DeleteMapping("eduvod/video/removeVideoById/{id}")
    public R removeVideoById(@PathVariable(value = "id") String id );
    @DeleteMapping("eduvod/video/deleteVideoBatch")
    public R deleteVideoBatch(@RequestParam("videoList") List<String> videoList);
}
