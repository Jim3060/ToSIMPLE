<template>
    <div id="tb">
        <h1>User Mananger</h1>
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
            <tr v-for="(data_item, index) in data" :key="data_item">
                <th>{{index + 1}}</th>
                <th>{{data_item.ID}}</th>
                <th v-if="data_item.role==2">封禁用户</th>
                <th v-else-if="data_item.role==0">普通用户</th>
                <th v-else>系统管理员</th>
                <th>{{data_item.username}}</th>
                <th>{{data_item.email}}</th>
                <th>
                    <el-button v-if="data_item.role == 2" type="warning" @click="unban(data_item.ID, index)">解封用户
                    </el-button>
                    <el-button v-if="data_item.role==0" type="danger" @click="ban(data_item.ID, index)">封禁用户</el-button>
                    <el-button v-if="data_item.role==0" type="warning" @click="set_manager(data_item.ID, index)">
                        更改为管理员
                    </el-button>
                </th>
            </tr>
            </tbody>
        </table>
        <div class="container">
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
    //import BootStrapVue from "bootstrap-vue";
    //Vue.use(BootStrapVue);

    //import {bAlert, bModal} from 'bootstrap-vue/lib/components'
    import {modal, alert} from "vue-strap"

    export default {
        data(){
            return {
                data: [{"ID": 1, "role": 0, "username": "zhubo", "email": "8@qq.com"},
                    {"ID": 1, "role": 2, "username": "zhu", "email": "8@qq.com"}],
                datachanged: [],
                pageIndex: {},
                pageLength: {},
                pageSize: {}
            }
        },
        methods: {
            ban(ID, index) {
                this.$confirm('此操作会改变用户的状态, 您确定继续吗?', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'danger'
                }).then(() => {
                    $.ajax({
                        type: "PUT",
                        url: "user/userId=" + ID,
                        data: {"role": 2},
                        success: data => {
                            if (data == '1' || data == 1) {
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

            unban(ID, index){
                this.$confirm('此操作会改变用户的状态, 您确定继续吗?', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'danger'
                }).then(() => {
                    $.ajax({
                        type: "PUT",
                        url: "user/ID=" + ID,
                        data: {"role": 0},
                        success: data => {
                            if (data == '1' || data == 1) {
                                var self = this;
                                self.data[index].role = 2;
                                this.$message.success("解封用户成功！");
                            }
                            else
                                this.$message.warning("网络传输异常！");
                        }
                    });
                })
            },
            set_manager(data, index){
                this.$confirm('此操作会改变用户的状态, 您确定继续吗?', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'danger'
                }).then(() => {
                    $.ajax({
                        type: "PUT",
                        url: "user/ID=" + ID,
                        data: {"role": 1},
                        success: data => {
                            if (data == '1' || data == 1) {
                                var self = this;
                                self.data[index].role = 0;
                                this.$message.success("设置管理员用户成功！");
                            }
                            else
                                this.$message.warning("网络传输异常！");
                        }
                    });
                })
            },
            handleCurrentChange(val) {
                var self = this;
                self.pageIndex = val;
                $.ajax({
                    type: "GET",
                    url: "allUser/page=" + (self.pageIndex - 1) + "&size=" + self.pageSize,
                    success: data => {

                    }
                });
            }
        },
        created() {
            var self = this;
            self.pageSize = 30; // The defaul size of page is 30
            self.pageIndex = 0;

            $.ajax({
                type: "GET",
                url: "allUser?page=" + self.pageIndex + "&size=" + self.pageSize,
                success: data => {

                }
            });

        }
    }
</script>

<style>
    table {
        width: 80% !important;
        margin-left: 10%;
    }

    th, td, td > input {
        text-align: center;
        width: 150px !important;
    }
</style>