package com.healthmanager.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.healthmanager.common.utils.ZhipuModelUtil;
import com.healthmanager.manage.service.IZhipuModelService;
import org.springframework.stereotype.Service;

@Service
public class ZhipuModelServiceImpl implements IZhipuModelService {

    public String callZhipuModel(String input) {
        String s = ZhipuModelUtil.callZhipuModel(input);
        return JSON.toJSONString(s);
    }
}
