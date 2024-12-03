package com.healthmanager.manage.controller.psychology;

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
import com.healthmanager.manage.domain.PsychologyQuestion;
import com.healthmanager.manage.service.IPsychologyQuestionService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 心理测试题目Controller
 * 
 * @author ccc212
 * @date 2024-11-21
 */
@Api(tags = "心理测试题目相关接口")
@RestController
@RequestMapping("/manage/question")
public class PsychologyQuestionController extends BaseController
{
    @Autowired
    private IPsychologyQuestionService psychologyQuestionService;

    /**
     * 查询心理测试题目列表
     */
    @ApiOperation("查询心理测试题目列表")
    @GetMapping("/list")
    public TableDataInfo list(PsychologyQuestion psychologyQuestion)
    {
        startPage();
        List<PsychologyQuestion> list = psychologyQuestionService.selectPsychologyQuestionList(psychologyQuestion);
        return getDataTable(list);
    }

    /**
     * 导出心理测试题目列表
     */
    @Log(title = "心理测试题目", businessType = BusinessType.EXPORT)
    @ApiOperation("导出心理测试题目列表")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsychologyQuestion psychologyQuestion)
    {
        List<PsychologyQuestion> list = psychologyQuestionService.selectPsychologyQuestionList(psychologyQuestion);
        ExcelUtil<PsychologyQuestion> util = new ExcelUtil<PsychologyQuestion>(PsychologyQuestion.class);
        util.exportExcel(response, list, "心理测试题目数据");
    }

    /**
     * 获取心理测试题目详细信息
     */
    @ApiOperation("获取心理测试题目详细信息")
    @GetMapping(value = "/{questionId}")
    public AjaxResult getInfo(@PathVariable("questionId") Long questionId)
    {
        return success(psychologyQuestionService.selectPsychologyQuestionByQuestionId(questionId));
    }

    /**
     * 新增心理测试题目
     */
    @Log(title = "心理测试题目", businessType = BusinessType.INSERT)
    @ApiOperation("新增心理测试题目")
    @PostMapping
    public AjaxResult add(@RequestBody PsychologyQuestion psychologyQuestion)
    {
        return toAjax(psychologyQuestionService.insertPsychologyQuestion(psychologyQuestion));
    }

    /**
     * 修改心理测试题目
     */
    @Log(title = "心理测试题目", businessType = BusinessType.UPDATE)
    @ApiOperation("修改心理测试题目")
    @PutMapping
    public AjaxResult edit(@RequestBody PsychologyQuestion psychologyQuestion)
    {
        return toAjax(psychologyQuestionService.updatePsychologyQuestion(psychologyQuestion));
    }

    /**
     * 删除心理测试题目
     */
    @Log(title = "心理测试题目", businessType = BusinessType.DELETE)
	@ApiOperation("删除心理测试题目")
	@DeleteMapping("/{questionIds}")
    public AjaxResult remove(@PathVariable Long[] questionIds)
    {
        return toAjax(psychologyQuestionService.deletePsychologyQuestionByQuestionIds(questionIds));
    }
}
