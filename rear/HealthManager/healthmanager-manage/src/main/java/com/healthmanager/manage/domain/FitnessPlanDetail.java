package com.healthmanager.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.healthmanager.common.annotation.Excel;
import com.healthmanager.common.core.domain.BaseEntity;

/**
 * 健身计划详情对象 health_fitness_plan_detail
 * 
 * @author ccc212
 * @date 2024-11-05
 */
public class FitnessPlanDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 计划详情ID */
    private Long detailId;

    /** 计划ID */
    @Excel(name = "计划ID")
    private Long planId;

    /** 锻炼项目ID */
    @Excel(name = "锻炼项目ID")
    private Long exerciseId;

    /** 锻炼时长（分钟） */
    @Excel(name = "锻炼时长", readConverterExp = "分=钟")
    private Long duration;

    /** 锻炼强度（0低 1中 2高） */
    @Excel(name = "锻炼强度", readConverterExp = "0=低,1=中,2=高")
    private String intensity;

    public void setDetailId(Long detailId) 
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }
    public void setPlanId(Long planId) 
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
    }
    public void setExerciseId(Long exerciseId) 
    {
        this.exerciseId = exerciseId;
    }

    public Long getExerciseId() 
    {
        return exerciseId;
    }
    public void setDuration(Long duration) 
    {
        this.duration = duration;
    }

    public Long getDuration() 
    {
        return duration;
    }
    public void setIntensity(String intensity) 
    {
        this.intensity = intensity;
    }

    public String getIntensity() 
    {
        return intensity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailId", getDetailId())
            .append("planId", getPlanId())
            .append("exerciseId", getExerciseId())
            .append("duration", getDuration())
            .append("intensity", getIntensity())
            .toString();
    }
}
