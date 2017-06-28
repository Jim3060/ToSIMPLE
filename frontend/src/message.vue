<template>
    <div>
        <alert :show="msgShow" placement="top-right" :duration="3000" :type="msgType" width="250px" style="opacity:0.75">
            <span class="glyphicon glyphicon-info-sign" ></span>
            <strong>{{msg}}</strong>
        </alert>
    </div>
</template>

<script>
import bus from "./bus.js"
import {alert} from "vue-strap"

export default {
    components:{alert},
    data(){return {
        msgShow: false,
        msgType: "",
        msg: ""
    }},
    created(){
        bus.$on("showMsg", (type, message)=>{
            this.msgType = type;
            this.msg = message;
            this.msgShow = true;
            setTimeout(()=>{this.msgShow = false}, 2000)
        })
    }
}
</script>

