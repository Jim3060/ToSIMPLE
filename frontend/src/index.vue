<template>
    <div class="headline">
        <div style="height:10%"></div>
        <span class="text"></span>
        <div style="height:30px;"></div>
        <el-button @click="$router.push({path:'/q'})">随便看看</el-button>
        <div id="Wfooter">
            <p>contact US&emsp;&emsp;&emsp;
                Tel:&ensp;222222&emsp;&emsp;&emsp;
                Email:&ensp;@sjtu.edu.cn</p>
            <p>©2017 To simple Design & Development.</p>
        </div>
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
    .headline{font-size:40px; text-align: center;}
    
    #Wfooter {
        background: gray;
        margin: 0 auto;
        overflow: hidden;
        width: 100%;
        position: bottom;
        min-width: 980px;
        position: relative;
        top: 200px;
        font-size: 15;
        margin-top: 10px;
    }
    .footer{width:100px;
    height:50px;background:black;margin-top:-50px;}
    .headline{font-size:40px; margin-left: 15px;}
    .headline{
        font-size:48px;
        margin-left: 15px; 
        text-align:center;
    }

</style>
