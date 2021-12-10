package com.tsuiraku.eduservice.service;

import com.tsuiraku.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-29
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String courseId);

    void removeSectionById(String id);
}
