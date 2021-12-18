package com.tsuiraku.eduorder.service;

import com.tsuiraku.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-11-29
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String userId);
}
