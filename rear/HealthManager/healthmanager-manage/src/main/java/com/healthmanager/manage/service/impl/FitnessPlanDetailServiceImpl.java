package com.healthmanager.manage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.FitnessPlanDetailMapper;
import com.healthmanager.manage.domain.FitnessPlanDetail;
import com.healthmanager.manage.service.IFitnessPlanDetailService;

/**
 * 健身计划详情Service业务层处理
 * 
 * @author ccc212
 * @date 2024-11-05
 */
@Service
public class FitnessPlanDetailServiceImpl implements IFitnessPlanDetailService 
{
    @Autowired
    private FitnessPlanDetailMapper fitnessPlanDetailMapper;

    /**
     * 查询健身计划详情
     * 
     * @param detailId 健身计划详情主键
     * @return 健身计划详情
     */
    @Override
    public FitnessPlanDetail selectFitnessPlanDetailByDetailId(Long detailId)
    {
        return fitnessPlanDetailMapper.selectFitnessPlanDetailByDetailId(detailId);
    }

    /**
     * 查询健身计划详情列表
     * 
     * @param fitnessPlanDetail 健身计划详情
     * @return 健身计划详情
     */
    @Override
    public List<FitnessPlanDetail> selectFitnessPlanDetailList(FitnessPlanDetail fitnessPlanDetail)
    {
        return fitnessPlanDetailMapper.selectFitnessPlanDetailList(fitnessPlanDetail);
    }

    /**
     * 新增健身计划详情
     * 
     * @param fitnessPlanDetail 健身计划详情
     * @return 结果
     */
    @Override
    public int insertFitnessPlanDetail(FitnessPlanDetail fitnessPlanDetail)
    {
        return fitnessPlanDetailMapper.insertFitnessPlanDetail(fitnessPlanDetail);
    }

    /**
     * 修改健身计划详情
     * 
     * @param fitnessPlanDetail 健身计划详情
     * @return 结果
     */
    @Override
    public int updateFitnessPlanDetail(FitnessPlanDetail fitnessPlanDetail)
    {
        return fitnessPlanDetailMapper.updateFitnessPlanDetail(fitnessPlanDetail);
    }

    /**
     * 批量删除健身计划详情
     * 
     * @param detailIds 需要删除的健身计划详情主键
     * @return 结果
     */
    @Override
    public int deleteFitnessPlanDetailByDetailIds(Long[] detailIds)
    {
        return fitnessPlanDetailMapper.deleteFitnessPlanDetailByDetailIds(detailIds);
    }

    /**
     * 删除健身计划详情信息
     * 
     * @param detailId 健身计划详情主键
     * @return 结果
     */
    @Override
    public int deleteFitnessPlanDetailByDetailId(Long detailId)
    {
        return fitnessPlanDetailMapper.deleteFitnessPlanDetailByDetailId(detailId);
    }
}
