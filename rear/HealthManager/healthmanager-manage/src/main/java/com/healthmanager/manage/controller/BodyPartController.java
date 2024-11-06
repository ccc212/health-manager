package com.healthmanager.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import com.healthmanager.common.annotation.Log;
import com.healthmanager.common.core.controller.BaseController;
import com.healthmanager.common.core.domain.AjaxResult;
import com.healthmanager.common.enums.BusinessType;
import com.healthmanager.manage.domain.BodyPart;
import com.healthmanager.manage.service.IBodyPartService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;

/**
 * 锻炼部位Controller
 * 
 * @author ccc212
 * @date 2024-11-06
 */
@Api(tags = "锻炼部位相关接口")
@RestController
@RequestMapping("/manage/part")
public class BodyPartController extends BaseController
{
    @Autowired
    private IBodyPartService bodyPartService;

    @ApiOperation("查询锻炼部位列表")
    @GetMapping("/list")
    public TableDataInfo list(BodyPart bodyPart)
    {
        startPage();
        List<BodyPart> list = bodyPartService.selectBodyPartList(bodyPart);
        return getDataTable(list);
    }

    @ApiOperation("导出锻炼部位列表")
    @Log(title = "锻炼部位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BodyPart bodyPart)
    {
        List<BodyPart> list = bodyPartService.selectBodyPartList(bodyPart);
        ExcelUtil<BodyPart> util = new ExcelUtil<BodyPart>(BodyPart.class);
        util.exportExcel(response, list, "锻炼部位数据");
    }

    @ApiOperation("获取锻炼部位详细信息")
    @GetMapping(value = "/{partId}")
    public AjaxResult getInfo(@PathVariable("partId") Long partId)
    {
        return success(bodyPartService.selectBodyPartByPartId(partId));
    }

    @ApiOperation("新增锻炼部位")
    @Log(title = "锻炼部位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BodyPart bodyPart)
    {
        return toAjax(bodyPartService.insertBodyPart(bodyPart));
    }

    @ApiOperation("修改锻炼部位")
    @Log(title = "锻炼部位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BodyPart bodyPart)
    {
        return toAjax(bodyPartService.updateBodyPart(bodyPart));
    }

    @ApiOperation("删除锻炼部位")
    @Log(title = "锻炼部位", businessType = BusinessType.DELETE)
    @DeleteMapping("/{partIds}")
    public AjaxResult remove(@PathVariable Long[] partIds)
    {
        return toAjax(bodyPartService.deleteBodyPartByPartIds(partIds));
    }
}
