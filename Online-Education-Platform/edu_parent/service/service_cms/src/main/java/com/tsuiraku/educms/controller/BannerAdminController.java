package com.tsuiraku.educms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuiraku.educms.entity.CrmBanner;
import com.tsuiraku.educms.service.CrmBannerService;
import com.tsuiraku.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 *
 * 后台banner接口
 * @author tsuiraku
 * @since 2021-11-02
 */
@Api(tags = "首页轮播图后台接口模块")
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/2 下午4:13
     *  @Description: 分页查询
     */
    @ApiOperation("分页查询banner")
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        crmBannerService.page(pageBanner, null);

        HashMap<Object, Object> map = new HashMap<>();
        map.put("items", pageBanner.getRecords());
        map.put("total", pageBanner.getTotal());

        return R.success().data("data", map);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/2 下午4:17
     *  @Description: 添加banner
     */
    @ApiOperation("添加banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);

        return R.success();
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/2 下午4:20
     *  @Description: 查询banner
     */
    @ApiOperation("查询banner")
     @GetMapping("get/{id}")
    public R getBannerById(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.success().data("items", banner);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/2 下午4:20
     *  @Description: 修改banner
     */
    @ApiOperation("修改banner")
    @PutMapping("update")
    public R updateBannerById(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateById(crmBanner);
        return R.success();
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/2 下午4:20
     *  @Description: 删除banner
     */
    @ApiOperation("删除banner")
    @DeleteMapping("delete/{id}")
    public R removeBannerById(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.success();
    }

}
