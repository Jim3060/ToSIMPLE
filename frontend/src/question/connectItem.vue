<template>
    <div class="connectItem">
        <span>第</span>
        <!--<select v-model.number="idx_show">
            <option v-for="i in options" :key="i">{{parseInt(i)+1}}</option>
        </select>-->
        {{idx_show}}
        <span>题</span>
        <button @click="cancel()" class="btn btn-danger btn-sm">删除</button>
        <div v-for="i in num" :key="i" class="options">
            <input type="checkbox" v-model="select" :value="i-1" :disabled="!edit"></input>
            <label>{{questionnaire.questions[idx].choices[i-1]}}</label>
        </div>
    </div>
</template>

<script>
export default {
    props:{
        questionnaire:{required: true},
        index:{required: true},
        idx:{default: 0},
        newItem:{default: false}
    },
    data(){return {
        edit: true,
        select:[],
        idx_show:0,
        options:[]
    }},
    computed:{
        num(){
            try{
                var questions = this.questionnaire.questions;
                if(this.idx < 0 || this.idx >= questions.length || questions[this.idx].type == 2)
                    return 0;
                return questions[this.idx].choices.length;
            }
            catch(err){
                return 0;
            }
        }
    },
    watch:{
        idx(){
            if(this.index >= 0)
            try{
                this.select = this.questionnaire.questions[this.index].showAfter[this.idx];
            }
            catch(err){
                this.select = {};
            }
        },
        idx_show(){
            this.idx = this.idx_show - 1;
        },
        select(){
            this.$emit("update", this.idx, this.index, this.select);
        }
    },
    created(){
        var questions = this.questionnaire.questions;
        for(var i in questions){
            if(questions[i].type <= 1 && (this.index == -1 || i < this.index))
                this.options.push(i);
        }

        if(this.idx >= 0)
            this.idx_show = parseInt(this.idx) + 1;
        if(this.index == -1 || this.questionnaire.questions[this.index].showAfter == undefined){
            this.select = [];
            return;
        }
        if(this.index >= 0 && this.idx >= 0)
            this.select = this.questionnaire.questions[this.index].showAfter[this.idx];
    },
    methods:{
        cancel(){
            this.$emit("cancel", this.idx);
        }
    } 
}
</script>

<style>
    /*.connectItem>*{float:left;}*/
    .connectItem>input{width:50px;}
    
</style>
