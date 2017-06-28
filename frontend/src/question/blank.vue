<template>
    <div>
        <ul class="question list-group">
            <li class="list-group-item">[填空] <span class="question-title">{{index+1}}. {{title}}</span></li>
            <li class="list-group-item">
                <input v-model="value"></input> 
            </li>
        </ul>
    </div>
</template>

<script>
    export default{
        name: "blank",
        props:{title:{required: true}, index:{}},
        data(){return {
            value:""
        }},
        watch:{
            value:function(){
                this.$emit("update", this.value);
            }
        }
    }
</script>

<style>
    .question-title{font-size:18px;}
</style>

