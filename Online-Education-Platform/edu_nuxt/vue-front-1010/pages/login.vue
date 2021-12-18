<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="user">

        <el-form-item class="input-prepend restyle" prop="mobile" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]">
          <div >
            <el-input type="text" placeholder="手机号" v-model="user.mobile"/>
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>

        <el-form-item class="input-prepend" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
            <el-input type="password" placeholder="密码" v-model="user.password"/>
            <i class="iconfont icon-password"/>
          </div>
        </el-form-item>

        <div class="btn">
          <input type="button" class="sign-in-button" value="登录" @click="submitLogin()">
        </div>
      </el-form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
        <h6>社交帐号登录</h6>
        <ul>
          <li><a id="weixin" class="weixin" target="_blank" href="http://localhost:8160/api/ucenter/wx/login"><i class="iconfont icon-weixin"/></a></li>
          <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq"/></a></li>
        </ul>
      </div>
    </div>

  </div>
</template>

<script>
  import '~/assets/css/sign.css'
  import '~/assets/css/iconfont.css'
  import cookie from 'js-cookie'
  import loginApi from '@/api/login'
  export default {
    layout: 'sign',

    data () {
      return {
        // 封装登录手机号和密码
        user: {
          mobile:'',
          password:''
        },
        // 用户信息
        loginInfo: {}
      }
    },

    created() {
    
    },
    methods: {
        // 登陆方法
        submitLogin() { 
            // 1. 调用接口返回token字符串
            loginApi.submitLoginUser(this.user)
                .then(res => {
                    // 2. 将token放入cookie
                    cookie.set('edu_token', res.data.data.token, { domin: 'localhost' })
                    // 3. 创建拦截器拦截（utils/request.js）将cookie中的值存入header（后端使用header获取）
                    // 4. 调用接口，根据token获取信息

                    console.log("login ==== 返回的token：" + res.data.data.token)

                    loginApi.getLoginUserInfo()
                        .then(res => {
                            this.loginInfo = JSON.stringify(res.data.data.userInfo)

                            console.log("login ==== 返回的loginInfo：" + this.loginInfo)
                            // 获取用户返回信息，放入cookie
                            cookie.set('edu_userInfo', this.loginInfo, { domin: 'localhost' })

                            // 跳转
                            this.$router.push({path:'/'}) 
                        })
                })
        },
        checkPhone (rule, value, callback) {
            //debugger
            if (!(/^1[34578]\d{9}$/.test(value))) {
            return callback(new Error('手机号码格式不正确'))
            }
            return callback()
        }

    }
  }
</script>

<style>
   .el-form-item__error{
    z-index: 9999999;
  }
</style>