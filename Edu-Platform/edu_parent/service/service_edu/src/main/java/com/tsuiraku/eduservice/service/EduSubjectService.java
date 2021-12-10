package com.tsuiraku.eduservice.service;

import com.tsuiraku.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tsuiraku.eduservice.entity.subject.SubjectNestedVo;
import com.tsuiraku.eduservice.entity.vo.CourseInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-28
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<SubjectNestedVo> nestedList();

}
