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
        <div>
            <button @click="submit()" class="btn btn-success">确认</button>
            <button @click="cancel()" class="btn btn-warning">取消</button>
        </div>
    </div>
</template>

<script>
export default {
    props:{
        question:{default:{}}
    },
    data(){return {
        type:"",
        types:["单选", "多选", "填空"],
        title:"",
        options:[],
        limit:'',
        edit:-1,
        buffer:""
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
        submit(){
            if(this.edit != -1)
                this.save(this.edit);
            var result = {};
            result.questionTitle = this.title;
            result.type = this.types.indexOf(this.type);
            if(result.type < 2)
                result.choices = this.options;
            if(result.type == 1)
                result.limit = this.limit;
            this.$emit("submit", result);
        }
    },
    created(){ 
        console.log(JSON.stringify(this.question));
        if( Object.keys(this.question).length > 0){
            console.log("call");
            this.title = this.question.questionTitle;
            this.type = this.types[this.question.type];
            this.limit = this.question.limit==undefined?0:this.question.limit;
            this.options = this.question.choices == undefined?{}:this.question.choices;
        }
    }
    
}
</script>

<style>
    .create, .create>div{margin:20px;}
    .limit{width:50px;}
    .option{width:300px;}
</style>

