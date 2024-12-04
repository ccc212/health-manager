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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
        // 获取用户之前的对话历史
        PsychologyAiCounseling queryParam = new PsychologyAiCounseling();
        queryParam.setUserId(psychologyAiQueryDTO.getUserId());
        List<PsychologyAiCounseling> historyList = selectPsychologyAiCounselingList(queryParam);
        
        // 构建上下文对话内容
        StringBuilder contextBuilder = new StringBuilder();
        contextBuilder.append("以下是之前的对话历史：\n\n");
        
        // 添加历史对话记录
        for (PsychologyAiCounseling history : historyList) {
            contextBuilder.append("用户：").append(history.getQuestion()).append("\n");
            contextBuilder.append("AI：").append(history.getAnswer()).append("\n\n");
        }
        
        // 添加当前问题
        contextBuilder.append("用户：").append(psychologyAiQueryDTO.getQuestion()).append("\n");
        contextBuilder.append("请基于以上对话历史，回答用户的最新问题。回答要保持连贯性和上下文关联性。\n");
        
        // 调用AI模型获取回答
        String answer = ZhipuModelUtil.callZhipuModel(
            ZhipuModelUtil.buildPsychologyCounseling(contextBuilder.toString())
        );
        
        // 保存新的对话记录
        PsychologyAiCounseling psychologyAiCounseling = BeanUtil.copyProperties(
            psychologyAiQueryDTO, 
            PsychologyAiCounseling.class
        );
        psychologyAiCounseling.setAnswer(answer);
        insertPsychologyAiCounseling(psychologyAiCounseling);
        
        return answer;
    }
}
