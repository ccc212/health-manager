package com.healthmanager.manage.service;

import java.util.List;
import com.healthmanager.manage.domain.BodyPart;

/**
 * 锻炼部位Service接口
 * 
 * @author ccc212
 * @date 2024-11-06
 */
public interface IBodyPartService 
{
    /**
     * 查询锻炼部位
     * 
     * @param partId 锻炼部位主键
     * @return 锻炼部位
     */
    public BodyPart selectBodyPartByPartId(Long partId);

    /**
     * 查询锻炼部位列表
     * 
     * @param bodyPart 锻炼部位
     * @return 锻炼部位集合
     */
    public List<BodyPart> selectBodyPartList(BodyPart bodyPart);

    /**
     * 新增锻炼部位
     * 
     * @param bodyPart 锻炼部位
     * @return 结果
     */
    public int insertBodyPart(BodyPart bodyPart);

    /**
     * 修改锻炼部位
     * 
     * @param bodyPart 锻炼部位
     * @return 结果
     */
    public int updateBodyPart(BodyPart bodyPart);

    /**
     * 批量删除锻炼部位
     * 
     * @param partIds 需要删除的锻炼部位主键集合
     * @return 结果
     */
    public int deleteBodyPartByPartIds(Long[] partIds);

    /**
     * 删除锻炼部位信息
     * 
     * @param partId 锻炼部位主键
     * @return 结果
     */
    public int deleteBodyPartByPartId(Long partId);
}
