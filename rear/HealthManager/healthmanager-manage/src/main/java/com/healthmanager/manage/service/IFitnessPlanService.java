package com.healthmanager.manage.service;

import java.util.List;
import com.healthmanager.manage.domain.FitnessPlan;

/**
 * 健身计划Service接口
 * 
 * @author ccc212
 * @date 2024-11-06
 */
public interface IFitnessPlanService 
{

    /**
     * 生成健身计划详情
     * @param planId 健身计划主键
     * @param userId 用户主键
     */
    void generate(Long planId);

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
     * 批量删除健身计划
     *
     * @param planIds 需要删除的健身计划主键集合
     * @return 结果
     */
    public int deleteFitnessPlanByPlanIds(Long[] planIds);

    /**
     * 删除健身计划信息
     *
     * @param planId 健身计划主键
     * @return 结果
     */
    public int deleteFitnessPlanByPlanId(Long planId);
}
