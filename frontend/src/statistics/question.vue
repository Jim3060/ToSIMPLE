<template>
    <div>
        <p>{{index+1}}.{{"title"}}</p>
        <table>
            <tr>
                <td width="500px">
                    <ve-pie height="300px" :data="chartData" :settings="chartSettingsPie"></ve-pie>
                </td>
                <td width="500px">
                    <ve-bar :colors="['#5ab1ef']" height="300px" :data="chartData" :settings="chartSettingsBar" :legend-visible="false"></ve-bar>
                </td>
            </tr>
        </table>
    </div>
</template>

<script>
export default {
    props:{
        statisticData:{required: true},
        index:{required: true},
        answerNumber:{}
    },
    data(){return {
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

