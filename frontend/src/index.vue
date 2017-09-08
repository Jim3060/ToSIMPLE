<template>
    <div class="headline">
        <span class="text"></span>
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
    .headline{font-size:40px; margin-left: 15px;}
</style>
