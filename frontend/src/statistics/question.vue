<template>
    <el-card class="box-card" style="height:370px">
        <div slot="header">
            <span class="statistic-title">{{index+1}}.{{title}}</span>
            <el-radio-group style="float: right" v-model="type" size="small">
                <el-radio-button label="饼图" style="font-weight:400"></el-radio-button>
                <el-radio-button label="条形图" style="font-weight:400"></el-radio-button>
                <el-radio-button label="详细信息" style="font-weight:400"></el-radio-button>
            </el-radio-group>
        </div>
        <ve-pie v-if="type=='饼图'" height="300px" :data="chartData" :settings="chartSettingsPie"></ve-pie>
        <div v-if="type=='条形图'" style="width:400px; height:300px">
            <ve-bar :colors="['#5ab1ef']" height="300px" width="400px" :data="chartData" :settings="chartSettingsBar" :legend-visible="false"></ve-bar>
        </div>
        <el-table class="detail-table" v-show="type=='详细信息'" :data="statisticData.blanks" border style="width: 100%; height: 280px; overflow: scroll">
            <el-table-column prop="resultId" label="答卷ID" width="225"></el-table-column>
            <el-table-column prop="content" label="回答" width="75"></el-table-column>
            <el-table-column label="操作">
                <template scope="scope">
                    <a :href="`#/r/${statisticData.blanks[scope.$index].resultId}`">查看</a>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
</template>

<script>
export default {
    props:{
        statisticData:{required: true},
        index:{required: true},
        answerNumber:{},
        title:{},
        questionType:{required: true}
    },
    data(){return {
        type:"饼图",
        chartData:{
            columns:["title", "number"]
        },
        chartSettingsPie:{
            dimension: "title",
            metrics: "number",
            dataType: "KMB",
            selectedMode: "single",
            hoverAnimation: false,
            radius: 100,
            offsetY: 150
        },
        chartSettingsBar: {
            dimension: ["title"],
            metrics: ["number"],
            xAxisType: ["KMB", "percent"],
            xAxisName: ["数量"],
            stack:{"数量":["number"]}
        }
    };
    },
    methods:{
        update(){
            this.chartData.rows = this.statisticData.choices;
            var blanks = this.statisticData.blanks;
            if(blanks.length > 0 && this.questionType <= 1){
                this.chartData.rows.push({title:"其他", number: blanks.length});
            }
            else if(blanks.length > 0 && this.questionType == 2){
                var temp = {};
                for(let i in blanks){
                    if(temp[blanks[i].content] == undefined)
                        temp[blanks[i].content] = 1;
                    else
                        temp[blanks[i].content] ++;
                }
                for(let i in temp){
                    this.chartData.rows.push({title:i, number: temp[i]});
                }
            }
        }
    },
    watch:{
        answerNumber:"update"
    },
    created(){
        this.update();
    }
};
</script>

<style>
    .box-card{width:40%; float:left; margin-right:0px; margin-left:4%; margin-bottom:20px;}
    .statistic-title{font-size: 20px;}
    .detail-table *{font-size:12px;}
    .box-card>.el-card__body{padding:0px;}
</style>

