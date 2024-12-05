import request from '@/utils/request'

// 查询医生信息列表
export function listDoctor(query) {
  return request({
    url: '/manage/doctor/list',
    method: 'get',
    params: query
  })
}

// 查询医生信息详细
export function getDoctor(doctorId) {
  return request({
    url: '/manage/doctor/' + doctorId,
    method: 'get'
  })
}

// 新增医生信息
export function addDoctor(data) {
  return request({
    url: '/manage/doctor',
    method: 'post',
    data: data
  })
}

// 修改医生信息
export function updateDoctor(data) {
  return request({
    url: '/manage/doctor',
    method: 'put',
    data: data
  })
}

// 删除医生信息
export function delDoctor(doctorId) {
  return request({
    url: '/manage/doctor/' + doctorId,
    method: 'delete'
  })
}
