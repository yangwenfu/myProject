package com.xinyunlian.jinfu.dealer.dealer.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.dealer.dealer.dto.req.DealerGroupWebDto;
import com.xinyunlian.jinfu.dealer.dto.DealerGroupDto;
import com.xinyunlian.jinfu.dealer.dto.DealerGroupSearchDto;
import com.xinyunlian.jinfu.dealer.service.DealerGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by menglei on 2016年09月05日.
 */
@Controller
@RequestMapping(value = "dealer/dealerGroup")
public class DealerGroupController {

    @Autowired
    private DealerGroupService dealerGroupService;

    /**
     * 查询组织列表
     *
     * @param dealerGroupSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<List<DealerGroupDto>> getGroupList(DealerGroupSearchDto dealerGroupSearchDto) {
        List<DealerGroupDto> list = dealerGroupService.getGroupList(dealerGroupSearchDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 新增组织
     *
     * @param dealerGroupWebDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDto<String> add(@RequestBody DealerGroupWebDto dealerGroupWebDto) {
        DealerGroupDto dto = ConverterService.convert(dealerGroupWebDto, DealerGroupDto.class);
        dealerGroupService.createDealerGroup(dto);
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 修改组织
     *
     * @param dealerGroupWebDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDto<String> update(@RequestBody DealerGroupWebDto dealerGroupWebDto) {
        DealerGroupDto dto = ConverterService.convert(dealerGroupWebDto, DealerGroupDto.class);
        dealerGroupService.updateDealerGroup(dto);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 删除组织
     *
     * @param groupId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultDto<String> delete(@RequestParam String groupId) {
        DealerGroupSearchDto searchDto = new DealerGroupSearchDto();
        searchDto.setParent(groupId);

        List<DealerGroupDto> list = dealerGroupService.getGroupList(searchDto);
        if (!CollectionUtils.isEmpty(list)) {
            return ResultDtoFactory.toNack("当前地区下有子组数据，不支持删除！");
        } else {
            dealerGroupService.deleteDealerGroup(groupId);
            return ResultDtoFactory.toAck("删除成功");
        }
    }

}
