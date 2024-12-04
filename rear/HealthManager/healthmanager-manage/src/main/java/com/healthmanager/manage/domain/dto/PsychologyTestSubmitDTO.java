package com.healthmanager.manage.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class PsychologyTestSubmitDTO {
    private Long userId;
    private List<AnswerDTO> answers;
}
