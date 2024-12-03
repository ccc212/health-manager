package com.healthmanager.manage.mapper;

import java.util.List;
import com.healthmanager.manage.domain.PsychologyAiCounseling;

/**
 * AI咨询记录Mapper接口
 * 
 * @author ccc212
 * @date 2024-11-21
 */
public interface PsychologyAiCounselingMapper 
{
    /**
     * 查询AI咨询记录
     * 
     * @param recordId AI咨询记录主键
     * @return AI咨询记录
     */
    public PsychologyAiCounseling selectPsychologyAiCounselingByRecordId(Long recordId);

    /**
     * 查询AI咨询记录列表
     * 
     * @param psychologyAiCounseling AI咨询记录
     * @return AI咨询记录集合
     */
    public List<PsychologyAiCounseling> selectPsychologyAiCounselingList(PsychologyAiCounseling psychologyAiCounseling);

    /**
     * 新增AI咨询记录
     * 
     * @param psychologyAiCounseling AI咨询记录
     * @return 结果
     */
    public int insertPsychologyAiCounseling(PsychologyAiCounseling psychologyAiCounseling);

    /**
     * 修改AI咨询记录
     * 
     * @param psychologyAiCounseling AI咨询记录
     * @return 结果
     */
    public int updatePsychologyAiCounseling(PsychologyAiCounseling psychologyAiCounseling);

    /**
     * 删除AI咨询记录
     * 
     * @param recordId AI咨询记录主键
     * @return 结果
     */
    public int deletePsychologyAiCounselingByRecordId(Long recordId);

    /**
     * 批量删除AI咨询记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsychologyAiCounselingByRecordIds(Long[] recordIds);
}
