package com.healthmanager.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthmanager.manage.domain.FitnessPlan;

/**
 * 健身计划Mapper接口
 * 
 * @author ccc212
 * @date 2024-11-06
 */
public interface FitnessPlanMapper extends BaseMapper<FitnessPlan>
{
    /**
     * 查询健身计划
     * 
     * @param planId 健身计划主键
     * @return 健身计划
     */
    public FitnessPlan selectFitnessPlanByPlanId(Long planId);

    /**
     * 查询健身计划列表
     * 
     * @param fitnessPlan 健身计划
     * @return 健身计划集合
     */
    public List<FitnessPlan> selectFitnessPlanList(FitnessPlan fitnessPlan);

    /**
     * 新增健身计划
     * 
     * @param fitnessPlan 健身计划
     * @return 结果
     */
    public int insertFitnessPlan(FitnessPlan fitnessPlan);

    /**
     * 修改健身计划
     * 
     * @param fitnessPlan 健身计划
     * @return 结果
     */
    public int updateFitnessPlan(FitnessPlan fitnessPlan);

    /**
     * 删除健身计划
     * 
     * @param planId 健身计划主键
     * @return 结果
     */
    public int deleteFitnessPlanByPlanId(Long planId);

    /**
     * 批量删除健身计划
     * 
     * @param planIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFitnessPlanByPlanIds(Long[] planIds);
}
