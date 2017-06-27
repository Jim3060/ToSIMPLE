<template>
    <div>
        <ul class="question list-group">
            <li class="list-group-item question-title">[单选] {{title}}</li>
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
        }}
    }
</script>

<style>
    .question-title{font-size:18px;}
</style>
