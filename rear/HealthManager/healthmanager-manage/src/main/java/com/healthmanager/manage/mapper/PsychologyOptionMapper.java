package com.healthmanager.manage.mapper;

import java.util.List;
import com.healthmanager.manage.domain.PsychologyOption;
import com.healthmanager.manage.domain.vo.PsychologyOptionVO;

/**
 * 心理测试选项Mapper接口
 * 
 * @author ccc212
 * @date 2024-11-21
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

    List<PsychologyOptionVO> selectPsychologyOptionVOList(PsychologyOption psychologyOption);
}
