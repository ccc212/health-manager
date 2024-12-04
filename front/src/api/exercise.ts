import request from '@/request'
import type { ApiResponse, TableDataInfo, Exercise } from '@/types'

const BASE_URL = '/manage'
const PART_URL = BASE_URL + '/part'
const EXERCISE_URL = BASE_URL + '/exercise'
const PLAN_URL = BASE_URL + '/plan'
const DETAIL_URL = BASE_URL + '/detail'

export default {

  getExerciseList: (params?: any) => {
    return request.get<ApiResponse<TableDataInfo<Exercise>>>(`${EXERCISE_URL}/getList`, { params })
  },

  getBodyPartList: () => {
    return request.get<TableDataInfo>(`${PART_URL}/list`)
  },

  getFitnessPlanList: () => {
    return request.get<ApiResponse<TableDataInfo>>(`${PLAN_URL}/list`)
  },

  createFitnessPlan: (data: any) => {
    return request.post(`${PLAN_URL}`, data)
  },

  generateFitnessPlan: (planId: number) => {
    return request.get(`${PLAN_URL}/generate/${planId}`, { timeout: 120000 })
  },

  getFitnessPlanDetail: (planId: number, params: { pageNum: number; pageSize: number }) => {
    return request.get<ApiResponse<TableDataInfo>>(`${DETAIL_URL}/list`, {
      params: {
        planId,
        pageNum: params.pageNum,
        pageSize: params.pageSize
      }
    })
  },

  deleteFitnessPlan: (planId: number) => {
    return request.delete(`${PLAN_URL}/${planId}`)
  },

  getAllExercises: () => {
    return request.get<ApiResponse<TableDataInfo<Exercise>>>(`${EXERCISE_URL}/list`, { params: { pageSize: 999 } })
  },

}