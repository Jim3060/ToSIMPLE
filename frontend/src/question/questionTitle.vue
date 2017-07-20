<template>
    <el-autocomplete popper-class="my-autocomplete" 
        v-model="title" 
        custom-item="item" 
        placeholder="请输入题目"
        :fetch-suggestions="fetchSuggestions" 
        @select="handleSelect" 
        size="small">
        <template slot="append">
            <el-button v-if="!showSuggestions" @click="showSuggestions=true">显示相似问题</el-button>
            <el-button v-else @click="hideSuggestions()">隐藏相似问题</el-button>    
        </template>   
    </el-autocomplete>
</template>

<script>
import Vue from "vue";

Vue.component("item", {
    functional: true,
    render: function (h, ctx) {
        var item = ctx.props.item;
        return h("li", ctx.data, [
            h("div", { attrs: { class: "title" } }, [item.questionTitle]),
            h("span", { attrs: { class: "options" } }, ["选项: " + (item.choices || []).map((choice) => {return choice.text;}).join(", ")])
        ]);
    },
    props: {
        item: { type: Object, required: true }
    }
});

export default {
    props:{
        questionnaireTitle:{type: String, default: ""},
        questionTitle:{type: String, default: ""}
    },
    data(){return {
        title:"",
        suggestions:[],
        showSuggestions: false
    };},
    methods:{
        loadSuggestions(){
            return new Promise((resolve, reject) => {
                if (this.title != "" && this.showSuggestions){
                    let url = "questionSojumpKW/";
                    if (this.questionnaireTitle != "")
                        url += `${this.questionnaireTitle}-`;
                    $.get(`${url}${this.title}`, data => {
                        if (data.valid == 1){
                            this.suggestions = data.question.slice(0, 10);
                            resolve("ok");
                        }
                        else{
                            this.suggestions = [];
                            resolve("ok");
                        }
                        this.showSuggestions = true;
                    }).fail(() => {
                        reject("error");
                    });
                }
                else{
                    this.suggestions = [];
                    resolve("ok");
                }
            });
        },
        hideSuggestions(){
            this.suggestions = [];
            this.showSuggestions = false;
        },
        handleSelect(item){
            this.title = item.questionTitle;
            this.$emit("select", item);
        },
        fetchSuggestions(query, callback){
            if (this.showSuggestions){
                this.loadSuggestions().then(() => {
                    callback(this.suggestions);
                }).catch(() => {
                    this.$message.error("网络异常");
                });
            }
            else{
                callback([]);
            }
        }
    },
    watch:{
        title(){
            this.$emit("update", this.title);
        }
    },
    created(){
        this.title = this.questionTitle;
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
