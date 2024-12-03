package com.healthmanager.manage.mapper;

import java.util.List;
import com.healthmanager.manage.domain.PsychologyQuestion;

/**
 * 心理测试题目Mapper接口
 * 
 * @author ccc212
 * @date 2024-11-21
 */
public interface PsychologyQuestionMapper 
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
     * 删除心理测试题目
     * 
     * @param questionId 心理测试题目主键
     * @return 结果
     */
    public int deletePsychologyQuestionByQuestionId(Long questionId);

    /**
     * 批量删除心理测试题目
     * 
     * @param questionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsychologyQuestionByQuestionIds(Long[] questionIds);
}
