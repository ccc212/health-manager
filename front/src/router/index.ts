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
          component: () => import('@/views/Empty.vue')
        }
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
        },
        {
          path: 'records',
          name: 'PsychologyTestRecords',
          component: () => import('@/views/Psychology/PsychologyTestRecords.vue'),
          meta: { title: '测试记录' }
        }
      ]
    },
    {
      path: '/exercise',
      component: Layout,
      redirect: '/exercise/library',
      meta: { requiresAuth: true },
      children: [
        {
          path: 'library',
          name: 'ExerciseLibrary',
          component: () => import('@/views/Exercise/ExerciseLibrary.vue'),
          meta: { title: '锻炼项目库' }
        },
        {
          path: 'plan',
          name: 'FitnessPlan',
          component: () => import('@/views/Exercise/FitnessPlan.vue'),
          meta: { title: '健身计划' }
        },
        {
          path: 'record',
          name: 'FitnessRecord',
          component: () => import('@/views/Exercise/FitnessRecord.vue'),
          meta: { title: '锻炼记录' }
        },
        // {
        //   path: 'bodypart',
        //   name: 'BodyPartGuide',
        //   component: () => import('@/views/Exercise/BodyPartGuide.vue'),
        //   meta: { title: '部位训练指南' }
        // }
      ]
    },
    {
      path: '/medical',
      component: Layout,
      redirect: '/medical/appointment',
      meta: { requiresAuth: true },
      children: [
        {
          path: 'appointment',
          name: 'MedicalAppointment',
          component: () => import('@/views/Medical/MedicalAppointment.vue'),
          meta: { title: '预约挂号' }
        },
        {
          path: 'record',
          name: 'MedicalRecord',
          component: () => import('@/views/UnderDevelopment.vue'),
          meta: { title: '就诊记录' }
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
