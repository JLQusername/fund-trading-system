//导入vue-router
import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import LoginVue from '@/views/Login.vue'
import ResetPwVue from '@/views/Resetpw.vue'
import CreateAccountVue from '@/views/CreateAccount.vue';
import UserInfoVue from '@/views/layout/UserInfo.vue';
import LayoutVue from '@/views/Layout.vue';
import ProductVue from '@/views/layout/Product.vue';
import SubscriptionVue from '@/views/layout/Subscription.vue';
import RedemptionVue from '@/views/layout/Redemption.vue';
import IncomeVue from '@/views/layout/Income.vue';
import TransactionsVue from '@/views/layout/Transactions.vue';

//定义路由关系
const routes:RouteRecordRaw[] = [
    { path: '/login', component: LoginVue },
    { path: '/resetpw', component: ResetPwVue },
    { path: '/create', component: CreateAccountVue },
    { path: '/', component: LayoutVue, redirect:'/info',children:[
        { path: '/info', component: UserInfoVue },
        { path: '/product', component: ProductVue },
        { path: '/subscription', component: SubscriptionVue },
        { path: '/redemption', component: RedemptionVue},
        { path: '/income', component: IncomeVue },
        { path: '/transactions', component: TransactionsVue }
    ]}
]
//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
});
export default router
