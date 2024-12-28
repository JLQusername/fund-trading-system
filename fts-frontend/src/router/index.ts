//导入vue-router
import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import LoginVue from '@/views/Login.vue'
import ResetPwVue from '@/views/Resetpw.vue'
import CreateAccountVue from '@/views/CreateAccount.vue';
import UserInfoVue from '@/views/UserInfo.vue';
//定义路由关系
const routes:RouteRecordRaw[] = [
    { path: '/', redirect: '/login' },
    { path: '/login', component: LoginVue },
    { path: '/resetpw', component: ResetPwVue },
    { path: '/create', component: CreateAccountVue },
    { path: '/info', component: UserInfoVue }
]
//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
});
export default router
