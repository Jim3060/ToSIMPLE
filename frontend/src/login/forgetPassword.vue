<template>
    <div>
        <div class="form">
            <el-input v-model="email" placeholder="请输入邮箱">
                <span slot="prepend" class="glyphicon glyphicon-envelope"></span>
            </el-input>
            <el-input v-model="token" placeholder="请输入验证码">
                <span slot="prepend" class="glyphicon glyphicon-check"></span>
                <el-button slot="append" @click="askToken()" :disabled="countDown>0">{{countDown==0?"发送验证码":`${countDown}秒后重发`}}</el-button>
            </el-input>
            <el-input v-model="password" type="password" placeholder="请输入新密码">
                <span slot="prepend" class="glyphicon glyphicon-lock"></span>
            </el-input>
            <el-input v-model="repeat" type="password" placeholder="请再次输入密码">
                <span slot="prepend" class="glyphicon glyphicon-lock"></span>
            </el-input>
        </div>
        <div class="buttons">
            <el-button @click="$emit('done')">取消</el-button>
            <el-button type="success" @click="submit()">提交</el-button>
        </div>
    </div>    
</template>

<script>
import encrypt from "./encrypt.js";

export default {
    data(){return {
        countDown: 0,
        email:"",
        token:"",
        password:"",
        repeat:""
    };},
    methods:{
        askToken() {
            if (this.email == "") {
                this.$message.error("请输入邮箱");
                return;
            }
            if (this.email.indexOf("@") == -1 || this.email.indexOf(".") <= this.email.indexOf("@")) {
                this.$message.error("请输入合法的邮箱地址");
                return;
            }
            
            $.get("sendCheckToken", {email: this.email}, data => {
                if (data == 1) {
                    this.$message.success("验证码已发送至邮箱");
                    this.countDown = 60;   
                }
                else {
                    this.$message.error("该邮箱尚未注册");
                }
            }).fail(() => {
                this.$message.error("网络异常");
            });
        },
        submit(){
            encrypt(this.password).then(password => {
                const sendBody = {
                    passwordNewSECURE: password,
                    checkToken: this.token,
                    email: this.email
                };
                $.get("forgotPassword", sendBody, data => {
                    if (data == 1) {
                        this.$message.success("密码设置成功,请重新登录");
                        this.$emit("done");
                    }
                    else {
                        this.$message.error("验证码错误");
                    }
                }, "json").fail(() => {
                    this.$message.error("网络异常");
                });
            }).catch(() => {
                this.$message.error("网络异常");
            });
        }
    },
    created(){
        setInterval(() => {
            if (this.countDown > 0) {
                this.countDown -= 1;
            }
        }, 1000);
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
