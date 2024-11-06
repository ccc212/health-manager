package com.healthmanager.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.healthmanager.common.annotation.Excel;
import com.healthmanager.common.core.domain.BaseEntity;

/**
 * 锻炼项目对象 health_exercise
 * 
 * @author ccc212
 * @date 2024-11-05
 */
public class Exercise extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 锻炼项目ID */
    @Excel(name = "锻炼项目ID")
    private Long exerciseId;

    /** 锻炼项目名称 */
    @Excel(name = "锻炼项目名称")
    private String exerciseName;

    /** 锻炼项目描述 */
    @Excel(name = "锻炼项目描述")
    private String description;

    public void setExerciseId(Long exerciseId) 
    {
        this.exerciseId = exerciseId;
    }

    public Long getExerciseId() 
    {
        return exerciseId;
    }
    public void setExerciseName(String exerciseName) 
    {
        this.exerciseName = exerciseName;
    }

    public String getExerciseName() 
    {
        return exerciseName;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("exerciseId", getExerciseId())
            .append("exerciseName", getExerciseName())
            .append("description", getDescription())
            .toString();
    }
}
