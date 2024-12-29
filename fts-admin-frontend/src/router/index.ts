//导入vue-router
import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import LoginVue from '@/views/Login.vue'
import ResetPwVue from '@/views/Resetpw.vue'
import UserInfoVue from '@/views/layout/UserInfo.vue';
import LayoutVue from '@/views/Layout.vue';
import ProductVue from '@/views/layout/Product.vue';
//定义路由关系
const routes:RouteRecordRaw[] = [
    { path: '/login', component: LoginVue },
    { path: '/resetpw', component: ResetPwVue },
    { path: '/', component: LayoutVue, redirect:'/info',children:[
        { path: '/info', component: UserInfoVue },
        { path: '/product', component: ProductVue },
    ]}
]
//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
});
export default router
