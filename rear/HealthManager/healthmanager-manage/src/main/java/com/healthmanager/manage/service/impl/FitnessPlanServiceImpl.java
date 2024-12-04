package com.healthmanager.manage.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.healthmanager.common.utils.DateUtils;
import com.healthmanager.common.utils.ZhipuModelUtil;
import com.healthmanager.manage.domain.FitnessPlanDetail;
import com.healthmanager.manage.domain.UserBodyData;
import com.healthmanager.manage.mapper.ExerciseMapper;
import com.healthmanager.manage.mapper.FitnessPlanDetailMapper;
import com.healthmanager.manage.mapper.UserBodyDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.FitnessPlanMapper;
import com.healthmanager.manage.domain.FitnessPlan;
import com.healthmanager.manage.service.IFitnessPlanService;

/**
 * 健身计划Service业务层处理
 *
 * @author ccc212
 * @date 2024-11-06
 */
@Service
public class FitnessPlanServiceImpl implements IFitnessPlanService {
    @Autowired
    private FitnessPlanMapper fitnessPlanMapper;

    @Autowired
    private FitnessPlanDetailMapper fitnessPlanDetailMapper;

    @Autowired
    private UserBodyDataMapper userBodyDataMapper;

    @Autowired
    private ExerciseMapper exerciseMapper;

    /**
     * 生成健身计划详情
     *
     * @param planId 健身计划主键
     */
    @Override
    public void generate(Long planId) {
        FitnessPlan fitnessPlan = fitnessPlanMapper.selectFitnessPlanByPlanId(planId);
        if (fitnessPlan == null) {
            throw new RuntimeException("计划不存在");
        }
        
        Long userId = fitnessPlan.getUserId();
        UserBodyData userBodyData = userBodyDataMapper.selectUserBodyDataByUserId(userId);
        List<Map<String, Object>> exerciseIdsAndNames = exerciseMapper.selectAllExerciseIdsAndNames();

        // 锻炼项目列表
        String exerciseList = exerciseIdsAndNames.stream()
                .map(exercise -> exercise.get("exercise_id") + ":" + exercise.get("exercise_name"))
                .collect(Collectors.joining("、"));

        // 构建提问消息
        String message = ZhipuModelUtil.buildFitnessPlanMessage(
            fitnessPlan.getInitialStatus(), 
            userBodyData.getGender(), 
            userBodyData.getAge(), 
            userBodyData.getHeight(), 
            userBodyData.getWeight(), 
            fitnessPlan.getGoals(), 
            exerciseList
        );

        // 调用AI获取结果
        String result = ZhipuModelUtil.callZhipuModel(message);
        
        // 删除该计划之前的详情记录
        fitnessPlanDetailMapper.deleteFitnessPlanDetailByPlanId(planId);
        
        // 解析结果并保存
        String[] exercises = result.split("\\n");
        for (String exercise : exercises) {
            String[] parts = exercise.trim().split(":");
            if (parts.length == 4) {
                // 解析数据
                Long exerciseId = Long.parseLong(parts[0].trim());
                String intensity = parts[1].trim();
                Long duration = Long.parseLong(parts[2].trim());
                Long frequency = Long.parseLong(parts[3].trim());

                // 创建详情记录
                FitnessPlanDetail detail = new FitnessPlanDetail();
                detail.setPlanId(planId);
                detail.setExerciseId(exerciseId);
                detail.setIntensity(intensity);
                detail.setDuration(duration);
                detail.setFrequency(frequency);

                // 保存详情记录
                fitnessPlanDetailMapper.insertFitnessPlanDetail(detail);
            }
        }
        
        // 更新计划状态
        fitnessPlan.setProgress(0);  // 设置初始进度
        fitnessPlan.setCurrentStatus(fitnessPlan.getInitialStatus());  // 设置当前状态为初始状态
        fitnessPlanMapper.updateFitnessPlan(fitnessPlan);
    }

    /**
     * 查询健身计划
     *
     * @param planId 健身计划主键
     * @return 健身计划
     */
    @Override
    public FitnessPlan selectFitnessPlanByPlanId(Long planId) {
        return fitnessPlanMapper.selectFitnessPlanByPlanId(planId);
    }

    /**
     * 查询健身计划列表
     *
     * @param fitnessPlan 健身计划
     * @return 健身计划
     */
    @Override
    public List<FitnessPlan> selectFitnessPlanList(FitnessPlan fitnessPlan) {
        return fitnessPlanMapper.selectFitnessPlanList(fitnessPlan);
    }

    /**
     * 新增健身计划
     *
     * @param fitnessPlan 健身计划
     * @return 结果
     */
    @Override
    public int insertFitnessPlan(FitnessPlan fitnessPlan) {
        fitnessPlan.setCreateTime(DateUtils.getNowDate());
        return fitnessPlanMapper.insertFitnessPlan(fitnessPlan);
    }

    /**
     * 修改健身计划
     *
     * @param fitnessPlan 健身计划
     * @return 结果
     */
    @Override
    public int updateFitnessPlan(FitnessPlan fitnessPlan) {
        fitnessPlan.setUpdateTime(DateUtils.getNowDate());
        return fitnessPlanMapper.updateFitnessPlan(fitnessPlan);
    }

    /**
     * 批量删除健身计划
     *
     * @param planIds 需要删除的健身计划主键
     * @return 结果
     */
    @Override
    public int deleteFitnessPlanByPlanIds(Long[] planIds) {
        for (Long planId : planIds) {
            fitnessPlanDetailMapper.deleteFitnessPlanDetailByPlanId(planId);
        }
        return fitnessPlanMapper.deleteFitnessPlanByPlanIds(planIds);
    }

    /**
     * 删除健身计划信息
     *
     * @param planId 健身计划主键
     * @return 结果
     */
    @Override
    public int deleteFitnessPlanByPlanId(Long planId) {
        fitnessPlanDetailMapper.deleteFitnessPlanDetailByPlanId(planId);
        return fitnessPlanMapper.deleteFitnessPlanByPlanId(planId);
    }

    @Override
    public void updatePlanProgress(Long userId) {
        FitnessPlan fitnessPlan = fitnessPlanMapper.selectFitnessPlanByUserId(userId);
        Long planId = fitnessPlan.getPlanId();
        int size = fitnessPlanDetailMapper.selectFitnessPlanDetailByPlanId(planId).size();
        int progress = fitnessPlan.getProgress() + (100 / size);
        progress = Math.min(progress, 100);
        fitnessPlan.setProgress(progress);
        fitnessPlanMapper.updateFitnessPlan(fitnessPlan);
    }

    @Override
    public boolean hasGeneratedPlan(Long userId) {
        FitnessPlan plan = new FitnessPlan();
        plan.setUserId(userId);
        List<FitnessPlan> plans = selectFitnessPlanList(plan);
        return plans.size() > 0 && plans.get(0) != null;
    }
}
