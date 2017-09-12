<template>
    <div>
        <div class="form">
            <el-input v-model="old" type="password" placeholder="请输入旧密码">
                <span class="glyphicon glyphicon-lock" slot="prepend"></span>    
            </el-input>
            <el-input v-model="password" type="password" placeholder="请输入新密码">
                <span class="glyphicon glyphicon-lock" slot="prepend"></span>    
            </el-input>
            <el-input v-model="repeat" type="password" placeholder="请再次输入新密码">
                <span class="glyphicon glyphicon-lock" slot="prepend"></span>    
            </el-input>
        </div>
        <div class="buttons">
            <el-button @click="$emit('done')">取消</el-button>
            <el-button @click="change()" type="success">修改密码</el-button>
        </div>
    </div>
</template>

<script>
import encrypt from "./encrypt.js";

export default {
    data(){return {
        old:"",
        password:"",
        repeat:""
    };},
    methods:{
        change(){
            if (this.old == "") {
                this.$message.error("请输入旧密码");
                return;
            }
            if (this.password == "") {
                this.$message.error("请输入新密码");
                return;
            }
            if (this.password != this.repeat) {
                this.$message.error("新密码输入不一致");
                return;
            }

            encrypt([this.old, this.password]).then(passwords => {
                const postBody = {
                    passwordSECURE: passwords[0],
                    passwordNewSECURE: passwords[1]
                };
                $.post("updatePassword", postBody, data => {
                    if (data == 1) {
                        this.$message.success("密码修改成功");
                        this.$emit("done");
                    }
                    else if (data == 0) {
                        this.$message.error("登录过期，请重新登录");
                    }
                    else {
                        this.$message.error("旧密码输入错误");
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
