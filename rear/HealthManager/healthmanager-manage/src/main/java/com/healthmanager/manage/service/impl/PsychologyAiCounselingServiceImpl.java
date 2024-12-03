package com.healthmanager.manage.service.impl;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.healthmanager.common.utils.DateUtils;
import com.healthmanager.common.utils.ZhipuModelUtil;
import com.healthmanager.manage.domain.dto.PsychologyAiQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.PsychologyAiCounselingMapper;
import com.healthmanager.manage.domain.PsychologyAiCounseling;
import com.healthmanager.manage.service.IPsychologyAiCounselingService;

/**
 * AI咨询记录Service业务层处理
 * 
 * @author ccc212
 * @date 2024-11-21
 */
@Service
public class PsychologyAiCounselingServiceImpl implements IPsychologyAiCounselingService 
{
    @Autowired
    private PsychologyAiCounselingMapper psychologyAiCounselingMapper;

    /**
     * 查询AI咨询记录
     * 
     * @param recordId AI咨询记录主键
     * @return AI咨询记录
     */
    @Override
    public PsychologyAiCounseling selectPsychologyAiCounselingByRecordId(Long recordId)
    {
        return psychologyAiCounselingMapper.selectPsychologyAiCounselingByRecordId(recordId);
    }

    /**
     * 查询AI咨询记录列表
     * 
     * @param psychologyAiCounseling AI咨询记录
     * @return AI咨询记录
     */
    @Override
    public List<PsychologyAiCounseling> selectPsychologyAiCounselingList(PsychologyAiCounseling psychologyAiCounseling)
    {
        return psychologyAiCounselingMapper.selectPsychologyAiCounselingList(psychologyAiCounseling);
    }

    /**
     * 新增AI咨询记录
     * 
     * @param psychologyAiCounseling AI咨询记录
     * @return 结果
     */
    @Override
    public int insertPsychologyAiCounseling(PsychologyAiCounseling psychologyAiCounseling)
    {
        psychologyAiCounseling.setCreateTime(DateUtils.getNowDate());
        return psychologyAiCounselingMapper.insertPsychologyAiCounseling(psychologyAiCounseling);
    }

    /**
     * 修改AI咨询记录
     * 
     * @param psychologyAiCounseling AI咨询记录
     * @return 结果
     */
    @Override
    public int updatePsychologyAiCounseling(PsychologyAiCounseling psychologyAiCounseling)
    {
        psychologyAiCounseling.setUpdateTime(DateUtils.getNowDate());
        return psychologyAiCounselingMapper.updatePsychologyAiCounseling(psychologyAiCounseling);
    }

    /**
     * 批量删除AI咨询记录
     * 
     * @param recordIds 需要删除的AI咨询记录主键
     * @return 结果
     */
    @Override
    public int deletePsychologyAiCounselingByRecordIds(Long[] recordIds)
    {
        return psychologyAiCounselingMapper.deletePsychologyAiCounselingByRecordIds(recordIds);
    }

    /**
     * 删除AI咨询记录信息
     * 
     * @param recordId AI咨询记录主键
     * @return 结果
     */
    @Override
    public int deletePsychologyAiCounselingByRecordId(Long recordId)
    {
        return psychologyAiCounselingMapper.deletePsychologyAiCounselingByRecordId(recordId);
    }

    @Override
    public String getPsychologyCounselingAnswer(PsychologyAiQueryDTO psychologyAiQueryDTO) {
        PsychologyAiCounseling psychologyAiCounseling = BeanUtil.copyProperties(psychologyAiQueryDTO, PsychologyAiCounseling.class);
        String answer = ZhipuModelUtil.callZhipuModel(ZhipuModelUtil.buildPsychologyCounseling(psychologyAiQueryDTO.getQuestion()));
        psychologyAiCounseling.setAnswer(answer);
        insertPsychologyAiCounseling(psychologyAiCounseling);
        return answer;
    }
}
