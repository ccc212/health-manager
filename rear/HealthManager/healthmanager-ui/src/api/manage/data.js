import request from '@/utils/request'

// 查询用户身体数据列表
export function listData(query) {
  return request({
    url: '/manage/healthuser/data/list',
    method: 'get',
    params: query
  })
}

// 查询用户身体数据详细
export function getData(userId) {
  return request({
    url: '/manage/healthuser/data/' + userId,
    method: 'get'
  })
}

// 新增用户身体数据
export function addData(data) {
  return request({
    url: '/manage/healthuser/data',
    method: 'post',
    data: data
  })
}

// 修改用户身体数据
export function updateData(data) {
  return request({
    url: '/manage/healthuser/data',
    method: 'put',
    data: data
  })
}

// 删除用户身体数据
export function delData(userId) {
  return request({
    url: '/manage/healthuser/data/' + userId,
    method: 'delete'
  })
}
