package com.tsuiraku.eduservice.service;

import com.tsuiraku.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tsuiraku.eduservice.entity.chapter.ChapterVo;
import com.tsuiraku.eduservice.entity.vo.CourseInfo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-29
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
