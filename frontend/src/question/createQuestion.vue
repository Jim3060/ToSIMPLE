<template>
    <div class="create">
        <div>
            问题类型:
            <!--<select v-model="type">
                <option>单选</option>
                <option>多选</option>
                <option>填空</option>
            </select>-->
            <el-radio-group v-model="type" size="small">
                <el-radio-button label="单选" style="font-weight:400"></el-radio-button>
                <el-radio-button label="多选" style="font-weight:400"></el-radio-button>
                <el-radio-button label="填空" style="font-weight:400"></el-radio-button>
            </el-radio-group>
        </div>
        <div>
            题目: <input v-model="title"></input>
            <el-checkbox v-model="forced">必答题</el-checkbox>
        </div>
        <div v-if="type=='多选'">
            最多可以选择<input class="limit" v-model.number="limit"></input>项
        </div>
        <div v-if="type!='填空'">
            选项: <el-checkbox v-model="mix" style="font-weight:400">允许其他选项</el-checkbox>
            <div></div>
            <ul class="list-group">
                <li class="list-group-item" v-for="(option, index) in options" :key="option">
                    <div v-show="edit==index">
                        <input class="option" v-model="buffer.text"></input>
                        <el-checkbox v-model="pictureMode" style="font-weight:400">添加图片</el-checkbox>
                        <el-button size="small" type="primary" @click="save(index)">保存</el-button>
                        <el-button size="small" @click="can(index)">取消</el-button>
                        <div v-if="pictureMode">
                            <el-upload class="avatar-uploader" action="file"
                                    :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                                <img v-if="buffer.photoId!=undefined" :src="'file/'+buffer.photoId" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            </el-upload>
                        </div>
                    </div>
                    <div v-show="edit!=index">
                        <label class="option">{{option.text}}</label>
                        <el-button size="small" type="primary" @click="change(index)">修改</el-button>
                        <el-button size="small" type="danger" @click="del(index)">删除</el-button>
                    </div>
                </li>
                <li v-if="edit==-1" class="list-group-item" @click="add()">新增选项</li>
            </ul>
        </div>
        <div>
             <span>题目关联: </span>
             <el-tooltip :disabled="connectAccessable.length>0 || Object.keys(showAfter).length>0" content="没有可以关联的问题" placement="right">
                <el-switch :disabled="connectAccessable.length==0 && Object.keys(showAfter).length==0" v-model="connect" ></el-switch>
             </el-tooltip>
        </div>
        <!--<input type="checkbox" v-model="connect">题目关联</input>-->
        <div v-show="connect">
            <ul class="list-group">
                <li class="list-group-item" v-for="(value, key) in showAfter" :key="key">
                    <div style="margin-top:10px"></div>
                    <connectItem @update="update" @cancel="delCon($event)" :questionnaire="questionnaire" :index="index" :idx="key"></connectItem>
                </li>
                <li v-if="newItem" class="list-group-item">
                    <!--第
                    <select v-model.number="newIdx">
                        <option v-for="i in connectAccessable" :key="i">{{i}}</option>
                    </select>
                    题-->
                    <el-select v-model.number="newIdx" size="small" style="width:150px" placeholder="请选择题号">
                        <el-option v-for="i in connectAccessable" :key="i" :value="i" :label="'第'+i+'题'"></el-option>
                    </el-select>
                    <el-button size="small" type="primary" @click="addCon()">确定</el-button>
                    <el-button size="small" @click="newItem=false">取消</el-button>
                </li>
                <li v-show="connectAccessable.length > 0 && !newItem" @click="newItem=true" class="list-group-item">新增关联</li>
            </ul>
        </div>
        <div>
            <el-button @click="submit()" type="primary">确认</el-button>
            <el-button @click="cancel()">取消</el-button>
        </div>
    </div>
</template>

<script>
import connectItem from "./connectItem.vue"
import Vue from "vue"
import bus from "../bus.js"
import Element from "element-ui"

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
        newIdx: 1,
        connectAccessable:[],
        imageUrl:"",
        pictureMode: false,
        mix:false,
        forced: false
    }},
    methods:{
        del(index){
            this.options.splice(index, 1);
        },
        add(){
            this.options.push("");
            this.buffer = {text: ""};
            this.edit = this.options.length-1;
            this.pictureMode = false;
        },
        can(index){
            this.edit = -1;
            if(this.options.length-1 == index && this.options[index] == "")
                this.options.splice(index, 1);
            this.pictureMode = false;
        },
        change(index){
            this.buffer = this.options[index];
            this.edit = index;
            this.pictureMode = this.buffer.photoId != undefined;
        },
        save(index){
            this.edit = -1;
            if(this.pictureMode == false){
                Vue.delete(this.buffer, "photoId");
            }
            this.options[index] = this.buffer;
            this.pictureMode = false;
        },
        cancel(){
            this.$emit("cancel");
        },
        delCon(idx){
            Vue.delete(this.showAfter, idx);
            this.connectAccessable.push(parseInt(idx)+1);
        },
        addCon(){
            var idx = this.newIdx - 1;
            this.newItem = false;
            Vue.set(this.showAfter, idx, new Array());
            Vue.delete(this.connectAccessable, this.connectAccessable.indexOf(idx+1));
        },
        update(idx, index, select){
            if(select.length > 0){
                this.showAfter[idx] = select;
                this.newItem = false;
            }

        },
        resultCheck(result){
            if(result.questionTitle == ""){
                this.$message.warning("题目不能为空");
                return false;
            }
            else if(result.type == 1 && (typeof result.limit == "string" || result.limit <= 0)){
                this.$message.warning("请设置选择数量限制");
                return false;
            }
            else if(result.type <=1 && result.choices.length < 2){
                if(result.choices.length == 1)
                    this.$message.warning("只有一个选项，会不会给人一种钦定的感觉？");
                else
                    this.$message.warning("选择题至少应该有2个选项");
                return false;
            }
            else
                return true;
        },
        submit(){
            if(this.edit != -1)
                this.save(this.edit);
            var result = {};
            result.questionTitle = this.title;
            result.type = this.types.indexOf(this.type);
            result.forced = this.forced;
            if(this.connect)
                result.showAfter = this.showAfter;
            else
                result.showAfter = {};
            if(result.type < 2){
                result.choices = this.options;
                result.mix = this.mix;
            }
            if(result.type == 1)
                result.limit = this.limit;
            if(this.resultCheck(result))
                this.$emit("submit", result);
        },
        handleAvatarSuccess(res, file) {
            Vue.set(this.buffer, "photoId", res.fileId);
            this.imageUrl = URL.createObjectURL(file.raw);
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
            this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
            this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        }
    },
    created(){
        if( Object.keys(this.questionnaire).length > 0 && this.index > -1){
            this.title = this.questionnaire.questions[this.index].questionTitle;
            this.type = this.types[this.questionnaire.questions[this.index].type];
            this.limit = this.questionnaire.questions[this.index].limit==undefined?0:this.questionnaire.questions[this.index].limit;
            this.options = this.questionnaire.questions[this.index].choices == undefined?{}:this.questionnaire.questions[this.index].choices;
            this.showAfter = this.questionnaire.questions[this.index].showAfter == undefined?{}:this.questionnaire.questions[this.index].showAfter;
            this.forced = this.questionnaire.questions[this.index].forced || false;
            this.connect = Object.keys(this.showAfter).length > 0;
        }
        var questions = this.questionnaire.questions;
        for(var i in questions){
            if((this.index == -1 || i < this.index) && questions[i].type < 2 && Object.keys(this.showAfter).indexOf(i) == -1)
                this.connectAccessable.push(parseInt(i)+1);
        }
    }

}
</script>

<style>
    .create, .create>div, .create>input{margin:10px;}
    .limit{width:50px;}
    .option{width:240px;}
    .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #20a0ff;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  input.el-upload__input{display:none!important;}
</style>
