<template>
    <div class="questionnaire">
        <div v-if="!edit" class="questionnaire-title">{{questionnaire.paperTitle}}</div>
        <div>
            <div v-for="(question, index) in questionnaire.questions" :key="question">
                <button v-if="edit" class="btn btn-danger" @click="del(index)">删除该问题</button>
                <single v-if="question.type==0" :title="question.questionTitle" :options="question.choices" @update="update(index, $event)"></single>
                <multiple v-if="question.type==1" :title="question.questionTitle" :options="question.choices" :limit="question.limit" @update="update(index, $event)"></multiple>
                <blank v-if="question.type==2" :title="question.questionTitle" @update="update(index, $event)"></blank>
            </div>
        </div>
        <button v-if="!edit" class="btn btn-success" @click="submit()">submit</button>
    </div>
</template>

<script>
import single from "./single.vue"
import multiple from "./multiple.vue"
import blank from "./blank.vue"

export default {
    name:"questionnaire",
    components:{single, multiple, blank},
    props:{
        questionnaire:{required:true},
        edit:{required:true, default:false}
    },
    data(){return {
        answer:{}
    }},
    methods:{
        update(index, data){
            console.log("catch", data, index);
            this.answer[index] = data;
        },
        del(index){
            this.$emit("delete", index);
        },
        submit(){
            var postBody = {answer: this.answer};
            postBody.objectId = this.questionnaire.objectId;
            postBody.answerTime = new Date();
            var self = this;
            $.ajax({
                url: "SaveAnAnswerPaper",
                type: "POST",
                data: { answerPaper : JSON.stringify(postBody)},
                
                success:(data)=>{
                    console.log(data);
                }
            });
        }
    }
}
</script>

<style>
    .questionnaire-title{font-size:24px; margin-bottom: 30px;}
    .questionnaire{margin:20px;}
</style>
