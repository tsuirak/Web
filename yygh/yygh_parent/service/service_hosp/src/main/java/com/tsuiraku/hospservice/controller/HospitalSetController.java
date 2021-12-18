package com.tsuiraku.hospservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuiraku.commonutil.result.Result;
import com.tsuiraku.hospservice.service.HospitalSetService;
import com.tsuiraku.modelservice.model.hosp.HospitalSet;
import com.tsuiraku.modelservice.vo.hosp.HospitalSetQueryVo;
import com.tsuiraku.serviceutil.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 *  @Author: tsuiraku
 *  @Date: 2021/12/10 下午4:01
 *  @Description: 医院设置的接口
 */
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
//@CrossOrigin
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/10 下午4:14
     *  @Description: 返回所有医院
     */
    @GetMapping("findAll")
    public Result findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list(null);

        return Result.ok(list);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/10 下午4:14
     *  @Description: 根据id删除医院接口
     */
    @DeleteMapping("removeById/{id}")
    public Result removeHospitalSetById(@PathVariable String id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/10 下午3:52
     *  @Description: 条件查询接口
     */
    @PostMapping("findPageHospset/{current}/{limit}")
    public Result findPageHospset(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        String hosname = "";
        String hoscode = "";

        Page<HospitalSet> page = new Page<>(current, limit);
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(hospitalSetQueryVo)) {
            hosname = hospitalSetQueryVo.getHosname(); /* 医院名称 */
            hoscode = hospitalSetQueryVo.getHoscode(); /* 医院编号 */
        }
        System.out.println(hosname);
        System.out.println(hoscode);

        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }

        Page<HospitalSet> pageResult = hospitalSetService.page(page, wrapper);

        /* 返回分页结果 */
        return Result.ok(pageResult);
    }


    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/10 下午3:52
     *  @Description: 添加医院接口
     */
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        /* 设置状态 1 使用 0 不能使用 */
        hospitalSet.setStatus(1);
        /* 签名秘钥 */
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));

        boolean save = hospitalSetService.save(hospitalSet);
        if(save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/10 下午4:00
     *  @Description: 根据id获取医院接口
     */
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }


    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/10 下午3:52
     *  @Description: 修改医院接口
     */
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/10 下午4:01
     *  @Description: 批量删除医院接口
     */
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/10 下午7:17
     *  @Description: 医院设置锁定和解锁的接口
     */
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/10 下午7:18
     *  @Description: 发送签名秘钥（短信）
     */
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        // TODO 发送短信
        return Result.ok();
    }
}
