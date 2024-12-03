package com.healthmanager.manage.controller.psychology;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.healthmanager.manage.domain.vo.PsychologyOptionVO;
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
import com.healthmanager.manage.domain.PsychologyOption;
import com.healthmanager.manage.service.IPsychologyOptionService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 心理测试选项Controller
 * 
 * @author ccc212
 * @date 2024-11-21
 */
@Api(tags = "心理测试选项相关接口")
@RestController
@RequestMapping("/manage/option")
public class PsychologyOptionController extends BaseController
{
    @Autowired
    private IPsychologyOptionService psychologyOptionService;

    /**
     * 查询心理测试选项列表
     */
    @ApiOperation("查询心理测试选项列表")
    @GetMapping("/list")
    public TableDataInfo list(PsychologyOption psychologyOption)
    {
        startPage();
        List<PsychologyOptionVO> list = psychologyOptionService.selectPsychologyOptionVOList(psychologyOption);
        return getDataTable(list);
    }

    /**
     * 导出心理测试选项列表
     */
    @Log(title = "心理测试选项", businessType = BusinessType.EXPORT)
    @ApiOperation("导出心理测试选项列表")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsychologyOption psychologyOption)
    {
        List<PsychologyOption> list = psychologyOptionService.selectPsychologyOptionList(psychologyOption);
        ExcelUtil<PsychologyOption> util = new ExcelUtil<PsychologyOption>(PsychologyOption.class);
        util.exportExcel(response, list, "心理测试选项数据");
    }

    /**
     * 获取心理测试选项详细信息
     */
    @ApiOperation("获取心理测试选项详细信息")
    @GetMapping(value = "/{optionId}")
    public AjaxResult getInfo(@PathVariable("optionId") Long optionId)
    {
        return success(psychologyOptionService.selectPsychologyOptionByOptionId(optionId));
    }

    /**
     * 新增心理测试选项
     */
    @Log(title = "心理测试选项", businessType = BusinessType.INSERT)
    @ApiOperation("新增心理测试选项")
    @PostMapping
    public AjaxResult add(@RequestBody PsychologyOption psychologyOption)
    {
        return toAjax(psychologyOptionService.insertPsychologyOption(psychologyOption));
    }

    /**
     * 修改心理测试选项
     */
    @Log(title = "心理测试选项", businessType = BusinessType.UPDATE)
    @ApiOperation("修改心理测试选项")
    @PutMapping
    public AjaxResult edit(@RequestBody PsychologyOption psychologyOption)
    {
        return toAjax(psychologyOptionService.updatePsychologyOption(psychologyOption));
    }

    /**
     * 删除心理测试选项
     */
    @Log(title = "心理测试选项", businessType = BusinessType.DELETE)
	@ApiOperation("删除心理测试选项")
	@DeleteMapping("/{optionIds}")
    public AjaxResult remove(@PathVariable Long[] optionIds)
    {
        return toAjax(psychologyOptionService.deletePsychologyOptionByOptionIds(optionIds));
    }
}
