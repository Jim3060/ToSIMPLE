<template>
    <div class="associate-content">
        <el-input v-model="associateID">
            <div slot="prepend">被关联问卷ID</div>
            <div slot="append" v-if="oldID!=''" @click="clear()">清除当前关联</div>
        </el-input>
        <div>{{associateTitle}}</div>
        <el-input type="textarea" placeholder="请填写关联简介" v-model="associateMessage"></el-input>
        <div class="buttons">
            <el-button @click="$emit('cancel')">取消</el-button>
            <el-button type="success" :disabled="associateValid==false" @click="submit()">确定</el-button>
        </div>
    </div>
</template>

<script>
export default {
    props:{
        id:{}
    },
    data(){return {
        associateID:"",
        associateTitle:"请正确输入ID",
        associateMessage:"",
        associateValid: false,
        oldID:""
    };},
    methods:{
        submit(){
            if (this.oldID != "") {
                $.get(`breakAssociation?questionnaireId1=${this.id}&questionnaireId2=${this.oldID}`);
            }
            $.get(`associateQuestionnaires?questionnaireId1=${this.id}&questionnaireId2=${this.associateID}&message=${this.associateMessage}`);
            this.$emit("submit");
        },
        fetchTitle(){
            $.get("questionnaire/" + this.associateID, data => {
                if (data.valid == "1") {
                    this.associateTitle = data.questionnaire.paperTitle;
                    this.associateValid = true;
                } else {
                    this.associateTitle = "问卷不存在";
                    this.associateValid = false;
                }
            }, "json").fail(() => {
                this.associateMessage = "网络异常";
                this.associateValid = false;
            });
        },
        clear(){
            this.$confirm("确定要清除当前关联的问题吗?", "警告", {
                confirmButtonText:"确定",
                cancelButtonText:"取消",
                type:"danger"
            }).then(() => {
                $.get(`breakAssociation?questionnaireId1=${this.id}&questionnaireId2=${this.oldID}`);
                this.oldID = "";
                this.associateID = "";
                this.associateMessage = "";
            }).catch(() => {});
        },
        loadAssociate(id){
            $.get(`getOneAssociationInfo?questionnaireId=${id}`, data => {
                if (data.valid == 1) {
                    this.associateID = data.questionnaireAssociation["questionnaireId"];
                    this.oldID = this.associateID;
                    this.associateMessage = data.questionnaireAssociation["message"];
                }
            }, "json");
        }
    },
    watch:{
        associateID(){
            if (this.associateID.length == 24) {
                this.fetchTitle();
            }
            else {
                this.associateTitle = "请正确输入ID";
            }
        }
    },
    created(){
        if (this.id != "") {
            this.loadAssociate(this.id);
        }
    }
};
</script>

<style scoped>
.associate-content>* {
    margin-top: 10px;
}

.associate-content .buttons {
    text-align: right
}
</style>
