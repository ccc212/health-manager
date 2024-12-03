package com.healthmanager.manage.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.healthmanager.common.annotation.Excel;
import com.healthmanager.common.core.domain.BaseEntity;

/**
 * 心理测试记录对象 health_psychology_test
 * 
 * @author ccc212
 * @date 2024-11-21
 */
public class PsychologyTest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 测试ID */
    private Long testId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 总分 */
    @Excel(name = "总分")
    private BigDecimal totalScore;

    /** AI分析报告 */
    @Excel(name = "AI分析报告")
    private String aiAnalysis;

    /** 测试日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "测试日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date testDate;

    public void setTestId(Long testId) 
    {
        this.testId = testId;
    }

    public Long getTestId() 
    {
        return testId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setTotalScore(BigDecimal totalScore) 
    {
        this.totalScore = totalScore;
    }

    public BigDecimal getTotalScore() 
    {
        return totalScore;
    }
    public void setAiAnalysis(String aiAnalysis) 
    {
        this.aiAnalysis = aiAnalysis;
    }

    public String getAiAnalysis() 
    {
        return aiAnalysis;
    }
    public void setTestDate(Date testDate) 
    {
        this.testDate = testDate;
    }

    public Date getTestDate() 
    {
        return testDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("testId", getTestId())
            .append("userId", getUserId())
            .append("totalScore", getTotalScore())
            .append("aiAnalysis", getAiAnalysis())
            .append("testDate", getTestDate())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
