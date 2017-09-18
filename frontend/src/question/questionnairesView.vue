<template>
    <div>
        <p v-show="notFound">  很抱歉，没有找到名称为"{{this.$route.params.name}}"的问卷</p>
        <div>
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
                $.get("questionnaire/random?size=10", data=>{
                    self.questionnaires = data;
                }, "json").fail(()=>{
                    this.$message.error("网络异常");
                });
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
