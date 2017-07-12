<template>
    <div id="my-questionnaires">
        <div width="30px"></div>
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>问卷标题</th>
                <th>状态</th>
                <th>已回答人数</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="questionnaire in questionnaires" :key="questionnaire">
                <td>{{questionnaire.paperTitle}}</td>
                <td>{{questionnaire.status==1?'已发布':'未发布'}}</td>
                <td>{{questionnaire.answerNumber || '--'}}</td>
                <td>
                    <a :href="'#/n/'+questionnaire.questionnaireId">查看问卷</a>
                    <a :href="'#/s/'+questionnaire.questionnaireId">查看统计</a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default{
        data(){return {
            questionnaires:[]
        }},
        methods:{
            getMyQuestionnaires(){
                var self = this;
                $.get("questionnaire", data=>{
                    if(data.valid == 1 || data.valid == '1'){
                        this.questionnaires = data.questionnaires;
                    }else{
                        this.$message.error("您还没有登录");
                    }
                }, "json").fail(()=>{
                    this.$message.error("网络异常");
                })
            }
        },
        created(){
            this.getMyQuestionnaires();
        }
    }
</script>