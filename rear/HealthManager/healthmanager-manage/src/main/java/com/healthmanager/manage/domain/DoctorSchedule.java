package com.healthmanager.manage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.healthmanager.common.annotation.Excel;
import com.healthmanager.common.core.domain.BaseEntity;

/**
 * 医生排班对象 health_doctor_schedule
 * 
 * @author ccc212
 * @date 2024-12-05
 */
public class DoctorSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 排班ID */
    private Long scheduleId;

    /** 医生ID */
    @Excel(name = "医生ID")
    private Long doctorId;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

    /** 时段（1上午 2下午 3晚上） */
    @Excel(name = "时段", readConverterExp = "1=上午,2=下午,3=晚上")
    private String period;

    /** 剩余名额 */
    @Excel(name = "剩余名额")
    private Long quota;

    /** 状态（0可预约 1约满 2停诊） */
    @Excel(name = "状态", readConverterExp = "0=可预约,1=约满,2=停诊")
    private String status;

    public void setScheduleId(Long scheduleId) 
    {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() 
    {
        return scheduleId;
    }
    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() 
    {
        return doctorId;
    }
    public void setDate(Date date) 
    {
        this.date = date;
    }

    public Date getDate() 
    {
        return date;
    }
    public void setPeriod(String period) 
    {
        this.period = period;
    }

    public String getPeriod() 
    {
        return period;
    }
    public void setQuota(Long quota) 
    {
        this.quota = quota;
    }

    public Long getQuota() 
    {
        return quota;
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
            .append("scheduleId", getScheduleId())
            .append("doctorId", getDoctorId())
            .append("date", getDate())
            .append("period", getPeriod())
            .append("quota", getQuota())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
