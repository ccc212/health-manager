package com.healthmanager.manage.controller.medical;

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
import com.healthmanager.manage.domain.MedicalAppointment;
import com.healthmanager.manage.service.IMedicalAppointmentService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;

/**
 * 医疗预约Controller
 * 
 * @author ccc212
 * @date 2024-12-05
 */
@RestController
@RequestMapping("/manage/appointment")
@Api(tags = "医疗预约相关接口")
public class MedicalAppointmentController extends BaseController
{
    @Autowired
    private IMedicalAppointmentService medicalAppointmentService;

    /**
     * 查询医疗预约列表
     */
    @ApiOperation("查询医疗预约列表")
    @GetMapping("/list")
    public TableDataInfo list(MedicalAppointment medicalAppointment)
    {
        startPage();
        List<MedicalAppointment> list = medicalAppointmentService.selectMedicalAppointmentList(medicalAppointment);
        return getDataTable(list);
    }

    /**
     * 导出医疗预约列表
     */
    @ApiOperation("导出医疗预约列表")
    @Log(title = "医疗预约", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MedicalAppointment medicalAppointment)
    {
        List<MedicalAppointment> list = medicalAppointmentService.selectMedicalAppointmentList(medicalAppointment);
        ExcelUtil<MedicalAppointment> util = new ExcelUtil<MedicalAppointment>(MedicalAppointment.class);
        util.exportExcel(response, list, "医疗预约数据");
    }

    /**
     * 获取医疗预约详细信息
     */
    @ApiOperation("获取医疗预约详细信息")
    @GetMapping(value = "/{appointmentId}")
    public AjaxResult getInfo(@PathVariable("appointmentId") Long appointmentId)
    {
        return success(medicalAppointmentService.selectMedicalAppointmentByAppointmentId(appointmentId));
    }

    /**
     * 新增医疗预约
     */
    @ApiOperation("新增医疗预约")
    @Log(title = "医疗预约", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MedicalAppointment medicalAppointment)
    {
        return toAjax(medicalAppointmentService.insertMedicalAppointment(medicalAppointment));
    }

    /**
     * 修改医疗预约
     */
    @ApiOperation("修改医疗预约")
    @Log(title = "医疗预约", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MedicalAppointment medicalAppointment)
    {
        return toAjax(medicalAppointmentService.updateMedicalAppointment(medicalAppointment));
    }

    /**
     * 删除医疗预约
     */
    @ApiOperation("删除医疗预约")
    @Log(title = "医疗预约", businessType = BusinessType.DELETE)
	@DeleteMapping("/{appointmentIds}")
    public AjaxResult remove(@PathVariable Long[] appointmentIds)
    {
        return toAjax(medicalAppointmentService.deleteMedicalAppointmentByAppointmentIds(appointmentIds));
    }
}
