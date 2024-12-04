package com.healthmanager.manage.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PsychologyTestResultVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal totalScore;
    private String aiAnalysis;
}
