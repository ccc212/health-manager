package com.healthmanager.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.healthmanager.manage.domain.FitnessRecord;
import com.healthmanager.manage.service.IFitnessRecordService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;

/**
 * 健身记录Controller
 *
 * @author ccc212
 * @date 2024-11-05
 */
@Api(tags = "健身记录相关接口")
@RestController
@RequestMapping("/manage/record")
public class FitnessRecordController extends BaseController {

    @Autowired
    private IFitnessRecordService fitnessRecordService;

    /**
     * 查询健身记录列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取健身记录列表")
    public TableDataInfo list(FitnessRecord fitnessRecord) {
        startPage();
        List<FitnessRecord> list = fitnessRecordService.selectFitnessRecordList(fitnessRecord);
        return getDataTable(list);
    }

    /**
     * 导出健身记录列表
     */
    @Log(title = "健身记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出健身记录列表")
    public void export(HttpServletResponse response, FitnessRecord fitnessRecord) {
        List<FitnessRecord> list = fitnessRecordService.selectFitnessRecordList(fitnessRecord);
        ExcelUtil<FitnessRecord> util = new ExcelUtil<FitnessRecord>(FitnessRecord.class);
        util.exportExcel(response, list, "健身记录数据");
    }

    /**
     * 获取健身记录详细信息
     */
    @GetMapping(value = "/{recordId}")
    @ApiOperation(value = "获取健身记录详细信息")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId) {
        return success(fitnessRecordService.selectFitnessRecordByRecordId(recordId));
    }

    /**
     * 新增健身记录
     */
    @Log(title = "健身记录", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增健身记录")
    public AjaxResult add(@RequestBody FitnessRecord fitnessRecord) {
        return toAjax(fitnessRecordService.insertFitnessRecord(fitnessRecord));
    }

    /**
     * 修改健身记录
     */
    @Log(title = "健身记录", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改健身记录")
    public AjaxResult edit(@RequestBody FitnessRecord fitnessRecord) {
        return toAjax(fitnessRecordService.updateFitnessRecord(fitnessRecord));
    }

    /**
     * 删除健身记录
     */
    @Log(title = "健身记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    @ApiOperation(value = "删除健身记录")
    public AjaxResult remove(@PathVariable Long[] recordIds) {
        return toAjax(fitnessRecordService.deleteFitnessRecordByRecordIds(recordIds));
    }
}
