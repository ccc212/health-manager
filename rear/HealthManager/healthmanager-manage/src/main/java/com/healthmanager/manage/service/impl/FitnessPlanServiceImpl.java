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
//            TODO 自定义异常
            return;
        }
        Long userId = fitnessPlan.getUserId();
        UserBodyData userBodyData = userBodyDataMapper.selectUserBodyDataByUserId(userId);
        List<Map<String, Object>> exerciseIdsAndNames = exerciseMapper.selectAllExerciseIdsAndNames();

        String exerciseList = exerciseIdsAndNames.stream()
                .map(exercise -> exercise.get("exercise_id") + ":" + exercise.get("exercise_name"))
                .collect(Collectors.joining("、"));
        String goals = fitnessPlan.getGoals();
        String initialStatus = fitnessPlan.getInitialStatus();
        String gender = userBodyData.getGender();
        Integer age = userBodyData.getAge();
        BigDecimal height = userBodyData.getHeight();
        BigDecimal weight = userBodyData.getWeight();

        String message = ZhipuModelUtil.buildFitnessPlanMessage(initialStatus, gender, age, height, weight, goals, exerciseList);
        String result = ZhipuModelUtil.callZhipuModel(message);
        Map<Integer, String> exercisePlan = new HashMap<>();

        String[] exercises = result.split("\\n");
        for (String exercise : exercises) {
            String[] parts = exercise.split(":");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[0].trim());
                String intensity = parts[1].trim();
                String time = parts[2].trim();
                exercisePlan.put(id, "强度: " + intensity + ", 时间: " + time + "分钟");
            }
        }

        exercisePlan.forEach((id, details) -> {
            System.out.println("健身项目ID: " + id + ", " + details);
        });
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
        return fitnessPlanMapper.deleteFitnessPlanByPlanId(planId);
    }
}
