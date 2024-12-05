package com.healthmanager.manage.service;

import java.util.Date;
import java.util.List;
import com.healthmanager.manage.domain.DoctorSchedule;

/**
 * 医生排班Service接口
 * 
 * @author ccc212
 * @date 2024-12-05
 */
public interface IDoctorScheduleService 
{
    /**
     * 查询医生排班
     * 
     * @param scheduleId 医生排班主键
     * @return 医生排班
     */
    public DoctorSchedule selectDoctorScheduleByScheduleId(Long scheduleId);

    /**
     * 查询医生排班列表
     * 
     * @param doctorSchedule 医生排班
     * @return 医生排班集合
     */
    public List<DoctorSchedule> selectDoctorScheduleList(DoctorSchedule doctorSchedule);

    /**
     * 新增医生排班
     * 
     * @param doctorSchedule 医生排班
     * @return 结果
     */
    public int insertDoctorSchedule(DoctorSchedule doctorSchedule);

    /**
     * 修改医生排班
     * 
     * @param doctorSchedule 医生排班
     * @return 结果
     */
    public int updateDoctorSchedule(DoctorSchedule doctorSchedule);

    /**
     * 批量删除医生排班
     * 
     * @param scheduleIds 需要删除的医生排班主键集合
     * @return 结果
     */
    public int deleteDoctorScheduleByScheduleIds(Long[] scheduleIds);

    /**
     * 删除医生排班信息
     * 
     * @param scheduleId 医生排班主键
     * @return 结果
     */
    public int deleteDoctorScheduleByScheduleId(Long scheduleId);

    DoctorSchedule getSchedule(Long doctorId, Date date);
}
