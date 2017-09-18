<template>
    <div style="width:100%; height:100%">
        <svg id="my-svg"></svg> 
    </div>
</template>

<script>
import FlowChart from "./flowChart.js";

export default {
    data(){return {
        
    };},
    mounted(){
        this.questions = JSON.parse(localStorage.questions);
        let flowChart = new FlowChart(this.questions);
        flowChart.mount("svg");
    }
};
</script>

<style>
    .node rect {
        stroke: #999;
        fill: #fff;
        stroke-width: 1.5px;
    }   

    text {
        font-weight: 300;
        font-size: 14px;
    }   

    .edgePath path {
        stroke: #333;
        stroke-width: 1.5px;
    }

    svg {
        height: 200%;
        width: 100%;
        overflow:visible;
    }
</style>
