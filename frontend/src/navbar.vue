<template>
    
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-book"></span> To Simple 问卷网</a>
    </div>
    <div>
        <ul class="nav navbar-nav" style="width:80%;">
            <li :class="{active:$route.path=='/index'}"><a href="#">首页</a></li>
            <li :class="{active:($route.path=='/q'||$route.name=='q')}"><a href="#/q">问卷</a></li>
            <li :class="{active:($route.path=='/n'||$route.name=='n')}"><a href="#/n">新建</a></li>

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
    export default{
        name:"navbar",
        data(){return {
            searchContent:"",
            role:0
        };},
        methods:{
            
        }
    }
</script>
    
<style>

</style>
