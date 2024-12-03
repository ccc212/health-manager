import request from '@/utils/request'

// 查询心理测试记录列表
export function listTest(query) {
  return request({
    url: '/manage/test/list',
    method: 'get',
    params: query
  })
}

// 查询心理测试记录详细
export function getTest(testId) {
  return request({
    url: '/manage/test/' + testId,
    method: 'get'
  })
}

// 新增心理测试记录
export function addTest(data) {
  return request({
    url: '/manage/test',
    method: 'post',
    data: data
  })
}

// 修改心理测试记录
export function updateTest(data) {
  return request({
    url: '/manage/test',
    method: 'put',
    data: data
  })
}

// 删除心理测试记录
export function delTest(testId) {
  return request({
    url: '/manage/test/' + testId,
    method: 'delete'
  })
}
