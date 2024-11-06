package com.healthmanager.manage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.healthmanager.manage.mapper.UserBodyDataMapper;
import com.healthmanager.manage.domain.UserBodyData;
import com.healthmanager.manage.service.IUserBodyDataService;

/**
 * 用户身体数据Service业务层处理
 * 
 * @author ccc212
 * @date 2024-11-06
 */
@Service
public class UserBodyDataServiceImpl implements IUserBodyDataService 
{
    @Autowired
    private UserBodyDataMapper userBodyDataMapper;

    /**
     * 查询用户身体数据
     * 
     * @param userId 用户身体数据主键
     * @return 用户身体数据
     */
    @Override
    public UserBodyData selectUserBodyDataByUserId(Long userId)
    {
        return userBodyDataMapper.selectUserBodyDataByUserId(userId);
    }

    /**
     * 查询用户身体数据列表
     * 
     * @param userBodyData 用户身体数据
     * @return 用户身体数据
     */
    @Override
    public List<UserBodyData> selectUserBodyDataList(UserBodyData userBodyData)
    {
        return userBodyDataMapper.selectUserBodyDataList(userBodyData);
    }

    /**
     * 新增用户身体数据
     * 
     * @param userBodyData 用户身体数据
     * @return 结果
     */
    @Override
    public int insertUserBodyData(UserBodyData userBodyData)
    {
        return userBodyDataMapper.insertUserBodyData(userBodyData);
    }

    /**
     * 修改用户身体数据
     * 
     * @param userBodyData 用户身体数据
     * @return 结果
     */
    @Override
    public int updateUserBodyData(UserBodyData userBodyData)
    {
        return userBodyDataMapper.updateUserBodyData(userBodyData);
    }

    /**
     * 批量删除用户身体数据
     * 
     * @param userIds 需要删除的用户身体数据主键
     * @return 结果
     */
    @Override
    public int deleteUserBodyDataByUserIds(Long[] userIds)
    {
        return userBodyDataMapper.deleteUserBodyDataByUserIds(userIds);
    }

    /**
     * 删除用户身体数据信息
     * 
     * @param userId 用户身体数据主键
     * @return 结果
     */
    @Override
    public int deleteUserBodyDataByUserId(Long userId)
    {
        return userBodyDataMapper.deleteUserBodyDataByUserId(userId);
    }
}
