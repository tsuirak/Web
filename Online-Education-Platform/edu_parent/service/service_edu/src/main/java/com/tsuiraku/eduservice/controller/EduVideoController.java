package com.tsuiraku.eduservice.controller;


import com.tsuiraku.eduservice.entity.EduVideo;
import com.tsuiraku.eduservice.service.EduVideoService;
import com.tsuiraku.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-29
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
@Api(tags = "课程小节模块")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/3 下午3:26
     *  @Description: 添加小节
     */
    @PostMapping("addVideo")
    @ApiOperation("添加小节")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.success().msg("success");
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/3 下午3:26
     *  @Description: 根据id删除小节
     */
    @ApiOperation("删除小节")
    @DeleteMapping("deleteSectionById/{id}")
    public R deleteSection(@PathVariable String id) {
        eduVideoService.removeSectionById(id);
        return R.success().msg("success");
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/3 下午7:30
     *  @Description: 根据id获取小节的数据
     */
    @ApiOperation("获取小节")
    @GetMapping("getVideo/{id}")
    public R getVideoById(@PathVariable String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        return  R.success().msg("success").data("eduVideo",eduVideo);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/3 下午8:13
     *  @Description: 更新小节的数据
     */
    @ApiOperation("更新小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return  R.success().msg("success");
    }
}

