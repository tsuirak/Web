import request from '@/utils/request'

export default {
    // 获取所有的课程分类信息
    getSubjectList() {
        return request ({
            url: `/eduservice/subject/getSubjectList`,
            method: 'get'
          })
    }
}

