package com.healthmanager.manage.service.impl;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
import com.healthmanager.common.utils.DateUtils;
import com.healthmanager.common.utils.ZhipuModelUtil;
import com.healthmanager.manage.domain.PsychologyOption;
import com.healthmanager.manage.domain.dto.AnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.PsychologyTestMapper;
import com.healthmanager.manage.domain.PsychologyTest;
import com.healthmanager.manage.service.IPsychologyTestService;
import com.healthmanager.manage.mapper.PsychologyOptionMapper;
import com.healthmanager.manage.domain.dto.PsychologyTestSubmitDTO;
import com.healthmanager.manage.domain.vo.PsychologyTestResultVO;

/**
 * 心理测试记录Service业务层处理
 * 
 * @author ccc212
 * @date 2024-11-21
 */
@Service
public class PsychologyTestServiceImpl implements IPsychologyTestService 
{
    @Autowired
    private PsychologyTestMapper psychologyTestMapper;
    @Autowired
    private PsychologyOptionMapper psychologyOptionMapper;
    @Autowired
    private ZhipuModelUtil zhipuModelUtil;

    /**
     * 查询心理测试记录
     * 
     * @param testId 心理测试记录主键
     * @return 心理测试记录
     */
    @Override
    public PsychologyTest selectPsychologyTestByTestId(Long testId)
    {
        return psychologyTestMapper.selectPsychologyTestByTestId(testId);
    }

    /**
     * 查询心理测试记录列表
     * 
     * @param psychologyTest 心理测试记录
     * @return 心理测试记录
     */
    @Override
    public List<PsychologyTest> selectPsychologyTestList(PsychologyTest psychologyTest)
    {
        return psychologyTestMapper.selectPsychologyTestList(psychologyTest);
    }

    /**
     * 新增心理测试记录
     * 
     * @param psychologyTest 心理测试记录
     * @return 结果
     */
    @Override
    public int insertPsychologyTest(PsychologyTest psychologyTest)
    {
        psychologyTest.setCreateTime(DateUtils.getNowDate());
        return psychologyTestMapper.insertPsychologyTest(psychologyTest);
    }

    /**
     * 修改心理测试记录
     * 
     * @param psychologyTest 心理测试记录
     * @return 结果
     */
    @Override
    public int updatePsychologyTest(PsychologyTest psychologyTest)
    {
        psychologyTest.setUpdateTime(DateUtils.getNowDate());
        return psychologyTestMapper.updatePsychologyTest(psychologyTest);
    }

    /**
     * 批量删除心理测试记录
     * 
     * @param testIds 需要删除的心理测试记录主键
     * @return 结果
     */
    @Override
    public int deletePsychologyTestByTestIds(Long[] testIds)
    {
        return psychologyTestMapper.deletePsychologyTestByTestIds(testIds);
    }

    /**
     * 删除心理测试记录信息
     *
     * @param testId 心理测试记录主键
     * @return 结果
     */
    @Override
    public int deletePsychologyTestByTestId(Long testId)
    {
        return psychologyTestMapper.deletePsychologyTestByTestId(testId);
    }

    @Override
    public PsychologyTestResultVO calculateAndSaveResult(PsychologyTestSubmitDTO submitDTO) {
        // 1. 计算总分
        BigDecimal totalScore = calculateTotalScore(submitDTO.getAnswers());
        
        // 2. 生成AI分析报告
        String aiAnalysis = generateAIAnalysis(totalScore);
        
        // 3. 保存测试记录
        PsychologyTest test = new PsychologyTest();
        test.setUserId(submitDTO.getUserId());
        test.setTotalScore(totalScore);
        test.setAiAnalysis(aiAnalysis);
        test.setTestDate(new Date());
        psychologyTestMapper.insertPsychologyTest(test);
        
        // 4. 返回结果
        PsychologyTestResultVO resultVO = new PsychologyTestResultVO();
        resultVO.setTotalScore(totalScore);
        resultVO.setAiAnalysis(aiAnalysis);
        return resultVO;
    }
    
    private BigDecimal calculateTotalScore(List<AnswerDTO> answers) {
        BigDecimal totalScore = BigDecimal.ZERO;
        
        for (AnswerDTO answer : answers) {
            // 根据问题ID和选项名称查询选项分数
            Long questionId = answer.getQuestionId();
            String optionName = answer.getOptionName();
            PsychologyOption option = psychologyOptionMapper.selectOptionByQuestionIdAndName(questionId, optionName);
            if (option != null) {
                totalScore = totalScore.add(option.getScore());
            }
        }
        
        return totalScore;
    }
    
    private String generateAIAnalysis(BigDecimal totalScore) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("作为一个专业的心理健康顾问，请根据以下抑郁自评量表(CES-D)的得分情况，给出专业的分析和建议：\n");
        prompt.append("总分：").append(totalScore).append("分（总分范围0-60分）\n\n");
        prompt.append("请从以下几个方面进行分析：\n");
        prompt.append("1. 当前情绪状态评估\n");
        prompt.append("2. 可能存在的心理健康风险\n");
        prompt.append("3. 具体的改善建议\n");
        prompt.append("4. 是否建议寻求专业帮助\n\n");
        prompt.append("注意：分析要专业、温和、积极，避免引起焦虑。");
        String analysis = ZhipuModelUtil.callZhipuModel(prompt.toString());
        return analysis;
    }
}
