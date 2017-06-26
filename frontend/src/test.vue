<template>
    <div>
        <div>url: <input v-model="url"></input></div>
        <div>content: <input v-model="content"></input></div>
        <p v-show="responseMsg != ''">response: {{responseMsg}}</p>
        <button class="btn btn-primary" @click="submit()">submit</button>
    </div>
</template>

<script>
export default {
    data(){return {
        url: "",
        content: "",
        responseMsg: ""
    }},
    methods:{
        submit(){
            var self = this;
            $.post(this.url, this.content, (data)=>{
                self.responseMsg = data;
            }).fail(()=>{
                self.responseMsg = "ERROR";
            })
        }
    }
}
</script>

<style>
    div, p{margin: 20px;}
</style>
