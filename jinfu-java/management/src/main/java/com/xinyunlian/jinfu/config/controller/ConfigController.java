package com.xinyunlian.jinfu.config.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.config.dto.ConfigDto;
import com.xinyunlian.jinfu.config.enums.ECategory;
import com.xinyunlian.jinfu.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by bright on 2017/1/6.
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultDto saveConfig(@RequestBody ConfigDto configDto){
        configService.save(configDto);
        return ResultDtoFactory.toAck("操作成功！");
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto list(@RequestParam  ECategory category){
        List<ConfigDto> configDtos = configService.getByCategory(category);
        return ResultDtoFactory.toAck("操作成功！", configDtos);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultDto delete(@RequestParam  Long id){
        configService.delete(id);
        return ResultDtoFactory.toAck("操作成功！");
    }

    @ResponseBody
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResultDto refresh(){
        configService.refreshCache();
        return ResultDtoFactory.toAck("操作成功！");
    }
}
