package com.healthmanager.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.healthmanager.common.annotation.Excel;
import com.healthmanager.common.core.domain.BaseEntity;

/**
 * 健身计划对象 health_fitness_plan
 * 
 * @author ccc212
 * @date 2024-11-06
 */
public class FitnessPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 计划ID */
    @Excel(name = "计划ID")
    private Long planId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 健身目标 */
    @Excel(name = "健身目标")
    private String goals;

    /** 最初身体状况 */
    @Excel(name = "最初身体状况")
    private String initialStatus;

    /** 当前身体状况 */
    @Excel(name = "当前身体状况")
    private String currentStatus;

    /** 完成进度 */
    @Excel(name = "完成进度")
    private Integer progress;

    public void setPlanId(Long planId) 
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setGoals(String goals) 
    {
        this.goals = goals;
    }

    public String getGoals() 
    {
        return goals;
    }
    public void setInitialStatus(String initialStatus) 
    {
        this.initialStatus = initialStatus;
    }

    public String getInitialStatus() 
    {
        return initialStatus;
    }
    public void setCurrentStatus(String currentStatus) 
    {
        this.currentStatus = currentStatus;
    }

    public String getCurrentStatus() 
    {
        return currentStatus;
    }
    public void setProgress(Integer progress)
    {
        this.progress = progress;
    }

    public Integer getProgress()
    {
        return progress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("planId", getPlanId())
            .append("userId", getUserId())
            .append("goals", getGoals())
            .append("initialStatus", getInitialStatus())
            .append("currentStatus", getCurrentStatus())
            .append("progress", getProgress())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
