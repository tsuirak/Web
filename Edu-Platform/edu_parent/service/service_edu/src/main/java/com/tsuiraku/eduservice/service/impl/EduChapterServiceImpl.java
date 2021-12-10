package com.tsuiraku.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsuiraku.eduservice.entity.EduChapter;
import com.tsuiraku.eduservice.entity.EduCourseDescription;
import com.tsuiraku.eduservice.entity.EduSubject;
import com.tsuiraku.eduservice.entity.EduVideo;
import com.tsuiraku.eduservice.entity.chapter.ChapterVo;
import com.tsuiraku.eduservice.entity.chapter.VideoVo;
import com.tsuiraku.eduservice.entity.vo.CourseInfo;
import com.tsuiraku.eduservice.mapper.EduChapterMapper;
import com.tsuiraku.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsuiraku.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-29
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapperChapterVo = new QueryWrapper<>();
        wrapperChapterVo.eq("course_id", courseId);
        List<EduChapter> eduChapterList = this.baseMapper.selectList(wrapperChapterVo);

        QueryWrapper<EduVideo> wrapperEduChapter = new QueryWrapper<>();
        wrapperChapterVo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperEduChapter);

        List<ChapterVo> finalList = new ArrayList<>();

        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            List<VideoVo> videoList = new ArrayList<>();
            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo eduVideo = eduVideoList.get(j);
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);
            finalList.add(chapterVo);
        }
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        if(count > 0) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }

}
