import request from '@/utils/request'

// 查询健身计划详情列表
export function listDetail(query) {
  return request({
    url: '/manage/detail/list',
    method: 'get',
    params: query
  })
}

// 查询健身计划详情详细
export function getDetail(detailId) {
  return request({
    url: '/manage/detail/' + detailId,
    method: 'get'
  })
}

// 新增健身计划详情
export function addDetail(data) {
  return request({
    url: '/manage/detail',
    method: 'post',
    data: data
  })
}

// 修改健身计划详情
export function updateDetail(data) {
  return request({
    url: '/manage/detail',
    method: 'put',
    data: data
  })
}

// 删除健身计划详情
export function delDetail(detailId) {
  return request({
    url: '/manage/detail/' + detailId,
    method: 'delete'
  })
}
