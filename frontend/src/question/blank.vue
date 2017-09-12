<template>
    <div>
        <ul class="question list-group">
            <li class="list-group-item">[填空{{forced?",必答":""}}]
                <span class="question-title">{{index+1}}. {{title}}</span>
            </li>
            <li class="list-group-item">
                <el-input v-model="value"></el-input>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    name: "blank",
    props: { title: { required: true }, index: {}, forced: {}, answer: {} },
    data() {
        return {
            value: ""
        };
    },
    watch: {
        value: function() {
            this.$emit("update", { choices: [], blank: this.value });
        }
    },
    created() {
        if (this.answer != undefined) {
            this.value = this.answer.blank;
        }
    }
};
</script>

<style>
.question-title {
    font-size: 18px;
}
</style>

