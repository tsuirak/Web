package com.tsuiraku.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuiraku.eduservice.entity.EduChapter;
import com.tsuiraku.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tsuiraku.eduservice.entity.EduTeacher;
import com.tsuiraku.eduservice.entity.frontvo.CourseFrontVo;
import com.tsuiraku.eduservice.entity.frontvo.CourseWebVo;
import com.tsuiraku.eduservice.entity.vo.CourseInfo;
import com.tsuiraku.eduservice.entity.vo.CoursePublish;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-29
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseInfoById(String chapterId);

    void updateCourseInfo(CourseInfo courseInfo);

    CoursePublish publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String id);
}
