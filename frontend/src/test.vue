<template>
    <div>
        <questionnaire :questionnaire="data"></questionnaire>
        <create :questionnaire="data"></create>
    </div>
</template>

<script>
import Vue from "vue"

import questionnaire from "./question/questionnaire.vue"
import data from "./question.js"
import create from "./question/createQuestionnaire.vue"

export default {
    components:{questionnaire, create},
    data(){return {
        url: "",
        content: "",
        responseMsg: "",
        data:data
    }},
    methods:{
        submit(){
            var self = this;
            $.post(this.url, this.content, (data)=>{
                self.responseMsg = data;
            }).fail(()=>{
                self.responseMsg = "ERROR";
            })
        }
    }
}
</script>

<style>
    
</style>
