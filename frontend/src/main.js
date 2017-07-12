import Vue from "vue";
import VueRouter from "vue-router"
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import VCharts from "v-charts"
Vue.use(VueRouter)
Vue.use(ElementUI)
Vue.use(VCharts)

import test from "./test.vue"
import index from "./index.vue"
import navbar from "./navbar.vue"
import message from "./message.vue"
import questionnaire from "./question/questionnaire.vue"
import create from "./question/createQuestionnaire.vue"
import userManager from "./question/userManager.vue"
import questionnaireManager from "./question/questionnaireManager.vue"
import statistic from "./statistics/questionnaire.vue"
import qView from "./question/questionnairesView.vue"


const routes = [
    {path:'/index',component: index},
    {path:"/", redirect:"/index"},
    {path:"/q", component: test},
    {path:"/q/:id", name:"q", component: questionnaire},
    {path:"/n", component:create},
    {path:"/n/:id", name:"n", component: create},
    {path:"/userManager", component: userManager},
    {path:"/questionnaireManager", component : questionnaireManager},
    {path:"/s/:id", name:"s", component: statistic},
    {path:"/search/:name", name:"search", component: qView}
]

const router = new VueRouter({
    routes
})

var app = new Vue({
    el: "#app",
    router,
    components:{navbar, message}
})