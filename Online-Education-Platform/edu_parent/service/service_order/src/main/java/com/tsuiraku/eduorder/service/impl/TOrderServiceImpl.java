package com.tsuiraku.eduorder.service.impl;

import com.tsuiraku.eduorder.client.EduClient;
import com.tsuiraku.eduorder.client.Uclient;
import com.tsuiraku.eduorder.entity.TOrder;
import com.tsuiraku.eduorder.mapper.TOrderMapper;
import com.tsuiraku.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsuiraku.eduorder.utils.OrderNoUtil;
import com.tsuiraku.utils.vo.CourseVo;
import com.tsuiraku.utils.vo.UcenterMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author tsuiraku
 * @since 2021-11-29
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private Uclient uclient;

    @Override
    public String createOrder(String courseId, String userId) {
        /* 通过远程调用获取课程信息和用户信息 */
        CourseVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        System.out.println("##################educlient yes!########################");
//        UcenterMemberVo userInfoOrder = uclient.getUserInfoOrder(userId);
        System.out.println("##################ucenterClient yes!########################");
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo()); /* 订单号 */
        order.setCourseId(courseId); /* 课程id */
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(userId);
//        order.setMobile(userInfoOrder.getMobile());
//        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  /* 订单状态（0：未支付 1：已支付）*/
        order.setPayType(1);  /* 支付类型 ，微信1 */
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
