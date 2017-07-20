<template>
    <div class="statistics">
        <el-button @click="goBack()">返回</el-button>
        <el-button @click="draw()">保存</el-button>
        <div id="images">
            <question class="image" v-for="(data, index) in resultData.questionStatistics" :key="data" 
                    :statisticData="data" :index="index" 
                    :title="questionnaire.questions[index]!=undefined?questionnaire.questions[index].questionTitle:'Loading...'" 
                    :questionType="questionnaire.questions[index]!=undefined?questionnaire.questions[index].type:'填空'" 
                    :total="resultData.answerNumber">
            </question>
        </div>
    </div>
</template>

<script>
import question from "./question.vue";
//import html2canvas from "html2canvas";

export default {
    components:{question},
    data(){return {
        resultData:{"questionStatistics":[],"answerNumber":1},
        questionnaire:{questions:[]}
    };},
    methods:{
        goBack(){
            this.$router.push({name:"n", params:{id: this.$route.params.id}});
        },
        draw(){
            window.print();
            /*html2canvas($("#images")[0]).then(canvas => {
                window.open(canvas.toDataURL());
            });*/
            /*let canvases = $("canvas");
            const length = canvases.length;
            for(let i = 0; i < length; i++){
                console.log(canvases[i]);
                window.open(canvases[i].toDataURL());
            }*/
        }
    },
    created(){
        var id = this.$route.params.id;
        var self = this;
        $.get("questionnaireStatistics/" + id, data=>{
            self.resultData = data;
        }, "json");
        $.get("questionnaire/" + id, data=>{
            if(data.valid == "1"){
                self.questionnaire = data.questionnaire;
            }else{
                this.$message.warning("该问卷不存在");
            }
        }, "json").fail(()=>{
            this.$message.error("网络异常");
        });
    }
};
</script>

<style>
    .statistics>button{margin-left:4%; margin-bottom:10px;}
</style>

