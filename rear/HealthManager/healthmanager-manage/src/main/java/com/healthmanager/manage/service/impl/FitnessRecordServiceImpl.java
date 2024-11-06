package com.healthmanager.manage.service.impl;

import java.util.List;
import com.healthmanager.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.FitnessRecordMapper;
import com.healthmanager.manage.domain.FitnessRecord;
import com.healthmanager.manage.service.IFitnessRecordService;

/**
 * 健身记录Service业务层处理
 * 
 * @author ccc212
 * @date 2024-11-05
 */
@Service
public class FitnessRecordServiceImpl implements IFitnessRecordService 
{
    @Autowired
    private FitnessRecordMapper fitnessRecordMapper;

    /**
     * 查询健身记录
     * 
     * @param recordId 健身记录主键
     * @return 健身记录
     */
    @Override
    public FitnessRecord selectFitnessRecordByRecordId(Long recordId)
    {
        return fitnessRecordMapper.selectFitnessRecordByRecordId(recordId);
    }

    /**
     * 查询健身记录列表
     * 
     * @param fitnessRecord 健身记录
     * @return 健身记录
     */
    @Override
    public List<FitnessRecord> selectFitnessRecordList(FitnessRecord fitnessRecord)
    {
        return fitnessRecordMapper.selectFitnessRecordList(fitnessRecord);
    }

    /**
     * 新增健身记录
     * 
     * @param fitnessRecord 健身记录
     * @return 结果
     */
    @Override
    public int insertFitnessRecord(FitnessRecord fitnessRecord)
    {
        fitnessRecord.setCreateTime(DateUtils.getNowDate());
        return fitnessRecordMapper.insertFitnessRecord(fitnessRecord);
    }

    /**
     * 修改健身记录
     * 
     * @param fitnessRecord 健身记录
     * @return 结果
     */
    @Override
    public int updateFitnessRecord(FitnessRecord fitnessRecord)
    {
        fitnessRecord.setUpdateTime(DateUtils.getNowDate());
        return fitnessRecordMapper.updateFitnessRecord(fitnessRecord);
    }

    /**
     * 批量删除健身记录
     * 
     * @param recordIds 需要删除的健身记录主键
     * @return 结果
     */
    @Override
    public int deleteFitnessRecordByRecordIds(Long[] recordIds)
    {
        return fitnessRecordMapper.deleteFitnessRecordByRecordIds(recordIds);
    }

    /**
     * 删除健身记录信息
     * 
     * @param recordId 健身记录主键
     * @return 结果
     */
    @Override
    public int deleteFitnessRecordByRecordId(Long recordId)
    {
        return fitnessRecordMapper.deleteFitnessRecordByRecordId(recordId);
    }
}
