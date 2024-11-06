import request from '@/utils/request'

// 查询锻炼项目列表
export function listExercise(query) {
  return request({
    url: '/manage/exercise/list',
    method: 'get',
    params: query
  })
}

// 查询锻炼项目详细
export function getExercise(exerciseId) {
  return request({
    url: '/manage/exercise/' + exerciseId,
    method: 'get'
  })
}

// 新增锻炼项目
export function addExercise(data) {
  return request({
    url: '/manage/exercise',
    method: 'post',
    data: data
  })
}

// 修改锻炼项目
export function updateExercise(data) {
  return request({
    url: '/manage/exercise',
    method: 'put',
    data: data
  })
}

// 删除锻炼项目
export function delExercise(exerciseId) {
  return request({
    url: '/manage/exercise/' + exerciseId,
    method: 'delete'
  })
}
