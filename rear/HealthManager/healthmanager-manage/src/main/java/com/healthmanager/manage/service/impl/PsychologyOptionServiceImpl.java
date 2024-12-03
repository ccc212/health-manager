package com.healthmanager.manage.service.impl;

import java.util.List;
import com.healthmanager.common.utils.DateUtils;
import com.healthmanager.manage.domain.vo.PsychologyOptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.PsychologyOptionMapper;
import com.healthmanager.manage.domain.PsychologyOption;
import com.healthmanager.manage.service.IPsychologyOptionService;

/**
 * 心理测试选项Service业务层处理
 * 
 * @author ccc212
 * @date 2024-11-21
 */
@Service
public class PsychologyOptionServiceImpl implements IPsychologyOptionService 
{
    @Autowired
    private PsychologyOptionMapper psychologyOptionMapper;

    /**
     * 查询心理测试选项
     * 
     * @param optionId 心理测试选项主键
     * @return 心理测试选项
     */
    @Override
    public PsychologyOption selectPsychologyOptionByOptionId(Long optionId)
    {
        return psychologyOptionMapper.selectPsychologyOptionByOptionId(optionId);
    }

    /**
     * 查询心理测试选项列表
     * 
     * @param psychologyOption 心理测试选项
     * @return 心理测试选项
     */
    @Override
    public List<PsychologyOption> selectPsychologyOptionList(PsychologyOption psychologyOption)
    {
        return psychologyOptionMapper.selectPsychologyOptionList(psychologyOption);
    }

    /**
     * 新增心理测试选项
     * 
     * @param psychologyOption 心理测试选项
     * @return 结果
     */
    @Override
    public int insertPsychologyOption(PsychologyOption psychologyOption)
    {
        psychologyOption.setCreateTime(DateUtils.getNowDate());
        return psychologyOptionMapper.insertPsychologyOption(psychologyOption);
    }

    /**
     * 修改心理测试选项
     * 
     * @param psychologyOption 心理测试选项
     * @return 结果
     */
    @Override
    public int updatePsychologyOption(PsychologyOption psychologyOption)
    {
        psychologyOption.setUpdateTime(DateUtils.getNowDate());
        return psychologyOptionMapper.updatePsychologyOption(psychologyOption);
    }

    /**
     * 批量删除心理测试选项
     * 
     * @param optionIds 需要删除的心理测试选项主键
     * @return 结果
     */
    @Override
    public int deletePsychologyOptionByOptionIds(Long[] optionIds)
    {
        return psychologyOptionMapper.deletePsychologyOptionByOptionIds(optionIds);
    }

    /**
     * 删除心理测试选项信息
     * 
     * @param optionId 心理测试选项主键
     * @return 结果
     */
    @Override
    public int deletePsychologyOptionByOptionId(Long optionId)
    {
        return psychologyOptionMapper.deletePsychologyOptionByOptionId(optionId);
    }

    @Override
    public List<PsychologyOptionVO> selectPsychologyOptionVOList(PsychologyOption psychologyOption) {
        return psychologyOptionMapper.selectPsychologyOptionVOList(psychologyOption);
    }
}
