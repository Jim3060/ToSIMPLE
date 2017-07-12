<template>
    <div id="tb">
        <h2 style="width:100%;text-align:center">问卷管理中心</h2>
        <h4 style="width:100%;text-align:center">{{title}}</h4>
        <br>
        <div style="width:100%;text-align:center">
            <el-button :plain="true" type="info" size="mini" @click="ViewAllReportQuestionnaires(1, 30)">未处理举报问卷</el-button>
            <el-button :plain="true" type="info" size="mini" @click="ViewAllQuestionnaires(1, 30)">所有问卷</el-button>
        </div>
        <br>
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
                    <th v-else-if="data_item.status==2">已回收</th>
                    <th v-else-if="data_item.status==3">被举报/已发布</th>
                    <th v-else-if="data_item.status==4">违规</th>
                    <th v-else>被举报/已回收</th>
                    <th>
                        <el-button type="text" size="small" @click="viewContent(data_item.id)">查看内容</el-button>
                        <el-button v-if="data_item.status==3 || data_item.status==5" type="text" 
                            size="small" @click="handle(data_item.id, index, 1, 2)">处理举报</el-button>
                        <el-button v-if="data_item.status==3 || data_item.status==5" type="text" 
                            style="width:100%;text-align:left" 
                            size="small" @click="ViewReport(data_item.id, 1, 2)">查看举报信息</el-button>
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
            size="tiny">
            <h5>举报内容</h5><br>
            <table class="table table-striped table-condensed table-bordered">
                <tbody>
                    <tr>
                        <th>提交时间</th>
                        <th>举报内容</th>
                        <th>操作</th>
                    </tr>
                    <tr v-for="(item, index) in reportInfo" :key="item">
                        <th>{{item.createTime}}</th>
                        <th>{{item.content}}</th>
                        <th>
                            <el-button type="text" size="small" @click="viewContent(data_item.id)">查看内容</el-button>
                        </th>
                    </tr>
                </tbody>
            </table>
            <h5>请做出你的决定</h5><br>
            <el-radio class="radio" v-model="status" label="1">举报通过</el-radio><br>
            <el-radio class="radio" v-model="status" label="0">举报不通过</el-radio>
            <span slot="footer" class="dialog-footer">
                <el-button @click="infoVisible = false">取 消</el-button>
                <el-button type="primary" @click="manageDecision()">确 定</el-button>
            </span>
            <div class="container" style="width:85%;text-align:right">
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="reportCurrentPage"
                layout="prev, pager, next, jumper"
                :total="reportPageLength">
            </el-pagination>
            </div>
        </el-dialog>

        <el-dialog
            title="处理意见"
            :visible.sync="dialogVisible"
            size="tiny">
            <h5>请做出你的决定</h5><br>
            <el-radio class="radio" v-model="status" label="1">举报通过</el-radio><br>
            <el-radio class="radio" v-model="status" label="0">举报不通过</el-radio>
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
            /* 处理意见 */
            status : {},
            pageLength : {},
            reportInfo : {},
            reportNum : {},
            reportPageLength : 0,
            reportCurrentPage : {},
            /* The index in the array of questionnaires of the current questionnaire */
            infoIndex : {},
            /* 0 : 显示所有问卷， 1 : 显示所有未处理举报的问卷 */
            displayType : {},
            title : "查看所有问卷",
            pageLength : 0,
            pageIndex : {},
        }},

        created() {
            var self = this;
            self.pageSize = 30; // The defaul size of page is 30
            self.pageIndex = 0;
            this.ViewAllQuestionnaires(1, 30);
        },

        methods:{

            ViewAllQuestionnaires(page, pageSize) {
                var self = this;
                $.ajax({
                    type : "GET",
                    url : "allUnhandledReports?page=" + (page-1) + "&pageSize=" + (pageSize),
                    data : "",
                    dataType : "json",
                    suceess : data=>{
                        self.questionnaires = data;
                        self.displayType = 1;
                        self.title = "查看所有问卷";
                        self.pageIndex = page;
                        self.pageLength = data.num / pageSize + 1;
                        this.$message.success("加载成功！");
                    }
                });
            },

            ViewAllReportQuestionnaires(page, pageSize) {
                var self = this;
                $.ajax({
                    type : "GET",
                    url : "questionnaireReported?page=" + (page-1) + "&pageSize=" + (pageSize),
                    data : "",
                    dataType : "json",
                    suceess : data=>{
                        console.log(1);
                        self.questionnaires = data.questionnaires;
                        console.log(self.questionnaires);
                        self.displayType = 1;
                        self.title = "查看被举报问卷";
                        self.pageIndex = page;
                        self.pageLength = data.num / pageSize + 1;
                        this.$message.success("加载成功！");
                    }
                });
            },

            /* View the report of the unhandle questionnaire */
            ViewReport(ID, page, pageSize) {
                var self = this;
                self.infoVisible = true;
                self.status = -1;
                $.ajax({
                    type:"GET",
                    url: "allUnhandledReports/questionnaireId=" + ID,
                    data: {"page" : page, "pageSize" : pageSize},
                    dataType : "json",
                    success: data=>{
                        self.reportInfo = data.reports;
                        self.reportNum = data.reportNum;
                    }
                });
            },

            /* Handle the report of the questionnaire */
            handle(ID, index) {
                var self = this;
                self.infoIndex = index;
                self.dialogVisible = true;
                self.status = -1;
            },

            /* View the content of the questionnaire */
            viewContent(ID) {
                this.$router.push({path:"/index"});
            },

            /* Manager change the status of the questionnaire */
            manageDecision() {
                var self = this;
                var status = self.status;

                /* If the mananager doesn't choose any status */
                if (status == -1) {
                    this.$message.warning("请做出你的选择！");
                    return;
                }

                this.$confirm('此操作会改变的状态, 您确定继续吗?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'danger'
                }).then(() => {
                    self.dialogVisible = false;
                    var index = self.infoIndex;
                    var target_status;

                    /* 成功与否的标记 */
                    var success1 = false;
                    var success2 = false;

                    /* 举报未通过*/
                    if (status == 0) {
                        if (self.questionnaires[index].status == 3)
                            target_status = 1;

                        else
                            target_status = 2;
                    }

                    /* 通过举报 */
                    else
                        target_status = 4;

                    $.ajax({
                        type:"POST",
                        url: "setQuestionnaireStatus",
                        data: {"status" : target_satus, "questionnaireId" : self.questionnaires[index].id},
                        dataType : "json",
                        success: data=>{
                            if(data.success == '1' || data.success == 1) {
                                self.questionnaires[index].status = target_status;
                                success1 = true;
                            }
                            else
                                this.$message.warning("失败！");
                                return;
                        }
                    });

                    $.ajax({
                        type: "POST",
                        url : "reportHandled",
                        data : {"questionnaireId" : self.questionnaires[index].id},
                        dataType : "json",
                        success : data=> {
                            if (data == "1" || data == 1) {
                                this.$message.success("处理举报成功！")
                            }
                            else
                                this.$message.warning("改变举报信息时出现异常！");
                        }
                    });
                }); 
            },

            handleCurrentChange(val) {
                var self = this;
                self.pageIndex = val;
                if (self.dataType ==  0)
                    this.ViewAllQuestionnaires(val, 30)
                else
                    this.ViewAllReportQuestionnaires(val, 30);
            }
        }
    }
</script>

<style>
    table{width:80% !important; margin-left:10%;}
    th, td, td>input{text-align: center; width:150px !important;}
</style>