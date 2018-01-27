package com.xinyunlian.jinfu.dealer.store.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.service.DealerService;
import com.xinyunlian.jinfu.store.dto.StoreWhiteListDto;
import com.xinyunlian.jinfu.store.dto.StoreWhiteListSearchDto;
import com.xinyunlian.jinfu.store.service.StoreWhiteListService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2017年06月20日.
 */
@Controller
@RequestMapping(value = "dealer/storeWhiteList")
public class StoreWhiteListController {

    @Autowired
    private StoreWhiteListService storeWhiteListService;
    @Autowired
    private DealerService dealerService;
    @Autowired
    private DealerUserService dealerUserService;

    /**
     * 分页查询白名单店铺
     *
     * @param storeWhiteListSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<StoreWhiteListSearchDto> getPage(StoreWhiteListSearchDto storeWhiteListSearchDto) {
        if (StringUtils.isNotEmpty(storeWhiteListSearchDto.getDealerName())) {
            List<DealerDto> dealerDtos = dealerService.findByDealerName(storeWhiteListSearchDto.getDealerName());
            if (CollectionUtils.isEmpty(dealerDtos)) {
                return ResultDtoFactory.toAck("获取成功", storeWhiteListSearchDto);
            }
            List<String> dealerIds = new ArrayList<>();
            for (DealerDto dealerDto : dealerDtos) {
                dealerIds.add(dealerDto.getDealerId());
            }
            storeWhiteListSearchDto.setDealerIds(dealerIds);
        }
        if (StringUtils.isNotEmpty(storeWhiteListSearchDto.getDealerUserName())) {
            List<DealerUserDto> userDtos = dealerUserService.findByNameLike(storeWhiteListSearchDto.getDealerUserName());
            if (CollectionUtils.isEmpty(userDtos)) {
                return ResultDtoFactory.toAck("获取成功", storeWhiteListSearchDto);
            }
            List<String> userIds = new ArrayList<>();
            for (DealerUserDto dealerUserDto : userDtos) {
                userIds.add(dealerUserDto.getUserId());
            }
            storeWhiteListSearchDto.setUserIds(userIds);
        }

        StoreWhiteListSearchDto page = storeWhiteListService.getStorePage(storeWhiteListSearchDto);

        Map<String, DealerDto> dealerDtoMap = new HashMap<>();
        Map<String, DealerUserDto> dealerUserDtoMap = new HashMap<>();
        List<String> dealerIds = new ArrayList<>();
        List<String> dealerUserIds = new ArrayList<>();
        for (StoreWhiteListDto storeWhiteListDto : page.getList()) {
            if (StringUtils.isNotEmpty(storeWhiteListDto.getDealerId())) {
                dealerIds.add(storeWhiteListDto.getDealerId());
            }
            if (StringUtils.isNotEmpty(storeWhiteListDto.getUserId())) {
                dealerUserIds.add(storeWhiteListDto.getUserId());
            }
        }
        if (!CollectionUtils.isEmpty(dealerIds)) {
            List<DealerDto> dealerDtos = dealerService.findByDealerIds(dealerIds);
            for (DealerDto dealerDto : dealerDtos) {
                dealerDtoMap.put(dealerDto.getDealerId(), dealerDto);
            }
        }
        if (!CollectionUtils.isEmpty(dealerUserIds)) {
            List<DealerUserDto> dealerUserDtos = dealerUserService.findByDealerUserIds(dealerUserIds);
            for (DealerUserDto dealerUserDto : dealerUserDtos) {
                dealerUserDtoMap.put(dealerUserDto.getUserId(), dealerUserDto);
            }
        }
        List<StoreWhiteListDto> list = new ArrayList<>();
        for (StoreWhiteListDto storeWhiteListDto : page.getList()) {
            DealerDto dealerDto = dealerDtoMap.get(storeWhiteListDto.getDealerId());
            if (dealerDto != null) {
                storeWhiteListDto.setDealerName(dealerDto.getDealerName());
            }
            DealerUserDto dealerUserDto = dealerUserDtoMap.get(storeWhiteListDto.getUserId());
            if (dealerUserDto != null) {
                storeWhiteListDto.setDealerUserName(dealerUserDto.getName());
            }
            list.add(storeWhiteListDto);
        }
        page.setList(list);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    @ResponseBody
    @RequestMapping(value = "/allDealerList", method = RequestMethod.GET)
    public ResultDto<List<DealerDto>> getAllDealerList(String dealerName) {
        List<DealerDto> list = dealerService.findByDealerNameLike(dealerName);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> assign(@RequestBody StoreWhiteListSearchDto storeWhiteListSearchDto) {
        if (storeWhiteListSearchDto == null || CollectionUtils.isEmpty(storeWhiteListSearchDto.getIds())) {
            return ResultDtoFactory.toNack("至少选择一个店铺");
        }
        if (storeWhiteListSearchDto == null || storeWhiteListSearchDto.getDealerId() == null || storeWhiteListSearchDto.getUserId() == null) {
            return ResultDtoFactory.toNack("请选择一个分销员");
        }
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(storeWhiteListSearchDto.getUserId());
        if (dealerUserDto == null || !dealerUserDto.getDealerId().equals(storeWhiteListSearchDto.getDealerId())) {
            return ResultDtoFactory.toNack("分销员不存在");
        }
        storeWhiteListService.updateDealerIdByIds(storeWhiteListSearchDto.getDealerId(), storeWhiteListSearchDto.getUserId(), storeWhiteListSearchDto.getIds());
        return ResultDtoFactory.toAck("操作成功");
    }

}
