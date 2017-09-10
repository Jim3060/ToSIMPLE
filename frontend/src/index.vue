<template>
    <div class="headline">
        <div style="height:10%"></div>
        <span class="text"></span>
        <div style="height:30px;"></div>
        <el-button @click="$router.push({path:'/q'})">随便看看</el-button>
    </div>
</template>

<script>
    import Typed from "typed.js";
    export default{
        data(){return {};},
        mounted(){
            new Typed(".text", {
                strings: ["欢迎访问 Too Simple", "欢迎访问 To Simple 问卷网"],
                typeSpeed: 30,
                backSpeed: 25,
                startDelay:1000,
                smartBackspace: true,
                autoInsertCss: true
            });
        }
    };
</script>

<style scoped>
    .headline{
        font-size:48px;
        margin-left: 15px; 
        text-align:center;
    }

</style>
