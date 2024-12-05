package com.healthmanager.manage.service.impl;

import java.util.List;
import com.healthmanager.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.DoctorMapper;
import com.healthmanager.manage.domain.Doctor;
import com.healthmanager.manage.service.IDoctorService;

/**
 * 医生信息Service业务层处理
 * 
 * @author ccc212
 * @date 2024-12-05
 */
@Service
public class DoctorServiceImpl implements IDoctorService 
{
    @Autowired
    private DoctorMapper doctorMapper;

    /**
     * 查询医生信息
     * 
     * @param doctorId 医生信息主键
     * @return 医生信息
     */
    @Override
    public Doctor selectDoctorByDoctorId(Long doctorId)
    {
        return doctorMapper.selectDoctorByDoctorId(doctorId);
    }

    /**
     * 查询医生信息列表
     * 
     * @param doctor 医生信息
     * @return 医生信息
     */
    @Override
    public List<Doctor> selectDoctorList(Doctor doctor)
    {
        return doctorMapper.selectDoctorList(doctor);
    }

    /**
     * 新增医生信息
     * 
     * @param doctor 医生信息
     * @return 结果
     */
    @Override
    public int insertDoctor(Doctor doctor)
    {
        doctor.setCreateTime(DateUtils.getNowDate());
        return doctorMapper.insertDoctor(doctor);
    }

    /**
     * 修改医生信息
     * 
     * @param doctor 医生信息
     * @return 结果
     */
    @Override
    public int updateDoctor(Doctor doctor)
    {
        doctor.setUpdateTime(DateUtils.getNowDate());
        return doctorMapper.updateDoctor(doctor);
    }

    /**
     * 批量删除医生信息
     * 
     * @param doctorIds 需要删除的医生信息主键
     * @return 结果
     */
    @Override
    public int deleteDoctorByDoctorIds(Long[] doctorIds)
    {
        return doctorMapper.deleteDoctorByDoctorIds(doctorIds);
    }

    /**
     * 删除医生信息信息
     * 
     * @param doctorId 医生信息主键
     * @return 结果
     */
    @Override
    public int deleteDoctorByDoctorId(Long doctorId)
    {
        return doctorMapper.deleteDoctorByDoctorId(doctorId);
    }
}
