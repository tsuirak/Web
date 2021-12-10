package com.tsuiraku.eduservice.controller;


import com.tsuiraku.eduservice.entity.EduChapter;
import com.tsuiraku.eduservice.entity.chapter.ChapterVo;
import com.tsuiraku.eduservice.service.EduChapterService;
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
@Api(tags = "课程章节模块")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/1 下午5:13
     *  @Description: 根据课程id返回课程大纲列表
     */
    @ApiOperation("返回课程大纲列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.success().msg("success").data("list",list);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/2 下午5:24
     *  @Description: 添加章节
     */
    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return R.success().msg("success");
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/2 下午5:25
     *  @Description: 根据章节id查询
     */
    @ApiOperation("根据章节id查询")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.success().msg("success").data("chapter",eduChapter);
    }


    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/2 下午5:26
     *  @Description: 修改章节
     */
    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.success();
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/2 下午5:26
     *  @Description: 删除章节
     */
    @ApiOperation("删除章节")
    @DeleteMapping("/delete/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag) {
            return R.success().msg("success");
        } else {
            return R.error().msg("error");
        }
    }

}

