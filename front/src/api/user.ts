import axios from 'axios';
import type { UserInfo, ApiResponse } from "@/types";

const BASE_URL = '/api/manage/healthuser';
const USER_URL = BASE_URL + '/user';

export default {
    /* 用户模块 */
    // 登录
    login: (username: string, password: string) => {
        return axios.post(`${USER_URL}/login`, { username, password })
    },

    // 注册
    register: (username: string, password: string) => {
        return axios.post(`${USER_URL}/register`, { username, password })
    },

    // 登出
    logout: () => {
        return axios.post(`${USER_URL}/logout`)
    },

    // 获取用户信息详细信息
    getInfo: (userId: number) => {
        return axios.get<ApiResponse<UserInfo>>(`${USER_URL}/getInfo/${userId}`)
    }
};