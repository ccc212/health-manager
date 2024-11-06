package com.healthmanager.manage.service;

import java.util.List;
import com.healthmanager.manage.domain.FitnessPlanDetail;

/**
 * 健身计划详情Service接口
 * 
 * @author ccc212
 * @date 2024-11-05
 */
public interface IFitnessPlanDetailService 
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
     * 批量删除健身计划详情
     * 
     * @param detailIds 需要删除的健身计划详情主键集合
     * @return 结果
     */
    public int deleteFitnessPlanDetailByDetailIds(Long[] detailIds);

    /**
     * 删除健身计划详情信息
     * 
     * @param detailId 健身计划详情主键
     * @return 结果
     */
    public int deleteFitnessPlanDetailByDetailId(Long detailId);
}
