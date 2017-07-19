<template>
    <el-autocomplete popper-class="my-autocomplete" 
        v-model="title" 
        custom-item="item" 
        placeholder="请输入题目"
        :fetch-suggestions="fetchSuggestions" 
        @select="handleSelect" 
        size="small">
        <template slot="append">
            <el-button v-if="!showSuggestions" @click="loadSuggestions()">显示相似问题</el-button>
            <el-button v-else @click="hideSuggestions()">隐藏相似问题</el-button>    
        </template>   
    </el-autocomplete>
</template>

<script>
import item from "./suggestItem.vue";
import Vue from "vue";

Vue.component("item", item);

export default {
    props:{
        questionnaireTitle:{type: String, default: ""}
    },
    data(){return {
        title:"",
        suggestions:[],
        showSuggestions: false
    };},
    methods:{
        loadSuggestions(){
            $.get(`questionSojumpKW/${this.questionnaireTitle}-${this.title}`, data => {
                if (data.valid == 1)
                    this.suggestions = data.question;
                else
                    this.suggestions = [];
                this.showSuggestions = true;
            });
            this.showSuggestions = true;
        },
        hideSuggestions(){
            this.suggestions = [];
            this.showSuggestions = false;
        },
        handleSelect(){

        },
        fetchSuggestions(query, callback){
            callback(this.suggestions);
        }
    },
    watch:{
        title(){
            this.$emit("update", this.title);
        }
    }
};
</script>

<style lang="less">
.my-autocomplete {
    li {
        line-height: normal;
        padding: 7px;

        .title {
            text-overflow: ellipsis;
            overflow: hidden;
        }
        .options {
            font-size: 12px;
            color: #b4b4b4;
        }

        .highlighted .options {
            color: #ddd;
        }
    }
}
</style>
