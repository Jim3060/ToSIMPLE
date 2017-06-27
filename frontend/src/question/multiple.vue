<template>
    <div>
        <ul class="question list-group">
            <li class="list-group-item question-title">[多选, 最多能选{{limit}}项] {{title}}</li>
            <li class="list-group-item" v-for="(option, index) in options" :key="option" @click="addOrRemove(index)">
                <input type="checkbox" v-model="select" :value="index" :disabled="select.length>=limit&&select.indexOf(index)==-1"></input>
                <label>{{option}}</label>
            </li>
        </ul>
    </div>
</template>

<script>
    export default{
        name: "multiple",
        props:{options:{required: true}, title:{required: true}, limit:{required: true}},
        data(){return {
            select:[]
        }},
        methods:{
            addOrRemove(index){
                var i = this.select.indexOf(index);
                if(i == -1){
                    if(this.select.length < this.limit)
                        this.select.push(index);
                }else{
                    this.select.splice(i, 1);
                }
            }
        }
    }
</script>

<style>
    .question-title{font-size:18px;}
</style>

