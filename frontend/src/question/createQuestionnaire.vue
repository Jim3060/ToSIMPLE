<template>
    <div class="creator">
        <span class="edit-title">问卷标题: </span><input v-model="title"></input>
        <p id="questionnaireId" type="hidden"> </p>
        <questionnaire :questionnaire="questionnaire" :edit="true" @delete="del($event)" @edit="edit($event)"></questionnaire>
        <button class="btn btn-primary" @click="showModal=true">添加问题</button>
        <button class="btn btn-success" @click="submit()">提交问卷</button>
        <modal :show="showModal" effect="zoom" :backdrop="false" >
            <div slot="modal-header" class="modal-header"><h4>编辑问题</h4></div>
            <div slot="modal-body" class="modal-body">
                <create v-if="showModal" :question="question" @submit="add($event)" @cancel="showModal=false"></create>
            </div>
            <div slot="modal-footer"></div>
        </modal>
    </div>
</template>

<script>
import create from "./createQuestion.vue"
import questionnaire from "./questionnaire.vue"
import {modal} from "vue-strap"

export default {
    props:{
        questionnaire:{default:{questions:[]}}
    },
    data(){return {
        showModal:false,
        question:{},
        idx: -1,
        title:""
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
                }
            });
           
        }
    }
}
</script>

<style>
    .edit-title{font-size:20px;}
    .creator>span, .creator>button{margin-left:20px;}
</style>
