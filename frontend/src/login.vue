<template>
    <div>
        <ul v-if="!ifLog" class="nav navbar-nav navbar-right">
            <li>
                <a @click="regModal()">
                    <span class="glyphicon glyphicon-user"></span> 注册</a>
            </li>
            <li>
                <a @click="loginModal()">
                    <span class="glyphicon glyphicon-log-in"></span> 登录</a>
            </li>
        </ul>
        <ul v-else class="nav navbar-nav navbar-right">
            <li>
                <a>
                    <span class="glyphicon glyphicon-user"></span>
                    <el-dropdown menu-align="start">
                        <span class="el-dropdown-link">
                            {{user["username"]}}
                            <i class="el-icon-caret-bottom el-icon--right"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>
                                <a href="#/m/q">我的问卷</a>
                            </el-dropdown-item>
                            <el-dropdown-item>
                                <el-button @click="showPasswordModal=true" type="text">修改密码</el-button>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </a>
            </li>
            <li>
                <a @click="logout()">
                    <span class="glyphicon glyphicon-log-out"></span> 退出</a>
            </li>
        </ul>
    
        <modal :show.sync="showLoginModal" effect="zoom" :backdrop="false">
            <div slot="modal-header" class="modal-header">
                <h4>登录</h4>
            </div>
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
                <el-button @click="showLoginModal=false">取消</el-button>
                <el-button type="primary" @click="login()">登录</el-button>
            </div>
        </modal>
    
        <modal :show.sync="showPasswordModal" effect="zoom" :backdrop="false">
            <div slot="modal-header" class="modal-header">
                <h4>修改密码</h4>
            </div>
            <div slot="modal-body" class="modal-body">
                <form id="password" class="bs-example bs-example-form" role="form">
                    <div class="input-group">
                        <span class="input-group-addon glyphicon glyphicon-lock"></span>
                        <input type="password" v-model="passwordBuf.old" placeholder="old password" class="form-control"></input>
                    </div>
                    <div style="height:20px"></div>
                    <div class="input-group">
                        <span class="input-group-addon glyphicon glyphicon-lock"></span>
                        <input type="password" v-model="passwordBuf.new" placeholder="new password" class="form-control"></input>
                    </div>
                    <div style="height:20px"></div>
                    <div class="input-group">
                        <span class="input-group-addon glyphicon glyphicon-lock"></span>
                        <input type="password" v-model="passwordBuf.repeat" placeholder="password repeat" class="form-control" @keyup.enter="changePassword()"></input>
                    </div>
                </form>
            </div>
            <div slot="modal-footer" class="modal-footer">
                <el-button @click="showPasswordModal=false">取消</el-button>
                <el-button type="primary" @click="changePassword()">提交</el-button>
            </div>
        </modal>
    
        <modal :show.sync="showRegModal" effect="zoom" :backdrop="false">
            <div slot="modal-header" class="modal-header">
                <h4>注册</h4>
            </div>
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
                <el-button @click="showRegModal=false">取消</el-button>
                <el-button type="primary" @click="register()">注册</el-button>
            </div>
        </modal>
    
    </div>
</template>

<script>
import { modal, alert } from "vue-strap";
import { JSEncrypt } from "./jsencrypt.js";

export default {
    name: "login",
    components: { modal, alert },
    data() {
        return {
            ifLog: false,
            user: {},
            loginBuf: { username: "", password: "" },
            regBuf: { name: "", password: "", email: "" },
            showLoginModal: false,
            showRegModal: false,
            publicKey: "",
            passwordBuf: { old: "", new: "", repeat: "" },
            showPasswordModal: false
        };
    },
    methods: {
        showMsg(msgType, msgContent) {
            this.msgType = msgType;
            this.msg = msgContent;
            this.msgShow = true;
            setTimeout(() => { this.msgShow = false; }, 2000);
        },
        loginModal() {
            this.loginBuf = {};
            this.showLoginModal = true;
        },
        regModal() {
            this.regBuf = { name: "", password1: "", password2: "", email: "" };
            this.showRegModal = true;
        },
        login() {
            this.encrypt(this.loginBuf.password).then(password => {
                var self = this;
                var postBody = {};
                postBody.userName = this.loginBuf.username;
                postBody.passwordSECURE = password;
                $.post("login", postBody, (data) => {
                    console.log(data);
                    if (data.loginSuccess == 1 || data.loginSuccess == "1") {
                        self.user.role = data.user.role;
                        self.user.username = data.user.userName;
                        localStorage.user = JSON.stringify(self.user);
                        self.ifLog = true;
                        self.showLoginModal = false;
                        this.$message.success("登录成功");
                        self.$emit("login", data.user.role);
                    } else {
                        this.$message.error("用户名或密码错误");
                    }
                }, "json").fail(() => {
                    this.$message.error("网络异常");
                });
            }).catch(() => {
                this.$message.error("网络异常");
            });
        },
        logout() {
            this.ifLog = false;
            $.get("logout");
            localStorage.removeItem("user");
            this.$router.push({ path: "/index" });
            this.$emit("logout");
            this.$message.info("您已登出");
        },
        register() {
            var self = this;
            if (this.regBuf.name == "") {
                this.$message.error("用户名不能为空");
                return;
            }
            else if (this.regBuf.name.indexOf("@") != -1) {
                this.$message.error("用户名中不能含有特殊字符");
                return;
            }
            else if (this.regBuf.password1 == "") {
                this.$message.error("密码不能为空");
                return;
            }
            else if (this.regBuf.password1 != this.regBuf.password2) {
                this.$message.error("密码不一致");
                return;
            }
            else if (this.regBuf.email == "") {
                this.$message.error("邮箱不能为空");
                return;
            }
            else if (this.regBuf.email.indexOf("@") == -1 || this.regBuf.email.indexOf(".") <= this.regBuf.email.indexOf("@")) {
                this.$message.error("请输入合法的邮箱地址");
                return;
            }

            this.encrypt(this.regBuf.password1).then((password) => {
                var postBody = {};
                postBody.userName = this.regBuf.name;
                postBody.passwordSECURE = password;
                postBody.email = this.regBuf.email;
                $.post("register", postBody, (data) => {
                    if (data >= 1) {
                        localStorage.user = JSON.stringify(self.user);
                        self.showRegModal = false;
                        this.$message.success("注册成功,请登录邮箱验证");
                    } else {
                        this.$message.error("用户名或邮箱重复");
                    }
                }).fail(() => {
                    this.$message.error("网络异常");
                });
            }).catch(() => {
                this.$message.error("网络异常");
            });
        },
        changePassword() {
            if (this.passwordBuf.new == this.passwordBuf.repeat){
                let postBody = {};
                this.encrypt(this.passwordBuf.old).then(password => {
                    postBody.passwordSECURE = password;
                    this.encrypt(this.passwordBuf.new, this.publicKey).then(password => {
                        postBody.passwordNewSECURE = password;
                        $.post("updatePassword", postBody, data => {
                            if (data == 1 || data == "1"){
                                this.$message.success("修改成功");
                                this.showPasswordModal = false;
                            }
                            else if (data == 0 || data == "0")
                                this.$message.error("未登录");
                            else
                                this.$message.error("密码错误");
                        });
                    });
                });
            }
        },
        getPublicKey() {
            return new Promise((resolve, reject) => {
                $.get("fetchRSA", data => {
                    resolve(data.publicKey);
                }, "json").fail(() => {
                    reject("error");
                });
            });
        },
        encrypt(password, key = "") {
            return new Promise((resolve, reject) => {
                if (key == "") {
                    var encrypt = new JSEncrypt();
                    this.getPublicKey().then(key => {
                        this.publicKey = key;
                        encrypt.setPublicKey(key);
                        resolve(encrypt.encrypt(password));
                    }).catch(() => {
                        reject("fail");
                    });
                }
                else {
                    let encrypt = new JSEncrypt();
                    encrypt.setPublicKey(key);
                    resolve(encrypt.encrypt(password));
                }
            });
        }
    },
    created() {
        if (localStorage.user) {
            this.user = JSON.parse(localStorage.user);
            this.ifLog = true;
            this.$emit("login", this.user.role);
        }
    }
};    
</script>

<style>
form#login .input-group,
form#reg .input-group {
    margin: 20px;
}
</style>
