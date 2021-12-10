package com.tsuiraku.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsuiraku.eduservice.entity.EduChapter;
import com.tsuiraku.eduservice.entity.EduCourse;
import com.tsuiraku.eduservice.entity.EduTeacher;
import com.tsuiraku.eduservice.service.EduCourseService;
import com.tsuiraku.eduservice.service.EduTeacherService;
import com.tsuiraku.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 *  @Author: tsuiraku
 *  @Date: 2021/11/2 下午4:37
 *  @Description: 前台接口
 */
@Api(tags = "首页名师和课程前台接口模块")
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class EduFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/2 下午4:39
     *  @Description: 查询前8条课程、前4条教师
     */
    @ApiOperation("课程教师查询")
    @GetMapping("index")
    public R index() {
        // 查询前8条课程
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("view_count");
        wrapperCourse.last("limit 8");
        List<EduCourse> eduCourseList = courseService.list(wrapperCourse);

        // 查询前4条教师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("sort");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> eduTeacherList = teacherService.list(wrapperTeacher);

        return R.success().data("courseList", eduCourseList).data("teacherList", eduTeacherList);
    }
}
