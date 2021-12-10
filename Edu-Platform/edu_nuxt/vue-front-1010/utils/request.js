import axios from 'axios'
import cookie from 'js-cookie'
import { MessageBox, Message } from 'element-ui'
// 创建axios实例

const service = axios.create({
    baseURL: 'http://localhost:9001', 
    timeout: 20000
})

// http request 拦截器
service.interceptors.request.use(
    config => {
    // debugger
    console.log("拦截器 ==== edu_token：" + cookie.get('edu_token'))
    if (cookie.get('edu_token')) {
      config.headers['token'] = cookie.get('edu_token');
    }
      return config
    },
  err => {
    return Promise.reject(err);
  })
  



export default service