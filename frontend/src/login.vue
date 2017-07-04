<template>
<div>
    <ul v-if="!ifLog" class="nav navbar-nav navbar-right"> 
        <li><a @click="regModal()"><span class="glyphicon glyphicon-user"></span> 注册</a></li> 
        <li><a @click="loginModal()"><span class="glyphicon glyphicon-log-in"></span> 登录</a></li> 
    </ul> 
    <ul v-else class="nav navbar-nav navbar-right"> 
        <li><a><span class="glyphicon glyphicon-user"></span> {{user["username"]}}</a></li> 
        <li><a @click="logout()"><span class="glyphicon glyphicon-log-out"></span> 退出</a></li> 
    </ul> 

        <modal :show.sync="showLoginModal" effect="zoom" :backdrop="false" >
            <div slot="modal-header" class="modal-header"><h4>登录</h4></div>
            <div slot="modal-body" class="modal-body">
                <form id="login" class="bs-example bs-example-form" role="form">
                    <div class="input-group">
                        <span class="input-group-addon glyphicon glyphicon-user"></span>
                        <input v-model="loginBuf.username" placeholder="username" class="form-control"></input>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon glyphicon glyphicon-lock"></span>
                        <input type="password" v-model="loginBuf.password" placeholder="password" class="form-control" @keyup.enter="login()"></input>
                    </div>
                </form>
            </div>
            <div slot="modal-footer" class="modal-footer">
                <button class="btn btn-default" @click="showLoginModal=false">取消</button>
                <button class="btn btn-success" @click="login()">登录</button>
            </div>
        </modal>
        <modal :show.sync="showRegModal" effect="zoom" :backdrop="false" >
            <div slot="modal-header" class="modal-header"><h4>注册</h4></div>
            <div slot="modal-body" class="modal-body">
                <form id="reg" class="bs-example bs-example-form" role="form">
                    <div class="input-group">
                        <span class="input-group-addon glyphicon glyphicon-user"></span>
                        <input v-model="regBuf.name" placeholder="username" class="form-control"></input>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon glyphicon glyphicon-lock"></span>
                        <input type="password" v-model="regBuf.password1" placeholder="password" class="form-control"></input>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon glyphicon glyphicon-lock"></span>
                        <input type="password" v-model="regBuf.password2" placeholder="password repeat" class="form-control"></input>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon glyphicon glyphicon-envelope"></span>
                        <input v-model="regBuf.email" placeholder="email" class="form-control" @keyup.enter="register()"></input>
                    </div>
                </form>
            </div>
            <div slot="modal-footer" class="modal-footer">
                <button class="btn btn-default" @click="showRegModal=false">取消</button>
                <button class="btn btn-success" @click="register()">注册</button>
            </div>
        </modal>

</div>
</template>

<script>
    import {modal, alert} from "vue-strap"
    import {JSEncrypt} from "./jsencrypt.js"
    import bus from "./bus.js"

    export default{
        name:"login",
        components:{modal, alert},
        data(){return {
            ifLog: false,
            user:{},
            loginBuf:{username:"", password:""},
            regBuf:{name:"", password:"", email:""},
            showLoginModal:false,
            showRegModal: false,
            publicKey:""
        }},
        methods:{
            showMsg(msgType, msgContent){
                this.msgType = msgType;
                this.msg = msgContent;
                this.msgShow = true;
                setTimeout(()=>{this.msgShow = false}, 2000);
            },
            loginModal(){
                this.loginBuf = {};
                this.showLoginModal = true;
            },
            regModal(){
                this.regBuf = {};
                this.showRegModal = true;
            },
            login(){
                var self = this;
                var postBody = {};
                postBody.userName = this.loginBuf.username;
                postBody.passwordSECURE = this.encrypt(this.loginBuf.password);
                $.post("login", postBody, (data)=>{
                    if(data >= 1){
                        self.user.role = data;
                        self.user.username = self.loginBuf.username;
                        localStorage.user = JSON.stringify(self.user);
                        self.ifLog = true;
                        self.showLoginModal = false;
                        bus.$emit("showMsg", "success", "登录成功");
                        self.$emit("login");
                    }else{
                        bus.$emit("showMsg", "danger", "用户名不存在或密码错误");
                    }
                }).fail(()=>{
                    bus.$emit("showMsg", "danger", "登录失败：网络异常");
                })

            },
            logout(){
                this.ifLog = false;
                $.get("logout");
                localStorage.removeItem("user");
                this.$router.push({path:"/index"});
                this.$emit("logout");
                bus.$emit("showMsg", "info", "您已登出");
            },
            register(){
                var self = this;
                if(this.regBuf.name == "" || this.regBuf.password1 == "" || this.regBuf.password1 != this.regBuf.password2 || this.regBuf.email == ""){
                    bus.$emit("showMsg", "warning", "请检查您的输入");
                    return ;
                }
                var postBody = {};
                postBody.name = this.regBuf.name;
                postBody.password = this.encrypt(this.regBuf.password1);
                postBody.email = this.regBuf.email;
                $.post("register", postBody, (data)=>{
                    if(data >= 1){
                        self.user.role = 1;
                        self.user.username = self.regBuf.name;
                        localStorage.user = JSON.stringify(self.user);
                        self.ifLog = true;
                        self.showRegModal = false;
                        self.$emit("login");
                        bus.$emit("showMsg", "success", "注册成功");
                    }else{
                        bus.$emit("showMsg", "danger", "注册失败：用户名重复");
                    }
                }).fail(()=>{
                    bus.$emit("showMsg", "danger", "注册失败：网络异常");
                })
            },
            getPublicKey(){
                var self = this;
                $.get("fetchRSA", data=>{
                    self.publicKey = data.publicKey;
                }, "json");
            },
            encrypt(password){
                var encrypt = new JSEncrypt();
                encrypt.setPublicKey(this.publicKey);
                return encrypt.encrypt(password);
            }
        },
        created(){
            if(localStorage.user){
                this.user = JSON.parse(localStorage.user);
                this.ifLog = true;
                this.$emit("login");
            }
            this.getPublicKey();
        }
    }    
</script>

<style>
    form#login .input-group, form#reg .input-group{margin:20px;}
</style>
