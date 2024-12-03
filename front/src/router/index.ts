import { createRouter, createWebHistory, type RouteLocationNormalized } from 'vue-router'
import Layout from '@/layout/index.vue'
import { getItem } from '@/utils/storage'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue'),
    },
    {
      path: '/',
      component: Layout,
      redirect: '/empty',
      meta: { requiresAuth: true },
      children: [
        {
          path: 'empty',
          name: 'Empty',
          component: () => import('@/views/Empty.vue'),
          meta: { requiresAuth: true }
        },
        {
          path: 'plan',
          name: 'Plan',
          component: () => import('@/views/FitnessPlan.vue'),
          meta: { requiresAuth: true }
        },
        {
          path: 'record',
          name: 'Record',
          component: () => import('@/views/FitnessRecord.vue'),
          meta: { requiresAuth: true }
        },
      ]
    },
    {
      path: '/profile',
      component: Layout,
      redirect: '/profile/info',
      meta: { requiresAuth: true },
      children: [
        {
          path: 'info',
          name: 'UserInfo',
          component: () => import('@/views/User/UserInfo.vue'),
          meta: { title: '个人信息' }
        },
        {
          path: 'physical',
          name: 'PhysicalData',
          component: () => import('@/views/User/UserPhysicalData.vue'),
          meta: { title: '身体数据' }
        }
      ]
    },
    {
      path: '/psychology',
      component: Layout,
      redirect: '/psychology/counsel',
      meta: { requiresAuth: true },
      children: [
        {
          path: 'counsel',
          name: 'PsychologyCounsel',
          component: () => import('@/views/Psychology/AICounseling.vue'),
          meta: { title: 'AI心理咨询' }
        },
        {
          path: 'test',
          name: 'PsychologyTest',
          component: () => import('@/views/Psychology/PsychologyTest.vue'),
          meta: { title: '心理测试' }
        }
      ]
    },
    {
      path: '/:catchAll(.*)',
      redirect: '/',
    }
  ]
})

router.beforeEach((to: RouteLocationNormalized, from: RouteLocationNormalized, next: Function) => {
  const token = getItem('token');

  if (to.meta.requiresAuth && !token) {
    next({ name: 'Login' });
  } else {
    next();
  }
});

export default router
