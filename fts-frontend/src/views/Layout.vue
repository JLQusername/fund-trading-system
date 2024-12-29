<script setup>
import { ref } from 'vue'
import { ElContainer, ElAside, ElMenu, ElMenuItem, ElHeader, ElMain, ElFooter } from 'element-plus';
import { useUserInfoStore } from '@/stores/userInfo';
import router from '@/router';
const userInfoStore = useUserInfoStore();
const userInfo = userInfoStore.info;
if(!userInfo || userInfo.name==="")
  router.push('/login');
const form = ref({ ...userInfo });
</script>

<template>
  <el-container>
    <!-- 左侧菜单 -->
    <el-aside width="250px">
      <el-menu active-text-color="#F3F3E0" background-color="#2E5077" text-color="#fff" router>
        <div class="menu-title">⭐系统服务选项⭐</div>
        <el-menu-item index="/info">
          <span>账号信息</span>
        </el-menu-item>
        <el-menu-item index="/product">
          <span>产品列表</span>
        </el-menu-item>
        <el-menu-item index="/income">
          <span>当前收入</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div>欢迎 {{ form.name }} 进入系统 基金帐号：{{ form.fundAccount }}</div>
      </el-header>
      <!-- 中间区域 -->
      <el-main>
        <router-view />
      </el-main>
       <!-- 底部区域 -->
       <el-footer>同济基金交易系统</el-footer>
    </el-container>
  </el-container>
</template>

<style scoped>
/* 容器 */
.el-container {
  height: 100vh;
  display: flex;
}

/* 侧边栏 */
.el-aside {
  background-color: #2E5077;
  color: #2E5077;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.el-menu {
  border-right: none; /* 移除默认右侧边框 */
}

.el-menu-item {
  transition: background-color 0.3s ease;
  font-size: 16px; 
}

.el-menu-item:hover, .el-menu-item.is-active {
  background-color: #4DA1A9;
}

.el-header, .el-footer {
  background-color: #f5f7fa;
  color: #606266;
  text-align: center;
  line-height: 60px;
}

.el-main {
  padding: 0px;
  background-color: #fff;
  overflow: auto;
}

.menu-title {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
  padding: 10px;
  font-size: 18px;
  color: #f6bc00;
  background-color: #2E5077;
}
</style>