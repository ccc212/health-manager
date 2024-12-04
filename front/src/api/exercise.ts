import request from '@/request'
import type { TableDataInfo } from '@/types'

const BASE_URL = '/manage'
const PART_URL = BASE_URL + '/part'
const EXERCISE_URL = BASE_URL + '/exercise'

export default {

  // 锻炼项目相关接口
  getExerciseList: (params?: any) => {
    return request.get<TableDataInfo>(`${EXERCISE_URL}/list`, { params })
  },

  // 身体部位相关接口
  getBodyPartList: () => {
    return request.get<TableDataInfo>(`${PART_URL}/list`)
  }
}