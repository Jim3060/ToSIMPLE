<template>
    <div>
        <div class="form">
            <el-input v-model="username" placeholder="请输入用户名">
                <span class="glyphicon glyphicon-user" slot="prepend"></span>
            </el-input>
            <el-input v-model="password" @keyup.enter.native="login()" type="password" placeholder="请输入密码">
                <span class="glyphicon glyphicon-lock" slot="prepend"></span>
            </el-input>
        </div>
        <div class="buttons">
            <el-button type="text" @click="$emit('forget')">忘记密码</el-button>
            <el-button @click="$emit('cancel')">取消</el-button>
            <el-button type="success" @click="login()">登录</el-button>
        </div>
    </div>
</template>

<script>
import encrypt from "./encrypt.js";

export default {
    data(){return {
        username:"",
        password:""
    };},
    methods:{
        login(){
            if (this.username == "") {
                this.$message.error("用户名不能为空");
                return;
            }
            if (this.password == "") {
                this.$message.error("密码不能为空");
                return;
            }
            encrypt(this.password).then(password => {
                const postBody = {
                    userName: this.username,
                    passwordSECURE: password
                };
                $.post("login", postBody, data => {
                    if (data.loginSuccess == 1 || data.loginSuccess == "1") {
                        this.$emit("login", data.user);
                    }
                    else {
                        this.$message.error("用户名或密码错误");
                    }
                }, "json").fail(() => {
                    this.$message.error("网络异常");
                });
            }).catch(() => {
                this.$message.error("网络异常");
            });
        }
    }
};
</script>

<style scoped>
    .buttons {
        text-align: right;
        margin-right: 15px;
    }
    .form>* {
        margin-bottom:20px;
    }
    .form {
        margin: 15px;
    }
</style>
