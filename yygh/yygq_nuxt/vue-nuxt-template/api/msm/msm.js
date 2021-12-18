import request from '@/utils/request'

const api_name = `/api/msm`

export default {
  sendCode(phoneNumber) {
    return request({
      url: `${api_name}/send/${phoneNumber}`,
      method: `get`
    })
  }
}
