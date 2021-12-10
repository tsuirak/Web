import request from '@/utils/request'
export default {
    // 条件分页课程查询
    getCourseList(page, limit, obj) {
        return request({
            url: `/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
            method: 'post',
            data: obj
      })
    },
    // 查询所有一级二级分类的方法
    getAllSubject() {
        return request({
            url: `/eduservice/subject/getSubjectList`,
            method: 'get'
      })
    },
    // 课程详情的方法
    getCourseInfo(id) {
        return request({
            url: `/eduservice/coursefront/getFrontCourseInfo/${id}`,
            method: 'get'
        })
    }
} 