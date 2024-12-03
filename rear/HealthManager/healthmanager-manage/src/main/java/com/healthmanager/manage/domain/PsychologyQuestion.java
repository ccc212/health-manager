package com.healthmanager.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.healthmanager.common.annotation.Excel;
import com.healthmanager.common.core.domain.BaseEntity;

/**
 * 心理测试题目对象 health_psychology_question
 * 
 * @author ccc212
 * @date 2024-11-21
 */
public class PsychologyQuestion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 问题ID */
    private Long questionId;

    /** 问题内容 */
    @Excel(name = "问题内容")
    private String questionText;

    public void setQuestionId(Long questionId) 
    {
        this.questionId = questionId;
    }

    public Long getQuestionId() 
    {
        return questionId;
    }
    public void setQuestionText(String questionText) 
    {
        this.questionText = questionText;
    }

    public String getQuestionText() 
    {
        return questionText;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("questionId", getQuestionId())
            .append("questionText", getQuestionText())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
