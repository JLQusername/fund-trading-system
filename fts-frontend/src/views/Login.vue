<script setup lang="ts">
import { ref } from 'vue'; // 引入 ref 来创建响应式数据
import { ElMessage, ElForm, ElFormItem, ElInput, ElButton, ElCard } from 'element-plus'; // 引入ElementPlus组件
import { loginService } from '@/api/login';
import type { LoginDTO, CustomerInfo } from '@/types/login';
import { useTokenStore } from '@/stores/token';
import { useUserInfoStore } from '@/stores/userInfo';
import {jwtDecode } from 'jwt-decode';
import { useRouter } from 'vue-router';

// 定义响应式变量
const loginForm = ref({
  phoneNumber: '',
  password: '',
  userType: 1,
} as LoginDTO);

const rules = {
  phoneNumber: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1\d{10}$/, message: '手机号必须是11位数字', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
  ],
};

const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();
const router = useRouter();

const login = async () => {
  const res = await loginService(loginForm.value);
  tokenStore.setToken(res.data);
  const decode = jwtDecode(res.data) as {claims: CustomerInfo};
  userInfoStore.setInfo(decode.claims);
  console.log(userInfoStore.info);
  ElMessage.success("登录成功");
};
</script>

<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 style="text-align: center">同济基金交易系统</h2>
      <h3 style="text-align: center">登录</h3>
      <el-form :model="loginForm" :rules="rules" label-position="top">
        <el-form-item label="手机号" prop="phoneNumber">
          <el-input v-model="loginForm.phoneNumber" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login" style="width: 48.5%;">登录</el-button>
          <el-button type="primary" plain @click="router.push('/resetpw')" style="width: 48.5%;">忘记密码</el-button>
        </el-form-item>
        <el-button type="success" @click="login" style="width:100%">开户</el-button>
      </el-form>  
    </el-card>
  </div>
</template>
  
<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  height: 100vh;
  background-color: #0b407ce0;
  background-image: url(https://bing.ireadpay.com/wallpaper/00m4ydan3qqdhv1q.jpg);
  background-size: cover;
}
  
.login-card {
  margin-top: 100px;
  height: 400px;
  width: 500px;
  padding: 20px;
  border-radius: 10px;
}
</style>
  