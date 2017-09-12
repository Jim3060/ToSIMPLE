<template>
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">
                    <span class="glyphicon glyphicon-book"></span> To Simple 问卷网</a>
            </div>
            <div>
                <ul class="nav navbar-nav" style="width:80%;">
                    <li :class="{active:$route.path=='/index'}">
                        <a href="#">首页</a>
                    </li>
                    <li :class="{active:$route.path=='/n'||$route.path=='/q'||$route.name=='n'||$route.name=='q'||$route.name=='s'||$route.path=='/m/q'}">
                        <a>
                            <el-dropdown menu-align="start">
                                <span class="el-dropdown-link">
                                    问卷
                                    <i class="el-icon-caret-bottom el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item>
                                        <a href="#/q">随便看看</a>
                                    </el-dropdown-item>
                                    <el-dropdown-item v-if="role>=0">
                                        <a href="#/n">新建问卷</a>
                                    </el-dropdown-item>
                                    <el-dropdown-item v-if="role>=0">
                                        <a href="#/m/q">我的问卷</a>
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </a>
                    </li>
                    <li v-if="role == 1" :class="{active:$route.path=='/backup'||$route.path=='/questionnaireManager'||$route.path=='/userManager'}">
                        <a>
                            <el-dropdown menu-align="start">
                                <span class="el-dropdown-link">
                                    管理
                                    <i class="el-icon-caret-bottom el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item >
                                        <a href="#/userManager">管理用户</a>
                                    </el-dropdown-item>
                                    <el-dropdown-item>
                                        <a href="#/questionnaireManager">管理问卷</a>
                                    </el-dropdown-item>
                                    <el-dropdown-item>
                                        <a href="#/backup">备份</a>
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </a>
                    </li>
                    <!--
                            <li :class="{active:($route.path=='/userManager')}">
                                <a href="#/userManager">管理用户</a>
                            </li>
                            <li :class="{active:($route.path=='/questionnaireManager')}">
                                <a href="#/questionnaireManager">管理问卷</a>
                            </li>
                            <li :class="{active:($route.path=='/backup')}">
                                <a href="#/backup">备份</a>
                            </li>
                            -->
    
                    <login class="navbar-right" @login="role=$event" @logout="role=-1"></login>
                    <form class="navbar-form navbar-right" role="search">
                        <div class="form-group">
                            <input v-model="searchContent" type="text" class="form-control" placeholder="Search">
                        </div>
                        <button type="submit" class="btn btn-default" @click="$router.push({name:'search', params:{name: searchContent}})">搜索</button>
                    </form>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script>
import login from "./login/login.vue";

export default {
    name: "navbar",
    components: { login },
    data() {
        return {
            searchContent: "",
            role: -1
        };
    },
    methods: {

    }
};
</script>
    
<style>
nav {
    position: fixed;
    top: 0px;
}
</style>
