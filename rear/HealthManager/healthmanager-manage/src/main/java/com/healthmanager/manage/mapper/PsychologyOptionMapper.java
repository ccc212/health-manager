package com.healthmanager.manage.mapper;

import java.util.List;
import com.healthmanager.manage.domain.PsychologyOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 心理测试选项Mapper接口
 * 
 * @author ccc212
 * @date 2024-12-03
 */
public interface PsychologyOptionMapper
{
    /**
     * 查询心理测试选项
     * 
     * @param optionId 心理测试选项主键
     * @return 心理测试选项
     */
    public PsychologyOption selectPsychologyOptionByOptionId(Long optionId);

    /**
     * 查询心理测试选项列表
     * 
     * @param psychologyOption 心理测试选项
     * @return 心理测试选项集合
     */
    public List<PsychologyOption> selectPsychologyOptionList(PsychologyOption psychologyOption);

    /**
     * 新增心理测试选项
     * 
     * @param psychologyOption 心理测试选项
     * @return 结果
     */
    public int insertPsychologyOption(PsychologyOption psychologyOption);

    /**
     * 修改心理测试选项
     * 
     * @param psychologyOption 心理测试选项
     * @return 结果
     */
    public int updatePsychologyOption(PsychologyOption psychologyOption);

    /**
     * 删除心理测试选项
     * 
     * @param optionId 心理测试选项主键
     * @return 结果
     */
    public int deletePsychologyOptionByOptionId(Long optionId);

    /**
     * 批量删除心理测试选项
     * 
     * @param optionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsychologyOptionByOptionIds(Long[] optionIds);

    @Select("select * from health_psychology_option where question_id = #{questionId}")
    List<PsychologyOption> selectOptionsByQuestionId(Long questionId);

    @Select("select * from health_psychology_option where question_id = #{questionId} and option_name = #{optionName}")
    PsychologyOption selectOptionByQuestionIdAndName(@Param("questionId") Long questionId, @Param("optionName") String optionName);
}
