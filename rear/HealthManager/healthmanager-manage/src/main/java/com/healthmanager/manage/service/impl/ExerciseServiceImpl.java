package com.healthmanager.manage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.ExerciseMapper;
import com.healthmanager.manage.domain.Exercise;
import com.healthmanager.manage.service.IExerciseService;

/**
 * 锻炼项目Service业务层处理
 * 
 * @author ccc212
 * @date 2024-11-05
 */
@Service
public class ExerciseServiceImpl implements IExerciseService 
{
    @Autowired
    private ExerciseMapper exerciseMapper;

    /**
     * 查询锻炼项目
     * 
     * @param exerciseId 锻炼项目主键
     * @return 锻炼项目
     */
    @Override
    public Exercise selectExerciseByExerciseId(Long exerciseId)
    {
        return exerciseMapper.selectExerciseByExerciseId(exerciseId);
    }

    /**
     * 查询锻炼项目列表
     * 
     * @param exercise 锻炼项目
     * @return 锻炼项目
     */
    @Override
    public List<Exercise> selectExerciseList(Exercise exercise)
    {
        return exerciseMapper.selectExerciseList(exercise);
    }

    /**
     * 新增锻炼项目
     * 
     * @param exercise 锻炼项目
     * @return 结果
     */
    @Override
    public int insertExercise(Exercise exercise)
    {
        return exerciseMapper.insertExercise(exercise);
    }

    /**
     * 修改锻炼项目
     * 
     * @param exercise 锻炼项目
     * @return 结果
     */
    @Override
    public int updateExercise(Exercise exercise)
    {
        return exerciseMapper.updateExercise(exercise);
    }

    /**
     * 批量删除锻炼项目
     * 
     * @param exerciseIds 需要删除的锻炼项目主键
     * @return 结果
     */
    @Override
    public int deleteExerciseByExerciseIds(Long[] exerciseIds)
    {
        return exerciseMapper.deleteExerciseByExerciseIds(exerciseIds);
    }

    /**
     * 删除锻炼项目信息
     * 
     * @param exerciseId 锻炼项目主键
     * @return 结果
     */
    @Override
    public int deleteExerciseByExerciseId(Long exerciseId)
    {
        return exerciseMapper.deleteExerciseByExerciseId(exerciseId);
    }
}
