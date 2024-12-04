package com.healthmanager.manage.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.healthmanager.common.annotation.Excel;
import com.healthmanager.common.core.domain.BaseEntity;

/**
 * 心理测试选项对象 health_psychology_option
 * 
 * @author ccc212
 * @date 2024-12-03
 */
public class PsychologyOption extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 选项ID */
    @Excel(name = "选项ID")
    private Long optionId;

    /** 选项标识 */
    @Excel(name = "选项标识")
    private String optionName;

    /** 问题ID */
    @Excel(name = "问题ID")
    private Long questionId;

    /** 选项内容 */
    @Excel(name = "选项内容")
    private String optionText;

    /** 选项分数 */
    @Excel(name = "选项分数")
    private BigDecimal score;

    public void setOptionId(Long optionId) 
    {
        this.optionId = optionId;
    }

    public Long getOptionId() 
    {
        return optionId;
    }
    public void setOptionName(String optionName) 
    {
        this.optionName = optionName;
    }

    public String getOptionName() 
    {
        return optionName;
    }
    public void setQuestionId(Long questionId) 
    {
        this.questionId = questionId;
    }

    public Long getQuestionId() 
    {
        return questionId;
    }
    public void setOptionText(String optionText) 
    {
        this.optionText = optionText;
    }

    public String getOptionText() 
    {
        return optionText;
    }
    public void setScore(BigDecimal score) 
    {
        this.score = score;
    }

    public BigDecimal getScore() 
    {
        return score;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("optionId", getOptionId())
            .append("optionName", getOptionName())
            .append("questionId", getQuestionId())
            .append("optionText", getOptionText())
            .append("score", getScore())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
