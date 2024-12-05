import request from '@/request'

// 获取医生列表
export function getDoctorList(params: any) {
  return request({
    url: '/manage/doctor/list',
    method: 'get',
    params
  })
}

// 获取医生排班
export function getDoctorSchedule(doctorId: number, date: string) {
  return request({
    url: `/manage/schedule/getSchedule/${doctorId}`,
    method: 'get',
    params: { date }
  })
}

// 创建预约
export function createAppointment(data: any) {
  return request({
    url: '/manage/appointment',
    method: 'post',
    data: {
      ...data,
      status: '0' // 默认待就诊状态
    }
  })
}

// 获取预约列表
export function getAppointmentList(params: any) {
  return request({
    url: '/manage/appointment/list',
    method: 'get',
    params
  })
}

// 取消预约
export function cancelAppointment(appointmentId: number) {
  return request({
    url: `/manage/appointment/${appointmentId}`,
    method: 'put',
    data: { status: '2' }
  })
}
