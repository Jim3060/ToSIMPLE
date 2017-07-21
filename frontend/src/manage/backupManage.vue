<template>
    <div>
        <div style="width:80%; margin-left:10%">
            <el-input v-model="mongoPath" placeholder="mongoDB备份路径">
                <template slot="prepend">
                    <div>MongoDB备份设置</div>
                </template>
                <template slot="append">
                    <el-button @click="setMongoPath()">提交</el-button>
                </template>
            </el-input>
            <div style="height:20px"></div>
            <el-input v-model="mysqlPath" placeholder="mysql备份路径">
                <template slot="prepend">
                    <div>MySQL备份设置</div>
                </template>
                <template slot="append">
                    <el-button @click="setMysqlPath()">提交</el-button>
                </template>
            </el-input>
            <div style="height:20px"></div>
            <el-input v-model="mongoPathRecover" placeholder="mongoDB备份路径">
                <template slot="prepend">
                    <div>MongoDB备份恢复</div>
                </template>
                <template slot="append">
                    <el-button @click="recoverMongo()">提交</el-button>
                </template>
            </el-input>
            <div style="height:20px"></div>
            <el-input v-model="mysqlPathRecover" placeholder="mysql备份路径">
                <template slot="prepend">
                    <div>MySQL备份恢复</div>
                </template>
                <template slot="append">
                    <el-button @click="recoverMySQL()">提交</el-button>
                </template>
            </el-input>
        </div>
    </div>
</template>

<script>
export default {
    data(){return {
        mongoPath:"",
        mysqlPath:"",
        mongoPathRecover:"",
        mysqlPathRecover:""
    };},
    methods:{
        setMongoPath(){
            $.get(`backupMongodb?file=${this.mongoPath}`, data => {
                if (data.valid == 1 || data.valid == "1")
                    this.$message.success("修改成功");
                else if (data.valid == 2 || data.valid == "2")
                    this.$message.error("您没有该权限");
                else
                    this.$message.error("路径错误");
            }, "json");
        },
        setMysqlPath(){
            $.get(`backupMysql?file=${this.mysqlPath}`, data => {
                if (data.valid == 1 || data.valid == "1")
                    this.$message.success("修改成功");
                else if (data.valid == 2 || data.valid == "2")
                    this.$message.error("您没有该权限");
                else
                    this.$message.error("路径错误");
            }, "json");
        },
        recoverMongo(){
            $.get(`restoreMongodb?file=${this.mongoPathRecover}`, data => {
                if (data.valid == 1 || data.valid == "1")
                    this.$message.success("恢复成功");
                else if (data.valid == 2 || data.valid == "2")
                    this.$message.error("您没有该权限");
                else
                    this.$message.error("路径错误");
            }, "json");
        },
        recoverMySQL(){
            $.get(`restoreMysql?file=${this.mysqlPathRecover}`, data => {
                if (data.valid == 1 || data.valid == "1")
                    this.$message.success("恢复成功");
                else if (data.valid == 2 || data.valid == "2")
                    this.$message.error("您没有该权限");
                else
                    this.$message.error("路径错误");
            }, "json");
        }
    }
};
</script>

<style>

</style>
