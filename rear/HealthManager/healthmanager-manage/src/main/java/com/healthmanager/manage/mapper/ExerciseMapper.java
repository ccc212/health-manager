package com.healthmanager.manage.mapper;

import java.util.List;
import java.util.Map;

import com.healthmanager.manage.domain.Exercise;
import org.apache.ibatis.annotations.MapKey;

/**
 * 锻炼项目Mapper接口
 * 
 * @author ccc212
 * @date 2024-11-05
 */
public interface ExerciseMapper 
{
    /**
     * 查询锻炼项目
     * 
     * @param exerciseId 锻炼项目主键
     * @return 锻炼项目
     */
    public Exercise selectExerciseByExerciseId(Long exerciseId);

    /**
     * 查询锻炼项目列表
     * 
     * @param exercise 锻炼项目
     * @return 锻炼项目集合
     */
    public List<Exercise> selectExerciseList(Exercise exercise);

    /**
     * 新增锻炼项目
     * 
     * @param exercise 锻炼项目
     * @return 结果
     */
    public int insertExercise(Exercise exercise);

    /**
     * 修改锻炼项目
     * 
     * @param exercise 锻炼项目
     * @return 结果
     */
    public int updateExercise(Exercise exercise);

    /**
     * 删除锻炼项目
     * 
     * @param exerciseId 锻炼项目主键
     * @return 结果
     */
    public int deleteExerciseByExerciseId(Long exerciseId);

    /**
     * 批量删除锻炼项目
     * 
     * @param exerciseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExerciseByExerciseIds(Long[] exerciseIds);

     /**
     * 查询所有锻炼项目的ID和名称
     * 
     * @return 锻炼项目的ID和名称列表
     */
     @MapKey("exercise_id")
     public List<Map<String, Object>> selectAllExerciseIdsAndNames();
}
