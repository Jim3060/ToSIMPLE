<template>
    <div class="creator">
        <span class="edit-title">问卷标题: </span><input v-model="title"></input>
        <div>
            <button @click="publish(1)" v-show="questionnaire.status == 0" class="btn btn-success">发布问卷</button>
            <button @click="publish(0)" v-show="questionnaire.status == 1" class="btn btn-warning">取消发布</button>
        </div>
        <p id="questionnaireId" type="hidden"> </p>
        <questionnaire :questionnaire="questionnaire" :edit="true" @delete="del($event)" @edit="edit($event)"></questionnaire>
        <button class="btn btn-primary" @click="showModal=true">添加问题</button>
        <button class="btn btn-success" @click="submit()">提交问卷</button>
        <modal :show="showModal" effect="zoom" :backdrop="false" >
            <div slot="modal-header" class="modal-header"><h4>编辑问题</h4></div>
            <div slot="modal-body" class="modal-body">
                <create v-if="showModal" :questionnaire="questionnaire" :index="idx" @submit="add($event)" @cancel="cancel($event)"></create>
            </div>
            <div slot="modal-footer"></div>
        </modal>
    </div>
</template>

<script>
import create from "./createQuestion.vue"
import questionnaire from "./questionnaire.vue"
import {modal} from "vue-strap"
import bus from "../bus.js"

export default {
    data(){return {
        showModal:false,
        question:{},
        idx: -1,
        title:"",
        questionnaire:{questions:[]}
    }},
    components:{modal, questionnaire, create},
    methods:{
        add(data){
            if(this.idx == -1)
                this.questionnaire.questions.push(data);
            else
                this.questionnaire.questions.splice(this.idx, 1, data);
            this.idx = -1;
            this.showModal = false;
            this.question = {};
        },
        del(index){
            this.questionnaire.questions.splice(index, 1);
        },
        cancel(){
            this.idx = -1;
            this.showModal = false;
        },
        create(){
            this.idx = -1;
            this.question = {};
            this.showModal = true;
        },
        edit(index){
            this.idx = index;
            this.question = this.questionnaire.questions[index];
            this.showModal = true;
        },
        submit(){
            var self = this;
            this.questionnaire["paperTitle"] = this.title;
            this.questionnaire["createDate"] = new Date();
            this.questionnaire["status"] = 0;

            $.ajax({
                type: 'POST',
                url: "addQuestionnaire",
                data: {questionnaire: JSON.stringify(this.questionnaire)},
                dataType: "json",
                success: function(data){
                    self.questionnaire["questionnaireId"] = data.questionnaireId;
                    bus.$emit("showMsg","success","提交成功");
                    this.$router.push({name:'n', params:{id: data.questionnaireId}});
                }
            });
           
        },
        publish(status){
            $.post("setQuestionnaireStatus", {questionnaireId: this.questionnaire.questionnaireId, status: status}, data=>{
                if(data == '1' || data == 1){
                    bus.$emit("showMsg", "success", "操作成功");
                    this.questionnaire.status = status;
                }
                else
                    bus.$emit("showMsg", "warning", "操作失败");
            }).fail(()=>{
                bus.$emit("showMsg", "danger", "网络异常");
            })
        },
        loadQuestionnaire(){
            if(this.$route.name == "n"){
                var id = this.$route.params.id;
                var self = this;
                $.post("findAQuestionnaire", {questionnaireId: id}, data=>{
                    if(data.valid == "1"){
                        self.questionnaire = data.questionnaire;
                        self.title = data.questionnaire.paperTitle;
                    }else{
                        bus.$emit("showMsg", "warning", "警告: 该问卷不存在!");
                    }
                }, "json").fail(()=>{
                    bus.$emit("showMsg", "danger", "错误: 网络异常!");
                })
            }

        }
    },
    watch:{
        '$route'(to, from){
            if(to.path == "/n"){
                this.questionnaire = {questions:[]};
            }else
                this.loadQuestionnaire();
        }
    },
    created(){
        this.loadQuestionnaire();
    }
}
</script>

<style>
    .edit-title{font-size:20px;}
    .creator>span, .creator>button {margin-left:20px;}
    .creator>div>button{margin:20px;}
</style>
