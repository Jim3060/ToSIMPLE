<template>
    <div v-show="!invalid" class="creator">
        <el-row>
            <el-col :span="4">&nbsp</el-col>
            <el-col :span="16">
                <div class="buttons">
                    <el-button type="primary" @click="publish(1)" v-show="questionnaire.status == 0 && $route.name=='n'">发布问卷</el-button>
                    <el-button type="danger" @click="deleteQuestionnaire()" v-show="$route.name=='n'">删除问卷</el-button>
                    <el-tooltip v-if="$route.name=='n'&&questionnaire.status==1" effect="light">
                        <div slot="content">
                            <input onfocus="this.select()" style="width:150px" :value="'http://106.14.171.169:8080/ToSimple/#/q/'+$route.params.id">
                            <qrcode style="margin-left:11px; margin-top:5px" :size="128" :value="'http://106.14.171.169:8080/ToSimple/#/q/'+$route.params.id"></qrcode>
                        </div>
                        <el-button>分享</el-button>
                    </el-tooltip>
                    <el-dropdown v-if="$route.name=='n'&&questionnaire.status==1" menu-align="start">
                        <el-button>
                            更多
                            <i class="el-icon-caret-bottom el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>
                                <a :href="'#/q/'+$route.params.id">前往回答</a>
                            </el-dropdown-item>
                            <el-dropdown-item>
                                <el-button type="text" @click="showAssociate=true">设置关联</el-button>
                            </el-dropdown-item>
                            <el-dropdown-item>
                                <a @click="showChart()">流程图预览</a>
                            </el-dropdown-item>
                            <el-dropdown-item>
                                <a :href="'#/s/'+$route.params.id">查看统计</a>
                            </el-dropdown-item>
                            <el-dropdown-item>
                                <a :href="'questionnaireResult/download/'+$route.params.id">下载回答</a>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
                <div class="head" v-show="questionnaire.status==0">
                    <el-row>
                        <el-col :span="16">
                            <el-input class="edit-title" placeholder="请输入问卷标题" :disabled="!editMode" v-model="title">
                                <el-button v-if="$route.path=='/n'" @click="forkMode=!forkMode" slot="append" style="font-size:14px">{{!forkMode?"导入问卷":"取消"}}</el-button>
                            </el-input>
                        </el-col>
                        <el-col :span="8">
                            <el-date-picker v-model="daterange" class="edit-date" type="daterange" placeholder="设置起止日期(留空则长期有效)"></el-date-picker>
                        </el-col>
                    </el-row>
                    <div></div>
                    <div v-if="forkMode" style="margin-left:15px; margin-bottom:10px">
                        <el-input placeholder="请输入ID" v-model="forkId">
                            <el-select style="width:120px" v-model="forkFrom" slot="prepend" placeholder="请选择来源">
                                <el-option label="To Simple" value="1"></el-option>
                                <el-option label="问卷星" value="2"></el-option>
                            </el-select>
                            <el-button @click="fork()" slot="append">导入</el-button>
                        </el-input>
                    </div>
                    <div></div>
                    <div>
                        <el-input type="textarea" placeholder="请输入问卷简介" class="edit-briefing" :disabled="!editMode" v-model="briefing"></el-input>
                    </div>
                    <div>
                    </div>
                </div>
            </el-col>
            <el-col :span="4"></el-col>
        </el-row>
        <questionnaire :questionnaire="questionnaire" :edit="questionnaire.status==0&&editMode" @delete="del($event)" @edit="edit($event)"></questionnaire>

        <el-row>
            <el-col :span="4">&nbsp</el-col>
            <el-col :span="16">
                <div v-show="questionnaire.status==0" class="buttons">
                    <el-button v-show="editMode" type="primary" @click="showModal=true">添加问题</el-button>
                    <el-button type="success" @click="submit()">提交问卷</el-button>
                    <el-dropdown v-if="$route.name=='n'||$route.path=='/n'" menu-align="start">
                        <el-button>
                            更多
                            <i class="el-icon-caret-bottom el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>
                                <a @click="showChart()">流程图预览</a>
                            </el-dropdown-item>
                            <el-tooltip :disabled="$route.name=='n'" content="需要先提交问卷才能关联其他问卷" placement="right">
                                <el-dropdown-item>
                                    <el-button type="text" :disabled="$route.path=='/n'" @click="showAssociate=true">关联问卷</el-button>
                                </el-dropdown-item>
                            </el-tooltip>
                            <el-dropdown-item v-if="editMode">
                                <a @click="save()">暂存问卷</a>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </el-col>
            <el-col :span="4"></el-col>
        </el-row>

        <modal :show="showModal" effect="zoom" :backdrop="false">
            <div slot="modal-header" class="modal-header">
                <h4>编辑问题</h4>
            </div>
            <div slot="modal-body" class="modal-body">
                <create v-if="showModal" :questionnaire="questionnaire" :index="idx" @submit="add($event)" @cancel="cancel($event)"></create>
            </div>
            <div slot="modal-footer"></div>
        </modal>
        <modal :show="showAssociate" effect="zoom" :backdrop="false">
            <div slot="modal-header" class="modal-header">
                <h4>问卷关联</h4>
            </div>
            <div slot="modal-body" class="modal-body associate-content">
                <associate :id="$route.params.id||''" @cancel="showAssociate=false" @submit="showAssociate=false"></associate>
            </div>
            <div slot="modal-footer"></div>
        </modal>
    </div>
</template>

<script>
import create from "./createQuestion.vue";
import questionnaire from "./questionnaire.vue";
import { modal } from "vue-strap";
import qrcode from "qrcode.vue";
import chart from "./flowChart/flowChart.vue";
import associate from "./associate.vue";

export default {
    data() {
        return {
            showModal: false,
            question: {},
            idx: -1,
            title: "",
            briefing: "",
            questionnaire: { questions: [], status: 0 },
            editMode: true,
            invalid: false,
            recovered: false,
            forkMode: false,
            forkFrom: "",
            forkId: "",
            showAssociate: false,
            daterange: [null, null]
        };
    },
    components: { modal, questionnaire, create, qrcode, chart, associate },
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
                localStorage.questionnaire = JSON.stringify(this.questionnaire);
                this.$message.success("问卷已暂存，请记得及时提交");
                this.recovered = false;
            }).catch(() => { });
        },
        recover() {
            this.title = "";
            this.briefing = "";
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
                }).catch(() => { });
            }
        },
        deleteQuestionnaire() {
            this.$confirm("此操作将永久删除该问卷, 是否继续?", "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "danger"
            }).then(() => {
                $.ajax({
                    type: "DELETE",
                    url: "questionnaire/" + this.$route.params.id,
                    dataType: "json",
                    success: data => {
                        if (data.deleteSuccess == "1" || data.deleteSuccess == 1) {
                            this.$message.success("删除成功，即将离开此页...");
                            setTimeout(() => {
                                this.$router.push({ path: "/m/q" });
                            }, 2000);
                        }
                        else
                            this.$message.warning("该问卷不存在，或您没有删除的权限");
                    }
                });
            });
        },
        jumpToStatistic() {
            this.$router.push({ name: "s", params: { id: this.$route.params.id } });
        },
        jumpToAnswer() {
            this.$router.push({ name: "q", params: { id: this.$route.params.id } });
        },
        submit() {
            var self = this;
            if (this.title == "") {
                this.$message.error("标题不能为空");
                return;
            }

            if (this.questionnaire.questions.length == 0) {
                this.$message.error("请设置问题");
                return;
            }

            this.questionnaire["paperTitle"] = this.title;
            this.questionnaire["briefing"] = this.briefing;
            this.questionnaire["createDate"] = new Date();
            this.questionnaire["status"] = 0;
            this.questionnaire["answerNumber"] = 0;
            this.questionnaire["startDate"] = this.daterange[0];
            this.questionnaire["endDate"] = this.daterange[1];


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
            });
        },
        publish(status) {
            this.$confirm("问卷一经发布无法修改, 是否继续?", "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "danger"
            }).then(() => {
                $.post("setQuestionnaireStatus", { questionnaireId: this.questionnaire.questionnaireId, status: status }, data => {
                    if (data == "1" || data == 1) {
                        this.$message.success("操作成功");
                        this.questionnaire.status = status;
                    }
                    else
                        this.$message.error("操作失败");
                }).fail(() => {
                    this.$message.error("网络异常");
                });
            });
        },
        loadQuestionnaire(qid) {
            this.questionnaire = { questions: [], status: 1 };
            var id = qid || this.$route.params.id;
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
                this.$message.error("网络异常");
            });
        },
        showChart() {
            localStorage.questions = JSON.stringify(this.questionnaire.questions);
            window.open("#/flow");
        },
        fork() {
            let id = this.forkId;
            let self = this;
            if (this.forkFrom == "") {
                this.$message.warning("请选择来源");
                return;
            }
            if (this.forkId == "") {
                this.$message.warning("ID不能为空");
                return;
            }
            let url = this.forkFrom == "1" ? "questionnaire/" : "questionnaireSojump/";
            $.get(url + id, data => {
                if (data.valid == "1") {
                    self.questionnaire = data.questionnaire;
                    self.title = data.questionnaire.paperTitle;
                    self.briefing = data.questionnaire.briefing || "";
                    self.questionnaire.status = 0;
                    delete self.questionnaire.questionnaireId;
                    delete self.questionnaire._id;
                    this.forkMode = false;
                } else {
                    this.$message.warning("问卷不存在");
                }
            }, "json").fail(() => {
                this.$message.error("网络异常");
            });
        }
    },
    watch: {
        "$route"(to) {
            if (to.path == "/n") {
                this.questionnaire = { questions: [], status: 0 };
                this.invalid = false;
                this.recover();
            } else
                this.loadQuestionnaire();
        },
        title() {
            this.questionnaire.paperTitle = this.title;
        }
    },
    created() {
        if (this.$route.name == "n") {
            this.loadQuestionnaire();
        }
        else
            this.recover();
    }
};
</script>

<style scoped>
.edit-title,
.edit-switch {
    font-size: 20px;
    margin: 10px 15px;
}

.edit-date {
    margin-left: 30px;
    margin-top: 10px;
    margin-bottom: 10px;
    font-size: 12px;
}

.edit-briefing {
    font-size: 14px;
    margin-left: 15px;
    padding-right: 20px;
}

.buttons>* {
    margin: 5px;
}

.buttons {
    margin: 15px;
}
</style>
