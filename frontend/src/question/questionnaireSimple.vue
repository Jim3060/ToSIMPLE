<template>
    <div>
        <el-card>{{title}}</el-card>
    </div>
</template>

<script>
export default {
    props:{title:{required:true}, id:{required:true}}
}
</script>
