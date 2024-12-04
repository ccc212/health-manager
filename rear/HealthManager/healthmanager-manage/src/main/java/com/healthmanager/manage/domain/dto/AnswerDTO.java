package com.healthmanager.manage.domain.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private Long questionId;
    private String optionName;  // A,B,C,D
}
