import request from '@/utils/request'

// 查询用户信息列表
export function listHealthuser(query) {
  return request({
    url: '/manage/healthuser/list',
    method: 'get',
    params: query
  })
}

// 查询用户信息详细
export function getHealthuser(userId) {
  return request({
    url: '/manage/healthuser/' + userId,
    method: 'get'
  })
}

// 新增用户信息
export function addHealthuser(data) {
  return request({
    url: '/manage/healthuser',
    method: 'post',
    data: data
  })
}

// 修改用户信息
export function updateHealthuser(data) {
  return request({
    url: '/manage/healthuser',
    method: 'put',
    data: data
  })
}

// 删除用户信息
export function delHealthuser(userId) {
  return request({
    url: '/manage/healthuser/' + userId,
    method: 'delete'
  })
}