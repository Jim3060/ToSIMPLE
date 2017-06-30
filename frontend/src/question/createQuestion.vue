<template>
    <div class="create">
        <div>
            问题类型: 
            <select v-model="type">
                <option>单选</option>
                <option>多选</option>
                <option>填空</option>
            </select>
        </div>
        <div>
            题目: <input v-model="title"></input>
        </div>
        <div v-if="type=='多选'">
            最多可以选择<input class="limit" v-model="limit"></input>项
        </div>
        <div v-if="type!='填空'">
            选项:
            <ul class="list-group">
                <li class="list-group-item" v-for="(option, index) in options" :key="option">
                    <div v-show="edit==index">
                        <input class="option" v-model="buffer"></input>
                        <button class="btn btn-success btn-sm" @click="save(index)">保存</button>
                        <button class="btn btn-warning btn-sm" @click="can(index)">取消</button>
                    </div>
                    <div v-show="edit!=index">
                        <label class="option">{{option}}</label>
                        <button class="btn btn-success btn-sm" @click="change(index)">修改</button>
                        <button class="btn btn-danger btn-sm" @click="del(index)">删除</button>
                    </div>
                </li>
                <li v-if="edit==-1" class="list-group-item" @click="add()">新增选项</li>
            </ul>
        </div>
        <input type="checkbox" v-model="connect">题目关联</input>
        <div v-show="connect">
            <ul class="list-group">
                <li class="list-group-item" v-for="(value, key) in showAfter" :key="key">
                    <connectItem @update="update" @cancel="delCon($event)" :questionnaire="questionnaire" :index="index" :idx="key"></connectItem>
                </li>
                <li v-if="newItem" class="list-group-item">
                    第<input v-model.number="newIdx" style="width:50px;"></input>题
                    <button @click="addCon()" class="btn btn-success btn-sm">确定</button>
                    <button @click="newItem=false" class="btn btn-warning btn-sm">取消</button>
                </li>
                <li @click="newItem=true" class="list-group-item">新增关联</li>
            </ul> 
        </div>
        <div>
            <button @click="submit()" class="btn btn-success">确认</button>
            <button @click="cancel()" class="btn btn-warning">取消</button>
        </div>
    </div>
</template>

<script>
import connectItem from "./connectItem.vue"
import Vue from "vue"
import bus from "../bus.js"

export default {
    components:{connectItem},
    props:{
        questionnaire:{default(){return{}}},
        index:{default:-1}
    },
    data(){return {
        type:"单选",
        types:["单选", "多选", "填空"],
        question:{},
        title:"",
        options:[],
        limit:'',
        showAfter:{},
        edit:-1,
        buffer:"",
        connect: false,
        newItem: false,
        newIdx: 0
    }},
    methods:{
        del(index){
            this.options.splice(index, 1);
        },
        add(){
            this.options.push("");
            this.buffer = "";
            this.edit = this.options.length-1;
        },
        can(index){
            this.edit = -1;
            if(this.options.length-1 == index)
                this.options.splice(index, 1);
        },
        change(index){
            this.buffer = this.options[index];
            this.edit = index;
        },
        save(index){
            this.edit = -1;
            this.options[index] = this.buffer;
        },
        cancel(){
            this.$emit("cancel");
        },
        delCon(idx){
            Vue.delete(this.showAfter, idx);
        },
        addCon(){
            var idx = this.newIdx - 1;
            this.newItem = false;
            Vue.set(this.showAfter, idx, new Array());
        },
        update(idx, index, select){
            console.log("update");
            /*if(index >= 0){
                this.questionnaire.questions[index].showAfter[idx] = select
            }*/
            if(select.length > 0){
                this.showAfter[idx] = select;
                this.newItem = false;
            }

        },
        submit(){
            if(this.edit != -1)
                this.save(this.edit);
            var result = {};
            result.questionTitle = this.title;
            result.type = this.types.indexOf(this.type);
            if(this.connect)
                result.showAfter = this.showAfter;
            else
                result.showAfter = {};
            if(result.type < 2)
                result.choices = this.options;
            if(result.type == 1)
                result.limit = this.limit;
            this.$emit("submit", result);
        }
    },
    created(){ 
        if( Object.keys(this.questionnaire).length > 0 && this.index > -1){
            console.log("call");
            this.title = this.questionnaire.questions[this.index].questionTitle;
            this.type = this.types[this.questionnaire.questions[this.index].type];
            this.limit = this.questionnaire.questions[this.index].limit==undefined?0:this.questionnaire.questions[this.index].limit;
            this.options = this.questionnaire.questions[this.index].choices == undefined?{}:this.questionnaire.questions[this.index].choices;
            this.showAfter = this.questionnaire.questions[this.index].showAfter == undefined?{}:this.questionnaire.questions[this.index].showAfter;
            this.connect = Object.keys(this.showAfter).length > 0; 
        }
    }
    
}
</script>

<style>
    .create, .create>div, .create>input{margin:20px;}
    .limit{width:50px;}
    .option{width:300px;}
</style>

