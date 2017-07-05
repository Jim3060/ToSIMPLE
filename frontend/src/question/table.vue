<template>
    <div id="tb">
        <h1>User Mananger</h1>
        <table class="table table-striped table-condensed table-bordered">
            <tbody>
                <tr>
                    <th>ID</th>
                    <th>Role</th>
                    <th>UserName</th>
                    <th>Email</th>
                    <th>Action</th>
                </tr>
                <tr v-for="data_item in data" :key="data_item">
                    <th>{{data_item.ID}}</th>
                    <th>{{data_item.role}}</th>
                    <th>{{data_item.username}}</th>
                    <th>{{data_item.email}}</th>
                    <th>
                        <button class="btn btn-success" @click="create()">创建</button>
                        <button class="btn btn-primary" @click="refresh()">刷新</button>
                    </th>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    //import Vue from "vue";
    //import BootStrapVue from "bootstrap-vue";
    //Vue.use(BootStrapVue);

    //import {bAlert, bModal} from 'bootstrap-vue/lib/components'
    import {modal, alert} from "vue-strap"

    export default {
        data(){return {
            data:[]
        }},
        /*
        created() {
            var self = this;
            $.get("/allUsers?page=1&size=30" , data=>{
                self.data = data;

            }, "json").fail(()=>{
                this.$message.error("网络异常");
            })
        }*/
    }
</script>

<style>
    table{width:80% !important; margin-left:10%;}
    th, td, td>input{text-align: center; width:150px !important;}
</style>
