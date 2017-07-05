<template>
    <div>
        <question v-for="(data, index) in resultData.questionStatistics" :key="data" :statisticData="data" :index="index" :total="resultData.answerNumber"></question>
    </div>
</template>

<script>
import question from "./question.vue"

export default {
    components:{question},
    data(){return {
        resultData:{"questionStatistics":[{"choices":[{"title":"A","number":2},{"title":"B","number":2}],"blanks":[],"title":"Q1","type":0},{"choices":[{"title":"A","number":2},{"title":"B","number":3},{"title":"C","number":2}],"blanks":[],"title":"Q2","type":1}],"answerNumber":4}
    }}
}
</script>
