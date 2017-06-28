<template>
    <div>
        <ul class="question list-group">
            <li class="list-group-item">[单选] <span class="question-title">{{title}}</span></li>
            <li class="list-group-item" v-for="(option, index) in options" :key="option" @click="select=index">
                <input type="radio" v-model="select" :value="index"></input>
                <label>{{option}}</label>
            </li>
        </ul>
    </div>
</template>

<script>
    export default{
        name: "single",
        props:{options:{required: true}, title:{required: true}},
        data(){return {
            select:""
        }},
        watch:{
            select:function(){
                this.$emit("update", this.select);
            }
        }
    }
</script>

<style>
    .question-title{font-size:18px;}
</style>
