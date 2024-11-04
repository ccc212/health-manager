package com.healthmanager.manage.service;

import java.util.List;


import com.healthmanager.manage.domain.HealthUser;
import com.healthmanager.manage.domain.dto.UserRegisterDTO;

/**
 * 用户信息Service接口
 * 
 * @author ccc212
 * @date 2024-11-03
 */
public interface IHealthUserService
{
    /**
     * 登录
     * @param healthUser
     * @return 用户信息
     */
    HealthUser login(HealthUser healthUser);

    /**
     * 查询用户信息
     *
     * @param userId 用户信息主键
     * @return 用户信息
     */
    public HealthUser selectHealthUserByUserId(Long userId);

    /**
     * 查询用户信息列表
     *
     * @param healthUser 用户信息
     * @return 用户信息集合
     */
    public List<HealthUser> selectHealthUserList(HealthUser healthUser);

    /**
     * 新增用户信息
     *
     * @param healthUser 用户信息
     * @return 结果
     */
    public int insertHealthUser(HealthUser healthUser);

    /**
     * 修改用户信息
     *
     * @param healthUser 用户信息
     * @return 结果
     */
    public int updateHealthUser(HealthUser healthUser);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户信息主键集合
     * @return 结果
     */
    public int deleteHealthUserByUserIds(Long[] userIds);

    /**
     * 删除用户信息信息
     *
     * @param userId 用户信息主键
     * @return 结果
     */
    public int deleteHealthUserByUserId(Long userId);

    void register(UserRegisterDTO userRegisterDTO);
}
