<template>
    <div id="tb">
        <h2 style="width:100%;text-align:center">问卷管理中心</h2>
        <h4 style="width:100%;text-align:center">未处理的举报</h4>
        <br>
    </el-radio-group>
        <br>
        <table class="table table-striped table-condensed table-bordered">
            <tbody>
                <tr>
                    <th>问卷ID</th>
                    <th>举报信息</th>
                    <th>操作</th>
                </tr>
                <tr v-for="(data_item, index) in questionnaires" :key="data_item">
                    <th>
                        <el-button type="text" size="small" @click="viewContent(data_item.questionnaireId)">查看内容</el-button>
                        <el-button v-if="data_item.status==3 || data_item.status==5" type="text" 
                            size="small" @click="handle(data_item.questionnaireId, index, 1, 2)">通过</el-button>
                        <el-button v-if="data_item.status==3 || data_item.status==5" type="text" 
                            size="small" @click="handle(data_item.questionnaireId, index, 1, 2)">不通过</el-button>
                        <el-button v-if="data_item.status==3 || data_item.status==5" type="text" 
                            style="width:100%;text-align:left" 
                            size="small" @click="ViewReport(data_item.questionnaireId, 1, 1)">查看举报信息</el-button>
                    </th>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import Vue from "vue";
    //import BootStrapVue from "bootstrap-vue";
    //Vue.use(BootStrapVue);

    //import {bAlert, bModal} from 'bootstrap-vue/lib/components'
    import {modal, alert} from "vue-strap"

    export default {
        data(){return {
            status : {},
            report: {},
            reportNum : {}
        }},

        created() {
            var self = this;
            self.pageSize = 30; // The defaul size of page is 30
            self.pageIndex = 0;
            this.ViewAllQuestionnaires(1, 30);
        },

        watch:{
            'radio3': "handleTableChange"
        },

        methods:{
        }
    }
</script>

<style>
    table{width:80% !important; margin-left:10%;}
    th, td, td>input{text-align: center; width:150px !important;}
</style>