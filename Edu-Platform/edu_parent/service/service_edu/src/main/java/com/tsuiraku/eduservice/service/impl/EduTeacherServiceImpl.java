package com.tsuiraku.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuiraku.eduservice.entity.EduTeacher;
import com.tsuiraku.eduservice.mapper.EduTeacherMapper;
import com.tsuiraku.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-08-26
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        baseMapper.selectPage(pageTeacher, wrapper);

        List<EduTeacher> records = pageTeacher.getRecords(); // 每页数据list集合
        long current = pageTeacher.getCurrent(); // 当前页
        long pages = pageTeacher.getPages(); // 总页数
        long size = pageTeacher.getSize(); // 每页记录数
        long total = pageTeacher.getTotal(); // 总记录数
        boolean hasNext = pageTeacher.hasNext(); // 是否有下一页
        boolean hasPrevious = pageTeacher.hasPrevious(); // 是否有下一页

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("records", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
