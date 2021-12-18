package com.tsuiraku.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsuiraku.eduservice.entity.EduCourse;
import com.tsuiraku.eduservice.entity.EduCourseDescription;
import com.tsuiraku.eduservice.entity.EduSubject;
import com.tsuiraku.eduservice.entity.excel.SubjectExcelVo;
import com.tsuiraku.eduservice.entity.subject.SubjectNestedVo;
import com.tsuiraku.eduservice.entity.subject.SubjectVo;
import com.tsuiraku.eduservice.entity.vo.CourseInfo;
import com.tsuiraku.eduservice.listener.SubjectExcelListener;
import com.tsuiraku.eduservice.mapper.EduSubjectMapper;
import com.tsuiraku.eduservice.service.EduCourseDescriptionService;
import com.tsuiraku.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-28
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/28 下午7:38
     *  @Description: EasyExcel
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectExcelVo.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/28 下午7:38
     *  @Description: 获取课程分类列表
     */
    @Override
    public List<SubjectNestedVo> nestedList() {
        ArrayList<SubjectNestedVo> subjectNestedVoArrayList = new ArrayList<>();

        QueryWrapper<EduSubject> wrapperOneSubject = new QueryWrapper<>();
        wrapperOneSubject.eq("parent_id", "0");
        List<EduSubject> subjectOneList = baseMapper.selectList(wrapperOneSubject);


        QueryWrapper<EduSubject> wrapperTwoSubject = new QueryWrapper<>();
        wrapperTwoSubject.ne("parent_id", "0");
        List<EduSubject> subjectTwoList = baseMapper.selectList(wrapperTwoSubject);

        for (int i = 0; i < subjectOneList.size(); i++) {
            SubjectNestedVo subjectOne = new SubjectNestedVo();
            EduSubject eduSubjectOne = subjectOneList.get(i);
            subjectOne.setId(eduSubjectOne.getId());
            subjectOne.setTitle(eduSubjectOne.getTitle());
            
            List<SubjectVo> children = new ArrayList<>();
            for (int j = 0; j < subjectTwoList.size(); j++) {
                SubjectVo subjectVo = new SubjectVo();
                EduSubject eduSubjectTwo = subjectTwoList.get(j);
                if(eduSubjectTwo.getParentId().equals(eduSubjectOne.getId())) {
                    subjectVo.setId(eduSubjectTwo.getId());
                    subjectVo.setTitle(eduSubjectTwo.getTitle());
                    children.add(subjectVo);
                }
            }
            subjectOne.setChildren(children);
            subjectNestedVoArrayList.add(subjectOne);
        }
        
        return subjectNestedVoArrayList;
    }

}
