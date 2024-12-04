package com.healthmanager.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthmanager.manage.domain.FitnessPlanDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * 健身计划详情Mapper接口
 * 
 * @author ccc212
 * @date 2024-11-05
 */
public interface FitnessPlanDetailMapper extends BaseMapper<FitnessPlanDetail>
{
    /**
     * 查询健身计划详情
     * 
     * @param detailId 健身计划详情主键
     * @return 健身计划详情
     */
    public FitnessPlanDetail selectFitnessPlanDetailByDetailId(Long detailId);

    /**
     * 查询健身计划详情列表
     * 
     * @param fitnessPlanDetail 健身计划详情
     * @return 健身计划详情集合
     */
    public List<FitnessPlanDetail> selectFitnessPlanDetailList(FitnessPlanDetail fitnessPlanDetail);

    /**
     * 新增健身计划详情
     * 
     * @param fitnessPlanDetail 健身计划详情
     * @return 结果
     */
    public int insertFitnessPlanDetail(FitnessPlanDetail fitnessPlanDetail);

    /**
     * 修改健身计划详情
     * 
     * @param fitnessPlanDetail 健身计划详情
     * @return 结果
     */
    public int updateFitnessPlanDetail(FitnessPlanDetail fitnessPlanDetail);

    /**
     * 删除健身计划详情
     * 
     * @param detailId 健身计划详情主键
     * @return 结果
     */
    public int deleteFitnessPlanDetailByDetailId(Long detailId);

    /**
     * 批量删除健身计划详情
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFitnessPlanDetailByDetailIds(Long[] detailIds);

    @Delete("delete from health_fitness_plan_detail where plan_id = #{planId}")
    void deleteFitnessPlanDetailByPlanId(Long planId);

    @Select("select * from health_fitness_plan_detail where plan_id = #{planId}")
    List<FitnessPlanDetail> selectFitnessPlanDetailByPlanId(Long planId);
}
