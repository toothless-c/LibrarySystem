<template>
  <div class="logincontainer">
    <el-form
      class="loginForm"
      :model="loginForm"
      ref="loginForm"
      :rules="rules"
      label-width="80px"
      :inline="false"
      size="normal"
    >
      <el-form-item>
        <span class="loginTitle">图书管理系统</span>
      </el-form-item>
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          placeholder="请输入用户名"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          v-model="loginForm.password"
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item prop="userType">
        <el-radio-group v-model="loginForm.userType">
          <el-radio :label="0">读者</el-radio>
          <el-radio :label="1">管理员</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <el-button type="primary" class="mybtn" @click="onSubmit"
            >登录</el-button
            >
          </el-col>
          <el-col :span="12" :offset="0">
            <el-button class="mybtn">取消</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {loginApi} from "@/api/user";
import axios from "axios";

export default {
  data() {
    return {
      //登录表单绑定数据源
      loginForm: {
        username: "",
        password: "",
        userType: "", //0：读者  1： 管理员
      },
      //表单验证规则
      rules: {
        username: [
          {
            trigger: "change",
            required: true,
            message: "请输入用户名",
          },
        ],
        password: [
          {
            trigger: "change",
            required: true,
            message: "请输入密码",
          },
        ],
      },
    };
  },
  methods: {
    //登录提交事件
    onSubmit() {
      //表单验证
      this.$refs.loginForm.validate((valid) => {
        //验证通过才提交表单
        if (valid) {
          this.loading = true;
          // if(this.loginForm.userType == null){
          //   this.$message({ type: "error", message: '请选择身份' });
          // }
          // if (this.loginForm.userType != ""){
            //调用store里面的login方法
            this.$store
              .dispatch("user/login", this.loginForm)
              .then(() => {
                //跳转路由
                this.$router.push({ path: this.redirect || "/" });
                this.loading = false;
              })
              .catch(() => {
                this.loading = false;
              });
          // }

        }
      });
    },
  },
};
</script>

<style  scoped>

.logincontainer {
  height: 100%;
  background: #fff;
  background-image: url("https://img.zcool.cn/community/037ed01593f5c5fa8012193a37b13a7.jpg");
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: 100% 100%;

}
.loginForm {
  height: 400px;
  width: 400px;
  background: #fff;
  padding: 35px 20px;
  border-radius: 10px;
}
.loginTitle {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  font-weight: 600;
  color: #409eff;
}
.logincontainer ::v-deep .el-form-item__content {
  margin-left: 0px !important;
}
.mybtn {
  width: 100%;
}
</style>
