package com.tsuiraku.hospservice.controller;

import com.tsuiraku.commonutil.result.Result;
import com.tsuiraku.hospservice.service.DepartmentService;
import com.tsuiraku.modelservice.vo.hosp.DepartmentVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hosp/department")
//@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/14 上午9:54
     *  @Description: 根据医院编号查询医院所有科室列表
     */
    @ApiOperation(value = "查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }
}
