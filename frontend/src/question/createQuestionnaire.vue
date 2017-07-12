<template>
    <div v-show="!invalid" class="creator">
        <div v-show="questionnaire.status==0">
            <span class="edit-title">问卷标题: </span>
            <input class="edit-title" :disabled="!editMode" v-model="title"></input>
            <div></div>
            <div>
                <span class="edit-briefing" style="float:left">简介: </span>
                <textarea class="edit-briefing" :disabled="!editMode" v-model="briefing"></textarea>
            </div>
        </div>
        <div class="buttons">
            <el-button type="primary" @click="publish(1)" v-show="questionnaire.status == 0 && $route.name=='n'">发布问卷</el-button>
            <!--<el-button type="warning" @click="publish(0)" v-show="questionnaire.status == 1">取消发布</el-button>-->
            <el-button type="danger" @click="deleteQuestionnaire()" v-show="$route.name=='n'">删除问卷</el-button>
            <el-button v-if="$route.name=='n'" @click="jumpToAnswer()">前往回答页</el-button>
            <a v-if="$route.name=='n'" :href="'questionnaireResult/download/'+$route.params.id">
                <el-button>下载回答</el-button>
            </a>
            <el-button v-if="$route.name=='n'" @click="jumpToStatistic()">查看统计</el-button>
        </div>
        <div v-show="questionnaire.status==0">
            <span class="edit-switch">编辑模式 </span>
            <el-switch v-model="editMode"></el-switch>
        </div>
        <p id="questionnaireId" type="hidden"> </p>
        <questionnaire :questionnaire="questionnaire" :edit="questionnaire.status==0&&editMode" @delete="del($event)" @edit="edit($event)"></questionnaire>
        <div v-show="questionnaire.status==0" class="buttons">
            <el-button v-show="editMode" type="primary" @click="showModal=true">添加问题</el-button>
            <el-button type="success" @click="submit()">提交问卷</el-button>
            <el-button @click="save()">暂存问卷</el-button>
        </div>
        <modal :show="showModal" effect="zoom" :backdrop="false">
            <div slot="modal-header" class="modal-header">
                <h4>编辑问题</h4>
            </div>
            <div slot="modal-body" class="modal-body">
                <create v-if="showModal" :questionnaire="questionnaire" :index="idx" @submit="add($event)" @cancel="cancel($event)"></create>
            </div>
            <div slot="modal-footer"></div>
        </modal>
    </div>
</template>

<script>
import create from "./createQuestion.vue"
import questionnaire from "./questionnaire.vue"
import { modal } from "vue-strap"
import bus from "../bus.js"

export default {
    data() {
        return {
            showModal: false,
            question: {},
            idx: -1,
            title: "",
            briefing: "",
            questionnaire: { questions: [], status:0 },
            editMode: true,
            invalid: false,
            recovered: false
        }
    },
    components: { modal, questionnaire, create },
    methods: {
        add(data) {
            if (this.idx == -1)
                this.questionnaire.questions.push(data);
            else
                this.questionnaire.questions.splice(this.idx, 1, data);
            this.idx = -1;
            this.showModal = false;
            this.question = {};
        },
        del(index) {
            this.questionnaire.questions.splice(index, 1);
        },
        cancel() {
            this.idx = -1;
            this.showModal = false;
        },
        create() {
            this.idx = -1;
            this.question = {};
            this.showModal = true;
        },
        edit(index) {
            this.idx = index;
            this.question = this.questionnaire.questions[index];
            this.showModal = true;
        },
        save() {
            this.$confirm("将问卷暂存在本地存在丢失和被他人看到的可能，并且会覆盖之前暂存的问卷，是否要继续？", "警告", {
                confirmButtonText: "暂存",
                cancelButtonText: "取消",
                type: "danger"
            }).then(() => {
                this.questionnaire.paperTitle = this.title;
                this.questionnaire.briefing = this.briefing;
                localStorage.questionnaire = JSON.stringify(this.questionnaire)
                this.$message.success("问卷已暂存，请记得及时提交");
                this.recovered = false;
            })
        },
        recover() {
            if (localStorage.questionnaire != undefined) {
                this.$confirm("检测到本地有暂存的问卷，是否恢复？", "信息", {
                    confirmButtonText: "恢复",
                    cancelButtonText: "取消",
                    type: "info"
                }).then(() => {
                    this.questionnaire = JSON.parse(localStorage.questionnaire);
                    this.title = this.questionnaire.paperTitle;
                    this.briefing = this.questionnaire.briefing || "";
                    this.recovered = true;
                })
            }
        },
        deleteQuestionnaire() {
            this.$confirm('此操作将永久删除该问卷, 是否继续?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'danger'
            }).then(() => {
                $.ajax({
                    type: "DELETE",
                    url: "questionnaire/" + this.$route.params.id,
                    dataType: "json",
                    success: data => {
                        if (data.deleteSuccess == '1' || data.deleteSuccess == 1) {
                            this.$message.success("删除成功，即将离开此页...");
                            setTimeout(() => {
                                this.$router.push({ path: "/index" });
                            }, 2000)
                        }
                        else
                            this.$message.warning("该问卷不存在，或您没有删除的权限");
                    }
                });
            })
        },
        jumpToStatistic() {
            this.$router.push({ name: "s", params: { id: this.$route.params.id } });
        },
        jumpToAnswer() {
            this.$router.push({ name: "q", params: { id: this.$route.params.id } });
        },
        submit() {
            var self = this;
            this.questionnaire["paperTitle"] = this.title;
            this.questionnaire["briefing"] = this.briefing;
            this.questionnaire["createDate"] = new Date();
            this.questionnaire["status"] = 0;
            this.questionnaire["answerNumber"] = 0;

            if (this.$route.name == "n") {
                var id = this.$route.params.id;
                $.ajax({
                    type: 'PUT',
                    url: "questionnaire/" + id,
                    data: { questionnaire: JSON.stringify(this.questionnaire) },
                    dataType: "json",
                    success: function (data) {
                        this.$message.success("修改成功");
                        if (this.recovered)
                            localStorage.removeItem("questionnaire");
                    }
                });
            }
            else
                $.post("questionnaire", { questionnaire: JSON.stringify(this.questionnaire) }, data => {
                    if (data.valid == 1) {
                        self.questionnaire["questionnaireId"] = data.questionnaireId;
                        this.$message.success("提交成功");
                        if (this.recovered)
                            localStorage.removeItem("questionnaire");
                        this.$router.push({ name: "n", params: { id: data.questionnaireId } });
                    }
                    else {
                        this.$message.error("提交失败");
                    }
                }, "json").fail(() => {
                    this.$message.error("网络异常");
                })

        },
        publish(status) {
            this.$confirm('问卷一经发布无法修改, 是否继续?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'danger'
            }).then(() => {
                $.post("setQuestionnaireStatus", { questionnaireId: this.questionnaire.questionnaireId, status: status }, data => {
                    if (data == '1' || data == 1) {
                        this.$message.success("操作成功");
                        this.questionnaire.status = status;
                    }
                    else
                        this.$message.error("操作失败");
                }).fail(() => {
                    this.$message.error("网络异常");
                })
            })
        },
        loadQuestionnaire() {
            if (this.$route.name == "n") {
                var id = this.$route.params.id;
                var self = this;
                $.get("questionnaire/" + id, data => {
                    if (data.valid == "1") {
                        self.questionnaire = data.questionnaire;
                        self.title = data.questionnaire.paperTitle;
                        self.briefing = data.questionnaire.briefing || "";
                        this.invalid = false;
                    } else {
                        this.$message.warning("问卷不存在");
                        this.invalid = true;
                    }
                }, "json").fail(() => {
                    //bus.$emit("showMsg", "danger", "错误: 网络异常!");
                    this.$message.error("网络异常");
                })
            }

        }
    },
    watch: {
        '$route'(to, from) {
            if (to.path == "/n") {
                this.questionnaire = { questions: [] };
                this.invalid = false;
                this.recover();
            } else
                this.loadQuestionnaire();
        }
    },
    created() {
        if (localStorage.questionnaire == undefined || this.$route.name == 'n')
            this.loadQuestionnaire();
        else
            this.recover();
    }
}
</script>

<style>
.edit-title,
.edit-switch {
    font-size: 20px;
    margin: 15px
}

.edit-briefing {
    font-size: 14px;
    margin: 15px;
}

.creator>.buttons {
    margin: 15px;
}
</style>
