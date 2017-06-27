<template>
    <div>
        <div>url: <input v-model="url"></input></div>
        <div>content: <input v-model="content"></input></div>
        <p v-show="responseMsg != ''">response: {{responseMsg}}</p>
        <b-button :variant="'primary'" :size="'sm'" @click="submit()">submit</b-button>
        <single :title="'A simple question'" :options="['choice1','choice2','choice3']"></single>
        <multiple :title="'Another simple question'" :options="['choice1','choice2','choice3']" :limit="2"></multiple>
        <blank :title="'The third simple question'"></blank>
    </div>
</template>

<script>
import Vue from "vue"
import BootStrapVue from "bootstrap-vue"
import single from "./question/single.vue"
import multiple from "./question/multiple.vue"
import blank from "./question/blank.vue"

Vue.use(BootStrapVue);

import {bBtn} from 'bootstrap-vue/lib/components'

export default {
    components:{bBtn, single, multiple, blank},
    data(){return {
        url: "",
        content: "",
        responseMsg: ""
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
    div, p{margin: 20px;}
</style>
