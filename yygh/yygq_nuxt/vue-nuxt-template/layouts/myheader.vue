<template>
  <div class="header-container">
    <div class="wrapper">
      <!-- logo -->
      <div class="left-wrapper v-link selected">
          <img style="width: 50px" width="50" height="50" src="~assets/images/logo.png">
          <span class="text">预约挂号统一平台</span>
      </div>
      <!-- 搜索框 -->
      <div class="search-wrapper">
          <div class="hospital-search animation-show">
              <el-autocomplete
                  class="search-input small"
                  prefix-icon="el-icon-search"
                  v-model="state"
                  :fetch-suggestions="querySearchAsync"
                  placeholder="点击输入医院名称"
                  @select="handleSelect"
                  >
                  <span slot="suffix" class="search-btn v-link highlight clickable selected">搜索 </span>
              </el-autocomplete>
          </div>
      </div>
      <!-- 右侧 -->
      <div class="right-wrapper">
        <span class="v-link clickable">帮助中心</span> 
        <span v-if="name == ''" class="v-link clickable" @click="dialogUserFormVisible = true" id="loginDialog">登录/注册</span>
        
        <el-dropdown v-else @command="loginMenu">
          <span class="el-dropdown-link">
            {{ name }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu class="user-name-wrapper" slot="dropdown">
            <el-dropdown-item command="/user">实名认证</el-dropdown-item>
            <el-dropdown-item command="/order">挂号订单</el-dropdown-item>
            <el-dropdown-item command="/patient">就诊人管理</el-dropdown-item>
            <el-dropdown-item command="/logout" divided>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!-- 登录弹出层 -->
    <el-dialog :visible.sync="dialogUserFormVisible" style="text-align: left;" top="50px" :append-to-body="true"  width="960px" @close="closeDialog()">
      <div class="container">
        <!-- 手机登录 #start -->
        <div class="operate-view" v-if="dialogAtrr.showLoginType === 'phone'">
          <div class="wrapper" style="width: 100%">
            <div class="mobile-wrapper" style="position: static;width: 70%">
              <!-- <span class="title">{{ dialogAtrr.labelTips }}</span> -->
              <el-form>
                <el-form-item>
                  <el-input v-model="dialogAtrr.inputValue" :placeholder="dialogAtrr.placeholder" :maxlength="dialogAtrr.maxlength" class="input v-input">
                    <span slot="suffix" class="sendText v-link" v-if="dialogAtrr.second > 0">{{ dialogAtrr.second }}s </span>
                    <span slot="suffix" class="sendText v-link highlight clickable selected" v-if="dialogAtrr.second == 0" @click="getCodeFun()">重新发送 </span>
                  </el-input>
                </el-form-item>
              </el-form>
              <div class="send-button v-button" @click="btnClick()"> {{ dialogAtrr.loginBtn }}</div>
            </div>
            <div class="bottom">
              <div class="wechat-wrapper" @click="weixinLogin()">
                <span class="iconfont icon"></span>
              </div>
              <span class="third-text"> 第三方账号登录 </span>
            </div>
          </div>
        </div>
        <!-- 手机登录 #end -->

        <!-- 微信登录 #start -->
        <!-- <div class="operate-view"  v-if="dialogAtrr.showLoginType === 'weixin'" >
          <div class="wrapper wechat" style="height: 400px">
            <div>
              <div id="weixinLogin"></div>
            </div>
            <div class="bottom wechat" style="margin-top: -80px;">
              <div class="phone-container">
                <div class="phone-wrapper"  @click="phoneLogin()"><span
                  class="iconfont icon"></span></div>
                <span class="third-text"> 手机短信验证码登录 </span></div>
            </div>
          </div>
        </div> -->
        <!-- 微信登录 #end -->

        <div class="info-wrapper">
          <div class="code-wrapper">
            <div><img src="//img.114yygh.com/static/web/code_login_wechat.png" class="code-img">
              <div class="code-text"><span class="iconfont icon"></span>微信扫一扫关注
              </div>
              <div class="code-text"> “快速预约挂号”</div>
            </div>
            <div class="wechat-code-wrapper"><img
              src="//img.114yygh.com/static/web/code_app.png"
              class="code-img">
              <div class="code-text"> 扫一扫下载</div>
              <div class="code-text"> “预约挂号”APP</div>
            </div>
          </div>
          <div class="slogan">
            <div>xxxxxx官方指定平台</div>
            <div>快速挂号 安全放心</div>
          </div>
        </div>
      </div>
    </el-dialog>
    </div>
</template>
<script>
import cookie from 'js-cookie'

import userInfoApi from '@/api/user/userInfo'
import msmApi from '@/api/msm/msm'

import Vue from 'vue'

const defaultDialogAtrr = {
  showLoginType: 'phone', // 控制手机登录与微信登录切换

  labelTips: '手机号码', // 输入框提示

  inputValue: '', // 输入框绑定对象
  placeholder: '请输入您的手机号', // 输入框placeholder
  maxlength: 11, // 输入框长度控制

  loginBtn: '获取验证码', // 登录按钮或获取验证码按钮文本

  sending: true,      // 是否可以发送验证码
  second: -1,        // 倒计时间  second>0 : 显示倒计时 second=0 ：重新发送 second=-1 ：什么都不显示
  clearSmsTime: null  // 倒计时定时任务引用 关闭登录层清除定时任务
}
export default {
  data() {
    return {
      userInfo: {
        phone: '',
        code: '',
        openid: ''
      },

      dialogUserFormVisible: false,
      // 弹出层相关属性
      dialogAtrr:defaultDialogAtrr,

      name: '' // 用户登录显示的名称
    }
  },
  created() {
    this.showInfo()
  },
  mounted() {
    // 注册全局登录事件对象
    window.loginEvent = new Vue();
    // 监听登录事件
    // 触发事件显示登录层，通过：loginEvent.$emit('loginDialogEvent')
    loginEvent.$on('loginDialogEvent', function () {
      document.getElementById("loginDialog").click();
    })
  },
  methods: {
    // 绑定登录或获取验证码按钮
    btnClick() {
      // 判断是获取验证码还是登录
      if(this.dialogAtrr.loginBtn == '获取验证码') {
        this.userInfo.phone = this.dialogAtrr.inputValue

        // 获取验证码
        this.getCodeFun()
      } else {
        // 登录
        this.login()
      }
    },

    // 绑定登录，点击显示登录层
    showLogin() {
      this.dialogUserFormVisible = true

      // 初始化登录层相关参数
      this.dialogAtrr = { ...defaultDialogAtrr }
    },

    // 登录
    login() {
      this.userInfo.code = this.dialogAtrr.inputValue

      if(this.dialogAtrr.loginBtn == '正在提交...') {
        this.$message.error('重复提交')
        return;
      }
      if (this.userInfo.code == '') {
        this.$message.error('验证码必须输入')
        return;
      }
      if (this.userInfo.code.length != 4) {
        this.$message.error('验证码格式不正确')
        return;
      }
      this.dialogAtrr.loginBtn = '正在提交...'
      userInfoApi.login(this.userInfo).then(response => {
        console.log(response.data)
        // 登录成功 设置cookie
        this.setCookies(response.data.name, response.data.token)
      }).catch(e => {
        this.dialogAtrr.loginBtn = '马上登录'
      })
    },

    setCookies(name, token) {
      cookie.set('token', token, { domain: 'localhost' })
      cookie.set('name', name, { domain: 'localhost' })
      window.location.reload()
    },

    // 获取验证码
    getCodeFun() {
      if (!(/^1[34578]\d{9}$/.test(this.userInfo.phone))) {
        this.$message.error('手机号码不正确')
        return;
      }

      // 初始化验证码相关属性
      this.dialogAtrr.inputValue = ''
      this.dialogAtrr.placeholder = '请输入验证码'
      this.dialogAtrr.maxlength = 6
      this.dialogAtrr.loginBtn = '马上登录'

      // 控制重复发送
      if (!this.dialogAtrr.sending) return;

      // 发送短信验证码
      this.timeDown();
      this.dialogAtrr.sending = false;
      msmApi.sendCode(this.userInfo.phone).then(response => {
        this.timeDown();
      }).catch(e => {
        this.$message.error('发送失败，重新发送')
        // 发送失败，回到重新获取验证码界面
        this.showLogin()
      })
    },

    // 倒计时
    timeDown() {
      if(this.clearSmsTime) {
        clearInterval(this.clearSmsTime);
      }
      this.dialogAtrr.second = 60;

      this.dialogAtrr.labelTips = '验证码已发送至' + this.userInfo.phone
      this.clearSmsTime = setInterval(() => {
        --this.dialogAtrr.second;
        if (this.dialogAtrr.second < 1) {
          clearInterval(this.clearSmsTime);
          this.dialogAtrr.sending = true;
          this.dialogAtrr.second = 0;
        }
      }, 1000);
    },

    // 关闭登录层
    closeDialog() {
      if(this.clearSmsTime) {
        clearInterval(this.clearSmsTime);
      }
    },

    showInfo() {
      let token = cookie.get('token')
      if (token) {
        this.name = cookie.get('name')
        console.log(this.name)
      }
    },

    loginMenu(command) {
      if('/logout' == command) {
        cookie.set('name', '', {domain: 'localhost'})
        cookie.set('token', '', {domain: 'localhost'})

        // 跳转页面
        window.location.href = '/'
      } else {
        window.location.href = command
      }
    },

    handleSelect(item) {
      window.location.href = '/hospital/' + item.hoscode
    },

    weixinLogin() {
      this.dialogAtrr.showLoginType = 'weixin'
    },

    phoneLogin() {
      this.dialogAtrr.showLoginType = 'phone'
      this.showLogin()
    }
  }
}
</script>
