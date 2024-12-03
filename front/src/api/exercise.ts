import request from '@/request'
import type { TableDataInfo } from '@/types'

const BASE_URL = '/manage'

export default {
  // 健身计划相关接口
  getFitnessPlanList: (params?: any) => {
    return request.get<TableDataInfo>(`${BASE_URL}/plan/list`, { params })
  },
  
  generatePlan: (planId: number) => {
    return request.get(`${BASE_URL}/plan/generate/${planId}`)
  },
  
  addFitnessPlan: (data: any) => {
    return request.post(`${BASE_URL}/plan`, data)
  },
  
  updateFitnessPlan: (data: any) => {
    return request.put(`${BASE_URL}/plan`, data)
  },
  
  deleteFitnessPlan: (planIds: number[]) => {
    return request.delete(`${BASE_URL}/plan/${planIds.join(',')}`)
  },

  // 健身记录相关接口
  getFitnessRecordList: (params?: any) => {
    return request.get<TableDataInfo>(`${BASE_URL}/record/list`, { params })
  },
  
  addFitnessRecord: (data: any) => {
    return request.post(`${BASE_URL}/record`, data)
  },
  
  updateFitnessRecord: (data: any) => {
    return request.put(`${BASE_URL}/record`, data)
  },
  
  deleteFitnessRecord: (recordIds: number[]) => {
    return request.delete(`${BASE_URL}/record/${recordIds.join(',')}`)
  }
}