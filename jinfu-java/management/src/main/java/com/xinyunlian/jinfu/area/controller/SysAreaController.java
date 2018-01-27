package com.xinyunlian.jinfu.area.controller;

import com.xinyunlian.jinfu.area.dto.SysAreaInfWebDto;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.dto.SysAreaInfSearchDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by DongFC on 2016-08-24.
 */
@RestController
@RequestMapping("sysArea")
public class SysAreaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysAreaController.class);

    @Autowired
    private SysAreaInfService sysAreaInfService;

    /**
     * 查询地区列表
     * @param sysAreaInfSearchDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<List<SysAreaInfDto>> getSysAreaList(SysAreaInfSearchDto sysAreaInfSearchDto){
        List<SysAreaInfDto> list = sysAreaInfService.getSysAreaInfList(sysAreaInfSearchDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 新增地区
     * @param sysAreaInfWebDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDto<String> addSysArea(@RequestBody @Valid SysAreaInfWebDto sysAreaInfWebDto, BindingResult result){
        if (result.hasErrors()){
            return ResultDtoFactory.toNack("地区必填字段为空");
        }
        SysAreaInfDto dto = ConverterService.convert(sysAreaInfWebDto, SysAreaInfDto.class);
        sysAreaInfService.createSysAreaInf(dto);
        return ResultDtoFactory.toAck("新增地区成功");
    }

    /**
     * 根据id获取地区
     * @param areaId
     * @return
     */
    @RequestMapping(value = "/{areaId}", method = RequestMethod.GET)
    public ResultDto<SysAreaInfDto> getSysAreaById(@PathVariable Long areaId){
        SysAreaInfDto dto = sysAreaInfService.getSysAreaInfById(areaId);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 更新地区
     * @param sysAreaInfDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDto<String> updateSysArea(@RequestBody SysAreaInfDto sysAreaInfDto){
        sysAreaInfService.updateSysAreaInf(sysAreaInfDto);
        return ResultDtoFactory.toAck("更新成功");
    }

    /**
     * 删除地区
     * @param areaId
     * @return
     */
    @RequestMapping(value = "/{areaId}", method = RequestMethod.DELETE)
    public ResultDto<String> deleteSysArea(@PathVariable Long areaId){
        SysAreaInfSearchDto searchDto = new SysAreaInfSearchDto();
        searchDto.setParent(areaId);

        List<SysAreaInfDto> list = sysAreaInfService.getSysAreaInfList(searchDto);
        if (!CollectionUtils.isEmpty(list)){
            return ResultDtoFactory.toNack("当前地区下有子地区数据，不支持删除！");
        }else{
            sysAreaInfService.deleteSysAreaInf(areaId);
            return ResultDtoFactory.toAck("成功删除");
        }
    }

}
