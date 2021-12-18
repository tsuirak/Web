import request from '@/utils/request'

export default {
    addVideo(eduVideo) {
        return request ({
            url: `/eduservice/video/addVideo`,
            method: 'post',
            data: eduVideo
          })
    },
    // 删除小节
    deleteVideo(id) {
        return request ({
            url: `/eduservice/video/deleteSectionById/${id}`,
            method: 'delete',
            })
    },
    getVideo(id) {
        return request ({
            url: `/eduservice/video/getVideo/${id}`,
            method: 'get',
            })
    },
    updateVideo(eduVideo) {
        return request ({
            url: `/eduservice/video/updateVideo`,
            method: 'post',
            data: eduVideo
            })
    },
    // 删除小节中的视频
    deleteVideoById(id) {
        return request ({
            url: `/eduvod/video/removeVideoById/${id}`,
            method: 'delete',
            })
    },
}

