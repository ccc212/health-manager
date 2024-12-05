package com.healthmanager.manage.service.impl;

import java.util.List;
import com.healthmanager.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.MedicalAppointmentMapper;
import com.healthmanager.manage.domain.MedicalAppointment;
import com.healthmanager.manage.service.IMedicalAppointmentService;

/**
 * 医疗预约Service业务层处理
 * 
 * @author ccc212
 * @date 2024-12-05
 */
@Service
public class MedicalAppointmentServiceImpl implements IMedicalAppointmentService 
{
    @Autowired
    private MedicalAppointmentMapper medicalAppointmentMapper;

    /**
     * 查询医疗预约
     * 
     * @param appointmentId 医疗预约主键
     * @return 医疗预约
     */
    @Override
    public MedicalAppointment selectMedicalAppointmentByAppointmentId(Long appointmentId)
    {
        return medicalAppointmentMapper.selectMedicalAppointmentByAppointmentId(appointmentId);
    }

    /**
     * 查询医疗预约列表
     * 
     * @param medicalAppointment 医疗预约
     * @return 医疗预约
     */
    @Override
    public List<MedicalAppointment> selectMedicalAppointmentList(MedicalAppointment medicalAppointment)
    {
        return medicalAppointmentMapper.selectMedicalAppointmentList(medicalAppointment);
    }

    /**
     * 新增医疗预约
     * 
     * @param medicalAppointment 医疗预约
     * @return 结果
     */
    @Override
    public int insertMedicalAppointment(MedicalAppointment medicalAppointment)
    {
        medicalAppointment.setCreateTime(DateUtils.getNowDate());
        return medicalAppointmentMapper.insertMedicalAppointment(medicalAppointment);
    }

    /**
     * 修改医疗预约
     * 
     * @param medicalAppointment 医疗预约
     * @return 结果
     */
    @Override
    public int updateMedicalAppointment(MedicalAppointment medicalAppointment)
    {
        medicalAppointment.setUpdateTime(DateUtils.getNowDate());
        return medicalAppointmentMapper.updateMedicalAppointment(medicalAppointment);
    }

    /**
     * 批量删除医疗预约
     * 
     * @param appointmentIds 需要删除的医疗预约主键
     * @return 结果
     */
    @Override
    public int deleteMedicalAppointmentByAppointmentIds(Long[] appointmentIds)
    {
        return medicalAppointmentMapper.deleteMedicalAppointmentByAppointmentIds(appointmentIds);
    }

    /**
     * 删除医疗预约信息
     * 
     * @param appointmentId 医疗预约主键
     * @return 结果
     */
    @Override
    public int deleteMedicalAppointmentByAppointmentId(Long appointmentId)
    {
        return medicalAppointmentMapper.deleteMedicalAppointmentByAppointmentId(appointmentId);
    }
}
