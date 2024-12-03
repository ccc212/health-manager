import request from '@/utils/request'

// 查询心理测试题目列表
export function listQuestion(query) {
  return request({
    url: '/manage/question/list',
    method: 'get',
    params: query
  })
}

// 查询心理测试题目详细
export function getQuestion(questionId) {
  return request({
    url: '/manage/question/' + questionId,
    method: 'get'
  })
}

// 新增心理测试题目
export function addQuestion(data) {
  return request({
    url: '/manage/question',
    method: 'post',
    data: data
  })
}

// 修改心理测试题目
export function updateQuestion(data) {
  return request({
    url: '/manage/question',
    method: 'put',
    data: data
  })
}

// 删除心理测试题目
export function delQuestion(questionId) {
  return request({
    url: '/manage/question/' + questionId,
    method: 'delete'
  })
}
