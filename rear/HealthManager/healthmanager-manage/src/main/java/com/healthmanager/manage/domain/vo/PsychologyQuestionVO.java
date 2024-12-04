package com.healthmanager.manage.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class PsychologyQuestionVO {
    private Long questionId;
    private String questionText;
    private List<PsychologyOptionVO> options;
}
