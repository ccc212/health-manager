package com.healthmanager.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthmanager.manage.domain.UserBodyData;

/**
 * 用户身体数据Mapper接口
 * 
 * @author ccc212
 * @date 2024-11-06
 */
public interface UserBodyDataMapper extends BaseMapper<UserBodyData>
{
    /**
     * 查询用户身体数据
     * 
     * @param userId 用户身体数据主键
     * @return 用户身体数据
     */
    public UserBodyData selectUserBodyDataByUserId(Long userId);

    /**
     * 查询用户身体数据列表
     * 
     * @param userBodyData 用户身体数据
     * @return 用户身体数据集合
     */
    public List<UserBodyData> selectUserBodyDataList(UserBodyData userBodyData);

    /**
     * 新增用户身体数据
     * 
     * @param userBodyData 用户身体数据
     * @return 结果
     */
    public int insertUserBodyData(UserBodyData userBodyData);

    /**
     * 修改用户身体数据
     * 
     * @param userBodyData 用户身体数据
     * @return 结果
     */
    public int updateUserBodyData(UserBodyData userBodyData);

    /**
     * 删除用户身体数据
     * 
     * @param userId 用户身体数据主键
     * @return 结果
     */
    public int deleteUserBodyDataByUserId(Long userId);

    /**
     * 批量删除用户身体数据
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserBodyDataByUserIds(Long[] userIds);
}
