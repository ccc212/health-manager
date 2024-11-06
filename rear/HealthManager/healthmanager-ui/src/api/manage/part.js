import request from '@/utils/request'

// 查询锻炼部位列表
export function listPart(query) {
  return request({
    url: '/manage/part/list',
    method: 'get',
    params: query
  })
}

// 查询锻炼部位详细
export function getPart(partId) {
  return request({
    url: '/manage/part/' + partId,
    method: 'get'
  })
}

// 新增锻炼部位
export function addPart(data) {
  return request({
    url: '/manage/part',
    method: 'post',
    data: data
  })
}

// 修改锻炼部位
export function updatePart(data) {
  return request({
    url: '/manage/part',
    method: 'put',
    data: data
  })
}

// 删除锻炼部位
export function delPart(partId) {
  return request({
    url: '/manage/part/' + partId,
    method: 'delete'
  })
}
