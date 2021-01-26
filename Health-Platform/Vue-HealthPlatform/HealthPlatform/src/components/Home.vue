<template>
  <!-- 引入containner布局 -->
  <el-container class="home-container">
    <!-- 头部 -->
    <el-header>
      <div>
        <img src="../assets/octopus.png" alt="" />
        <span>健康管理平台</span>
      </div>
      <el-button type="info" @click="logout">安全退出</el-button>
    </el-header>
    <!-- 主体 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isCollapse?'64px':'130px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <el-menu background-color="#545c64" text-color="#fff" actice-text-color="#409eff"
        :collapse="isCollapse" :collapse-transition="false" :router="true" :default-active="activePath">
            <!-- 一级菜单 -->
          <el-submenu :index="item.id+''" v-for="item in menuList" :key="item.id">
            <template slot="title">
              <i :class="iconsObject[item.id]"></i>
              <span>{{item.title}}</span>
            </template>
            <!-- 二级菜单 -->
            <el-menu-item :index="it.path" v-for="it in item.sList" :key="it.id" @click="saveNavState(it.path)">
              <template slot="title">
                <i :class="iconsObject[it.id]"></i>
                <span>{{it.title}}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!-- 主体内容 -->
      <el-main>
          <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
    data(){
        return{
            // 菜单列表
            menuList:[],
            isCollapse:false, // 伸缩
            iconsObject: {
                '100':'iconfont icon-guanliyuan',
                '200':'iconfont icon-yundong',
                '101':'iconfont icon-login',
                '102':'iconfont icon-mima',
                '103':'iconfont icon-yundong',
                '104':'iconfont icon-shangpin',
                '201':'iconfont icon-shu',
                '202':'iconfont icon-qialuli',
                '203':'iconfont icon-shiwu',
                '204':'iconfont icon-login',
            },
            activePath:'/welcome', // 默认路径
        }

    },
    created(){
        // 查询menuList
        this.getMenuList();
        this.activePath = window.sessionStorage.getItem("activePath"); // 取出session中的path 动态修改 activePath
    },
  methods: {
    // 安全退出
    logout() {
      window.sessionStorage.clear(); // 清楚session
      this.$router.push("/login"); // 返回登录页面
    },
    // 获取导航菜单方法
    async getMenuList(){
        const {data:res} = await this.$http.get("/menus");
        console.log(res);
        if(res.flag != 200) return this.$message.error("获取失败"); // 访问失败
        this.menuList = res.menus; // 访问成功后回填数据
    },
    // 控制菜单伸缩
    toggleCollapse(){
        this.isCollapse = !this.isCollapse;
    },
    // 保存路径
    saveNavState(activePath){
        window.sessionStorage.setItem("activePath",activePath); // 存在在session中
        this.activePath = activePath;
    }
  }
};
</script>
<style lang="less" scoped>
// 布局样式
.home-container {
  height: 100%;
}

// 头部样式
.el-header {
  background-color: #2b4b6b;
  display: flex;
  justify-content: space-between; // 左右贴边
  padding-left: 0%; // 左边界
  align-items: center;
  color: #fff;
  font-size: 20px;
  display: flex;
  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15x;
    }
  }
  align-items: center;
  span {
    margin-left: 15px;
  }
}

// 侧边栏样式
.el-aside {
  background-color: #2b4b6b;
  .el-menu{
      border-right: none;
  }
}

// 主体样式
.el-main {
  background-color: #eaedf1;
}

img {
  width: 60px;
  height: 60px;
}

// 按钮样式
.toggle-button{
    background-color: #2b4b6b;
    font-size: 10px;
    line-height: 24px;
    color: #fff;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer; // 显示手
}
</style>