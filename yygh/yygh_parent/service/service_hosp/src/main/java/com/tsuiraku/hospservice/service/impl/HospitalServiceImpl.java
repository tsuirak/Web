package com.tsuiraku.hospservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tsuiraku.cmnclient.client.DictFeignClient;
import com.tsuiraku.hospservice.repository.HospitalRepository;
import com.tsuiraku.hospservice.service.HospitalService;
import com.tsuiraku.modelservice.model.hosp.Hospital;
import com.tsuiraku.modelservice.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public void save(Map<String, Object> paramMap) {
        /* JSONObject将map集合转换对象 */
        String mapString = JSONObject.toJSONString(paramMap); /* 转化为字符串 */
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class); /* 转化为对象 */

        /* 判断是否存在数据 */
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);

        if(hospitalExist != null) {
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        } else {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        return hospital;
    }

    /* 分页查询mongodb中的数据 */
    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        /* 创建pageable对象 */
        Pageable pageable = PageRequest.of(page - 1,limit);
        /* 创建条件匹配器 */
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) /* 模糊查询 */
                .withIgnoreCase(true);  /* 忽略大小写 */

        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo,hospital);

        Example<Hospital> example = Example.of(hospital, matcher);

        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);

        pages.getContent().stream().forEach(item -> {
            this.setHospitalHosType(item);
        });

        return pages;
    }


    @Override
    public void updateStatus(String id, Integer status) {
        /* 根据id查询医院信息 */
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    @Override
    public Map<String, Object> getHospById(String id) {
        Map<String, Object> result = new HashMap<>();
        Hospital hospital = this.setHospitalHosType(hospitalRepository.findById(id).get());
        /* 医院基本信息（包含医院等级）*/
        result.put("hospital", hospital);
        result.put("bookingRule", hospital.getBookingRule());
        hospital.setBookingRule(null);
        return result;
    }

    @Override
    public String getHospName(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        if(hospital != null) {
            return hospital.getHosname();
        }
        return null;
    }

    @Override
    public List<Hospital> findByHosname(String hosname) {
        return hospitalRepository.findHospitalByHosnameLike(hosname);
    }

    /* 根据医院编号获取医院预约挂号详情 */
    @Override
    public Map<String, Object> item(String hoscode) {
        Map<String, Object> result = new HashMap<>();
        /* 医院详情 */
        Hospital hospital = this.setHospitalHosType(this.getByHoscode(hoscode));
        result.put("hospital", hospital);
        /* 预约规则 */
        result.put("bookingRule", hospital.getBookingRule());
        hospital.setBookingRule(null);
        return result;
    }

    /* 获取查询list集合
     * 遍历进行医院等级封装 */
    private Hospital setHospitalHosType(Hospital hospital) {
        /* 根据dictCode和value获取医院等级名称 */
        String hostypeString = dictFeignClient.getName("Hostype", hospital.getHostype());

        /* 查询省、市 、地区 */
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("fullAddress", provinceString+cityString+districtString);
        hospital.getParam().put("hostypeString", hostypeString);
        return hospital;
    }

}
