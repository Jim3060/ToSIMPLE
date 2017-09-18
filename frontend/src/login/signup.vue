<template>
    <div>
        <div class="form">
            <el-input v-model="username" placeholder="请输入用户名">
                <span class="glyphicon glyphicon-user" slot="prepend"></span>    
            </el-input>            
            <el-input v-model="password" type="password" placeholder="请输入密码">
                <span class="glyphicon glyphicon-lock" slot="prepend"></span>    
            </el-input>            
            <el-input v-model="repeat" type="password" placeholder="请重复输入密码">
                <span class="glyphicon glyphicon-lock" slot="prepend"></span>    
            </el-input>
            <el-input v-model="email" placeholder="请输入E-mail地址" @keyup.enter.native="register()">
                <span class="glyphicon glyphicon-envelope" slot="prepend"></span>    
            </el-input>            
        </div>
        <div class="buttons">
            <el-button @click="$emit('done')">取消</el-button>
            <el-button type="success" @click="register()">注册</el-button>
        </div>
    </div>
</template>

<script>
import encrypt from "./encrypt.js";

export default {
    data(){return {
        username:"",
        password:"",
        repeat:"",
        email:""
    };},
    methods:{
        register(){
            if (this.username == "") {
                this.$message.error("用户名不能为空");
                return;
            }
            else if (this.username.indexOf("@") != -1) {
                this.$message.error("用户名中不能含有特殊字符");
                return;
            }
            else if (this.password == "") {
                this.$message.error("密码不能为空");
                return;
            }
            else if (this.password != this.repeat) {
                this.$message.error("密码不一致");
                return;
            }
            else if (this.email == "") {
                this.$message.error("邮箱不能为空");
                return;
            }
            else if (this.email.indexOf("@") == -1 || this.email.indexOf(".") <= this.email.indexOf("@")) {
                this.$message.error("请输入合法的邮箱地址");
                return;
            }

            encrypt(this.password).then(password => {
                const postBody = {
                    userName: this.username,
                    passwordSECURE: password,
                    email: this.email
                };      
                $.post("register", postBody, data => {
                    if (data >= 1) { // success
                        this.$message.success("注册成功，请验证邮箱");
                        this.$emit("done");
                    } else {
                        this.$message.error("用户名或邮箱重复");
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
