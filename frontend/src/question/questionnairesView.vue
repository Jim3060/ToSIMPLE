<template>
    <div>
        <p v-show="notFound">  很抱歉，没有找到名称为"{{this.$route.params.name}}"的问卷</p>
        <div>
            <ul class="list-group">
                <li v-for="questionnaire in questionnaires" :key="questionnaire" class="list-group-item">
                    <simple :title="questionnaire.paperTitle" :id="questionnaire.questionnaireId"></simple>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    import simple from "./questionnaireSimple.vue"

    export default{
        components:{simple},
        data(){return {
            questionnaires:[],
            notFound: false
        }},
        watch:{
            '$route'(to, from){
                if(to.path == "/q"){
                    this.getRandom();
                }else
                    this.getSearch();
            }
        },
        methods:{
            getSearch(){
                var self = this;
                $.get("questionnaire/search?name=" + this.$route.params.name, data=>{
                    self.questionnaires = data;
                    if(data.length == 0)
                        self.notFound = true;
                    else
                        self.notFound = false;
                }, "json").fail(()=>{
                    this.$message.error("网络异常");
                })
            },
            getRandom(){
                var self = this;
                $.get("questionnaire/random?size=10", data=>{
                    self.questionnaires = data;
                }, "json").fail(()=>{
                    this.$message.error("网络异常");
                })
            }
        },
        created(){
            if(this.$route.name == "search"){ // search
                this.getSearch();
            }
            else{ // random
                this.notFound = false;
                this.getRandom();
            }
        }
    }
</script>
