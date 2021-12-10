package com.tsuiraku.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsuiraku.eduservice.entity.EduSubject;
import com.tsuiraku.eduservice.entity.excel.SubjectExcelVo;
import com.tsuiraku.eduservice.service.EduSubjectService;

/**
 *  @Author: tsuiraku
 *  @Date: 2021/8/28 下午4:18
 *  @Description: EasyExcel监听器进行文件读入
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectExcelVo> {
    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @Override
    public void invoke(SubjectExcelVo subjectExcelVo, AnalysisContext analysisContext) {
        EduSubject existOneSubject = this.existOneSubject(subjectExcelVo.getSubjectOne(), subjectService);
        if(existOneSubject == null) {
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectExcelVo.getSubjectOne());
            subjectService.save(existOneSubject);
        }
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(subjectExcelVo.getSubjectTwo(), pid, subjectService);
        if(existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectExcelVo.getSubjectTwo());
            subjectService.save(existTwoSubject);
        }
    }

    // 判断一级分类不重复添加
    public EduSubject existOneSubject(String name, EduSubjectService subjectService) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject subjectOne = subjectService.getOne(wrapper);
        return subjectOne;
    }

    // 判断二级分类不重复添加
    public EduSubject existTwoSubject(String name, String pid, EduSubjectService subjectService) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject subjectTwo = subjectService.getOne(wrapper);
        return subjectTwo;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
