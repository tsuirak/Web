package com.tsuiraku.eduservice.mapper;

import com.tsuiraku.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsuiraku.eduservice.entity.frontvo.CourseWebVo;
import com.tsuiraku.eduservice.entity.vo.CoursePublish;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-29
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublish getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
