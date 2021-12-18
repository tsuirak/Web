<template>

  <!-- header -->
  <div class="nav-container page-component">
    <!--左侧导航 #start -->
    <div class="nav left-nav">
      <div class="nav-item selected">
        <span class="v-link selected dark" :onclick="'javascript:window.location=\'/hosp/' + hospital.hoscode+'\''">预约挂号 </span>
      </div>
      <div class="nav-item ">
        <span class="v-link clickable dark" :onclick="'javascript:window.location=\'/hosp/detail/' + hospital.hoscode+'\''"> 医院详情 </span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" :onclick="'javascript:window.location=\'/hosp/notice/' + hospital.hoscode+'\''"> 预约须知 </span>
      </div>
      <div class="nav-item "><span
        class="v-link clickable dark"> 停诊信息 </span>
      </div>
      <div class="nav-item "><span
        class="v-link clickable dark"> 查询/取消 </span>
      </div>
    </div>
    <!-- 左侧导航 #end -->

    <!-- 右侧内容 #start -->
    <div class="page-container">
      <div class="hospital-home">
        <div class="common-header">
          <div class="title-wrapper"><span
            class="hospital-title">{{ hospital.hosname }}</span>
            <div class="icon-wrapper">
              <span class="iconfont"></span>{{ hospital.param.hostypeString }}
            </div>
          </div>
        </div>
        <div class="info-wrapper">
          <img class="hospital-img" :src="'data:image/jpeg;base64,'+hospital.logoData" :alt="hospital.hosname">
          <div class="content-wrapper">
            <div> 挂号规则</div>
            <div class="line">
              <div><span class="label">预约周期：</span><span>{{ bookingRule.cycle }}天</span></div>
              <div class="space"><span class="label">放号时间：</span><span>{{ bookingRule.releaseTime }}</span></div>
              <div class="space"><span class="label">停挂时间：</span><span>{{ bookingRule.stopTime }}</span></div>
            </div>
            <div class="line"><span class="label">退号时间：</span>
              <span v-if="bookingRule.quitDay == -1">就诊前一工作日{{ bookingRule.quitTime }}前取消</span>
              <span v-if="bookingRule.quitDay == 0">就诊前当天{{ bookingRule.quitTime }}前取消</span>
            </div>
            <div style="margin-top:20px"> 医院预约规则</div>
            <div class="rule-wrapper">
              <ol>
                <li v-for="item in bookingRule.rule" :key="item">{{ item }}</li>
              </ol>
            </div>
          </div>
        </div>
        <div class="title select-title"> 选择科室</div>
        <div class="select-dept-wrapper">
          <div class="department-wrapper">
            <div class="hospital-department">
              <div class="dept-list-wrapper el-scrollbar" style="height: 100%;">
                <div class="dept-list el-scrollbar__wrap" style="margin-bottom: -17px; margin-right: -17px;">
                  <div class="el-scrollbar__view">
                    <div class="sub-item" v-for="(item,index) in departmentVoList" :key="item.id" :class="index == activeIndex ? 'selected' : ''" @click="move(index,item.depcode)"> {{ item.depname }}</div>
                  </div>
                </div>
                <div class="el-scrollbar__bar is-horizontal">
                  <div class="el-scrollbar__thumb" style="transform: translateX(0%);"></div>
                </div>
                <div class="el-scrollbar__bar is-vertical">
                  <div class="el-scrollbar__thumb" style="transform: translateY(0%); height: 91.4761%;"></div>
                </div>
              </div>
            </div>
          </div>
          <div class="sub-dept-container">
            <div v-for="(item,index) in departmentVoList" :key="item.id" :class="index == 0 ? 'selected' : ''" class="sub-dept-wrapper" :id="item.depcode">
              <div class="sub-title">
                <div class="block selected"></div>{{ item.depname }}
              </div>
              <div class="sub-item-wrapper">
                <div v-for="it in item.children" :key="it.id" class="sub-item" @click="schedule(it.depcode)"><span class="v-link clickable">{{ it.depname }} </span></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 右侧内容 #end -->
  </div>
  <!-- footer -->
</template>

<script>
import '~/assets/css/hospital_personal.css'
import '~/assets/css/hospital.css'

import cookie from 'js-cookie'
import hospitalApi from '@/api/hosp/hospital'
// import userInfoApi from '@/api/user/userInfo'

export default {
  data() {
      return {
        hoscode: null,
        activeIndex: 0,

        hospital: {
            param: {}
        },

        bookingRule : {},
        departmentVoList : []
      }
  },
  created() {
      this.hoscode = this.$route.params.hoscode
      this.init()
  },
  methods: {
    init() {
        // 根据医院编号查询详情
        hospitalApi.show(this.hoscode).then(response => {
            this.hospital = response.data.hospital
            this.bookingRule = response.data.bookingRule
        })
        // 根据医院编号查询科室
        hospitalApi.findDepartment(this.hoscode).then(response => {
            this.departmentVoList = response.data
        })
    },

    move(index, depcode) {
      this.activeIndex = index
      document.getElementById(depcode).scrollIntoView();
    },

    schedule(depcode) {
        // 登录判断
        let token = cookie.get('token')
        console.log("token ========================= ")
        console.log(token)
        if (!token) {
            console.log("no token ========================= ")
            loginEvent.$emit('loginDialogEvent')
            return
        }

        //判断认证
        // userInfoApi.getUserInfo().then(response => {
        //     let authStatus = response.data.authStatus
        //     // 状态为2认证通过
        //     if (!authStatus || authStatus != 2) {
        //     window.location.href = '/user'
        //     return
        //     }
        // })
        window.location.href = '/hospital/schedule?hoscode=' + this.hospital.hoscode + "&depcode=" + depcode
    }
  }
}
</script>
