package com.tsuiraku.eduservice.entity.chapter;

import lombok.Data;

@Data
public class VideoVo {
    private String id;

    private String title;

    private String videoSourceId;

    private Integer isFree;
}
