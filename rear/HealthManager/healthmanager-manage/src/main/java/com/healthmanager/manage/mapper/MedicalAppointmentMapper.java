package com.healthmanager.manage.mapper;

import java.util.List;
import com.healthmanager.manage.domain.MedicalAppointment;

/**
 * 医疗预约Mapper接口
 * 
 * @author ccc212
 * @date 2024-12-05
 */
public interface MedicalAppointmentMapper 
{
    /**
     * 查询医疗预约
     * 
     * @param appointmentId 医疗预约主键
     * @return 医疗预约
     */
    public MedicalAppointment selectMedicalAppointmentByAppointmentId(Long appointmentId);

    /**
     * 查询医疗预约列表
     * 
     * @param medicalAppointment 医疗预约
     * @return 医疗预约集合
     */
    public List<MedicalAppointment> selectMedicalAppointmentList(MedicalAppointment medicalAppointment);

    /**
     * 新增医疗预约
     * 
     * @param medicalAppointment 医疗预约
     * @return 结果
     */
    public int insertMedicalAppointment(MedicalAppointment medicalAppointment);

    /**
     * 修改医疗预约
     * 
     * @param medicalAppointment 医疗预约
     * @return 结果
     */
    public int updateMedicalAppointment(MedicalAppointment medicalAppointment);

    /**
     * 删除医疗预约
     * 
     * @param appointmentId 医疗预约主键
     * @return 结果
     */
    public int deleteMedicalAppointmentByAppointmentId(Long appointmentId);

    /**
     * 批量删除医疗预约
     * 
     * @param appointmentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMedicalAppointmentByAppointmentIds(Long[] appointmentIds);
}
