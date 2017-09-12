<template>
    <div>
        <p v-show="notFound">  很抱歉，没有找到名称为"{{this.$route.params.name}}"的问卷</p>
        <div>
            <h3 style="text-align:center">共为您找到{{randomNum}}份随机的问卷</h3>
            <ul class="list-group">
                <li v-for="(questionnaire, index) in questionnaires" :key="questionnaire" class="list-group-item" 
                style="width:80%; margin-left:10%; margin-top:5px; margin-bottom:5px; background:#DDDBDB">
                    <simple :title="questionnaire.paperTitle" :id="questionnaire.questionnaireId" :index="index+1"></simple>
                </li>
            </ul>
        </div>
        <div v-if="$route.name=='search'">
            <el-pagination @current-change="handleCurrentChange" 
               :current-page="currentPage" :page-size="25" layout="total, prev, pager, next" :total="count">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import simple from "./questionnaireSimple.vue";

    export default{
        components:{simple},
        data(){return {
            questionnaires:[],
            notFound: false,
            currentPage: 1,
            count: 1,
            randomNum: 0,
        };},
        watch:{
            "$route"(to){
                if(to.path == "/q"){
                    this.getRandom();
                }else
                    this.getSearch(1);
            }
        },
        methods:{
            getSearch(page){
                var self = this;
                $.get(`questionnaire/search?name=${this.$route.params.name}&page=${page-1}&pageSize=25`, data=>{
                    self.questionnaires = data.items;
                    self.count = data.count;
                    if(data.length == 0)
                        self.notFound = true;
                    else
                        self.notFound = false;
                }, "json").fail(()=>{
                    this.$message.error("网络异常");
                });
            },
            getRandom(){
                var self = this;
                /*
                $.get("questionnaire/random?size=10", data=>{
                    self.questionnaires = data;
                }, "json").fail(()=>{
                    this.$message.error("网络异常");
                });*/
                console.log(1);
                self.questionnaires=[{"_id":{"$oid":"59b72dd42b267803aebf1f52"},"paperTitle":"问卷关联测试","questionnaireId":"59b72dd42b267803aebf1f52"},{"_id":{"$oid":"59b72dd42b267803aebf1f52"},"paperTitle":"问卷关联测试","questionnaireId":"59b72dd42b267803aebf1f52"},{"_id":{"$oid":"59b72dd42b267803aebf1f52"},"paperTitle":"问卷关联测试","questionnaireId":"59b72dd42b267803aebf1f52"},{"_id":{"$oid":"59b4f4ef7f68be1de866ccb0"},"paperTitle":"专业方向选择现状问卷调查","questionnaireId":"59b4f4ef7f68be1de866ccb0"},{"_id":{"$oid":"59b4e8b47f68be1de866ccac"},"paperTitle":"测试问卷一","questionnaireId":"59b4e8b47f68be1de866ccac"},{"_id":{"$oid":"59b4e99a7f68be1de866ccae"},"paperTitle":"测试问卷二","questionnaireId":"59b4e99a7f68be1de866ccae"}];
                console.log(self.questionnaires);
                self.randomNum = self.questionnaires.length;
            },
            handleCurrentChange(page){
                this.currentPage = page;
                this.getSearch(page);
            }
        },
        created(){
            if(this.$route.name == "search"){ // search
                this.getSearch(1);
            }
            else{ // random
                this.notFound = false;
                this.getRandom();
            }
        }
    };
</script>
