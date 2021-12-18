package com.tsuiraku.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsuiraku.eduorder.entity.TOrder;
import com.tsuiraku.eduorder.service.TOrderService;
import com.tsuiraku.utils.JwtUtils;
import com.tsuiraku.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author tsuiraku
 * @since 2021-11-29
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
@Api("订单管理模块")
public class TOrderController {
    @Autowired
    private TOrderService orderService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/29 下午1:38
     *  @Description: 生成订单的方法
     */
    @PostMapping("createOrder/{courseId}")
    @ApiOperation("生成订单的接口")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        /* 创建订单，返回订单号 */
        String orderId = orderService.createOrder(courseId, userId);
        return R.success().data("orderId", orderId);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/29 下午2:25
     *  @Description: 根据订单id查询订单的信息
     */
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        TOrder info = orderService.getOne(wrapper);
        return R.success().data("item", info);
    }
}

