package com.healthmanager.manage.controller.psychology;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.bean.BeanUtil;
import com.healthmanager.manage.domain.dto.PsychologyAiQueryDTO;
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
import com.healthmanager.manage.domain.PsychologyAiCounseling;
import com.healthmanager.manage.service.IPsychologyAiCounselingService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * AI咨询记录Controller
 * 
 * @author ccc212
 * @date 2024-11-21
 */
@Api(tags = "AI咨询记录相关接口")
@RestController
@RequestMapping("/manage/counseling")
public class PsychologyAiCounselingController extends BaseController
{
    @Autowired
    private IPsychologyAiCounselingService psychologyAiCounselingService;

    /**
     * AI心理咨询
     */
    @Log(title = "AI咨询记录", businessType = BusinessType.INSERT)
    @ApiOperation("AI心理咨询")
    @PostMapping("/getAnswer")
    public AjaxResult getPsychologyCounselingAnswer(@RequestBody PsychologyAiQueryDTO psychologyAiQueryDTO)
    {
        return success(psychologyAiCounselingService.getPsychologyCounselingAnswer(psychologyAiQueryDTO));
    }

    /**
     * 查询AI咨询记录列表
     */
    @ApiOperation("查询AI咨询记录列表")
    @GetMapping("/list")
    public TableDataInfo list(PsychologyAiCounseling psychologyAiCounseling)
    {
        startPage();
        List<PsychologyAiCounseling> list = psychologyAiCounselingService.selectPsychologyAiCounselingList(psychologyAiCounseling);
        return getDataTable(list);
    }

    /**
     * 导出AI咨询记录列表
     */
    @Log(title = "AI咨询记录", businessType = BusinessType.EXPORT)
    @ApiOperation("导出AI咨询记录列表")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsychologyAiCounseling psychologyAiCounseling)
    {
        List<PsychologyAiCounseling> list = psychologyAiCounselingService.selectPsychologyAiCounselingList(psychologyAiCounseling);
        ExcelUtil<PsychologyAiCounseling> util = new ExcelUtil<PsychologyAiCounseling>(PsychologyAiCounseling.class);
        util.exportExcel(response, list, "AI咨询记录数据");
    }

    /**
     * 获取AI咨询记录详细信息
     */
    @ApiOperation("获取AI咨询记录详细信息")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(psychologyAiCounselingService.selectPsychologyAiCounselingByRecordId(recordId));
    }

    /**
     * 新增AI咨询记录
     */
    @Log(title = "AI咨询记录", businessType = BusinessType.INSERT)
    @ApiOperation("新增AI咨询记录")
    @PostMapping
    public AjaxResult add(@RequestBody PsychologyAiCounseling psychologyAiCounseling)
    {
        return toAjax(psychologyAiCounselingService.insertPsychologyAiCounseling(psychologyAiCounseling));
    }

    /**
     * 修改AI咨询记录
     */
    @Log(title = "AI咨询记录", businessType = BusinessType.UPDATE)
    @ApiOperation("修改AI咨询记录")
    @PutMapping
    public AjaxResult edit(@RequestBody PsychologyAiCounseling psychologyAiCounseling)
    {
        return toAjax(psychologyAiCounselingService.updatePsychologyAiCounseling(psychologyAiCounseling));
    }

    /**
     * 删除AI咨询记录
     */
    @Log(title = "AI咨询记录", businessType = BusinessType.DELETE)
	@ApiOperation("删除AI咨询记录")
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(psychologyAiCounselingService.deletePsychologyAiCounselingByRecordIds(recordIds));
    }
}
