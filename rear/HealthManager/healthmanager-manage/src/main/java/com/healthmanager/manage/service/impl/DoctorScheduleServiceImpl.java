package com.healthmanager.manage.service.impl;

import java.util.Date;
import java.util.List;
import com.healthmanager.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.DoctorScheduleMapper;
import com.healthmanager.manage.domain.DoctorSchedule;
import com.healthmanager.manage.service.IDoctorScheduleService;

/**
 * 医生排班Service业务层处理
 * 
 * @author ccc212
 * @date 2024-12-05
 */
@Service
public class DoctorScheduleServiceImpl implements IDoctorScheduleService 
{
    @Autowired
    private DoctorScheduleMapper doctorScheduleMapper;

    /**
     * 查询医生排班
     * 
     * @param scheduleId 医生排班主键
     * @return 医生排班
     */
    @Override
    public DoctorSchedule selectDoctorScheduleByScheduleId(Long scheduleId)
    {
        return doctorScheduleMapper.selectDoctorScheduleByScheduleId(scheduleId);
    }

    /**
     * 查询医生排班列表
     * 
     * @param doctorSchedule 医生排班
     * @return 医生排班
     */
    @Override
    public List<DoctorSchedule> selectDoctorScheduleList(DoctorSchedule doctorSchedule)
    {
        return doctorScheduleMapper.selectDoctorScheduleList(doctorSchedule);
    }

    /**
     * 新增医生排班
     * 
     * @param doctorSchedule 医生排班
     * @return 结果
     */
    @Override
    public int insertDoctorSchedule(DoctorSchedule doctorSchedule)
    {
        doctorSchedule.setCreateTime(DateUtils.getNowDate());
        return doctorScheduleMapper.insertDoctorSchedule(doctorSchedule);
    }

    /**
     * 修改医生排班
     * 
     * @param doctorSchedule 医生排班
     * @return 结果
     */
    @Override
    public int updateDoctorSchedule(DoctorSchedule doctorSchedule)
    {
        doctorSchedule.setUpdateTime(DateUtils.getNowDate());
        return doctorScheduleMapper.updateDoctorSchedule(doctorSchedule);
    }

    /**
     * 批量删除医生排班
     * 
     * @param scheduleIds 需要删除的医生排班主键
     * @return 结果
     */
    @Override
    public int deleteDoctorScheduleByScheduleIds(Long[] scheduleIds)
    {
        return doctorScheduleMapper.deleteDoctorScheduleByScheduleIds(scheduleIds);
    }

    /**
     * 删除医生排班信息
     * 
     * @param scheduleId 医生排班主键
     * @return 结果
     */
    @Override
    public int deleteDoctorScheduleByScheduleId(Long scheduleId)
    {
        return doctorScheduleMapper.deleteDoctorScheduleByScheduleId(scheduleId);
    }

    @Override
    public DoctorSchedule getSchedule(Long doctorId, Date date) {
        return doctorScheduleMapper.getSchedule(doctorId, date);
    }
}
