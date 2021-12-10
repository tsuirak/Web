import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/teacher',
    component: Layout,
    redirect: '/teacher/table',
    name: '教师信息管理',
    meta: { title: '教师信息管理', icon: 'teacher' },
    children: [
      {
        path: 'table',
        name: '教师列表',
        component: () => import('@/views/edu/teacher/list'),
        meta: { title: '教师列表', icon: 'list' }
      },
      {
        path: 'save',
        name: '添加教师',
        component: () => import('@/views/edu/teacher/save'),
        meta: { title: '添加教师', icon: 'insert' }
      },
      {
        path: 'edit/:id',
        name: '编辑教师',
        component: () => import('@/views/edu/teacher/save'),
        meta: { title: '编辑教师', noCache: true },
        hidden: true
      }
    ]
  },
  {
    path: '/subject',
    component: Layout,
    redirect: '/subject/list',
    name: '课程分类管理',
    meta: { title: '课程分类管理', icon: 'course_info' },
    children: [
      {
        path: 'list',
        name: '课程分类列表',
        component: () => import('@/views/edu/subject/list'),
        meta: { title: '课程分类列表', icon: 'list' }
      },
      {
        path: 'save',
        name: '添加课程分类',
        component: () => import('@/views/edu/subject/save'),
        meta: { title: '添加课程分类', icon: 'insert' }
      }
    ]
  },
  {
    path: '/course',
    component: Layout,
    redirect: '/course/list',
    name: '课程管理',
    meta: { title: '课程管理', icon: 'course' },
    children: [
      {
        path: 'list',
        name: '课程列表',
        component: () => import('@/views/edu/course/list'),
        meta: { title: '课程列表', icon: 'list' }
      },
      {
        path: 'info',
        name: '添加课程',
        component: () => import('@/views/edu/course/info'),
        meta: { title: '添加课程', icon: 'insert' }
      },
      {
        path: 'info/:id',
        name: 'EduCourseInfoEdit',
        component: () => import('@/views/edu/course/info'), 
        meta: { title: '编辑课程基本信息', noCache: true }, 
        hidden: true
      }, 
      {
        path: 'chapter/:id',
        name: 'EduCourseChapterEdit',
        component: () => import('@/views/edu/course/chapter'), 
        meta: { title: '编辑课程大纲', noCache: true },
        hidden: true
      }, 
      {
        path: 'publish/:id',
        name: 'EduCoursePublishEdit',
        component: () => import('@/views/edu/course/publish'), 
        meta: { title: '发布课程', noCache: true },
        hidden: true
      }
    ]
  },
  {
    path: '/sta',
    component: Layout,
    redirect: '/sta/table',
    name: '统计分析',
    meta: { title: '统计分析', icon: 'sta' },
    children: [
      {
        path: 'create',
        name: '数据生成',
        component: () => import('@/views/sta/create'),
        meta: { title: '数据生成', icon: 'insert' }
      },
      {
        path: 'show',
        name: '图表显示',
        component: () => import('@/views/sta/show'),
        meta: { title: '图表显示', icon: 'table' }
      },
    ]
  },
  // {
  //   path: '/acl',
  //   component: Layout,
  //   redirect: '/acl/user/list',
  //   name: '权限管理',
  //   meta: { title: '权限管理', icon: 'chart' },
  //   children: [
  //     {
  //       path: 'user/list',
  //       name: '用户管理',
  //       component: () => import('@/views/acl/user/list'),
  //       meta: { title: '用户管理' }
  //     },
  //     {
  //       path: 'role/list',
  //       name: '角色管理',
  //       component: () => import('@/views/acl/role/list'),
  //       meta: { title: '角色管理' }
  //     },
  //     {
  //       path: 'role/form',
  //       name: '角色添加',
  //       component: () => import('@/views/acl/role/form'),
  //       meta: { title: '角色添加' },
  //       hidden: true
  //     },
  //     {
  //       path: 'role/update/:id',
  //       name: '角色修改',
  //       component: () => import('@/views/acl/role/form'),
  //       meta: { title: '角色修改' },
  //       hidden: true
  //     },
  //     {
  //       path: 'role/distribution/:id',
  //       name: '角色权限',
  //       component: () => import('@/views/acl/role/roleForm'),
  //       meta: { title: '角色权限' },
  //       hidden: true
  //     },
  //     {
  //       path: 'menu/list',
  //       name: '菜单管理',
  //       component: () => import('@/views/acl/menu/list'),
  //       meta: { title: '菜单管理' }
  //     },
  //     {
  //       path: 'user/add',
  //       name: '用户添加',
  //       component: () => import('@/views/acl/user/form'),
  //       meta: { title: '用户添加' },
  //       hidden: true
  //     },
  //     {
  //       path: 'user/update/:id',
  //       name: '用户修改',
  //       component: () => import('@/views/acl/user/form'),
  //       meta: { title: '用户修改' },
  //       hidden: true
  //     },
  //     {
  //       path: 'user/role/:id',
  //       name: '用户角色',
  //       component: () => import('@/views/acl/user/roleForm'),
  //       meta: { title: '用户角色' },
  //       hidden: true
  //     }
  //   ]
  // },




  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
