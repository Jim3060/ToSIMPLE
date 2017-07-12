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
                    <th v-else>已发布</th> 
                    <th>
                        <el-button type="text" @click="viewContent(data_item.id)">查看内容</el-button>
                        <el-button v-if="data.status==1" type="text" @click="selectStatus(data_item.id, index)">处理举报</el-button>
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
            title="修改问卷状态"
            :visible.sync="infoVisible"
            size="tiny"
            :before-close="handleClose">
            <span>请做出你的决定</span><br>
            <el-radio class="radio" v-model="status" label="1">发布问卷</el-radio><br>
            <el-radio class="radio" v-model="status" label="2">回收问卷</el-radio><br>
            <el-radio class="radio" v-model="status" label="3">删除问卷</el-radio>
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
            questionnaires : [{"id" : 1, "title" : "investigation", "date" : "2017-07-12", "user" : "Zhu", "status" : 0},
            {"id" : 1, "title" : "interview", "date" : "2017-07-13", "user" : "Zhu", "status" : 1}],
            dialogVisible : false,
            infoVisible : false,
            questionnaireId : {},
            /* The status of the questionnaire */
            status : {},
            pageLength : {}
        }},
        methods:{
            ban(ID, index) {
                this.$confirm('此操作会改变用户的状态, 您确定继续吗?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'danger'
                }).then(() => {
                    $.ajax({
                        type:"PUT",
                        url: "user/userId=" + ID,
                        data: {"role" : 2},
                        success: data=>{
                            if(data == '1' || data == 1) {
                                var self = this;
                                this.data[index].role = 2;
                                this.$message.success("封禁用户成功");
                            }
                            else
                                this.$message.warning("网络传输异常！");
                        }
                    });
                })  
            },

            viewContent(id) {

                this.$router.push({path:"/index"});
            },

            manageDecision() {
                var self = this;
                self.dialogVisible = false;
                console.log(self.radio);
            },

            handleCurrentChange(val) {
                var self = this;
                self.pageIndex = val;
                $.ajax({
                    type:"GET",
                    url:"allUser/page=" + (self.pageIndex - 1) + "&size=" + self.pageSize,
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
                url:"allUser?page=" + self.pageIndex + "&size=" + self.pageSize,
                success: data=>{
                    self.users = data.users;
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