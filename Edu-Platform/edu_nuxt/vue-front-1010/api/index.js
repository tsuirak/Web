import request from '@/utils/request'
export default {
    // 查询课程和教师
    getIndexData() {
        return request({
        url: `/eduservice/indexfront/index`,
        method: 'get'
    })
} } 