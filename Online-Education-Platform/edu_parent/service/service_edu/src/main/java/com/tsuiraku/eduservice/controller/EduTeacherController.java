package com.tsuiraku.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuiraku.eduservice.entity.EduTeacher;
import com.tsuiraku.eduservice.entity.vo.TeacherQuery;
import com.tsuiraku.eduservice.service.EduTeacherService;
import com.tsuiraku.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-26
 */
@Api(tags = "教师管理模块")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/26 上午9:10
     *  @Description: 返回教师的列表
     */
    @ApiOperation(value = "所有教师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.success().msg("success").data("list", list);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/26 上午9:10
     *  @Description: 教师信息的逻辑删除
     */
    @ApiOperation(value = "逻辑删除教师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "教师ID", required = true)
                           @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if(flag) {
            return R.success().msg("success");
        } else {
            return R.error().msg("error");
        }
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/26 上午9:17
     *  @Description: 返回分页查询教师的列表
     */
    @ApiOperation(value = "分页查询教师的列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);

        eduTeacherService.page(page, null);

        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();

        HashMap<Object, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", records);

        return R.success().msg("success").data("data",map);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/26 上午9:24
     *  @Description: 返回按照条件查询教师的列表
     */
    @ApiOperation(value = "返回按照条件查询教师的列表")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        // 创建Page对象
        Page<EduTeacher> page = new Page<>(current, limit);

        // 创建wrapper条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        // 多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // wrapper中的属性均为数据库表中的属性
        if(!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.like("gmt_create", end);
        }

        eduTeacherService.page(page, wrapper);

        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();

        HashMap<Object, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", records);

        return R.success().msg("success").data("data",map);
    }


    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/26 上午9:35
     *  @Description: 教师信息的添加
     */
    @ApiOperation(value = "教师信息的添加")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if(save) {
            return R.success().msg("success");
        } else {
            return R.error().msg("error");
        }
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/26 上午9:36
     *  @Description: 根据教师id查询
     */
    @ApiOperation(value = "根据教师id查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.success().msg("success").data("teacherInfo",eduTeacher);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/26 上午9:37
     *  @Description: 根据教师id更新
     */
    @ApiOperation(value = "根据教师id更新")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if(flag) {
            return R.success().msg("success");
        } else {
            return R.error().msg("error");
        }
    }

}



