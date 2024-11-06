package com.healthmanager.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.healthmanager.common.annotation.Log;
import com.healthmanager.common.core.controller.BaseController;
import com.healthmanager.common.core.domain.AjaxResult;
import com.healthmanager.common.enums.BusinessType;
import com.healthmanager.manage.domain.UserBodyData;
import com.healthmanager.manage.service.IUserBodyDataService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;

/**
 * 用户身体数据Controller
 * 
 * @author ccc212
 * @date 2024-11-06
 */
@RestController
@RequestMapping("/manage/healthuser/data")
public class UserBodyDataController extends BaseController
{
    @Autowired
    private IUserBodyDataService userBodyDataService;

    /**
     * 查询用户身体数据列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserBodyData userBodyData)
    {
        startPage();
        List<UserBodyData> list = userBodyDataService.selectUserBodyDataList(userBodyData);
        return getDataTable(list);
    }

    /**
     * 导出用户身体数据列表
     */
    @Log(title = "用户身体数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserBodyData userBodyData)
    {
        List<UserBodyData> list = userBodyDataService.selectUserBodyDataList(userBodyData);
        ExcelUtil<UserBodyData> util = new ExcelUtil<UserBodyData>(UserBodyData.class);
        util.exportExcel(response, list, "用户身体数据数据");
    }

    /**
     * 获取用户身体数据详细信息
     */
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(userBodyDataService.selectUserBodyDataByUserId(userId));
    }

    /**
     * 新增用户身体数据
     */
    @Log(title = "用户身体数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserBodyData userBodyData)
    {
        return toAjax(userBodyDataService.insertUserBodyData(userBodyData));
    }

    /**
     * 修改用户身体数据
     */
    @Log(title = "用户身体数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserBodyData userBodyData)
    {
        return toAjax(userBodyDataService.updateUserBodyData(userBodyData));
    }

    /**
     * 删除用户身体数据
     */
    @Log(title = "用户身体数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(userBodyDataService.deleteUserBodyDataByUserIds(userIds));
    }
}
