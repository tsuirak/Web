package com.tsuiraku.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuiraku.eduservice.entity.*;
import com.tsuiraku.eduservice.entity.frontvo.CourseFrontVo;
import com.tsuiraku.eduservice.entity.frontvo.CourseWebVo;
import com.tsuiraku.eduservice.entity.vo.CourseInfo;
import com.tsuiraku.eduservice.entity.vo.CoursePublish;
import com.tsuiraku.eduservice.mapper.EduCourseMapper;
import com.tsuiraku.eduservice.service.EduChapterService;
import com.tsuiraku.eduservice.service.EduCourseDescriptionService;
import com.tsuiraku.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsuiraku.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-29
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;


    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/29 上午11:19
     *  @Description: 创建课程信息
     * @return
     */
    @Override
    public String addCourseInfo(CourseInfo courseInfo) {


        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int insertFlag = baseMapper.insert(eduCourse);

        try {
            if(insertFlag == 0){
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String courseId = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseId);
        eduCourseDescription.setDescription(courseInfo.getDescription());
        courseDescriptionService.save(eduCourseDescription);

        return courseId;
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/2 下午4:53
     *  @Description: 查询课程大纲列表
     */
    @Override
    public CourseInfo getCourseInfoById(String chapterId) {
        EduCourse eduCourse = this.baseMapper.selectById(chapterId);
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(eduCourse, courseInfo);

        EduCourseDescription courseDescription = courseDescriptionService.getById(chapterId);
        courseInfo.setDescription(courseDescription.getDescription());
        return courseInfo;
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/2 下午4:53
     *  @Description: 更新课程大纲列表
     */
    @Override
    public void updateCourseInfo(CourseInfo courseInfo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int flag = this.baseMapper.updateById(eduCourse);
        if(flag == 0) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfo.getId());
        eduCourseDescription.setDescription(courseInfo.getDescription());

        courseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublish publishCourseInfo(String id) {
        CoursePublish publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void removeCourse(String courseId) {
        // 根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        // 根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);

        // 根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        // 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result == 0) { //失败返回
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }

        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { // 关注度
            wrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { // 最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) { // 价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageCourse, wrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String id) {
        return baseMapper.getBaseCourseInfo(id);
    }
}
