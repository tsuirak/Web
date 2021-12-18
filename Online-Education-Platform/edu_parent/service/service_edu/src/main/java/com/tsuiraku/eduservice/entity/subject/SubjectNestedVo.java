package com.tsuiraku.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *  @Author: tsuiraku
 *  @Date: 2021/8/28 下午7:35
 *  @Description: 1级分类
 */
@Data
public class SubjectNestedVo {
    private String id;
    private String title;;
    private List<SubjectVo> children = new ArrayList<>();
}
