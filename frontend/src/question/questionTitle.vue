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
//import item from "./suggestItem.vue";
import Vue from "vue";

//Vue.component("item", item);

Vue.component("item", {
    functional: true,
    render: function (h, ctx) {
        var item = ctx.props.item;
        return h("li", ctx.data, [
            h("div", { attrs: { class: "title" } }, [item.questionTitle]),
            h("span", { attrs: { class: "options" } }, ["选项: " + item.choices.map((choice) => {return choice.text;}).join(", ")])
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
            this.suggestions = [
                {questionTitle: "Q1", choices:[{text:"A"}, {text: "B"}, {text: "C"}], type: 0},
                {questionTitle: "Q2", choices:[{text:"A"}, {text: "B"}, {text: "C"}], type: 0},
                {questionTitle: "Q3", choices:[{text:"A"}, {text: "B"}, {text: "C"}], type: 0},
                {questionTitle: "Q4", choices:[{text:"A"}, {text: "B"}, {text: "C"}], type: 0},
                {questionTitle: "Q5", choices:[{text:"A"}, {text: "B"}, {text: "C"}], type: 0},
                {questionTitle: "Q6", choices:[{text:"A"}, {text: "B"}, {text: "C"}], type: 0},
                {questionTitle: "Q7", choices:[{text:"A"}, {text: "B"}, {text: "C"}], type: 0},
                {questionTitle: "Q8", choices:[{text:"A"}, {text: "B"}, {text: "C"}], type: 0},
                {questionTitle: "Q9", choices:[{text:"A"}, {text: "B"}, {text: "C"}], type: 0}
            ];
            /*$.get(`questionSojumpKW/${this.questionnaireTitle}-${this.title}`, data => {
                if (data.valid == 1)
                    this.suggestions = data.question;
                else
                    this.suggestions = [];
                this.showSuggestions = true;
            });*/
            this.showSuggestions = true;
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
            callback(this.suggestions);
        }
    },
    watch:{
        title(){
            if (this.showSuggestions)
                this.loadSuggestions;
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
