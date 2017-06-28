import Vue from "vue";
import VueRouter from "vue-router"
Vue.use(VueRouter)

import test from "./test.vue"
import navbar from "./navbar.vue"
import create from "./question/createQuestionnaire.vue"

const routes = [
    {path:'/index',component:test},
    {path:"/", redirect:"/index"},
    {path:"/q/:id", name:"q", component:test},
    {path:"/n", component:create}
]

const router = new VueRouter({
    routes
})

var app = new Vue({
    el: "#app",
    router,
    components:{navbar}
})