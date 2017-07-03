<template>
    <div class="questionnaire">
        <div v-if="!edit" class="questionnaire-title">{{questionnaire.paperTitle}}</div>
        <div>
            <div v-for="(question, index) in questionnaire.questions" v-if="edit || !hidden[index]" :key="question">
                <button v-if="edit" class="btn btn-success" @click="change(index)">修改</button>
                <button v-if="edit" class="btn btn-danger" @click="del(index)">删除</button>
                <single v-if="question.type==0" :index="index" :title="question.questionTitle" :options="question.choices" @update="update(index, $event)"></single>
                <multiple v-if="question.type==1" :index="index" :title="question.questionTitle" :options="question.choices" :limit="question.limit" @update="update(index, $event)"></multiple>
                <blank v-if="question.type==2" :index="index" :title="question.questionTitle" @update="update(index, $event)"></blank>
            </div>
        </div>
        <button v-if="!edit" class="btn btn-success" @click="submit()">提交</button>
    </div>
</template>

<script>
import single from "./single.vue"
import multiple from "./multiple.vue"
import blank from "./blank.vue"
import bus from "../bus.js"
import Vue from "vue"
export default {
    name:"questionnaire",
    components:{single, multiple, blank},
    props:{
        questionnaire:{default(){return {}}},
        edit:{default:false}
    },
    data(){return {
        answer:{},
        hidden:[],
        dirty: false
    }},
    methods:{
        update(index, data){
            this.answer[index] = data;
            this.dirty = true;
        },
        del(index){
            this.$emit("delete", index);
        },
        change(index){
            this.$emit("edit", index);
        },
        submit(){
            var postBody = {answer: {}};
            for(var key in Object.keys(this.answer)){
                var temp = this.answer[key];
                if(typeof temp === "number"){
                    postBody.answer[key] = [[temp],""];
                }else if(typeof temp === "object"){
                    postBody.answer[key] = [temp, ""];
                }else{
                    postBody.answer[key] = [[], temp];
                }
            }
            postBody.objectId = this.questionnaire["_id"]["$oid"];
            postBody.answerTime = new Date();
            var self = this;
            $.post("SaveAnAnswerPaper", {answerPaper:JSON.stringify(postBody)}, (data)=>{
                if(data == "1" || data == 1){
                    bus.$emit("showMsg", "success", "提交成功");
                }
            }).fail(()=>{
                bus.$emit("showMsg", "danger", "错误: 网络异常")
            });
        },
        ifShow(index){
            var showAfter = this.questionnaire.questions[index].showAfter;
            if(typeof showAfter == "undefined" || Object.keys(showAfter).length == 0)
                return true;

            for(var k in showAfter){
                if(typeof showAfter[k] == "undefined")
                    break;
                if(typeof this.answer[k] == "undefined")
                    break;
                var ans = this.answer[k];
                if(this.hidden[k])
                    break;
                if(typeof ans == "number" && showAfter[k].indexOf(ans) > -1)
                    return true;
                if(typeof ans == "object"){
                    for(var v in ans){
                        console.log(v);
                        if(showAfter[k].indexOf(ans[v]) > -1)
                            return true;
                    }
                }
            }
            return false;
        }
    },
    watch:{
        dirty: function(){
            if(this.dirty){
                for(var k = 0; k < this.questionnaire.questions.length; k++){
                    Vue.set(this.hidden, k, !this.ifShow(k));
                }
                this.dirty = false;
            }
        }
    },
    created(){
        if(Object.keys(this.questionnaire) == 0){
            var id = this.$route.params.id;
            var self = this;
            $.post("findAQuestionnaire", {questionnaireId: id}, data=>{
                if(data.valid == "1"){
                    self.questionnaire = data.questionnaire;
                }else{
                    bus.$emit("showMsg", "warning", "警告: 该问卷不存在!");
                }
            }, "json").fail(()=>{
                bus.$emit("showMsg", "danger", "错误: 网络异常!");
            })
        }
        for(var i = 0; i < this.questionnaire.questions.length; i++){
            this.hidden[i] = !this.ifShow(i);
        }
    }
}
</script>

<style>
    .questionnaire-title{font-size:24px; margin-bottom: 30px;}
    .questionnaire{margin:20px;}
</style>