package com.xinyunlian.jinfu.system.controller;

import com.xinyunlian.jinfu.api.dto.ApiBaiduDto;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.dto.SysAreaInfSearchDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.wechat.dto.JsApiDto;
import com.xinyunlian.jinfu.wechat.service.ApiWeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by menglei on 2017/1/5.
 */
@Controller
@RequestMapping(value = "system")
public class SystemController {

    @Autowired
    private ApiWeChatService apiWeChatService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private ApiBaiduService apiBaiduService;

    /**
     * 获取微信js api 参数
     *
     * @return
     */
    @RequestMapping(value = "/getWeChatJsApi", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getWeChatJsApi(@RequestParam String url) {
        JsApiDto jsApiDto = apiWeChatService.getJsApi(url);
        return ResultDtoFactory.toAck("获取成功", jsApiDto);
    }

    /**
     * 查询地区列表
     *
     * @param sysAreaInfSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/area/list", method = RequestMethod.POST)
    public ResultDto<List<SysAreaInfDto>> getSysAreaList(@RequestBody SysAreaInfSearchDto sysAreaInfSearchDto) {
        List<SysAreaInfDto> list = sysAreaInfService.getSysAreaInfList(sysAreaInfSearchDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 根据百度坐标获取省市区
     *
     * @param location
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBaiduArea", method = RequestMethod.GET)
    public ResultDto<Object> getBaiduArea(@RequestParam String location) {
        ApiBaiduDto apiBaiduDto = apiBaiduService.getArea(location);
        return ResultDtoFactory.toAck("获取成功", apiBaiduDto);
    }

}
