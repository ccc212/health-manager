package com.healthmanager.manage.domain.vo;

import com.healthmanager.common.annotation.Excel;
import com.healthmanager.manage.domain.BodyPart;

import java.io.Serializable;
import java.util.List;

public class ExerciseShowVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long exerciseId;
    private String exerciseName;
    private String description;
    private List<BodyPart> bodyParts;
}
