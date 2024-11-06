import request from '@/utils/request'

// 查询健身计划列表
export function listPlan(query) {
  return request({
    url: '/manage/plan/list',
    method: 'get',
    params: query
  })
}

// 查询健身计划详细
export function getPlan(planId) {
  return request({
    url: '/manage/plan/' + planId,
    method: 'get'
  })
}

// 新增健身计划
export function addPlan(data) {
  return request({
    url: '/manage/plan',
    method: 'post',
    data: data
  })
}

// 修改健身计划
export function updatePlan(data) {
  return request({
    url: '/manage/plan',
    method: 'put',
    data: data
  })
}

// 删除健身计划
export function delPlan(planId) {
  return request({
    url: '/manage/plan/' + planId,
    method: 'delete'
  })
}
