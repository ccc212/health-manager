import request from '@/utils/request'

// 查询医疗预约列表
export function listAppointment(query) {
  return request({
    url: '/manage/appointment/list',
    method: 'get',
    params: query
  })
}

// 查询医疗预约详细
export function getAppointment(appointmentId) {
  return request({
    url: '/manage/appointment/' + appointmentId,
    method: 'get'
  })
}

// 新增医疗预约
export function addAppointment(data) {
  return request({
    url: '/manage/appointment',
    method: 'post',
    data: data
  })
}

// 修改医疗预约
export function updateAppointment(data) {
  return request({
    url: '/manage/appointment',
    method: 'put',
    data: data
  })
}

// 删除医疗预约
export function delAppointment(appointmentId) {
  return request({
    url: '/manage/appointment/' + appointmentId,
    method: 'delete'
  })
}
