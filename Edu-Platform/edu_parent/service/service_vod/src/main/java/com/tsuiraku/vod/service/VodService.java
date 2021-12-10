package com.tsuiraku.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideoToAly(MultipartFile file);

    void removeVideoById(String id);

    void removeVideoByBatch(List<String> videoList);
}
