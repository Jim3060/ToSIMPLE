import Vue from "vue";
import VueRouter from "vue-router";
import ElementUI from "element-ui";
import "element-ui/lib/theme-default/index.css";
import VChart from "v-charts";

Vue.use(VueRouter);
Vue.use(ElementUI);
Vue.use(VChart);

import index from "./index.vue";
import navbar from "./navbar.vue";
import message from "./message.vue";
import questionnaire from "./question/questionnaire.vue";
import create from "./question/createQuestionnaire.vue";
import userManager from "./question/userManager.vue";
import questionnaireManager from "./question/qmOfm.vue";
import statistic from "./statistics/questionnaire.vue";
import qView from "./question/questionnairesView.vue";
import myQuestionnaire from "./manage/myquestionnaires.vue";
import backup from "./manage/backupManage.vue"


const routes = [
    {path:"/index",component: index},
    {path:"/", redirect:"/index"},
    {path:"/q", component: qView},
    {path:"/q/:id", name:"q", component: questionnaire},
    {path:"/n", component:create},
    {path:"/n/:id", name:"n", component: create},
    {path:"/userManager", component: userManager},
    {path:"/questionnaireManager", component : questionnaireManager},
    {path:"/s/:id", name:"s", component: statistic},
    {path:"/search/:name", name:"search", component: qView},
    {path:"/m/q", component: myQuestionnaire},
    {path:"/r/:id", name: "r", component: questionnaire},
    {path:"/backup", component: backup}
];

const router = new VueRouter({
    routes
});

new Vue({
    el: "#app",
    router,
    components:{navbar, message}
});
