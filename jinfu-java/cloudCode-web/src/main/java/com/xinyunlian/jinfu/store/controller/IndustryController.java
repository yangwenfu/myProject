package com.xinyunlian.jinfu.store.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年05月18日.
 */
@Controller
@RequestMapping(value = "industry")
public class IndustryController {

    @Autowired
    private IndustryService industryService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private UserService userService;

    /**
     * 所有行业列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "所有行业列表")
    public ResultDto<List<IndustryDto>> getList() {
        List<IndustryDto> list = industryService.getAllIndustry();
        List<IndustryDto> result = new ArrayList<>();
        for (IndustryDto industryDto : list) {
            if (!"5227".equals(industryDto.getMcc())) {//过滤烟草店
                result.add(industryDto);
            }
        }
        return ResultDtoFactory.toAck("获取成功", result);
    }

    /**
     * 获取商户所属行业
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getIndustry", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取商户所属行业 null=没有店铺")
    public ResultDto<IndustryDto> getIndustry() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        List<StoreInfDto> storeList = storeService.findByUserId(userInfoDto.getUserId());
        if (CollectionUtils.isEmpty(storeList)) {
            return ResultDtoFactory.toAck("获取成功", null);
        }
        IndustryDto industry = industryService.getByMcc(storeList.get(0).getIndustryMcc());
        return ResultDtoFactory.toAck("获取成功", industry);
    }

}
