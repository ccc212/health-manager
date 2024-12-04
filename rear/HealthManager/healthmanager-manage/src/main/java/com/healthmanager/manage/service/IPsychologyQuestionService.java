package com.healthmanager.manage.service;

import java.util.List;
import com.healthmanager.manage.domain.PsychologyQuestion;
import com.healthmanager.manage.domain.vo.PsychologyQuestionVO;

/**
 * 心理测试题目Service接口
 * 
 * @author ccc212
 * @date 2024-11-21
 */
public interface IPsychologyQuestionService 
{
    /**
     * 查询心理测试题目
     * 
     * @param questionId 心理测试题目主键
     * @return 心理测试题目
     */
    public PsychologyQuestion selectPsychologyQuestionByQuestionId(Long questionId);

    /**
     * 查询心理测试题目列表
     * 
     * @param psychologyQuestion 心理测试题目
     * @return 心理测试题目集合
     */
    public List<PsychologyQuestion> selectPsychologyQuestionList(PsychologyQuestion psychologyQuestion);

    /**
     * 新增心理测试题目
     * 
     * @param psychologyQuestion 心理测试题目
     * @return 结果
     */
    public int insertPsychologyQuestion(PsychologyQuestion psychologyQuestion);

    /**
     * 修改心理测试题目
     * 
     * @param psychologyQuestion 心理测试题目
     * @return 结果
     */
    public int updatePsychologyQuestion(PsychologyQuestion psychologyQuestion);

    /**
     * 批量删除心理测试题目
     * 
     * @param questionIds 需要删除的心理测试题目主键集合
     * @return 结果
     */
    public int deletePsychologyQuestionByQuestionIds(Long[] questionIds);

    /**
     * 删除心理测试题目信息
     * 
     * @param questionId 心理测试题目主键
     * @return 结果
     */
    public int deletePsychologyQuestionByQuestionId(Long questionId);

    List<PsychologyQuestionVO> getQuestionsWithOptions();
}
