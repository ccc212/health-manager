import request from '@/request'
import type { TableDataInfo } from '@/types'

const BASE_URL = '/manage/counseling'

export default {
  // AI心理咨询
  getAIAnswer: (data: { userId: number; question: string }) => {
    return request.post(`${BASE_URL}/getAnswer`, data, {
      timeout: 120000  // 120秒超时
    })
  },

  // 获取咨询记录列表
  getCounselingList: (params?: any) => {
    return request.get<TableDataInfo>(`${BASE_URL}/list`, { params })
  },

  // 删除咨询记录
  deleteCounselingRecord: (recordIds: number[]) => {
    return request.delete(`${BASE_URL}/${recordIds.join(',')}`)
  }
}