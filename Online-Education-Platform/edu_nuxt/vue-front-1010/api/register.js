import request from '@/utils/request'
import { FormItem } from 'element-ui'
export default {
    // 发送注册验证码
    sendCode(phoneNumber) {
        return request({
        url: `/edumsm/msm/send/${phoneNumber}`,
        method: 'get'
        })
    },
    // 注册方法
    registerMember(formItem) {
        return request ({
            url: `/educenter/register`,
            method: 'post',
            data: formItem
        })
    }
} 