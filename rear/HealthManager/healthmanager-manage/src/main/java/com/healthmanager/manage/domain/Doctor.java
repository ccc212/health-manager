package com.healthmanager.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.healthmanager.common.annotation.Excel;
import com.healthmanager.common.core.domain.BaseEntity;

/**
 * 医生信息对象 health_doctor
 * 
 * @author ccc212
 * @date 2024-12-05
 */
public class Doctor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 医生ID */
    private Long doctorId;

    /** 医生姓名 */
    @Excel(name = "医生姓名")
    private String name;

    /** 职称 */
    @Excel(name = "职称")
    private String title;

    /** 科室 */
    @Excel(name = "科室")
    private String department;

    /** 个人简介 */
    @Excel(name = "个人简介")
    private String introduction;

    /** 状态（0在职 1离职） */
    @Excel(name = "状态", readConverterExp = "0=在职,1=离职")
    private String status;

    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() 
    {
        return doctorId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }
    public void setIntroduction(String introduction) 
    {
        this.introduction = introduction;
    }

    public String getIntroduction() 
    {
        return introduction;
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
            .append("doctorId", getDoctorId())
            .append("name", getName())
            .append("title", getTitle())
            .append("department", getDepartment())
            .append("introduction", getIntroduction())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
