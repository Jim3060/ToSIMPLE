<template>
    <div class="questionnaire-card"><a :href="'#/q/'+id">{{title}}</a></div>
</template>

<script>
export default {
    props:{title:{required:true}, id:{required:true}}
}
</script>

<style>
    
</style>
