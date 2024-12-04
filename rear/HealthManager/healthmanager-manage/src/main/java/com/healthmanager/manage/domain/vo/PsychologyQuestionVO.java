package com.healthmanager.manage.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PsychologyQuestionVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long questionId;
    private String questionText;
    private List<PsychologyOptionVO> options;
}
