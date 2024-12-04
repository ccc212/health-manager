package com.healthmanager.manage.service;

import java.util.List;
import com.healthmanager.manage.domain.Exercise;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.healthmanager.manage.domain.dto.ExerciseSearchDTO;
import com.healthmanager.manage.domain.vo.ExerciseShowVO;

/**
 * 锻炼项目Service接口
 * 
 * @author ccc212
 * @date 2024-11-05
 */
public interface IExerciseService 
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
     * 批量删除锻炼项目
     * 
     * @param exerciseIds 需要删除的锻炼项目主键集合
     * @return 结果
     */
    public int deleteExerciseByExerciseIds(Long[] exerciseIds);

    /**
     * 删除锻炼项目信息
     * 
     * @param exerciseId 锻炼项目主键
     * @return 结果
     */
    public int deleteExerciseByExerciseId(Long exerciseId);

    List<ExerciseShowVO> selectExerciseShowList(ExerciseSearchDTO exerciseSearchDTO, int offset, Integer limit);

    int countExerciseShowList(ExerciseSearchDTO exerciseSearchDTO);
}
