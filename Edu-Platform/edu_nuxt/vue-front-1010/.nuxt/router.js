import Vue from 'vue'
import Router from 'vue-router'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _09540bd0 = () => interopDefault(import('../pages/course/index.vue' /* webpackChunkName: "pages/course/index" */))
const _2bae01c2 = () => interopDefault(import('../pages/login.vue' /* webpackChunkName: "pages/login" */))
const _3721d15d = () => interopDefault(import('../pages/register.vue' /* webpackChunkName: "pages/register" */))
const _1cf307ca = () => interopDefault(import('../pages/teacher/index.vue' /* webpackChunkName: "pages/teacher/index" */))
const _58f5c7fc = () => interopDefault(import('../pages/aliyunplayer/_vid.vue' /* webpackChunkName: "pages/aliyunplayer/_vid" */))
const _e068bb00 = () => interopDefault(import('../pages/course/_id.vue' /* webpackChunkName: "pages/course/_id" */))
const _6d345ce6 = () => interopDefault(import('../pages/order/_oid.vue' /* webpackChunkName: "pages/order/_oid" */))
const _612ac943 = () => interopDefault(import('../pages/teacher/_id.vue' /* webpackChunkName: "pages/teacher/_id" */))
const _20815c08 = () => interopDefault(import('../pages/index.vue' /* webpackChunkName: "pages/index" */))

// TODO: remove in Nuxt 3
const emptyFn = () => {}
const originalPush = Router.prototype.push
Router.prototype.push = function push (location, onComplete = emptyFn, onAbort) {
  return originalPush.call(this, location, onComplete, onAbort)
}

Vue.use(Router)

export const routerOptions = {
  mode: 'history',
  base: decodeURI('/'),
  linkActiveClass: 'nuxt-link-active',
  linkExactActiveClass: 'nuxt-link-exact-active',
  scrollBehavior,

  routes: [{
    path: "/course",
    component: _09540bd0,
    name: "course"
  }, {
    path: "/login",
    component: _2bae01c2,
    name: "login"
  }, {
    path: "/register",
    component: _3721d15d,
    name: "register"
  }, {
    path: "/teacher",
    component: _1cf307ca,
    name: "teacher"
  }, {
    path: "/aliyunplayer/:vid?",
    component: _58f5c7fc,
    name: "aliyunplayer-vid"
  }, {
    path: "/course/:id",
    component: _e068bb00,
    name: "course-id"
  }, {
    path: "/order/:oid?",
    component: _6d345ce6,
    name: "order-oid"
  }, {
    path: "/teacher/:id",
    component: _612ac943,
    name: "teacher-id"
  }, {
    path: "/",
    component: _20815c08,
    name: "index"
  }],

  fallback: false
}

export function createRouter () {
  return new Router(routerOptions)
}
