package com.healthmanager.manage.controller;

import com.healthmanager.common.core.controller.BaseController;
import com.healthmanager.manage.service.IZhipuModelService;
import com.healthmanager.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/zhipu")
@Api(tags = "智谱模型相关接口")
public class ZhipuModelController extends BaseController {

    @Autowired
    private IZhipuModelService zhipuModelService;

    @PostMapping("/process")
    @ApiOperation(value = "调用智谱模型")
    public AjaxResult processInput(@RequestBody String input) {
        String result = zhipuModelService.callZhipuModel(input);
        return success(result);
    }
}
