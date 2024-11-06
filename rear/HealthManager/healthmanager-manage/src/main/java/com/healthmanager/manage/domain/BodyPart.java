package com.healthmanager.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.healthmanager.common.annotation.Excel;
import com.healthmanager.common.core.domain.BaseEntity;

/**
 * 锻炼部位对象 health_body_part
 * 
 * @author ccc212
 * @date 2024-11-06
 */
public class BodyPart extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 部位ID */
    @Excel(name = "部位ID")
    private Long partId;

    /** 部位名称 */
    @Excel(name = "部位名称")
    private String partName;

    public void setPartId(Long partId) 
    {
        this.partId = partId;
    }

    public Long getPartId() 
    {
        return partId;
    }
    public void setPartName(String partName) 
    {
        this.partName = partName;
    }

    public String getPartName() 
    {
        return partName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("partId", getPartId())
            .append("partName", getPartName())
            .toString();
    }
}
