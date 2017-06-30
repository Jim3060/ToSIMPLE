import Vue from "vue";
import VueRouter from "vue-router"
Vue.use(VueRouter)

import test from "./test.vue"
import index from "./index.vue"
import navbar from "./navbar.vue"
import message from "./message.vue"
import questionnaire from "./question/questionnaire.vue"
import create from "./question/createQuestionnaire.vue"

const routes = [
    {path:'/index',component: index},
    {path:"/", redirect:"/index"},
    {path:"/q", component: test},
    {path:"/q/:id", name:"q", component: questionnaire},
    {path:"/n", component:create},
    {path:"/n/:id", name:"n", component: create}
]

const router = new VueRouter({
    routes
})

var app = new Vue({
    el: "#app",
    router,
    components:{navbar, message}
})