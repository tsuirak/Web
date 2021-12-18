package com.tsuiraku.hospservice.controller;


import com.tsuiraku.commonutil.result.Result;
import com.tsuiraku.hospservice.service.HospitalService;
import com.tsuiraku.modelservice.model.hosp.Hospital;
import com.tsuiraku.modelservice.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/13 下午2:40
     *  @Description: 医院列表（条件查询分页）
     */
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page,
                           @PathVariable Integer limit,
                           HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> pageModel = hospitalService.selectHospPage(page,limit,hospitalQueryVo);
        List<Hospital> content = pageModel.getContent();
        long totalElements = pageModel.getTotalElements();

        return Result.ok(pageModel);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/13 下午2:40
     *  @Description: 更新医院上线状态
     */
    @GetMapping("updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id, @PathVariable Integer status) {
        hospitalService.updateStatus(id, status);
        return Result.ok();
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/13 下午2:40
     *  @Description: 医院详情信息
     */
    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail(@PathVariable String id) {
        Map<String, Object> map = hospitalService.getHospById(id);
        return Result.ok(map);
    }
}
