package com.healthmanager.manage.domain.vo;

import com.healthmanager.common.annotation.Excel;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;


@Builder
public class PsychologyOptionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long optionId;

    private String questionText;

    private String optionText;

    private BigDecimal score;
}
