package com.healthmanager.manage.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PsychologyTestResultVO {
    private BigDecimal totalScore;
    private String aiAnalysis;
}
