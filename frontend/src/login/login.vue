<template>
    <div>
        <ul v-if="!ifLog" class="nav navbar-nav navbar-right">
            <li>
                <a @click="showRegModal=true">
                    <span class="glyphicon glyphicon-user"></span> 注册</a>
            </li>
            <li>
                <a @click="showLoginModal=true">
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
    
        <!-- login -->
        <modal :show="showLoginModal" effect="zoom" :backdrop="false">
            <div slot="modal-header" class="modal-header">
                <h4>登录</h4>
            </div>
            <div slot="modal-body" class="modal-body">
                <signin @cancel="showLoginModal=false" @login="login($event)" @forget="forget()"></signin>
            </div>
            <div slot="modal-footer"></div>
        </modal>
    
        <modal :show.sync="showPasswordModal" effect="zoom" :backdrop="false">
            <div slot="modal-header" class="modal-header">
                <h4>修改密码</h4>
            </div>
            <div slot="modal-body" class="modal-body">
                <change @done="showPasswordModal=false"></change>
            </div>
            <div slot="modal-footer"></div>
        </modal>
    
        <modal :show.sync="showRegModal" effect="zoom" :backdrop="false">
            <div slot="modal-header" class="modal-header">
                <h4>注册</h4>
            </div>
            <div slot="modal-body" class="modal-body">
                <signup @done="showRegModal=false"></signup>
            </div>
            <div slot="modal-footer"></div>
        </modal>

        <modal :show="showForgetModal" effect="zoom" :backdrop="false">
            <div slot="modal-header" class="modal-header">
                <h4>忘记密码</h4>
            </div>
            <div slot="modal-body" class="modal-body">
                <forget @done="showForgetModal=false"></forget>
            </div>
            <div slot="modal-footer"></div>
        </modal>
    
    </div>
</template>

<script>
import { modal } from "vue-strap";
import signin from "./signin.vue";
import signup from "./signup.vue";
import change from "./changePassword.vue";
import forget from "./forgetPassword.vue";

export default {
    name: "login",
    components: { modal, signin, signup, change, forget},
    data() {
        return {
            ifLog: false,
            user: {},
            showLoginModal: false,
            showRegModal: false,
            showForgetModal: false,
            showPasswordModal: false
        };
    },
    methods: {
        login(user) {
            this.user = user;
            localStorage.user = JSON.stringify(user);
            this.ifLog = true;
            this.showLoginModal = false;
            this.$message.success("登录成功");
            this.$emit("login", user.role);
        },
        logout() {
            this.ifLog = false;
            $.get("logout");
            localStorage.removeItem("user");
            this.$router.push({ path: "/index" });
            this.$emit("logout");
            this.$message.info("您已登出");
        },
        forget(){
            this.showForgetModal = true;
            this.showLoginModal = false;
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

<style scoped>
form#login .input-group,
form#reg .input-group {
    margin: 20px;
}
</style>
