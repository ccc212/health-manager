import request from '@/request'
import type { UserInfo, ApiResponse } from "@/types";

const BASE_URL = '/manage/healthuser';
const USER_URL = BASE_URL + '/user';

export default {
    /* 用户模块 */
    // 登录
    login: (username: string, password: string) => {
        return request.post(`${USER_URL}/login`, { username, password })
    },

    // 注册
    register: (username: string, password: string) => {
        return request.post(`${USER_URL}/register`, { username, password })
    },

    // 登出
    logout: () => {
        return request.post(`${USER_URL}/logout`)
    },

    // 获取用户信息详细信息
    getInfo: (userId: number) => {
        return request.get<ApiResponse<UserInfo>>(`${USER_URL}/getInfo/${userId}`)
    },

    // 更新用户信息
    updateUser: (userInfo: Partial<UserInfo>) => {
        return request.put(`${USER_URL}`, userInfo)
    },

    // 获取用户身体数据
    getPhysicalData: (userId: number) => {
        return request.get(`${BASE_URL}/data/${userId}`)
    },

    // 添加用户身体数据
    addPhysicalData: (data: any) => {
        return request.post(`${BASE_URL}/data`, data)
    },

    // 更新用户身体数据
    updatePhysicalData: (data: any) => {
        return request.put(`${BASE_URL}/data`, data)
    }
};