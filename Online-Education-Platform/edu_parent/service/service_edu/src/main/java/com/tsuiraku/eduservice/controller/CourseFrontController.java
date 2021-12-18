package com.tsuiraku.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuiraku.eduservice.entity.EduCourse;
import com.tsuiraku.eduservice.entity.chapter.ChapterVo;
import com.tsuiraku.eduservice.entity.frontvo.CourseFrontVo;
import com.tsuiraku.eduservice.entity.frontvo.CourseWebVo;
import com.tsuiraku.eduservice.service.EduChapterService;
import com.tsuiraku.eduservice.service.EduCourseService;
import com.tsuiraku.utils.R;
import com.tsuiraku.utils.vo.CourseVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("查询课程分页")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page,
                                @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);

        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);

        return R.success().data(map);
    }

    @ApiOperation("查询课程信息")
    @GetMapping("getFrontCourseInfo/{id}")
    public R getFrontCourseInfo(@PathVariable String id) {
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(id);

        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(id);
        return R.success().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/29 下午1:55
     *  @Description: 根据课程id查询课程信息
     */
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseVo getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(courseInfo, courseVo);
        return courseVo;
    }

}
