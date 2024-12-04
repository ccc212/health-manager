package com.healthmanager.manage.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.healthmanager.common.utils.DateUtils;
import com.healthmanager.manage.domain.PsychologyOption;
import com.healthmanager.manage.mapper.PsychologyOptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.PsychologyQuestionMapper;
import com.healthmanager.manage.domain.PsychologyQuestion;
import com.healthmanager.manage.service.IPsychologyQuestionService;
import com.healthmanager.manage.domain.vo.PsychologyQuestionVO;
import com.healthmanager.manage.domain.vo.PsychologyOptionVO;

/**
 * 心理测试题目Service业务层处理
 * 
 * @author ccc212
 * @date 2024-11-21
 */
@Service
public class PsychologyQuestionServiceImpl implements IPsychologyQuestionService 
{
    @Autowired
    private PsychologyQuestionMapper psychologyQuestionMapper;
    @Autowired
    private PsychologyOptionMapper psychologyOptionMapper;

    /**
     * 查询心理测试题目
     * 
     * @param questionId 心理测试题目主键
     * @return 心理测试题目
     */
    @Override
    public PsychologyQuestion selectPsychologyQuestionByQuestionId(Long questionId)
    {
        return psychologyQuestionMapper.selectPsychologyQuestionByQuestionId(questionId);
    }

    /**
     * 查询心理测试题目列表
     * 
     * @param psychologyQuestion 心理测试题目
     * @return 心理测试题目
     */
    @Override
    public List<PsychologyQuestion> selectPsychologyQuestionList(PsychologyQuestion psychologyQuestion)
    {
        return psychologyQuestionMapper.selectPsychologyQuestionList(psychologyQuestion);
    }

    /**
     * 新增心理测试题目
     * 
     * @param psychologyQuestion 心理测试题目
     * @return 结果
     */
    @Override
    public int insertPsychologyQuestion(PsychologyQuestion psychologyQuestion)
    {
        psychologyQuestion.setCreateTime(DateUtils.getNowDate());
        return psychologyQuestionMapper.insertPsychologyQuestion(psychologyQuestion);
    }

    /**
     * 修改心理测试题目
     * 
     * @param psychologyQuestion 心理测试题目
     * @return 结果
     */
    @Override
    public int updatePsychologyQuestion(PsychologyQuestion psychologyQuestion)
    {
        psychologyQuestion.setUpdateTime(DateUtils.getNowDate());
        return psychologyQuestionMapper.updatePsychologyQuestion(psychologyQuestion);
    }

    /**
     * 批量删除心理测试题目
     * 
     * @param questionIds 需要删除的心理测试题目主键
     * @return 结果
     */
    @Override
    public int deletePsychologyQuestionByQuestionIds(Long[] questionIds)
    {
        return psychologyQuestionMapper.deletePsychologyQuestionByQuestionIds(questionIds);
    }

    /**
     * 删除心理测试题目信息
     * 
     * @param questionId 心理测试题目主键
     * @return 结果
     */
    @Override
    public int deletePsychologyQuestionByQuestionId(Long questionId)
    {
        return psychologyQuestionMapper.deletePsychologyQuestionByQuestionId(questionId);
    }

    @Override
    public List<PsychologyQuestionVO> getQuestionsWithOptions() {
        // 获取所有题目
        List<PsychologyQuestion> questions = psychologyQuestionMapper.selectPsychologyQuestionList(new PsychologyQuestion());

        // 创建一个列表来存储题目及其选项
        List<PsychologyQuestionVO> questionVOList = new ArrayList<>();

        for (PsychologyQuestion question : questions) {
            // 获取每个题目的选项
            List<PsychologyOption> options = psychologyOptionMapper.selectOptionsByQuestionId(question.getQuestionId());

            // 将选项转换为VO对象
            List<PsychologyOptionVO> optionVOList = options.stream().map(option -> {
                PsychologyOptionVO optionVO = new PsychologyOptionVO();
                optionVO.setOptionId(option.getOptionId());
                optionVO.setOptionName(option.getOptionName());
                optionVO.setOptionText(option.getOptionText());
                return optionVO;
            }).collect(Collectors.toList());

            // 创建题目VO对象
            PsychologyQuestionVO questionVO = new PsychologyQuestionVO();
            questionVO.setQuestionId(question.getQuestionId());
            questionVO.setQuestionText(question.getQuestionText());
            questionVO.setOptions(optionVOList);

            // 添加到列表中
            questionVOList.add(questionVO);
        }

        return questionVOList;
    }
}
