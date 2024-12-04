import request from '@/request'
import type { TableDataInfo } from '@/types'
import type {ApiResponse, Question, TestResult} from "@/type";

const BASE_URL = '/manage'
const COUNSELING_URL = BASE_URL + '/counseling'
const TEST_URL = BASE_URL + '/test'



export default {
  // AI心理咨询
  getAIAnswer: (data: { userId: number; question: string }) => {
    return request.post(`${COUNSELING_URL}/getAnswer`, data, {
      timeout: 120000  // 120秒超时
    })
  },

  // 获取咨询记录列表
  getCounselingList: (params?: any) => {
    return request.get<TableDataInfo>(`${COUNSELING_URL}/list`, { params })
  },

  // 删除咨询记录
  deleteCounselingRecord: (recordIds: number[]) => {
    return request.delete(`${COUNSELING_URL}/${recordIds.join(',')}`)
  },

  // 开始测试，获取所有题目
  startPsychologyTest: () => {
    return request.get<ApiResponse<Question[]>>(`${TEST_URL}/start`)
  },
  
  // 提交测试答案
  submitPsychologyTest: (data: {
    userId: number
    answers: Array<{
      questionId: number
      optionName: string
    }>
  }) => {
    return request.post<TestResult>(`${TEST_URL}/submit`, data, {
      timeout: 120000 // 120秒超时
    })
  },

  // 获取测试记录列表
  getTestRecords: () => {
    return request.get(`${TEST_URL}/list`)
  }
}