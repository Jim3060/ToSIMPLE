<template>
    <div class="connectItem">
        <span>第</span>
        {{idx_show}}
        <span>题</span>
        <el-button type="danger" size="small" @click="cancel()" >删除</el-button>
        <el-checkbox-group v-model="select">
            <el-checkbox v-for="i in num" :key="i" :label="i-1">
                <span>{{questionnaire.questions[idx].choices[i-1].text}}</span>
                <img v-if="questionnaire.questions[idx].choices[i-1].photoId!=undefined" :src="`file/+${questionnaire.questions[idx].choices[i-1].photoId}`" />
            </el-checkbox>
        </el-checkbox-group>
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
    };},
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
};
</script>

<style>
    .connectItem>input{width:50px;}
    
</style>
