<template>
    <div>
        <el-row>
            <el-col :span="4">&nbsp</el-col>
            <el-col :span="16">
                <el-row>
                    <el-col :span="8">
                        <div style="height:40px;"></div>
                        <el-upload class="avatar-uploader" action="file" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                            <img :src="portrait?`file/${portrait}`:'data:image/jpeg;base64,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'" class="avatar">
                        </el-upload>
                    </el-col>
                    <el-col :span="16">
                        <h2>{{userName}}</h2>
                        <div style="height:20px"></div>
                        <el-row>
                            <el-col :span="6">
                                <div class="attr">QQ</div>
                            </el-col>
                            <el-col :span="12">
                                <el-input v-model="qq"></el-input>
                            </el-col>
                        </el-row>
                        <div style="height:20px"></div>
                        <el-row>
                            <el-col :span="6">
                                <div class="attr">微信</div>
                            </el-col>
                            <el-col :span="12">
                                <el-input v-model="weixin"></el-input>
                            </el-col>
                        </el-row>
                        <div style="height:20px"></div>
                        <el-row>
                            <el-col :span="6">
                                <div class="attr">手机</div>
                            </el-col>
                            <el-col :span="12">
                                <el-input v-model="phone"></el-input>
                            </el-col>
                        </el-row>
                    </el-col>
                </el-row>
                <div style="height:20px"></div>
                <el-row>
                    <el-col :span="6">
                        <div class="attr attr-center">地址</div>
                    </el-col>
                    <el-col :span="14">
                        <el-input v-model="address"></el-input>
                    </el-col>
                </el-row>
            </el-col>
            <el-col :span="4">&nbsp</el-col>
        </el-row>
        <div class="buttons">
        <el-button type="success" @click="submit()">提交</el-button>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            userId:"",
            imageUrl: "",
            portrait: "",
            address: "",
            qq: "",
            weixin: "",
            phone:"",
            userName: ""
        };
    },
    methods: {
        handleAvatarSuccess(res, file) {
            this.imageUrl = URL.createObjectURL(file.raw);
            this.portrait = res.fileId;
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === "image/jpeg" || file.type === "image/png";
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error("上传头像图片只能是 JPG 格式!");
            }
            if (!isLt2M) {
                this.$message.error("上传头像图片大小不能超过 2MB!");
            }
            return isJPG && isLt2M;
        },
        submit(){
            if (!(/^1[34578]\d{9}$/.test(this.phone))) {
                this.$message.error("手机号格式错误");
                return ;
            }

            const postBody = {
                address: this.address,
                qq: this.qq,
                weixin: this.weixin,
                phone: this.phone,
                portrait: this.portrait
            };
            $.post(`user/${this.userId}`, postBody, data => {
                if (data.valid == 1) {
                    this.$message.success("修改成功");
                }
                else {
                    this.$message.error("没有修改权限");
                }
            }, "json").fail(() => {
                this.$message.error("网络异常");
            });
        }
    },
    created(){
        this.userId = this.$route.params.id;
        $.get(`user/${this.userId}`, data => {
            if (Object.keys(data).length == 0) {
                this.$message.error("非法访问");
                return;
            }
            this.address = data.user.address;
            this.qq = data.user.qq;
            this.weixin = data.user.weixin;
            this.phone = data.user.phone;
            this.portrait = data.user.portrait;
            this.userName = data.user.userName;
        }, "json").fail(() => {
            this.$message.error("网络异常,加载失败");
        });
    }
}
</script>

<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #20a0ff;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}

.el-upload>input {
    display: none;
}

.attr {
    font-size:14px;
}

.attr-center {
    text-align:center;
}

.buttons {
    text-align: center;
    margin-top: 30px;
}
</style>