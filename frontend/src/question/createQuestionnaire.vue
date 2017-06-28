<template>
    <div>
        <questionnaire :questionnaire="questionnaire" :edit="true" @delete="del($event)"></questionnaire>
        <button class="btn btn-primary" @click="showModal=true">新建问题</button>
        <modal :show="showModal" effect="zoom" :backdrop="false" >
            <div slot="modal-header" class="modal-header"><h4>添加问题</h4></div>
            <div slot="modal-body" class="modal-body">
                <create v-if="showModal" @submit="add($event)" @cancel="showModal=false"></create>
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
    data(){return {
        questionnaire:{title:"paper title", questions:[]},
        showModal:false
    }},
    components:{modal, questionnaire, create},
    methods:{
        add(data){
            this.questionnaire.questions.push(data);
            this.showModal = false;
        },
        del(index){
            this.questionnaire.questions.splice(index, 1);
        },
        create(){
            this.showModal = true;
        }
    }
}
</script>
