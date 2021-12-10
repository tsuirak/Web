package com.tsuiraku.educms.service;

import com.tsuiraku.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-11-02
 */
public interface CrmBannerService extends IService<CrmBanner> {

    // 查询所有的banner
    List<CrmBanner> selectAllBanner();
}
