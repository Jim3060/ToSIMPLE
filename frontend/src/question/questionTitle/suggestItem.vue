<template>
    <li>
        <div class="title">
            {{item.questionTitle}}
        </div>
        <div v-if="item.type<=1" class="options">
            {{`选项: ${item.choices.map((item) => {return item.text}).join(", ")}`}}
        </div>
    </li>
</template>

<script>
export default {
    props:{
        item:{type: Object, default: () => {return {title:"", options:[]};}}
    }
};
</script>
