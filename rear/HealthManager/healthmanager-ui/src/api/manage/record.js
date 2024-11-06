import request from '@/utils/request'

// 查询健身记录列表
export function listRecord(query) {
  return request({
    url: '/manage/record/list',
    method: 'get',
    params: query
  })
}

// 查询健身记录详细
export function getRecord(recordId) {
  return request({
    url: '/manage/record/' + recordId,
    method: 'get'
  })
}

// 新增健身记录
export function addRecord(data) {
  return request({
    url: '/manage/record',
    method: 'post',
    data: data
  })
}

// 修改健身记录
export function updateRecord(data) {
  return request({
    url: '/manage/record',
    method: 'put',
    data: data
  })
}

// 删除健身记录
export function delRecord(recordId) {
  return request({
    url: '/manage/record/' + recordId,
    method: 'delete'
  })
}
