<template>
  <!-- header -->
  <div class="nav-container page-component">
    <!--左侧导航 #start -->
    <div class="nav left-nav">
      <div class="nav-item ">
        <span class="v-link clickable dark" :onclick="'javascript:window.location=\'/hospital/'+hospital.hoscode+'\''">预约挂号 </span>
      </div>
      <div class="nav-item ">
        <span class="v-link clickable dark" :onclick="'javascript:window.location=\'/hospital/detail/'+hospital.hoscode+'\''"> 医院详情 </span>
      </div>
      <div class="nav-item selected">
        <span class="v-link selected dark" :onclick="'javascript:window.location=\'/hospital/notice/'+hospital.hoscode+'\''"> 预约须知 </span>
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
      <div class="hospital-notice">
        <div class="content"><h2>{{ hospital.hosname }}预约挂号须知</h2>
          <p>为方便您早日就医康复，请您认真阅读预约挂号须知：</p>
          <h4 id="一、预约实名制：">一、预约实名制：</h4>
          <p>统一平台电话预约和网上预约挂号均采取实名制注册预约，请您如实提供就诊人员的真实姓名、有效证件号（身份证、护照）、性别、手机号码、社保卡号等基本信息。</p>
          <h4 id="二、预约挂号：">二、预约挂号：</h4>
          <p>按照北京市卫健委统一平台要求，预约挂号规则如下：</p>
          <ul>
            <li>在同一自然日，同一医院，同一科室，同一就诊单元，同一就诊人，可以预约最多1个号源；</li>
            <li>在同一自然周，同一就诊人，可以预约最多8个号源；</li>
            <li>在同一自然月，同一就诊人，可以预约最多12个号源；</li>
            <li>在同一自然季度，同一就诊人，可以预约最多24个号源。</li>
          </ul>
          <h4 id="三、取消预约：">三、取消预约：</h4>
          <p>已完成预约的号源，如需办理退号，至少在就诊前一工作日14:00前通过网站、微信公众号、114电话等平台预约渠道进行取消预约。</p>
          <h4 id="四、爽约处理：">四、爽约处理：</h4>
          <p>如预约成功后患者未能按时就诊且不办理取消预约号视为爽约，同一患者在自然年内爽约规则如下：</p>
          <ul>
            <li>累计爽约3次，自3次爽约日起，90天内不允许通过114平台进行预约挂号；</li>
            <li>累计爽约6次，自6次爽约日起，180天内不允许通过114平台进行预约挂号。</li>
          </ul>
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
import hospitalApi from '@/api/hosp/hospital'
export default {
  data() {
    return {
      hoscode: null,
      hospital: {
        param: {}
      }
    }
  },

  created() {
    this.hoscode = this.$route.params.hoscode

    this.init()
  },

  methods: {
    init() {
      hospitalApi.show(this.hoscode).then(response => {
        this.hospital = response.data.hospital
      })

    }
  }
}
</script>
<style>
  .hospital-detail .info-wrapper {
    width: 100%;
    padding-left: 0;
    padding-top: 0;
    margin-top: 0;
    flex-direction: inherit;
  }

  .hospital-detail .info-wrapper .text {
    font-size: 14px;

  }

  .hospital-detail .content-wrapper p {
    text-indent: 0;
  }
</style>
