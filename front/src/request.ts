import axios from 'axios'
import { ElMessage } from 'element-plus'
import { getItem } from '@/utils/storage'
import router from './router'

// 创建 axios 实例
const service = axios.create({
  baseURL: '/api',
  timeout: 5000
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    const token = getItem('token')
    if (token) {  
      config.headers['token'] = token
    }
    else {
      router.push('/login')
    }
    return config
  },
  error => {
    console.error('请求错误：', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    
    if (res.code !== 200) {
      ElMessage({
        message: res.msg || '请求失败',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.msg || '请求失败'))
    }
    return response
  },
  error => {
    if (error.response.status === 401) {
      router.push('/login')
      ElMessage({
        message: "未登录",
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error("未登录"))
    }

    console.error('响应错误：', error)
    ElMessage({
      message: error.message || '请求失败',
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service