import request from '@/utils/request'
export default {
    // 生成订单接口
    createOrder(courseId) {
        return request({
        url: `/eduorder/order/createOrder/${courseId}`,
        method: 'post'
    })},
    // 获取订单信息
    getOrderInfo(id) {
        return request({
        url: `/eduorder/order/getOrderInfo/${id}`,
        method: 'get'
    })}
}