package com.xinyunlian.jinfu.trade.controller;

import com.xinyunlian.jinfu.clerk.dto.ClerkAuthDto;
import com.xinyunlian.jinfu.clerk.dto.ClerkInfDto;
import com.xinyunlian.jinfu.clerk.service.ClerkAuthService;
import com.xinyunlian.jinfu.clerk.service.ClerkService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.trade.dto.YmTradeDayDto;
import com.xinyunlian.jinfu.trade.dto.YmTradeDto;
import com.xinyunlian.jinfu.trade.service.YmTradeService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by menglei on 2017/1/11.
 */
@Controller
@RequestMapping(value = "trade")
public class TradeController {

    @Autowired
    private YmTradeService ymTradeService;
    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private ClerkAuthService clerkAuthService;

    /**
     * 按日统计查询流水列表
     *
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getDayList(@RequestParam Long storeId) {
        YMMemberDto yMMemberDto = yMMemberService.getMemberByStoreId(storeId);
        if (yMMemberDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto != null && StringUtils.isNotEmpty(yMUserInfoDto.getUserId())) {//店主
            if (!StringUtils.equals(yMUserInfoDto.getUserId(), yMMemberDto.getUserId())) {
                return ResultDtoFactory.toNack("获取失败");
            }
        } else {//店员
            YMUserInfoDto userInfoDto = (YMUserInfoDto) SecurityContext.getCurrentUser().getParamMap().get("ymUserInfoDto");
            ClerkInfDto dto = clerkService.findByOpenId(userInfoDto.getOpenId());
            if (dto == null) {
                return ResultDtoFactory.toNack("获取失败");
            }
            ClerkAuthDto clerkAuthDto = clerkAuthService.findByClerkIdAndStoreId(dto.getClerkId(), String.valueOf(yMMemberDto.getStoreId()));
            if (clerkAuthDto == null) {
                return ResultDtoFactory.toNack("获取失败");
            }
        }
        List<YmTradeDayDto> list = ymTradeService.findDayByMemberNo(yMMemberDto.getMemberNo());
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 按日期和商户号查询流水列表
     *
     * @return
     */
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getDetail(@RequestParam Long storeId, @RequestParam String dates) {
        YMMemberDto yMMemberDto = yMMemberService.getMemberByStoreId(storeId);
        if (yMMemberDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto != null && StringUtils.isNotEmpty(yMUserInfoDto.getUserId())) {//店主
            if (!StringUtils.equals(yMUserInfoDto.getUserId(), yMMemberDto.getUserId())) {
                return ResultDtoFactory.toNack("获取失败");
            }
        } else {//店员
            YMUserInfoDto userInfoDto = (YMUserInfoDto) SecurityContext.getCurrentUser().getParamMap().get("ymUserInfoDto");
            ClerkInfDto dto = clerkService.findByOpenId(userInfoDto.getOpenId());
            if (dto == null) {
                return ResultDtoFactory.toNack("获取失败");
            }
            ClerkAuthDto clerkAuthDto = clerkAuthService.findByClerkIdAndStoreId(dto.getClerkId(), String.valueOf(yMMemberDto.getStoreId()));
            if (clerkAuthDto == null) {
                return ResultDtoFactory.toNack("获取失败");
            }
        }
        List<YmTradeDto> list = ymTradeService.findByMemberNoAndDates(yMMemberDto.getMemberNo(), dates);
        return ResultDtoFactory.toAck("获取成功", list);
    }

}
