package com.healthmanager.manage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.BodyPartMapper;
import com.healthmanager.manage.domain.BodyPart;
import com.healthmanager.manage.service.IBodyPartService;

/**
 * 锻炼部位Service业务层处理
 * 
 * @author ccc212
 * @date 2024-11-06
 */
@Service
public class BodyPartServiceImpl implements IBodyPartService 
{
    @Autowired
    private BodyPartMapper bodyPartMapper;

    /**
     * 查询锻炼部位
     * 
     * @param partId 锻炼部位主键
     * @return 锻炼部位
     */
    @Override
    public BodyPart selectBodyPartByPartId(Long partId)
    {
        return bodyPartMapper.selectBodyPartByPartId(partId);
    }

    /**
     * 查询锻炼部位列表
     * 
     * @param bodyPart 锻炼部位
     * @return 锻炼部位
     */
    @Override
    public List<BodyPart> selectBodyPartList(BodyPart bodyPart)
    {
        return bodyPartMapper.selectBodyPartList(bodyPart);
    }

    /**
     * 新增锻炼部位
     * 
     * @param bodyPart 锻炼部位
     * @return 结果
     */
    @Override
    public int insertBodyPart(BodyPart bodyPart)
    {
        return bodyPartMapper.insertBodyPart(bodyPart);
    }

    /**
     * 修改锻炼部位
     * 
     * @param bodyPart 锻炼部位
     * @return 结果
     */
    @Override
    public int updateBodyPart(BodyPart bodyPart)
    {
        return bodyPartMapper.updateBodyPart(bodyPart);
    }

    /**
     * 批量删除锻炼部位
     * 
     * @param partIds 需要删除的锻炼部位主键
     * @return 结果
     */
    @Override
    public int deleteBodyPartByPartIds(Long[] partIds)
    {
        return bodyPartMapper.deleteBodyPartByPartIds(partIds);
    }

    /**
     * 删除锻炼部位信息
     * 
     * @param partId 锻炼部位主键
     * @return 结果
     */
    @Override
    public int deleteBodyPartByPartId(Long partId)
    {
        return bodyPartMapper.deleteBodyPartByPartId(partId);
    }
}
