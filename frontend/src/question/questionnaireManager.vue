<template>
    <div id="tb">
        <h1>问卷管理中心</h1>
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
                        <el-button @click="viewContent(data_item.id)">查看问卷内容</el-button>
                        <el-button v-if="data_item.status == 1">处理举报</el-button>
                        <el-button v-else-if="data_item.status==2" @click="ban(data_item.id, index)">封禁问卷</el-button>
                        <el-button v-else-if="data_item.status==3" @click="unban(data_item.id, index)">解封问卷</el-button>
                    </th>
                </tr>
            </tbody>
        </table>
        <el-table :data="tableData" stripe border style="width: 100%">
            <el-table-column prop="id" label="ID" width="180"></el-table-column>
            <el-table-column prop="title" label="问卷标题" width="180"></el-table-column>
            <el-table-column prop="date" label="创建时间" width="180"></el-table-column>
            <el-table-column prop="date" label="创建用户" width="180"></el-table-column>
            <el-table-column prop="date" label="状态" width="180"></el-table-column>
        </el-table>
        <div class="container">
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="pageIndex"
                layout="prev, pager, next, jumper"
                :total="pageLength">
            </el-pagination>
        </div>
        <el-dialog
            title="操作"
            :visible.sync="dialogVisible"
            size="tiny"
            :before-close="handleClose">
            <span>请做出你的决定</span><br>
            <el-radio class="radio" v-model="radio" label="1">举报通过</el-radio><br>
            <el-radio class="radio" v-model="radio" label="2">举报不通过</el-radio>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="manageDecision()">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog
            title="问卷举报信息"
            :visible.sync="infoVisible"
            size="tiny"
            :before-close="handleClose">
            <span>以下为问卷举报信息</span><br>
            <span>{{reportInfo}}</span>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="infoVisible=false">关 闭</el-button>
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
            reportInfo : {},
            radio : 1
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
                
            },

            manageDecision() {
                var self = this;
                self.dialogVisible = false;
                console.log(self.radio);
            },

            ban(data, index){
                this.$confirm('此操作会改变问卷的状态, 您确定继续吗?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'danger'
                }).then(() => {
                    $.ajax({
                        type:"PUT",
                        url: "user/ID=" + ID,
                        data: {"role" : 1},
                        success: data=>{
                            if(data == '1' || data == 1) {
                                var self = this;
                                this.$message.success("封禁成功！");
                            }
                            else
                                this.$message.warning("网络传输异常！");
                        }
                    });
                })
            },

            unban(id, index) {
                this.$confirm('此操作会改变问卷的状态, 您确定继续吗?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'danger'
                }).then(() => {

                });
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