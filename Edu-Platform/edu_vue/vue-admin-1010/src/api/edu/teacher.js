import request from '@/utils/request'

export default {
    // 获取按照条件查询的教师列表
    getTeacheListPage(current, limit, teacherQuery) {
        return request ({
            url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            data: teacherQuery
          })
    },
    // 教师信息的逻辑删除
    removeTeacherById(id) {
      return request ({
        url: `/eduservice/teacher/${id}`,
        method: 'delete'
      })
    },
    // 添加教师信息
    addTeacher(teacher) {
      return request ({
        url: `/eduservice/teacher/addTeacher`,
        method: 'post',
        data: teacher
      })
    },
    // 根据id查询教师信息
    getTeacherInfoById(id) {
      return request ({
        url: `/eduservice/teacher/getTeacher/${id}`,
        method: 'get'
      })
    },
    // 修改教师信息
    updateTeacherInfo(teacher) {
      return request ({
        url: `/eduservice/teacher/updateTeacher`,
        method: 'post',
        data: teacher
      })
    }
}

