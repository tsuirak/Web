import request from '@/utils/request'

export default {
    // 根据id获取课程大纲的列表
    getChapterList(courseID) {
        return request ({
            url: `/eduservice/chapter/getChapterVideo/${courseID}`,
            method: 'get'
          })
    },
    
    addChapter(eduChapter) {
        return request ({
            url: `/eduservice/chapter/addChapter`,
            method: 'post',
            data: eduChapter
            })
    },
    getChapter(chapterID) {
        return request ({
            url: `/eduservice/chapter/getChapterInfo/${chapterID}`,
            method: 'get',
            })
    },
    updateChapter(eduChapter) {
        return request ({
            url: `/eduservice/chapter/updateChapter`,
            method: 'post',
            data: eduChapter
            })
    },
    deleteChapter(chapterId) {
        return request ({
            url: `/eduservice/chapter/delete/${chapterId}`,
            method: 'delete',
            })
    },
}

