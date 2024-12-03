package com.healthmanager.manage.controller.psychology;

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
import com.healthmanager.manage.domain.PsychologyTest;
import com.healthmanager.manage.service.IPsychologyTestService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;

/**
 * 心理测试记录Controller
 * 
 * @author ccc212
 * @date 2024-11-21
 */
@Api(tags = "心理测试记录相关接口")
@RestController
@RequestMapping("/manage/test")
public class PsychologyTestController extends BaseController
{
    @Autowired
    private IPsychologyTestService psychologyTestService;

    /**
     * 查询心理测试记录列表
     */
    @ApiOperation(value = "查询心理测试记录列表")
    @GetMapping("/list")
    public TableDataInfo list(PsychologyTest psychologyTest)
    {
        startPage();
        List<PsychologyTest> list = psychologyTestService.selectPsychologyTestList(psychologyTest);
        return getDataTable(list);
    }

    /**
     * 导出心理测试记录列表
     */
    @ApiOperation(value = "导出心理测试记录列表")
    @Log(title = "心理测试记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsychologyTest psychologyTest)
    {
        List<PsychologyTest> list = psychologyTestService.selectPsychologyTestList(psychologyTest);
        ExcelUtil<PsychologyTest> util = new ExcelUtil<PsychologyTest>(PsychologyTest.class);
        util.exportExcel(response, list, "心理测试记录数据");
    }

    /**
     * 获取心理测试记录详细信息
     */
    @ApiOperation(value = "获取心理测试记录详细信息")
    @GetMapping(value = "/{testId}")
    public AjaxResult getInfo(@PathVariable("testId") Long testId)
    {
        return success(psychologyTestService.selectPsychologyTestByTestId(testId));
    }

    /**
     * 新增心理测试记录
     */
    @ApiOperation(value = "新增心理测试记录")
    @Log(title = "心理测试记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsychologyTest psychologyTest)
    {
        return toAjax(psychologyTestService.insertPsychologyTest(psychologyTest));
    }

    /**
     * 修改心理测试记录
     */
    @ApiOperation(value = "修改心理测试记录")
    @Log(title = "心理测试记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsychologyTest psychologyTest)
    {
        return toAjax(psychologyTestService.updatePsychologyTest(psychologyTest));
    }

    /**
     * 删除心理测试记录
     */
    @ApiOperation(value = "删除心理测试记录")
    @Log(title = "心理测试记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{testIds}")
    public AjaxResult remove(@PathVariable Long[] testIds)
    {
        return toAjax(psychologyTestService.deletePsychologyTestByTestIds(testIds));
    }
}
