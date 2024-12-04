package com.healthmanager.manage.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PsychologyOptionVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long optionId;
    private String optionName;
    private String optionText;
}
