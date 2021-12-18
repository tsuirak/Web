package com.tsuiraku.eduservice.controller;

import com.tsuiraku.eduservice.entity.subject.SubjectNestedVo;
import com.tsuiraku.eduservice.service.EduSubjectService;
import com.tsuiraku.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-28
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
@Api(tags = "课程分类模块")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/28 下午4:47
     *  @Description: EasyExcel上传文件读入数据库
     */
    @ApiOperation("EasyExcel上传文件")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.success().msg("success");
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/28 下午7:36
     *  @Description: 获取课程分类列表
     */
    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("getSubjectList")
    public R nestedList(){
        List<SubjectNestedVo> subjectNestedVoList = eduSubjectService.nestedList();
        return R.success().msg("success").data("items", subjectNestedVoList);
    }

}

