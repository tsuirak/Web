<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333"></span>
        </h2>
        <section class="c-tab-title">
          <a id="subjectAll" title="全部" href="#"> </a>
          <!-- <c:forEach var="subject" items="${subjectList }">
                            <a id="${subject.subjectId}" title="${subject.subjectName }" href="javascript:void(0)" onclick="submitForm(${subject.subjectId})">${subject.subjectName }</a>
          </c:forEach> -->
        </section>
      </header>
      <section class="c-sort-box unBr">
        <div>
          <!-- 无数据提示 -->
          <section class="no-data-wrap" v-if="data.total=0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>

          <article class="i-teacher-list" v-else>
            <ul class="of">
              <li v-for="teacher in data.records" :key="teacher.id">
                <section class="i-teach-wrap">
                  <div class="i-teach-pic">
                    <a :href="'/teacher/'+teacher.id" :title="teacher.name" target="_blank">
                      <img :src="teacher.avatar" alt>
                    </a>
                  </div>
                  <div class="mt10 hLh30 txtOf tac">
                    <a href="'/teacher/'+teacher.id" :title="teacher.name" target="_blank" class="fsize18 c-666">{{teacher.name}}</a>
                  </div>
                  <div class="hLh30 txtOf tac">
                    <span class="fsize14 c-999">{{teacher.intro}}</span>
                  </div>
                  <div class="mt15 i-q-txt">
                    <p class="c-999 f-fA">{{teacher.career}}</p>
                  </div>
                </section>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>

      <!-- 公共分页 开始 -->
      <div>
        <div class="paging">
          <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
          <!-- <a
            :class="{undisable: !data.hasPrevious}"
            href="#"
            title="首页"
            @click.prevent="gotoPage(1)">首页</a> -->
<!-- 
          <a
            :class="{undisable: !data.hasPrevious}"
            href="#"
            title="前一页"
            @click.prevent="goToPage(data.current-1)">&lt;</a> -->

          <a
            v-for="page in data.pages"
            :key="page"
            :class="{current: data.current == page, undisable: data.current == page}"
            :title="'第'+page+'页'"
            href="#"
            @click.prevent="goToPage(page)">{{ page }}</a>

          <!-- <a
            :class="{undisable: !data.hasNext}"
            href="#"
            title="后一页"
            @click.prevent="goToPage(data.current+1)">&gt;</a> -->

          <!-- <a
            :class="{undisable: !data.hasNext}"
            href="#"
            title="末页"
            @click.prevent="gotoPage(data.pages)">末页</a> -->
          <div class="clear"/>
        </div>
      </div>
      <!-- 公共分页 结束 -->

      </section>
    </section>
    <!-- /讲师列表 结束 -->
  </div>
</template>
<script>

import teacher from '@/api/teacher'

export default {
  data() {
    return {
      data: {}
    }
  },
  created() {
    this.showTeacherList()
  },
  methods:{
    // 教师列表
    showTeacherList() {
      teacher.getTeacherList(1, 8)
        .then(res => {
          this.data = res.data.data
        })
    },
    // 分页切换
    goToPage(page) {
      teacher.getTeacherList(page, 8)
        .then(res => {
          this.data = res.data.data
        })
    }
  }
};
</script>

