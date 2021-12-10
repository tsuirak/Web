import request from '@/utils/request'
export default {
    // 分页教师查询
    getTeacherList(page, limit) {
        return request({
            url: `/eduservice/teadcherfront/getTeacherFrontList/${page}/${limit}`,
            method: 'post',
      })
    },
    getTeacherInfo(id) {
        return request({
            url: `/eduservice/teadcherfront/getTeacherFrontInfo/${id}`,
            method: 'get'
        })
    }
} 