package com.tsuiraku.eduservice.client;

import com.tsuiraku.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeVideoById(String id) {
        return R.error().msg("删除小节中的视频出错");
    }

    @Override
    public R deleteVideoBatch(List<String> videoList) {
        return R.error().msg("删除课程下的视频出错");
    }
}
