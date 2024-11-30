import { createRouter, createWebHistory, type RouteLocationNormalized } from 'vue-router'
import api from '@/api/user'
import { getItem, setItem } from '@/utils/storage'
import { useUserStore } from '@/stores/user';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Empty',
      component: () => import('@/views/Empty.vue'),
      // component: () => import('@/views/FitnessPlan.vue'),
      // component: () => import('@/views/FitnessRecord.vue'),
      // component: () => import('@/views/UserInfo.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue'),
      meta: { hideNavbar: true }
    },
    {
      path: '/plan',
      component: () => import('@/views/FitnessPlan.vue'),
      // meta: { requiresAuth: true }
    },
    {
      path: '/record',
      component: () => import('@/views/FitnessRecord.vue'),
      // meta: { requiresAuth: true }
    },
    {
      path: '/userInfo',
      component: () => import('@/views/UserInfo.vue'),
      // meta: { requiresAuth: true }
    },
    {
      path: '/:catchAll(.*)',
      redirect: '/',
    }
  ]
})

router.beforeEach((to: RouteLocationNormalized, from: RouteLocationNormalized, next: Function) => {
  const userStore = useUserStore();
  const token = userStore.token || getItem('token'); // 从 store 或 localStorage 获取 token

  if (to.meta.requiresAuth && !token) {
    // 如果路由需要认证且没有 token，则跳转到登录页
    next({ name: 'Login' });
  } else {
    next();
  }
});

export default router
