package com.healthmanager.manage.service.impl;

import java.util.List;
import com.healthmanager.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.PsychologyTestMapper;
import com.healthmanager.manage.domain.PsychologyTest;
import com.healthmanager.manage.service.IPsychologyTestService;

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
}
