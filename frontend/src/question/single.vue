<template>
    <div>
        <ul class="question list-group">
            <li class="list-group-item">[单选{{forced?", 必答":""}}]
                <span class="question-title">{{index+1}}. {{title}}</span>
            </li>
            <li class="list-group-item" v-for="(option, index) in options" :key="option">
                <el-radio style="margin:2px" class="radio" v-model="select" :label="index">
                    <span>{{option.text}}</span>
                    <div v-if="option.photoId!=undefined&&option.photoId!=''"><img :src="'file/'+option.photoId" /></div>
                </el-radio>
            </li>
            <li v-if="mix" class="list-group-item">
                <el-radio v-model="select" style="margin:2px">
                    <el-input :disabled="select<options.length" placeholder="其他"></el-input>
                </el-radio>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    name: "single",
    props: { options: { required: true }, title: { required: true }, index: {}, mix: { default: false }, forced: {}, answer: {} },
    data() {
        return {
            select: "",
            blank: ""
        };
    },
    methods: {
        update() {
            if (this.select == this.options.length) {
                this.$emit("update", { choice: [], blank: this.blank });
            } else {
                this.$emit("update", { choice: [this.select], blank: "" });
            }
        }
    },
    watch: {
        select() { this.update(); },
        blank() { this.update(); }
    },
    created() {
        if (this.answer != undefined) {
            this.select = (this.answer.choice || [this.options.length])[0];
            this.blank = this.answer.blank;
        }
    }
};
</script>

<style>
.question-title {
    font-size: 18px;
}
</style>
