package com.healthmanager.manage.service;

import java.util.List;
import com.healthmanager.manage.domain.FitnessRecord;

/**
 * 健身记录Service接口
 * 
 * @author ccc212
 * @date 2024-11-05
 */
public interface IFitnessRecordService 
{
    /**
     * 查询健身记录
     * 
     * @param recordId 健身记录主键
     * @return 健身记录
     */
    public FitnessRecord selectFitnessRecordByRecordId(Long recordId);

    /**
     * 查询健身记录列表
     * 
     * @param fitnessRecord 健身记录
     * @return 健身记录集合
     */
    public List<FitnessRecord> selectFitnessRecordList(FitnessRecord fitnessRecord);

    /**
     * 新增健身记录
     * 
     * @param fitnessRecord 健身记录
     * @return 结果
     */
    public int insertFitnessRecord(FitnessRecord fitnessRecord);

    /**
     * 修改健身记录
     * 
     * @param fitnessRecord 健身记录
     * @return 结果
     */
    public int updateFitnessRecord(FitnessRecord fitnessRecord);

    /**
     * 批量删除健身记录
     * 
     * @param recordIds 需要删除的健身记录主键集合
     * @return 结果
     */
    public int deleteFitnessRecordByRecordIds(Long[] recordIds);

    /**
     * 删除健身记录信息
     * 
     * @param recordId 健身记录主键
     * @return 结果
     */
    public int deleteFitnessRecordByRecordId(Long recordId);
}
