package com.xinyunlian.jinfu.crm.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.crm.dto.CrmCallTypeDto;
import com.xinyunlian.jinfu.crm.service.CrmCallService;
import com.xinyunlian.jinfu.crm.service.CrmCallTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jll on 2016-08-24.
 */
@RestController
@RequestMapping("crm/callType")
public class CallTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallTypeController.class);

    @Autowired
    private CrmCallTypeService callTypeService;
    @Autowired
    private CrmCallService callService;

    /**
     * 查询子目录数据
     * @param callTypeDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultDto<List<CrmCallTypeDto>> getList(@RequestBody CrmCallTypeDto callTypeDto){
        List<CrmCallTypeDto> list = callTypeService.getCallTypeList(callTypeDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }


    /**
     * 查询显示目录数据
     * @param callTypeDto
     * @return
     */
    @RequestMapping(value = "/listDisplay", method = RequestMethod.POST)
    public ResultDto<List<CrmCallTypeDto>> getListDisplay(@RequestBody CrmCallTypeDto callTypeDto){
        callTypeDto.setDisplay(true);
        List<CrmCallTypeDto> list = callTypeService.getCallTypeList(callTypeDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 新增
     * @param callTypeDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultDto<String> save(@RequestBody CrmCallTypeDto callTypeDto){
        callTypeService.save(callTypeDto);
        return ResultDtoFactory.toAck("新增成功");
    }

    /**
     * 更新
     * @param callTypeDtos
     * @return
     */
    @RequestMapping(value = "/updateList", method = RequestMethod.POST)
    public ResultDto<String> updateList(@RequestBody List<CrmCallTypeDto> callTypeDtos){
        if(CollectionUtils.isEmpty(callTypeDtos)){
            ResultDtoFactory.toNack("更新数据不可为空");
        }
        callTypeService.update(callTypeDtos);
        return ResultDtoFactory.toAck("更新成功");
    }

    /**
     * 删除
     * @param callTypeIds
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDto<String> delete(@RequestParam String callTypeIds){
        String[] callTypeIdArr = callTypeIds.split(",");

        List<Long> parents = new ArrayList<>();
        for (String id : callTypeIdArr) {
            parents.add(Long.valueOf(id));
        }

        CrmCallTypeDto callTypeDto = new CrmCallTypeDto();
        callTypeDto.setParents(parents);
        List<CrmCallTypeDto> callTypeDtos = callTypeService.getCallTypeList(callTypeDto);

        if(!CollectionUtils.isEmpty(callTypeDtos)){
            return ResultDtoFactory.toNack("有子级项不可删除");
        }

        Long count = callService.countByCallTypeIdIn(parents);
        if(count != null && count.longValue() > 0){
            return ResultDtoFactory.toNack("已关联通话记录不可删除");
        }

        for(String callTypeId : callTypeIdArr){
            callTypeService.delete(Long.valueOf(callTypeId));
        }

        return ResultDtoFactory.toAck("删除成功");
    }

}
