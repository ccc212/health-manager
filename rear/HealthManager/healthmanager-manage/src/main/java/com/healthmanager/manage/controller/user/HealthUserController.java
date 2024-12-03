package com.healthmanager.manage.controller.user;

import cn.hutool.core.bean.BeanUtil;
import com.healthmanager.common.annotation.Log;
import com.healthmanager.common.core.controller.BaseController;
import com.healthmanager.common.core.domain.AjaxResult;
import com.healthmanager.common.core.page.TableDataInfo;
import com.healthmanager.common.enums.BusinessType;
import com.healthmanager.common.utils.JwtUtil;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.framework.config.properties.JwtProperties;
import com.healthmanager.manage.domain.HealthUser;
import com.healthmanager.manage.domain.dto.UserLoginDTO;
import com.healthmanager.manage.domain.dto.UserRegisterDTO;
import com.healthmanager.manage.domain.vo.UserInfoVO;
import com.healthmanager.manage.domain.vo.UserLoginVO;
import com.healthmanager.manage.service.IHealthUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息Controller
 * 
 * @author ccc212
 * @date 2024-11-03
 */
@Api(tags = "用户管理相关接口")
@RestController
@RequestMapping("/manage/healthuser/user")
public class HealthUserController extends BaseController
{
    @Autowired
    private IHealthUserService healthUserService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "登录后会返回token，调用其他接口时需在请求头中携带该token")
    public AjaxResult login(@RequestBody UserLoginDTO userLoginDTO)
    {
        HealthUser healthUser = BeanUtil.copyProperties(userLoginDTO, HealthUser.class);
        HealthUser user = healthUserService.login(healthUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getUserId());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims
        );

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getUserId())
                .username(user.getUserName())
                .name(user.getName())
                .token(token)
                .lastLoginTime(user.getLastLoginTime())
                .build();

        return success(userLoginVO);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "用户名和邮箱是唯一的")
    public AjaxResult register(@RequestBody UserRegisterDTO userRegisterDTO)
    {
        healthUserService.register(userRegisterDTO);
        return success();
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    @ApiOperation(value = "用户登出")
    public AjaxResult logout(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        JwtUtil.invalidateToken(token);
        return success();
    }

    /**
     * 查询用户信息列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取用户信息列表")
    public TableDataInfo list(HealthUser healthUser)
    {
        startPage();
        List<HealthUser> list = healthUserService.selectHealthUserList(healthUser);
        return getDataTable(list);
    }

    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出用户信息列表")
    public void export(HttpServletResponse response, HealthUser healthUser)
    {
        List<HealthUser> list = healthUserService.selectHealthUserList(healthUser);
        ExcelUtil<HealthUser> util = new ExcelUtil<HealthUser>(HealthUser.class);
        util.exportExcel(response, list, "用户信息数据");
    }

    @GetMapping("/getInfo/{userId}")
    @ApiOperation(value = "获取用户信息详细信息（非若依）")
    public AjaxResult get(@PathVariable("userId") Long userId)
    {
        HealthUser healthUser = healthUserService.selectHealthUserByUserId(userId);
        UserInfoVO userInfoVO = UserInfoVO.builder()
                .email(healthUser.getEmail())
                .name(healthUser.getName())
                .phoneNumber(healthUser.getPhoneNumber())
                .birthDate(healthUser.getBirthDate())
                .avatar(healthUser.getAvatar())
                .status(healthUser.getStatus())
                .createTime(healthUser.getCreateTime())
                .username(healthUser.getUserName())
                .build();
        return success(userInfoVO);
    }

    /**
     * 获取用户信息详细信息
     */
    @GetMapping("/{userId}")
    @ApiOperation(value = "获取用户信息详细信息")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(healthUserService.selectHealthUserByUserId(userId));
    }

    /**
     * 新增用户信息
     */
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增用户信息（用户注册）", notes = "用户名和邮箱是唯一的")
    public AjaxResult add(@RequestBody HealthUser healthUser)
    {
        return toAjax(healthUserService.insertHealthUser(healthUser));
    }

    /**
     * 修改用户信息
     */
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改用户信息")
    public AjaxResult edit(@RequestBody HealthUser healthUser)
    {
        return toAjax(healthUserService.updateHealthUser(healthUser));
    }

    /**
     * 删除用户信息
     */
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    @ApiOperation(value = "删除用户信息")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(healthUserService.deleteHealthUserByUserIds(userIds));
    }
}
