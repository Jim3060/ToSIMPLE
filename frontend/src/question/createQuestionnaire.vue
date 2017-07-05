<template>
    <div class="creator">
        <span v-show="edit" class="edit-title">问卷标题: </span><input v-show="edit" v-model="title"></input>
        <div>
            <el-button type="primary" @click="publish(1)" v-show="questionnaire.status == 0" >发布问卷</el-button>
            <el-button type="warning" @click="publish(0)" v-show="questionnaire.status == 1">取消发布</el-button>
            <el-button type="danger" @click="deleteQuestionnaire()" v-show="$route.name=='n'">删除问卷</el-button>
            <a v-if="$route.name=='n'" :href="'questionnaire/download/'+$route.params.id"><el-button>下载回答</el-button></a>
        </div>
        <span>编辑模式 </span><el-switch v-model="edit"></el-switch>
        <p id="questionnaireId" type="hidden"> </p>
        <questionnaire :questionnaire="questionnaire" :edit="edit" @delete="del($event)" @edit="edit($event)"></questionnaire>
        <el-button v-show="edit" type="primary" @click="showModal=true">添加问题</el-button>
        <el-button v-show="edit" type="success" @click="submit()">提交问卷</el-button>
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
        questionnaire:{questions:[]},
        edit:true
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
        deleteQuestionnaire(){
            this.$confirm('此操作将永久删除该问卷, 是否继续?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'danger'
            }).then(() => {
                $.ajax({
                    type:"DELETE",
                    url: "questionnaire/" + this.$route.params.id,
                    success: data=>{
                        if(data == '1' || data == 1)
                            this.$message.success("删除成功");
                        else
                            this.$message.warning("该问卷不存在，或您没有删除的权限");
                    }
                }) ;
            })
        },
        submit(){
            var self = this;
            this.questionnaire["paperTitle"] = this.title;
            this.questionnaire["createDate"] = new Date();
            this.questionnaire["status"] = 0;

            if(this.$route.name == "n"){
                var id = this.$route.params.id;
                $.ajax({
                    type: 'PUT',
                    url: "questionnaire/" + id,
                    data: {questionnaire: JSON.stringify(this.questionnaire)},
                    dataType: "json",
                    success: function(data){
                        this.$message.success("修改成功");
                    }
                });
            }
            else
                $.post("questionnaire",{questionnaire:JSON.stringify(this.questionnaire)}, data=>{
                    self.questionnaire["questionnaireId"] = data.questionnaireId;
                    this.$message.success("提交成功");
                    this.$router.push({name:"n", params:{id: data.questionnaireId}});
                }, "json").fail(()=>{
                    this.$message.error("网络异常");
                })
           
        },
        publish(status){
            $.post("setQuestionnaireStatus", {questionnaireId: this.questionnaire.questionnaireId, status: status}, data=>{
                if(data == '1' || data == 1){
                    this.$message.success("操作成功");
                    this.questionnaire.status = status;
                }
                else
                    this.$message.error("操作失败");
            }).fail(()=>{
                this.$message.error("网络异常");
            })
        },
        loadQuestionnaire(){
            if(this.$route.name == "n"){
                var id = this.$route.params.id;
                var self = this;
                $.get("questionnaire/" + id, data=>{
                    if(data.valid == "1"){
                        self.questionnaire = data.questionnaire;
                        self.title = data.questionnaire.paperTitle;
                    }else{
                        this.$message.warning("问卷不存在"); 
                    }
                }, "json").fail(()=>{
                    //bus.$emit("showMsg", "danger", "错误: 网络异常!");
                    this.$message.error("网络异常");
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
    .creator>div{margin:15px;}
    .creator>span{font-size:20px;}
</style>
