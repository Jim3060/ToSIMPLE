<template>
    <div id="tb">
        <h3 style="width:100%;text-align:center">问卷管理中心</h3>
        <table class="table table-striped table-condensed table-bordered">
            <tbody>
                <tr>
                    <th>序号</th>
                    <th>ID</th>
                    <th>问卷标题</th>
                    <th>创建时间</th>
                    <th>创建用户</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                <tr v-for="(data_item, index) in questionnaires" :key="data_item">
                    <th>{{index + 1}}</th>
                    <th>{{data_item.id}}</th>
                    <th>{{data_item.title}}</th>
                    <th>{{data_item.date}}</th>
                    <th>{{data_item.user}}</th>
                    <th v-if="data_item.status==0">未发布</th>
                    <th v-else-if="data_item.status==1">已发布</th>
                    <th v-else-if="data_item.status==2">未发布</th>
                    <th v-else-if="data_item.status==3">被举报</th>
                    <th v-else-if="data_item.status==4">违规</th>
                    <th>
                        <el-button type="text" @click="viewContent(data_item.id)">查看内容</el-button>
                        <el-button v-if="data_item.status==3" type="text" @click="ViewReport(data_item.id, index)">处理举报</el-button>
                    </th>
                </tr>
            </tbody>
        </table>
        <div class="container" style="width:85%;text-align:right">
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="pageIndex"
                layout="prev, pager, next, jumper"
                :total="pageLength">
            </el-pagination>
        </div>
        <el-dialog
            title="问卷举报信息"
            :visible.sync="infoVisible"
            size="tiny"
            :before-close="handleClose">
            <h5>举报内容</h5><br>
            <p>{{reportInfo}}</p>
            <h5>请做出你的决定</h5><br>
            <el-radio class="radio" v-model="status" label="4">举报通过</el-radio><br>
            <el-radio class="radio" v-model="status" label="2">举报不通过</el-radio>
            <span slot="footer" class="dialog-footer">
                <el-button @click="infoVisible = false">取 消</el-button>
                <el-button type="primary" @click="manageDecision()">确 定</el-button>
            </span>
        </el-dialog>
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
            questionnaires : [{"id" : 3, "title" : "investigation", "date" : "2017-07-12", "user" : "Zhu", "status" : 3},
            {"id" : 1, "title" : "interview", "date" : "2017-07-13", "user" : "Zhu", "status" : 4}],
            dialogVisible : false,
            infoVisible : false,
            questionnaireId : {},
            /* The status of the questionnaire */
            status : {},
            pageLength : {},
            reportInfo : "fdsfdsfdsfdsfdsds"
        }},
        methods:{

            ViewReport(ID, index) {
                var self = this;
                self.infoVisible = true;
                $.ajax({
                    type:"GET",
                    url: "user/userId=" + ID,
                    data: {"role" : 2},
                    success: data=>{
                        if(data == '1' || data == 1) {
                            var self = this;
                            this.data[index].role = 2;
                            this.$message.success("处理问卷举报成功！");
                        }
                        else
                            this.$message.warning("网络传输异常！");
                    }
                });
            },

            viewContent(ID) {
                this.$router.push({path:"/index"});
            },

            manageDecision() {
                var self = this;
                this.$confirm('此操作会改变的状态, 您确定继续吗?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'danger'
                }).then(() => {
                    self.dialogVisible = false;
                    Vue.set(self.questionnaires[index], "status", self.status);
                    self.status = -1;
                    /*
                    $.ajax({
                        type:"PUT",
                        url: "user/userId=" + ID,
                        data: {"role" : 2},
                        success: data=>{
                            if(data == '1' || data == 1) {
                                var self = this;
                                this.data[index].role = 2;
                                this.$message.success("处理问卷举报成功！");
                            }
                            else
                                this.$message.warning("网络传输异常！");
                        }
                    });*/
                }); 
            },

            handleCurrentChange(val) {
                var self = this;
                self.pageIndex = val;
                $.ajax({
                    type:"GET",
                    url:"allQuestionnaire/page=" + (self.pageIndex - 1) + "&pageSize=" + self.pageSize,
                    success: data=>{
                        
                    }
                });
            }
        },
        created() {
            var self = this;
            self.pageSize = 30; // The defaul size of page is 30
            self.pageIndex = 0;

            $.ajax({
                type:"GET",
                url:"allQuestionnaire?page=" + self.pageIndex + "&pageSize=" + self.pageSize,
                dataType : "json",
                success: data=>{
                    self.questionnaires = data.questionnaires;
                    self.userNum = data.userNum;
                    self.pageLength = self.userNum / self.pageSize + 1;
                }
            });
            
        }
    }
</script>

<style>
    table{width:80% !important; margin-left:10%;}
    th, td, td>input{text-align: center; width:150px !important;}
</style>