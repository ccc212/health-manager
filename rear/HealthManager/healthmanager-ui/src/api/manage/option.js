import request from '@/utils/request'

// 查询心理测试选项列表
export function listOption(query) {
  return request({
    url: '/manage/option/list',
    method: 'get',
    params: query
  })
}

// 查询心理测试选项详细
export function getOption(optionId) {
  return request({
    url: '/manage/option/' + optionId,
    method: 'get'
  })
}

// 新增心理测试选项
export function addOption(data) {
  return request({
    url: '/manage/option',
    method: 'post',
    data: data
  })
}

// 修改心理测试选项
export function updateOption(data) {
  return request({
    url: '/manage/option',
    method: 'put',
    data: data
  })
}

// 删除心理测试选项
export function delOption(optionId) {
  return request({
    url: '/manage/option/' + optionId,
    method: 'delete'
  })
}
