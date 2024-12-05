package com.healthmanager.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.healthmanager.common.annotation.Excel;
import com.healthmanager.common.core.domain.BaseEntity;

/**
 * 医疗预约对象 health_medical_appointment
 * 
 * @author ccc212
 * @date 2024-12-05
 */
public class MedicalAppointment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预约ID */
    private Long appointmentId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 医生ID */
    @Excel(name = "医生ID")
    private Long doctorId;

    /** 排班ID */
    @Excel(name = "排班ID")
    private Long scheduleId;

    /** 病情描述 */
    @Excel(name = "病情描述")
    private String description;

    /** 预约状态（0待就诊 1已完成 2已取消） */
    @Excel(name = "预约状态", readConverterExp = "0=待就诊,1=已完成,2=已取消")
    private String status;

    public void setAppointmentId(Long appointmentId) 
    {
        this.appointmentId = appointmentId;
    }

    public Long getAppointmentId() 
    {
        return appointmentId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() 
    {
        return doctorId;
    }
    public void setScheduleId(Long scheduleId) 
    {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() 
    {
        return scheduleId;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("appointmentId", getAppointmentId())
            .append("userId", getUserId())
            .append("doctorId", getDoctorId())
            .append("scheduleId", getScheduleId())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
