<template>
    <div class="headline" style="background: url('background.jpg')">
        <div style="height:30%;"></div>
        <span class="text"></span>
        <div style="text-align:center">
                <hr style="height:0px;border:none;border-top:1px solid #FFFFFF; width:50%"/>
            </div>
        <el-button @click="$router.push({path:'/q'})"><span class="glyphicon glyphicon-sunglasses" />&nbsp随便看看</el-button>
    </div>
</template>

<script>
import Typed from "typed.js";
export default {
    data() { return {
        notes:{
            backgroundImage: "url("+ require("./background.jpg") +")",
            backgroundSize: "100% 90%",
            backgroundRepeat: "no-repeat",
        }
    }; },
    mounted() {
        new Typed(".text", {
            strings: ["欢迎访问 Too Simple", "欢迎访问 To Simple 问卷网"],
            typeSpeed: 30,
            backSpeed: 25,
            startDelay: 1000,
            smartBackspace: true,
            autoInsertCss: true
        });
    }
};
</script>

<style>
.headline {
    font-size: 48px;
    text-align: center;
    color: #FFF;
    margin-top:-20px;
    height: 100%;
    /*background: url("background.jpg") no-repeat center;*/
    background-size: 100% 100%;
}

/*#Wfooter {
    background: #cac2c2;
    overflow: hidden;
    width: 100%;
    position: fixed;
    left: 0px;
    bottom: 0;
    font-size: 15;
    font-family: cursive;
}

.footer {
    width: 100px;
    height: 50px;
    background: black;
    margin-top: -50px;
}*/

</style>
