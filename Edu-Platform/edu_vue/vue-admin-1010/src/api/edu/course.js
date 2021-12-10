import request from '@/utils/request'

export default {
    // 提交添加课程信息
    addCourseInfo(courseInfo) {
        return request ({
            url: `/eduservice/educourse/addCourseInfo`,
            method: 'post',
            data: courseInfo
          })
    },
    // 查询所有教师信息
    getTeacherInfo() {
        return request ({
            url: `/eduservice/teacher/findAll`,
            method: 'get',
          })
    },
    // 根据id查询所有课程大纲信息
    getCourseInfoById(id) {
        return request ({
            url: `/eduservice/educourse/getChapterInfo/${id}`,
            method: 'get',
            })
    },
    // 查询所有教师信息
    updateChapterInfo(courseInfo) {
        return request ({
            url: `/eduservice/educourse/updateChapter`,
            method: 'post',
            data: courseInfo
          })
    },
    // 课程确认信息
    getPublishInfo(id) {
        return request ({
            url: `/eduservice/educourse/getPublishCourseInfo/${id}`,
            method: 'get'
          })
    },
    publishInfo(id) {
        return request ({
            url: `/eduservice/educourse/publish/${id}`,
            method: 'post'
          })
    },
    getCourseList() {
        return request ({
            url: `/eduservice/educourse/getCourseList`,
            method: 'get'
          })
    },
    deleteCourse(courseId) {
        return request ({
            url: `/eduservice/educourse/delete/${courseId}`,
            method: 'delete'
          })
    },

}