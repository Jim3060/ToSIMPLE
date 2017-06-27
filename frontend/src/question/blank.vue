<template>
    <div>
        <ul class="question list-group">
            <li class="list-group-item question-title">[填空] {{title}}</li>
            <li class="list-group-item">
                <input v-model="value"></input> 
            </li>
        </ul>
    </div>
</template>

<script>
    export default{
        name: "blank",
        props:{title:{required: true}},
        data(){return {
            value:""
        }}
    }
</script>

<style>
    .question-title{font-size:18px;}
</style>

