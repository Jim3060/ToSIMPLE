<template>
    <div>
        <ul class="question list-group">
            <li class="list-group-item">[多选{{limit>0?`, 最多能选${limit}项`:''}}{{forced?", 必答":""}}] <span class="question-title">{{index+1}}. {{title}}</span></li>
            <!--<li class="list-group-item" v-for="(option, index) in options" :key="option">
                <input type="checkbox" v-model="select" :value="index" :disabled="select.length>=limit&&select.indexOf(index)==-1"></input>
                <label>{{option}}</label>
            </li> -->
            <el-checkbox-group v-model="select" :max="limit">
                <li class="list-group-item" v-for="(option, index) in options" :key="option">
                    <el-checkbox :label="index">
                        <span>{{option.text}}</span>
                        <div v-if="option.photoId!=undefined&&option.photoId!=''" ><img :src="'file/'+option.photoId" /></div>
                    </el-checkbox>
                </li>
                <li v-if="mix" class="list-group-item">
                    <el-checkbox :label="options.length">
                        <input v-model="blank" :disabled="select.indexOf(options.length)==-1" placeholder="其他">
                    </el-checkbox>
                </li>
            </el-checkbox-group>

        </ul>
    </div>
</template>

<script>
    export default{
        name: "multiple",
        props:{options:{required: true}, title:{required: true}, limit:{required: true}, index:{}, mix:{default:false}, forced:{}, answer:{}},
        data(){return {
            select:[],
            blank:""
        };},
        methods:{
            addOrRemove(index){
                var i = this.select.indexOf(index);
                if(i == -1){
                    if(this.select.length < this.limit)
                        this.select.push(index);
                }else{
                    this.select.splice(i, 1);
                }
            },
            update(){
                let idx = this.select.indexOf(this.options.length);
                if(idx == -1){
                    this.$emit("update", {choice:this.select, blank:""});
                }else{
                    let temp = this.select.slice();
                    temp.splice(idx, 1);
                    this.$emit("update", {choice:temp , blank: this.blank});
                }
            }
        },
        watch:{
            select(){this.update();},
            blank(){this.update();}
        },
        created(){
            if (this.answer != undefined){
                this.select = this.answer.choice;
                if (this.answer.blank != ""){
                    this.select.push(this.options.length);
                    this.blank = this.answer.blank;
                }
            }
        }
    };
</script>

<style>
    .question-title{font-size:18px;}
</style>

