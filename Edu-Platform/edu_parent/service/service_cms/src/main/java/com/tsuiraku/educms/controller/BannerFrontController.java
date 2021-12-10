package com.tsuiraku.educms.controller;

import com.tsuiraku.educms.entity.CrmBanner;
import com.tsuiraku.educms.service.CrmBannerService;
import com.tsuiraku.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

/**
 * 前台
 * @author tsuiraku
 * @since 2021-11-02
 */
@Api(tags = "首页轮播图前台接口模块")
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/2 下午8:41
     *  @Description: 查询所有的banner
     */
    @GetMapping("getAllBanner")
    public R getAllBanner() {
         List<CrmBanner> list = crmBannerService.selectAllBanner();

         return R.success().data("list", list);
    }
}
