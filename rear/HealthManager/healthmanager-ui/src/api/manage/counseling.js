import request from '@/utils/request'

// 查询AI咨询记录列表
export function listCounseling(query) {
  return request({
    url: '/manage/counseling/list',
    method: 'get',
    params: query
  })
}

// 查询AI咨询记录详细
export function getCounseling(recordId) {
  return request({
    url: '/manage/counseling/' + recordId,
    method: 'get'
  })
}

// 新增AI咨询记录
export function addCounseling(data) {
  return request({
    url: '/manage/counseling',
    method: 'post',
    data: data
  })
}

// 修改AI咨询记录
export function updateCounseling(data) {
  return request({
    url: '/manage/counseling',
    method: 'put',
    data: data
  })
}

// 删除AI咨询记录
export function delCounseling(recordId) {
  return request({
    url: '/manage/counseling/' + recordId,
    method: 'delete'
  })
}
