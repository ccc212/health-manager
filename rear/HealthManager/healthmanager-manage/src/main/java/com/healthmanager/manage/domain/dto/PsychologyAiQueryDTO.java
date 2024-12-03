package com.healthmanager.manage.domain.dto;

import com.healthmanager.common.annotation.Excel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PsychologyAiQueryDTO {

    /** 用户ID */
    private Long userId;

    /** 用户问题 */
    private String question;
}
