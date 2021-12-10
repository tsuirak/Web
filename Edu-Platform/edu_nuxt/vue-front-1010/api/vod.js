import request from '@/utils/request'
export default {
    // 查询banner信息
    getPlayAuth(vid) {
        return request({
        url: `/eduvod/video/getPlayAuth/${vid}`,
        method: 'get'
    })
} }