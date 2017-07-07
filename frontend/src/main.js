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
<<<<<<< HEAD
import table from "./question/table.vue"
=======
import statistic from "./statistics/questionnaire.vue"
>>>>>>> 790f44bf672589ba416bfdd6f523886ed84b6f68

const routes = [
    {path:'/index',component: index},
    {path:"/", redirect:"/index"},
    {path:"/q", component: test},
    {path:"/q/:id", name:"q", component: questionnaire},
    {path:"/n", component:create},
    {path:"/n/:id", name:"n", component: create},
<<<<<<< HEAD
    {path:"/usermanager", component: table}
=======
    {path:"/s/:id", name:"s", component: statistic}
>>>>>>> 790f44bf672589ba416bfdd6f523886ed84b6f68
]

const router = new VueRouter({
    routes
})

var app = new Vue({
    el: "#app",
    router,
    components:{navbar, message}
})