package com.healthmanager.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthmanager.common.exception.user.UserException;
import com.healthmanager.common.utils.DateUtils;
import com.healthmanager.manage.domain.HealthUser;
import com.healthmanager.manage.domain.dto.UserRegisterDTO;
import com.healthmanager.manage.mapper.HealthUserMapper;
import com.healthmanager.manage.service.IHealthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息Service业务层处理
 *
 * @author ccc212
 * @date 2024-11-03
 */
@Service
public class HealthUserServiceImpl extends ServiceImpl<HealthUserMapper, HealthUser> implements IHealthUserService
{
    @Autowired
    private HealthUserMapper healthUserMapper;

    @Override
    public HealthUser login(HealthUser healthUser) {
        String username = healthUser.getUserName();
        String password = healthUser.getPassword();

        HealthUser user;
        try {
            user = healthUserMapper.selectOne(new LambdaQueryWrapper<HealthUser>().eq(HealthUser::getUserName, username));
        } catch (Exception e) {
            throw new UserException("user.login.failed", null);
        }

        if (user == null) {
            throw new UserException("user.not.exists", null);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            throw new UserException("user.password.not.match", null);
        }
        healthUserMapper.update(new LambdaUpdateWrapper<HealthUser>()
                .eq(HealthUser::getUserName, username)
                .set(HealthUser::getLastLoginTime, LocalDateTime.now()));

        return user;
    }

    /**
     * 查询用户信息
     *
     * @param userId 用户信息主键
     * @return 用户信息
     */
    @Override
    public HealthUser selectHealthUserByUserId(Long userId)
    {
        return healthUserMapper.selectHealthUserByUserId(userId);
    }

    /**
     * 查询用户信息列表
     *
     * @param healthUser 用户信息
     * @return 用户信息
     */
    @Override
    public List<HealthUser> selectHealthUserList(HealthUser healthUser)
    {
        return healthUserMapper.selectHealthUserList(healthUser);
    }

    /**
     * 新增用户信息
     *
     * @param healthUser 用户信息
     * @return 结果
     */
    @Override
    public int insertHealthUser(HealthUser healthUser)
    {
        String username = healthUser.getUserName();
        HealthUser existUser = lambdaQuery().eq(HealthUser::getUserName, username).one();
        if (existUser != null) {
            throw new UserException("user.username.exist", null);
        }

        String email = healthUser.getEmail();
        existUser = lambdaQuery().eq(HealthUser::getEmail, email).one();
        if (existUser != null) {
            throw new UserException("user.email.exist", null);
        }

        healthUser.setPassword(DigestUtils.md5DigestAsHex(healthUser.getPassword().getBytes()));
        healthUser.setCreateTime(DateUtils.getNowDate());
        return healthUserMapper.insertHealthUser(healthUser);
    }

    /**
     * 修改用户信息
     *
     * @param healthUser 用户信息
     * @return 结果
     */
    @Override
    public int updateHealthUser(HealthUser healthUser)
    {
        healthUser.setUpdateTime(DateUtils.getNowDate());
        return healthUserMapper.updateHealthUser(healthUser);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteHealthUserByUserIds(Long[] userIds)
    {
        return healthUserMapper.deleteHealthUserByUserIds(userIds);
    }

    /**
     * 删除用户信息信息
     *
     * @param userId 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteHealthUserByUserId(Long userId)
    {
        return healthUserMapper.deleteHealthUserByUserId(userId);
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        HealthUser existUser = lambdaQuery().eq(HealthUser::getUserName, username).one();
        if (existUser != null) {
            throw new UserException("user.username.exist", null);
        }

        String email = userRegisterDTO.getEmail();
        existUser = lambdaQuery().eq(HealthUser::getEmail, email).one();
        if (existUser != null) {
            throw new UserException("user.email.exist", null);
        }

        HealthUser user = BeanUtil.copyProperties(userRegisterDTO, HealthUser.class);
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setCreateTime(DateUtils.getNowDate());
        user.setCreateTime(DateUtils.getNowDate());

        if (user.getName() == null) {
            user.setName(username);
        }
        save(user);
    }
}
