package com.healthmanager.manage.controller.exercise;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.healthmanager.manage.domain.dto.ExerciseSearchDTO;
import com.healthmanager.manage.domain.vo.ExerciseShowVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.healthmanager.common.annotation.Log;
import com.healthmanager.common.core.controller.BaseController;
import com.healthmanager.common.core.domain.AjaxResult;
import com.healthmanager.common.enums.BusinessType;
import com.healthmanager.manage.domain.Exercise;
import com.healthmanager.manage.service.IExerciseService;
import com.healthmanager.common.utils.poi.ExcelUtil;
import com.healthmanager.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 锻炼项目Controller
 * 
 * @author ccc212
 * @date 2024-11-05
 */
@Api(tags = "锻炼项目相关接口")
@RestController
@RequestMapping("/manage/exercise")
public class ExerciseController extends BaseController
{
    @Autowired
    private IExerciseService exerciseService;

    /**
     * 查询锻炼项目列表
     */
    @GetMapping("/getList")
    @ApiOperation(value = "获取锻炼项目列表")
    public AjaxResult getList(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              ExerciseSearchDTO exerciseSearchDTO) {
        // 计算分页的起始位置
        int offset = (pageNum - 1) * pageSize;
        List<ExerciseShowVO> list = exerciseService.selectExerciseShowList(exerciseSearchDTO, offset, pageSize);
        int total = exerciseService.countExerciseShowList(exerciseSearchDTO);
        return AjaxResult.success(new TableDataInfo(list, total));
    }

    /**
     * 查询锻炼项目列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取锻炼项目列表")
    public TableDataInfo list(Exercise exercise)
    {
        startPage();
        List<Exercise> list = exerciseService.selectExerciseList(exercise);
        return getDataTable(list);
    }

    /**
     * 导出锻炼项目列表
     */
    @Log(title = "锻炼项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出锻炼项目列表")
    public void export(HttpServletResponse response, Exercise exercise)
    {
        List<Exercise> list = exerciseService.selectExerciseList(exercise);
        ExcelUtil<Exercise> util = new ExcelUtil<Exercise>(Exercise.class);
        util.exportExcel(response, list, "锻炼项目数据");
    }

    /**
     * 获取锻炼项目详细信息
     */
    @GetMapping(value = "/{exerciseId}")
    @ApiOperation(value = "获取锻炼项目详细信息")
    public AjaxResult getInfo(@PathVariable("exerciseId") Long exerciseId)
    {
        return success(exerciseService.selectExerciseByExerciseId(exerciseId));
    }

    /**
     * 新增锻炼项目
     */
    @Log(title = "锻炼项目", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增锻炼项目")
    public AjaxResult add(@RequestBody Exercise exercise)
    {
        return toAjax(exerciseService.insertExercise(exercise));
    }

    /**
     * 修改锻炼项目
     */
    @Log(title = "锻炼项目", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改锻炼项目")
    public AjaxResult edit(@RequestBody Exercise exercise)
    {
        return toAjax(exerciseService.updateExercise(exercise));
    }

    /**
     * 删除锻炼项目
     */
    @Log(title = "锻炼项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{exerciseIds}")
    @ApiOperation(value = "删除锻炼项目")
    public AjaxResult remove(@PathVariable Long[] exerciseIds)
    {
        return toAjax(exerciseService.deleteExerciseByExerciseIds(exerciseIds));
    }
}
