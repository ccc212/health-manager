package com.healthmanager.manage.controller.exercise;

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
import com.healthmanager.manage.domain.FitnessPlanDetail;
import com.healthmanager.manage.service.IFitnessPlanDetailService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 健身计划详情Controller
 * 
 * @author ccc212
 * @date 2024-11-05
 */
@Api(tags = "健身计划详情相关接口")
@RestController
@RequestMapping("/manage/detail")
public class FitnessPlanDetailController extends BaseController
{
    @Autowired
    private IFitnessPlanDetailService fitnessPlanDetailService;

    /**
     * 查询健身计划详情列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取健身计划详情列表")
    public TableDataInfo list(FitnessPlanDetail fitnessPlanDetail)
    {
        startPage();
        List<FitnessPlanDetail> list = fitnessPlanDetailService.selectFitnessPlanDetailList(fitnessPlanDetail);
        return getDataTable(list);
    }

    /**
     * 导出健身计划详情列表
     */
    @Log(title = "健身计划详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出健身计划详情列表")
    public void export(HttpServletResponse response, FitnessPlanDetail fitnessPlanDetail)
    {
        List<FitnessPlanDetail> list = fitnessPlanDetailService.selectFitnessPlanDetailList(fitnessPlanDetail);
        ExcelUtil<FitnessPlanDetail> util = new ExcelUtil<FitnessPlanDetail>(FitnessPlanDetail.class);
        util.exportExcel(response, list, "健身计划详情数据");
    }

    /**
     * 获取健身计划详情详细信息
     */
    @GetMapping(value = "/{detailId}")
    @ApiOperation(value = "获取健身计划详情详细信息")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return success(fitnessPlanDetailService.selectFitnessPlanDetailByDetailId(detailId));
    }

    /**
     * 新增健身计划详情
     */
    @Log(title = "健身计划详情", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增健身计划详情")
    public AjaxResult add(@RequestBody FitnessPlanDetail fitnessPlanDetail)
    {
        return toAjax(fitnessPlanDetailService.insertFitnessPlanDetail(fitnessPlanDetail));
    }

    /**
     * 修改健身计划详情
     */
    @Log(title = "健身计划详情", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改健身计划详情")
    public AjaxResult edit(@RequestBody FitnessPlanDetail fitnessPlanDetail)
    {
        return toAjax(fitnessPlanDetailService.updateFitnessPlanDetail(fitnessPlanDetail));
    }

    /**
     * 删除健身计划详情
     */
    @Log(title = "健身计划详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    @ApiOperation(value = "删除健身计划详情")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(fitnessPlanDetailService.deleteFitnessPlanDetailByDetailIds(detailIds));
    }
}
