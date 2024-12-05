package com.healthmanager.manage.controller.medical;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.healthmanager.common.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.healthmanager.common.annotation.Log;
import com.healthmanager.common.core.controller.BaseController;
import com.healthmanager.common.core.domain.AjaxResult;
import com.healthmanager.common.enums.BusinessType;
import com.healthmanager.manage.domain.DoctorSchedule;
import com.healthmanager.manage.service.IDoctorScheduleService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;

/**
 * 医生排班Controller
 * 
 * @author ccc212
 * @date 2024-12-05
 */
@RestController
@RequestMapping("/manage/schedule")
@Api(tags = "医生排班相关接口")
public class DoctorScheduleController extends BaseController
{
    @Autowired
    private IDoctorScheduleService doctorScheduleService;

    @GetMapping("/getSchedule")
    @ApiOperation("获取医生排班信息")
    public AjaxResult getSchedule(@PathVariable("doctorId") Long doctorId, @RequestParam String date)
    {
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctorId(doctorId);
        schedule.setDate(DateUtils.parseDate(date));
        List<DoctorSchedule> list = doctorScheduleService.selectDoctorScheduleList(schedule);
        return success(list);
    }

    /**
     * 查询医生排班列表
     */
    @ApiOperation("查询医生排班列表")
    @GetMapping("/list")
    public TableDataInfo list(DoctorSchedule doctorSchedule)
    {
        startPage();
        List<DoctorSchedule> list = doctorScheduleService.selectDoctorScheduleList(doctorSchedule);
        return getDataTable(list);
    }

    /**
     * 导出医生排班列表
     */
    @ApiOperation("导出医生排班列表")
    @Log(title = "医生排班", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DoctorSchedule doctorSchedule)
    {
        List<DoctorSchedule> list = doctorScheduleService.selectDoctorScheduleList(doctorSchedule);
        ExcelUtil<DoctorSchedule> util = new ExcelUtil<DoctorSchedule>(DoctorSchedule.class);
        util.exportExcel(response, list, "医生排班数据");
    }

    /**
     * 获取医生排班详细信息
     */
    @ApiOperation("获取医生排班详细信息")
    @GetMapping(value = "/{scheduleId}")
    public AjaxResult getInfo(@PathVariable("scheduleId") Long scheduleId)
    {
        return success(doctorScheduleService.selectDoctorScheduleByScheduleId(scheduleId));
    }

    /**
     * 新增医生排班
     */
    @ApiOperation("新增医生排班")
    @Log(title = "医生排班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DoctorSchedule doctorSchedule)
    {
        return toAjax(doctorScheduleService.insertDoctorSchedule(doctorSchedule));
    }

    /**
     * 修改医生排班
     */
    @ApiOperation("修改医生排班")
    @Log(title = "医生排班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DoctorSchedule doctorSchedule)
    {
        return toAjax(doctorScheduleService.updateDoctorSchedule(doctorSchedule));
    }

    /**
     * 删除医生排班
     */
    @ApiOperation("删除医生排班")
    @Log(title = "医生排班", businessType = BusinessType.DELETE)
	@DeleteMapping("/{scheduleIds}")
    public AjaxResult remove(@PathVariable Long[] scheduleIds)
    {
        return toAjax(doctorScheduleService.deleteDoctorScheduleByScheduleIds(scheduleIds));
    }
}
