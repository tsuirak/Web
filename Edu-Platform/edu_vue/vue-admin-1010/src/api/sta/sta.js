import request from '@/utils/request'

export default {
    createStaByDay(day) {
        return request ({
            url: `/edusta/registerCount/${day}`,
            method: 'post'
          })
    },
    getDataSta(searchObj) {
        return request ({
            url: `/edusta/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get'
          })
    }
}

