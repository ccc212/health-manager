package com.healthmanager.manage.domain.vo;

import com.healthmanager.common.core.domain.BaseEntity;
import com.healthmanager.manage.domain.BodyPart;
import lombok.Data;

import java.util.List;

@Data
public class ExerciseShowVO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long exerciseId;
    private String exerciseName;
    private String description;
    private List<BodyPart> bodyParts;
}
