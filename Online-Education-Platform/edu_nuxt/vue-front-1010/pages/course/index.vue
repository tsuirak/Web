<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#" @click="showAll()" :class="{active:this.show==1}">全部</a>
                </li>
                <li v-for="(item,index) in subjectNestedList" :key="item.id">
                  <a :title="item.title" href="#" @click="searchOne(item.id,index)" :class="{active:oneIndex==index}">{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li"> 
              <ul class="clearfix">
                <li v-for="(item,index) in subSubjectList" :key="item">
                  <a :title="item.title" href="#" @click="searchTwo(item.id,index)" :class="{active:twoIndex==index}">{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">

             <li :class="{'current bg-orange':buyCountSort!=''}">
                <a title="销量" href="javascript:void(0);" @click="searchBuyCount()">销量
                <span :class="{hide:buyCountSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':gmtCreateSort!=''}">
                <a title="最新" href="javascript:void(0);" @click="searchGmtCreate()">最新
                <span :class="{hide:gmtCreateSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':priceSort!=''}">
                <a title="价格" href="javascript:void(0);" @click="searchPrice()">价格&nbsp;
                  <span :class="{hide:priceSort==''}">↓</span>
                </a>
              </li>

            </ol>
          </section>
        </div>
        <div class="mt40">

          <section class="no-data-wrap" v-if="data.total==0">
            <!-- <em class="icon30 no-data-ico">&nbsp;</em> -->
            <span class="c-666 fsize14 ml10 vam">该课程暂时还未提供...</span>
          </section>

          <article class="comm-course-list" v-else>
            <ul class="of" id="bna">

              <li v-for="item in data.items" :key="item.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="item.cover" class="img-responsive img" :alt="item.title">
                    <div class="cc-mask">
                      <a :href="'/course/' + item.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>  
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a href="'/course/' + item.id" title="听力口语" class="course-title fsize18 c-333">{{item.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-red" v-if="Number(item.price) === 0">
                      <i class="free">免费</i>
                    </span>
                    <span class="fr jgTag bg-red" v-else>
                      <i class="charge">付费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{item.buyCount}}人正在学习</i>
                      |
                      <i class="c-999 f-fA">{{item.viewCount}}条评论</i>
                    </span>
                  </section>
                </div>
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

          <!-- <a
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
<!-- 
          <a
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
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import courseApi from '@/api/course'

export default {
  data() {
    return{
      page:1, // 当前页
      data:{},  // 课程列表
      subjectNestedList: [], // 一级分类列表
      subSubjectList: [], // 二级分类列表

      searchObj: {}, // 查询表单对象

      oneIndex:-1, // 一级分类
      twoIndex:-1, // 二级分类
      buyCountSort:"",
      gmtCreateSort:"",
      priceSort:"",
      show:""
    }
  },
  created() {
    this.initCourseFirst()
    this.initSubject()
  },
  methods:{
    // 查询第一页数据
    initCourseFirst() {
      courseApi.getCourseList(1,8,this.searchObj)
        .then(response => {
          this.data = response.data.data
          console.log(this.data)
      })
    },
    // 查询所有subject
    initSubject() {
      courseApi.getAllSubject()
        .then(res => {
          this.subjectNestedList = res.data.data.items
        })
    },
    // 分页切换
    goToPage(page) {
      courseApi.getCourseList(page,8,this.searchObj)
        .then(response => {
          this.data = response.data.data
      })
    },
    searchOne(subjectParentId, index) {
      // 把传递index值赋值给oneIndex,为了active样式生效
      this.show=""
      this.oneIndex = index

      this.twoIndex = -1
      this.searchObj.subjectId = ""
      this.subSubjectList = []

      this.searchObj.subjectParentId = subjectParentId
      this.goToPage(1)

      for(let i=0;i<this.subjectNestedList.length;i++) {
        var oneSubject = this.subjectNestedList[i]
        if(subjectParentId == oneSubject.id) {
          this.subSubjectList = oneSubject.children
        }
      }
    },

    searchTwo(subjectId,index) {
      this.show=""
      this.twoIndex = index
      this.searchObj.subjectId = subjectId
      this.goToPage(1)
    },

    // 根据销量排序
    searchBuyCount() {
      this.show=""
      this.buyCountSort = "1"
      this.gmtCreateSort = ""
      this.priceSort = ""

      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;

      this.goToPage(1)
    },

    // 根据发布最新时间排序
    searchGmtCreate() {
      this.show=""
      this.buyCountSort = ""
      this.gmtCreateSort = "1"
      this.priceSort = ""

      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;

      this.goToPage(1)
    },

    // 根据价格排序
    searchPrice() {
      this.show=""
      this.buyCountSort = ""
      this.gmtCreateSort = ""
      this.priceSort = "1"

      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;

      this.goToPage(1)
    },
    // 显示全部
    showAll() {
      console.log("点击执行了showAll()")
      this.show=1
      this.oneIndex=-1, // 一级分类
      this.twoIndex=-1, // 二级分类
      this.buyCountSort="",
      this.gmtCreateSort="",
      this.priceSort="",
      this.searchObj={}
      courseApi.getCourseList(1,8,this.searchObj)
      .then(res => {
        this.data = res.data.data
        console.log("执行成功")
        console.log(this.data)
      })
    }
  }

};
</script>

<style scoped>
  .active {
    /* background: #bdbdbd; */
    color: #da5f3c;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }
  .free {
    color: #3CB371;
    font-family: "Times New Roman", Times, serif;
  }
  .charge {
    color: red;
    font-family: "Times New Roman", Times, serif;
  }
  .img{
    width: 100%;
    height: 15vw;
  }
</style>