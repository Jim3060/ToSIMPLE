<template>
    <div class="questionnaire">
        <div v-if="!edit" class="questionnaire-title">{{questionnaire.paperTitle}}</div>
        <div v-if="!edit&&questionnaire!=undefined&&questionnaire.briefing!=undefined" class="questionnaire-briefing">{{questionnaire.briefing}}</div>
        <div></div>
        <div>
            <div v-for="(question, index) in questionnaire.questions" v-if="edit || !hidden[index]" :key="question">
                <el-button v-if="edit" type="primary" size="small" @click="change(index)">修改</el-button>
                <el-button v-if="edit" type="danger" size="small" @click="del(index)">删除</el-button>
                <single v-if="question.type==0" :index="index" :answer="answer[index]" :title="question.questionTitle" :options="question.choices" :mix="question.mix||false" :forced="question.forced" @update="update(index, $event)"></single>
                <multiple v-if="question.type==1" :index="index" :answer="answer[index]" :title="question.questionTitle" :options="question.choices" :limit="question.limit" :mix="question.mix||false" :forced="question.forced" @update="update(index, $event)"></multiple>
                <blank v-if="question.type==2" :index="index" :answer="answer[index]" :title="question.questionTitle" :forced="question.forced" @update="update(index, $event)"></blank>
            </div>
        </div>
        <el-button type="primary" v-if="$route.path!='/n'&&$route.name!='n'&&$route.name!='r'" @click="submit()">提交</el-button>
    </div>
</template>

<script>
import single from "./single.vue";
import multiple from "./multiple.vue";
import blank from "./blank.vue";
import Vue from "vue";

export default {
    name:"questionnaire",
    components:{single, multiple, blank},
    props:{
        questionnaire:{default(){return {};}},
        edit:{default:false}
    },
    data(){return {
        answer:{},
        hidden:[],
        dirty: false,
        beginTime:""
    };},
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
            if(localStorage.answered != undefined){
                let answered = JSON.parse(localStorage.answered);
                if(answered.indexOf(this.$route.params.id) != -1){
                    this.$message.warning("请勿重复回答该问题！");
                    return;
                }
            }
            var postBody = {answers: []};
            for(var i = 0; i < this.questionnaire.questions.length; i++){
                var temp = this.answer[i];
                if(this.hidden[i])
                    continue;
                if(this.questionnaire.questions[i].forced == false){
                    postBody.answers.push(temp);
                    continue;
                }
                if(typeof temp === "undefined"){
                    //postBody.answers.push({choice:[], blank:""});
                    this.$message.warning("请记得回答第"+ (i+1) +"题");
                    return;
                }else{
                    if((temp.choice == undefined || temp.choice.length == 0) && temp.blank == ""){
                        this.$message.warning("请记得回答第"+ (i+1) +"题");
                        return;
                    }
                    postBody.answers.push(temp);
                }
            }
            postBody.questionnaireId = this.questionnaire.questionnaireId;
            postBody.beginTime = this.beginTime;
            postBody.endTime = new Date();
            $.post("questionnaireResult", {answerPaper:JSON.stringify(postBody)}, (data)=>{
                if(data == "1" || data == 1){
                    this.$message.success("提交成功"); 
                    let answered = JSON.parse(localStorage.answered || "[]");
                    answered.push(this.$route.params.id);
                    localStorage.answered = JSON.stringify(answered);
                }
            }).fail(()=>{
                this.$message.error("网络异常");
            });
        },
        loadAnswer(){
            return new Promise((resolve, reject) => {
                if(this.$route.name == "r"){
                    let id = this.$route.params.id;
                    $.get("questionnaireResult/"+id, data => {
                        if (data.valid == 1 || data.valid == "1"){
                            this.answer = data.questionnaireResult.answers;
                            resolve(data.questionnaireResult.questionnaireId);
                        }
                        else
                            reject("invalid id");
                    }).fail(() => {
                        reject("error");
                    });
                }
                else
                    reject("wrong route");
            });
        },
        loadQuestionnaire(qid){
            var id = qid;
            $.get("questionnaire/"+id, data=>{
                if(data.valid == "1"){
                    this.questionnaire = data.questionnaire;
                    for(var i = 0; i < this.questionnaire.questions.length; i++){
                        this.hidden[i] = !this.ifShow(i);
                    }
                }else{
                    this.$message.warning("该问卷不存在");
                }
            }, "json").fail(()=>{
                this.$message.error("网络异常");
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
                    for(var v in ans.choice){
                        if(showAfter[k].indexOf(ans.choice[v]) > -1)
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
        this.beginTime = new Date();
        if(this.$route.name == "r"){
            this.loadAnswer().then(qid => {
                this.loadQuestionnaire(qid);
            }).catch(() => {
                this.$message.error("网络异常");
            });
        }
        else if(this.$route.name == "q"){
            this.loadQuestionnaire(this.$route.params.id);
        }
        this.beginTime = new Date();
    }
};
</script>

<style>
    .questionnaire-title{font-size:32px; margin-bottom: 10px; text-align:center;}
    .questionnaire-briefing{font-size:16px; margin-bottom: 30px; text-align:center;}
    .questionnaire{margin:20px;}
</style>