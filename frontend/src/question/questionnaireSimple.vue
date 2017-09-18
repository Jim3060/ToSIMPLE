<template>
    <div class="questionnaire-card"><a :href="'#/q/'+id">{{index}}.{{title}}</a></div>
</template>

<script>
export default {
    props:{title:{required:true}, id:{required:true}, index:{required:true}}
};
</script>

<style>
    
</style>
