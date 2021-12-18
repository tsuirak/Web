package com.tsuiraku.hospservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tsuiraku.hospservice.repository.DepartmentRepository;
import com.tsuiraku.hospservice.service.DepartmentService;
import com.tsuiraku.modelservice.model.hosp.Department;
import com.tsuiraku.modelservice.vo.hosp.DepartmentQueryVo;
import com.tsuiraku.modelservice.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        String paramMapString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(paramMapString,Department.class);

        Department departmentExist = departmentRepository.
                getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());

        if(departmentExist != null) {
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        /* 创建Pageable对象
         * 设置当前页和每页记录数 */
        Pageable pageable = PageRequest.of(page - 1, limit);

        /* 创建Example对象 */
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Department> example = Example.of(department,matcher);

        /* findAll(example, pageable) */
        Page<Department> all = departmentRepository.findAll(example, pageable);

        return all;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department != null) {
            departmentRepository.deleteById(department.getId());
        }
    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        /* 创建list集合用于最终数据封装 */
        List<DepartmentVo> result = new ArrayList<>();

        /* 根据医院编号查询医院所有科室信息 */
        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);
        Example example = Example.of(departmentQuery);

        /* 所有科室列表 */
        List<Department> departmentList = departmentRepository.findAll(example);

        /* 根据大科室编号bigcode进行分组
         * 获取每个大科室里面下级子科室 */
        Map<String, List<Department>> deparmentMap =
                departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));

        for(Map.Entry<String,List<Department>> entry : deparmentMap.entrySet()) {
            /* 大科室编号 */
            String bigcode = entry.getKey();

            /* 大科室编号对应的全部数据 */
            List<Department> deparment1List = entry.getValue();

            DepartmentVo departmentVo1 = new DepartmentVo();
            departmentVo1.setDepcode(bigcode);
            departmentVo1.setDepname(deparment1List.get(0).getBigname());

            /* 封装小科室 */
            List<DepartmentVo> children = new ArrayList<>();
            for(Department department: deparment1List) {
                DepartmentVo departmentVo2 =  new DepartmentVo();
                departmentVo2.setDepcode(department.getDepcode());
                departmentVo2.setDepname(department.getDepname());
                children.add(departmentVo2);
            }
            /* 小科室list集合放到大科室children里面 */
            departmentVo1.setChildren(children);
            result.add(departmentVo1);
        }
        return result;
    }

    @Override
    public String getDepName(String hoscode, String depcode) {
        return null;
    }

    @Override
    public Department getDepartment(String hoscode, String depcode) {
        return null;
    }
}
