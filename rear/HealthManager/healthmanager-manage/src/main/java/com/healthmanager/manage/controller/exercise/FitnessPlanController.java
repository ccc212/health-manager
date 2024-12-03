package com.healthmanager.manage.controller.exercise;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.healthmanager.common.annotation.Log;
import com.healthmanager.common.core.controller.BaseController;
import com.healthmanager.common.core.domain.AjaxResult;
import com.healthmanager.common.enums.BusinessType;
import com.healthmanager.manage.domain.FitnessPlan;
import com.healthmanager.manage.service.IFitnessPlanService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;

/**
 * 健身计划Controller
 * 
 * @author ccc212
 * @date 2024-11-06
 */
@Api(tags = "健身计划相关接口")
@RestController
@RequestMapping("/manage/plan")
@Validated
public class FitnessPlanController extends BaseController
{
    @Autowired
    private IFitnessPlanService fitnessPlanService;

    @ApiOperation("生成健身计划详情")
    @GetMapping("/generate/{planId}")
    public AjaxResult generate(@PathVariable("planId") @NotNull Long planId)
    {
        fitnessPlanService.generate(planId);
        return success();
    }

    @ApiOperation("查询健身计划列表")
    @GetMapping("/list")
    public TableDataInfo list(FitnessPlan fitnessPlan)
    {
        startPage();
        List<FitnessPlan> list = fitnessPlanService.selectFitnessPlanList(fitnessPlan);
        return getDataTable(list);
    }

    @ApiOperation("导出健身计划列表")
    @Log(title = "健身计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FitnessPlan fitnessPlan)
    {
        List<FitnessPlan> list = fitnessPlanService.selectFitnessPlanList(fitnessPlan);
        ExcelUtil<FitnessPlan> util = new ExcelUtil<FitnessPlan>(FitnessPlan.class);
        util.exportExcel(response, list, "健身计划数据");
    }

    @ApiOperation("获取健身计划详细信息")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return success(fitnessPlanService.selectFitnessPlanByPlanId(planId));
    }

    @ApiOperation("新增健身计划")
    @Log(title = "健身计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FitnessPlan fitnessPlan)
    {
        return toAjax(fitnessPlanService.insertFitnessPlan(fitnessPlan));
    }

    @ApiOperation("修改健身计划")
    @Log(title = "健身计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FitnessPlan fitnessPlan)
    {
        return toAjax(fitnessPlanService.updateFitnessPlan(fitnessPlan));
    }

    @ApiOperation("删除健身计划")
    @Log(title = "健身计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        return toAjax(fitnessPlanService.deleteFitnessPlanByPlanIds(planIds));
    }
}
