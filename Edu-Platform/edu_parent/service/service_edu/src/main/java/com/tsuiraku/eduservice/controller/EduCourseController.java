package com.tsuiraku.eduservice.controller;


import com.tsuiraku.eduservice.entity.EduCourse;
import com.tsuiraku.eduservice.entity.vo.CourseInfo;
import com.tsuiraku.eduservice.entity.vo.CoursePublish;
import com.tsuiraku.eduservice.service.EduCourseService;
import com.tsuiraku.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-29
 */
@RestController
@RequestMapping("/eduservice/educourse")
@CrossOrigin
@Api(tags = "课程管理模块")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/29 上午11:21
     *  @Description: 添加课程
     */
    @ApiOperation("添加课程")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfo courseInfo) {

        String id = eduCourseService.addCourseInfo(courseInfo);

        return R.success().msg("success").data("courseId",id);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/2 下午3:38
     *  @Description: 根据章节id查询
     */
    @ApiOperation("查询章节")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        CourseInfo courseInfo = eduCourseService.getCourseInfoById(chapterId);
        return R.success().msg("success").data("courseInfo", courseInfo);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/2 下午3:40
     *  @Description: 修改章节
     */
    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody CourseInfo courseInfo) {
        eduCourseService.updateCourseInfo(courseInfo);
        return R.success().msg("success");
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/3 下午6:54
     *  @Description: 根据课程id查询课程确认信息
     */
    @ApiOperation("查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublish coursePublishVo = eduCourseService.publishCourseInfo(id);
        return R.success().msg("success").data("publishCourse",coursePublishVo);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/3 下午9:08
     *  @Description: 课程最终发布
     */
    @ApiOperation("课程发布")
    @PostMapping("publish/{id}")
    public R publishCourse(@PathVariable  String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.success().msg("success");
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/3 下午9:22
     *  @Description: 返回所有的课程
     */
    @ApiOperation("返回课程列表")
    @GetMapping("getCourseList")
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.success().msg("success").data("list", list);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/3 下午10:19
     *  @Description: 根据课程id删除课程
     */
    @ApiOperation("删除整个课程")
    @DeleteMapping("delete/{courseId}")
    public R removeCourseById(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return  R.success().msg("success");
    }
}

