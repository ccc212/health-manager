import axios from 'axios'
import type { TableDataInfo } from '@/types'

const BASE_URL = '/api/manage'

export default {
  // 健身计划相关接口
  getFitnessPlanList: (params?: any) => {
    return axios.get<TableDataInfo>(`${BASE_URL}/plan/list`, { params })
  },
  
  generatePlan: (planId: number) => {
    return axios.get(`${BASE_URL}/plan/generate/${planId}`)
  },
  
  addFitnessPlan: (data: any) => {
    return axios.post(`${BASE_URL}/plan`, data)
  },
  
  updateFitnessPlan: (data: any) => {
    return axios.put(`${BASE_URL}/plan`, data)
  },
  
  deleteFitnessPlan: (planIds: number[]) => {
    return axios.delete(`${BASE_URL}/plan/${planIds.join(',')}`)
  },

  // 健身记录相关接口
  getFitnessRecordList: (params?: any) => {
    return axios.get<TableDataInfo>(`${BASE_URL}/record/list`, { params })
  },
  
  addFitnessRecord: (data: any) => {
    return axios.post(`${BASE_URL}/record`, data)
  },
  
  updateFitnessRecord: (data: any) => {
    return axios.put(`${BASE_URL}/record`, data)
  },
  
  deleteFitnessRecord: (recordIds: number[]) => {
    return axios.delete(`${BASE_URL}/record/${recordIds.join(',')}`)
  }
}