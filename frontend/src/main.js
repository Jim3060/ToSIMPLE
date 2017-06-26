import Vue from "vue";
import VueRouter from "vue-router"
Vue.use(VueRouter)

import test from "./test.vue"

const routes = [
    {path:'/index',component:test},
    {path:"/", redirect:"/index"}
]

const router = new VueRouter({
    routes
})

var app = new Vue({
    el: "#app",
    router
})