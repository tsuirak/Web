package com.tsuiraku.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsuiraku.eduservice.client.VodClient;
import com.tsuiraku.eduservice.entity.EduVideo;
import com.tsuiraku.eduservice.mapper.EduVideoMapper;
import com.tsuiraku.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-29
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    @Autowired
    private EduVideoService eduVideoService;


    @Override
    public void removeVideoByCourseId(String courseId) {

        QueryWrapper<EduVideo> wrapperList = new QueryWrapper<>();
        wrapperList.eq("course_id", courseId);
        wrapperList.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperList);

        List<String> videoList = new ArrayList<>();
        for(EduVideo eduVideo : eduVideoList) {
            if(StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                continue;
            }
            videoList.add(eduVideo.getVideoSourceId());
        }

        if(videoList.size() > 0) {
            vodClient.deleteVideoBatch(videoList);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);


    }

    @Override
    public void removeSectionById(String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoId)) {
            vodClient.removeVideoById(videoId);
        }

        baseMapper.deleteById(id);
    }
}
