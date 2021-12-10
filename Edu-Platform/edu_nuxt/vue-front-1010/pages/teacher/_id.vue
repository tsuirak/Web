<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师介绍 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">讲师介绍</span>
        </h2>
      </header>
      <div class="t-infor-wrap">
        <!-- 教师基本信息 -->
        <section class="fl t-infor-box c-desc-content">
          <div class="mt20 ml20">
            <section class="t-infor-pic">
              <img :src="teacherInfo.avatar">
            </section>
            <h3 class="hLh30">
              <span class="fsize24 c-333">{{teacherInfo.name}}&nbsp;{{ teacherInfo.level === 1 ? '高级教师':'首席教师'}}</span>
            </h3>
            <section class="mt10">
              <span class="t-tag-bg">{{teacherInfo.intro}}</span>
            </section>
            <section class="t-infor-txt">
              <p
                class="mt20"
              >{{teacherInfo.career}}</p>
            </section>
            <div class="clear"></div>
          </div>
        </section>
        <div class="clear"></div>
      </div>
      <section class="mt30">
        <div>
          <header class="comm-title all-teacher-title c-course-content">
            <h2 class="fl tac">
              <span class="c-333">主讲课程</span>
            </h2>
            <section class="c-tab-title">
              <a href="javascript: void(0)">&nbsp;</a>
            </section>
          </header>

          <!-- 无数据提示 -->
          <section class="no-data-wrap" v-if="courseList.length==0">
            <span class="c-666 fsize14 ml10 vam">该讲师还未发布任何课程...</span>
          </section>
          
          <article class="comm-course-list" v-else>
            <ul class="of">
              <li v-for="course in courseList" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="course.cover" class="img-responsive" >
                    <div class="cc-mask">
                      <a href="#" title="开始学习" target="_blank" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a href="#" :title="course.title" target="_blank" class="course-title fsize18 c-333">{{course.title}}</a>
                  </h3>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
      </section>
    </section>
    <!-- /讲师介绍 结束 -->
  </div>
</template>
<script>

import teacherApi from '@/api/teacher'

export default {
  data() {
    return {
      teacherInfo: [],
      courseList: []
    }
  },
  created() {
    this.showTeacherInfo(this.$route.params.id)
  },
  methods: {
    showTeacherInfo(id) {
      teacherApi.getTeacherInfo(id)
        .then(res => {
          this.teacherInfo = res.data.data.teacherInfo
          this.courseList = res.data.data.courseList

          console.log(this.teacherInfo)
          console.log(this.courseList)
        })
    }
  }
};
</script>