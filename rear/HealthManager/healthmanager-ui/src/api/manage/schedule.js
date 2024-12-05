import request from '@/utils/request'

// 查询医生排班列表
export function listSchedule(query) {
  return request({
    url: '/manage/schedule/list',
    method: 'get',
    params: query
  })
}

// 查询医生排班详细
export function getSchedule(scheduleId) {
  return request({
    url: '/manage/schedule/' + scheduleId,
    method: 'get'
  })
}

// 新增医生排班
export function addSchedule(data) {
  return request({
    url: '/manage/schedule',
    method: 'post',
    data: data
  })
}

// 修改医生排班
export function updateSchedule(data) {
  return request({
    url: '/manage/schedule',
    method: 'put',
    data: data
  })
}

// 删除医生排班
export function delSchedule(scheduleId) {
  return request({
    url: '/manage/schedule/' + scheduleId,
    method: 'delete'
  })
}
