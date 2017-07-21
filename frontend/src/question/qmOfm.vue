<template>
    <div id="tb">
        <h2 style="width:100%;text-align:center">问卷管理中心</h2>
        <h4 style="width:100%;text-align:center">未处理的举报</h4>
        <br>
        <el-radio-group>
        </el-radio-group>
        <br>
        <table class="table table-striped table-condensed table-bordered">
            <tbody>
                <tr>
                    <th>问卷ID</th>
                    <th>举报信息</th>
                    <th>操作</th>
                </tr>
                <tr v-for="(item, index) in report" :key="item">
                    <th>{{item.questionnaireId}}</th>
                    <th>{{item.content}}</th>
                    <th>
                        <el-button type="text" size="small" @click="viewContent(item.questionnaireId)">查看内容</el-button>
                        <el-button type="text" size="small" @click="pass(item.questionnaireId, item.id)">通过</el-button>
                        <el-button type="text" size="small" @click="noPass(item.id)">不通过</el-button>
                    </th>
                </tr>
            </tbody>
        </table>
        <div class="container" style="width:80%;text-align:right">
            <h4>总举报数量:{{reportNum}}</h4>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            status: {},
            report: {},
            reportNum: {}
        };
    },

    created() {
        this.getReport();
    },

    methods: {
        getReport() {
            var self = this;
            $.ajax({
                type: "GET",
                url: "allUnhandledReports?page=0&pageSize=1",
                data: "",
                dataType: "json",
                success: data => {
                    self.report = data.reports;
                    console.log(1);
                    console.log(data.reportNum);
                    self.reportNum = data.reportNum;
                }
            });
        },

        pass(ID, reportId) {
            this.$confirm("此操作会改变问卷的状态, 您确定继续吗?", "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "danger"
            }).then(() => {
                $.ajax({
                    type: "POST",
                    url: ("report/" + reportId),
                    data: { "status": 1, "questionnaireId": ID },
                    dataType: "json",
                    success: data => {
                        console.log(data);
                        this.getReport();
                        this.$message.success("操作成功！");
                    }
                });
            });
        },

        noPass(reportId) {
            this.$confirm("此操作会改变问卷的状态, 您确定继续吗?", "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "danger"
            }).then(() => {
                $.ajax({
                    type: "POST",
                    url: ("report/" + reportId),
                    data: { "status": 1, "questionnaireId": "" },
                    dataType: "json",
                    success: data => {
                        console.log(data);
                        this.getReport();
                        this.$message.success("操作成功！");
                    }
                });
            });
        },

        viewContent() {
            //this.$router.push({ name: "q" + this.report[0].questionnaireId});
            window.open(`#/q/${this.report[0].questionnaireId}`);
        }
    }
};
</script>

<style>
table {
    width: 80% !important;
    margin-left: 10%;
}

th,
td,
td>input {
    text-align: center;
    width: 150px !important;
}
</style>