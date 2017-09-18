<template>
    <div id="tb">
        <h3 style="width:100%;text-align:center">用户管理中心</h3>
        <table class="table table-striped table-condensed table-bordered">
            <tbody>
                <tr>
                    <th>序号</th>
                    <th>ID</th>
                    <th>状态</th>
                    <th>用户名</th>
                    <th>邮箱</th>
                    <th>操作</th>
                </tr>
                <tr v-for="(data_item, index) in users" :key="data_item">
                    <th>{{index + 1}}</th>
                    <th>{{data_item.id}}</th>
                    <th v-if="data_item.role==2">封禁用户</th>
                    <th v-else-if="data_item.role==0">普通用户</th>
                    <th v-else>系统管理员</th>
                    <th>{{data_item.userName}}</th>
                    <th>{{data_item.email}}</th>
                    <th>
                        <el-button v-if="data_item.role==2" type="text" @click="unban(data_item.id, index)">解封用户</el-button>
                        <el-button v-if="data_item.role==0" type="text" @click="ban(data_item.id, index)">封禁用户</el-button>
                        <el-button v-if="data_item.role==0" type="text" @click="set_manager(data_item.id, index)">设管理员</el-button>
                        <el-button type="text" @click="delete_user(data_item.id, index)">删除用户</el-button>
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
    </div>
</template>

<script>
    import Vue from "vue";

    export default {
        data(){return {
            users: [],
            datachanged:[],
            pageIndex : {default(){return 1;}},
            pageLength : {default(){return 0;}},
            pageSize : {},
            userNum : {}
        };},
        methods:{
            ban(ID, index) {
                this.$confirm("此操作会封禁此用户, 您确定继续吗?", "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "danger"
                }).then(() => {
                    $.ajax({
                        type:"POST",
                        url: "user/role", 
                        data: {"role" : 2, "userId" : ID},
                        dataType : "json",
                        success: data=>{
                            if(data["success"] == "1" || data["success"] == 1) {
                                var self = this;
                                Vue.set(self.users[index], "role", 2);
                                this.$message.success("封禁用户成功");
                            }
                            else
                                this.$message.warning("网络传输异常！");
                        }
                    });
                });  
            },

            unban(ID, index){
                this.$confirm("此操作会改变用户的状态, 您确定继续吗?", "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "danger"
                }).then(() => {
                    $.ajax({
                        type:"POST",
                        url: "user/role",
                        data: {"role" : 0, "userId" : ID},
                        dataType : "json",
                        success: data=>{
                            if(data["success"] == "1" || data["success"] == 1) {
                                var self = this;
                                Vue.set(self.users[index], "role", 0);
                                this.$message.success("解封用户成功！");
                            }
                            else
                                this.$message.warning("网络传输异常！");
                        }
                    });
                });
            },

            delete_user(ID, index) {
                this.$confirm("此操作会改变用户的状态, 您确定继续吗?", "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "danger"
                }).then(() => {
                    $.ajax({
                        type:"GET",
                        url:"deleteUser/" + ID,
                        dataType:"json",
                        success : data=>{
                            if(data["success"] == "1" || data["success"] == 1) {
                                var self = this;
                                self.users.splice(index, 1);
                                this.$message.success("删除用户成功！");
                            }
                            else
                                this.$message.warning("网络传输异常！");
                        }
                    });
                });
            },

            set_manager(ID, index){
                this.$confirm("此操作会改变用户的状态, 您确定继续吗?", "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "danger"
                }).then(() => {
                    $.ajax({
                        type:"POST",
                        url: "user/role",
                        data: {"role" : 1, "userId" : ID},
                        dataType : "json",
                        success: data=>{
                            if(data["success"] == "1" || data["success"] == 1) {
                                var self = this;
                                Vue.set(self.users[index], "role", 1);
                                this.$message.success("设置管理员用户成功！");
                            }
                            else
                                this.$message.warning("网络传输异常！");
                        }
                    });
                });
            },
            handleCurrentChange(val) {
                console.log(val);
                var self = this;
                self.pageIndex = val;
            }
        },
        created() {
            var self = this;
            self.pageSize = 30; // The defaul size of page is 30
            self.pageIndex = 0;

            $.ajax({
                type:"GET",
                url:"allUser?page=" + self.pageIndex + "&pageSize=" + self.pageSize,
                success: data=>{
                    console.log(data);
                    self.users = data.users;
                    self.userNum = self.users.length;
                    self.pageLength = self.userNum / self.pageSize + 1;
                }
            });
        }
    };
</script>

<style scoped>
    table{width:80% !important; margin-left:10%;}
    th, td, td>input{text-align: center; width:150px !important;}
</style>