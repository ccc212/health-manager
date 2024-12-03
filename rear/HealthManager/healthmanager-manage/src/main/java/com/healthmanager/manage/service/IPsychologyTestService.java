package com.healthmanager.manage.service;

import java.util.List;
import com.healthmanager.manage.domain.PsychologyTest;

/**
 * 心理测试记录Service接口
 * 
 * @author ccc212
 * @date 2024-11-21
 */
public interface IPsychologyTestService 
{
    /**
     * 查询心理测试记录
     * 
     * @param testId 心理测试记录主键
     * @return 心理测试记录
     */
    public PsychologyTest selectPsychologyTestByTestId(Long testId);

    /**
     * 查询心理测试记录列表
     * 
     * @param psychologyTest 心理测试记录
     * @return 心理测试记录集合
     */
    public List<PsychologyTest> selectPsychologyTestList(PsychologyTest psychologyTest);

    /**
     * 新增心理测试记录
     * 
     * @param psychologyTest 心理测试记录
     * @return 结果
     */
    public int insertPsychologyTest(PsychologyTest psychologyTest);

    /**
     * 修改心理测试记录
     * 
     * @param psychologyTest 心理测试记录
     * @return 结果
     */
    public int updatePsychologyTest(PsychologyTest psychologyTest);

    /**
     * 批量删除心理测试记录
     * 
     * @param testIds 需要删除的心理测试记录主键集合
     * @return 结果
     */
    public int deletePsychologyTestByTestIds(Long[] testIds);

    /**
     * 删除心理测试记录信息
     * 
     * @param testId 心理测试记录主键
     * @return 结果
     */
    public int deletePsychologyTestByTestId(Long testId);
}
