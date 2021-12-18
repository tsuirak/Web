package com.tsuiraku.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsuiraku.educms.entity.CrmBanner;
import com.tsuiraku.educms.mapper.CrmBannerMapper;
import com.tsuiraku.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-11-02
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    // 查询banner
    @Cacheable(value = "banner", key = "'selectAllBanner'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        // 根据id进行降序排序 显示排序之后的前两条
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(wrapper);
        return list;
    }
}
