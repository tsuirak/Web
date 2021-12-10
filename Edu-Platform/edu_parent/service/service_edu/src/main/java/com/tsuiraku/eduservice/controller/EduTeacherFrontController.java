package com.tsuiraku.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuiraku.eduservice.entity.EduCourse;
import com.tsuiraku.eduservice.entity.EduTeacher;
import com.tsuiraku.eduservice.service.EduCourseService;
import com.tsuiraku.eduservice.service.EduTeacherService;
import com.tsuiraku.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  @Author: tsuiraku
 *  @Date: 2021/11/6 下午7:54
 *  @Description: 前台教师管理模块
 */
@Api(tags = "前台教师模块")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teadcherfront")
public class EduTeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("教师分页数据")
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);

        Map<String, Object> map = teacherService.getTeacherFrontList(pageTeacher);

        return R.success().data(map);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/7 下午7:05
     *  @Description: 查询教师详情的功能
     */
    @ApiOperation("查询教师详情")
    @GetMapping("getTeacherFrontInfo/{id}")
    public R getTeacherFrontInfo(@PathVariable String id) {
        EduTeacher teacherInfo = teacherService.getById(id);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> courseList = courseService.list(wrapper);

        return R.success().data("teacherInfo", teacherInfo).data("courseList", courseList);
    }



}
