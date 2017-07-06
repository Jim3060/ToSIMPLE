<template>
    <el-card class="box-card">
        <div slot="header">
            <span class="statistic-title">{{index+1}}.{{title}}</span>
            <el-radio-group style="float: right" v-model="type" size="small">
                <el-radio-button label="饼图" style="font-weight:400"></el-radio-button>
                <el-radio-button label="条形图" style="font-weight:400"></el-radio-button>
            </el-radio-group>
        </div>
            <ve-pie v-show="type=='饼图'" height="300px" :data="chartData" :settings="chartSettingsPie"></ve-pie>
            <div v-show="type=='条形图'" style="width:400px; height:300px">
                <ve-bar :colors="['#5ab1ef']" height="300px" width="400px" :data="chartData" :settings="chartSettingsBar" :legend-visible="false"></ve-bar>
            </div>
    </el-card>
</template>

<script>
export default {
    props:{
        statisticData:{required: true},
        index:{required: true},
        answerNumber:{},
        title:{}
    },
    data(){return {
        type:"饼图",
        chartData:{
            columns:["title", "number"]
        },
        chartSettingsPie:{
            dimension: "title",
            metrics: "number",
            dataType: 'KMB',
            selectedMode: 'single',
            hoverAnimation: false,
            radius: 100,
            offsetY: 150
        },
        chartSettingsBar: {
            dimension: ['title'],
            metrics: ['number'],
            xAxisType: ['KMB', 'percent'],
            xAxisName: ['数量'],
            stack:{"数量":["number"]}
        }
    }},
    created(){
        this.chartData.rows = this.statisticData.choices;
    }
}
</script>

<style>
    .box-card{width:40%; float:left; margin-right:0px; margin-left:4%;}
    .statistic-title{font-size: 20px;}
</style>

