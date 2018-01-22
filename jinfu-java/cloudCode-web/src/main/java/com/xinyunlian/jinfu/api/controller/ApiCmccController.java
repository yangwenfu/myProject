package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.dto.MobileOpenApiDto;
import com.xinyunlian.jinfu.api.dto.OrderOpenApiDto;
import com.xinyunlian.jinfu.api.validate.OpenApi;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.clerk.dto.ClerkAuthDto;
import com.xinyunlian.jinfu.clerk.dto.ClerkInfDto;
import com.xinyunlian.jinfu.clerk.service.ClerkAuthService;
import com.xinyunlian.jinfu.clerk.service.ClerkService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.order.dto.CmccOrderDto;
import com.xinyunlian.jinfu.order.dto.CmccOrderInfoDto;
import com.xinyunlian.jinfu.order.service.CmccOrderService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.trade.dto.YmTradeDto;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.enums.ETradeStatus;
import com.xinyunlian.jinfu.trade.service.YmTradeService;
import com.xinyunlian.jinfu.wechat.enums.EWeChatPushType;
import com.xinyunlian.jinfu.wechat.service.ApiWeChatService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.enums.EYmProd;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YMProdService;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * 中移积分
 * Created by menglei on 2016年11月21日.
 */
@Controller
@RequestMapping(value = "open-api/cmcc")
public class ApiCmccController {

    @Autowired
    private CmccOrderService cmccOrderService;
    @Autowired
    private YMProdService yMProdService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private ApiWeChatService apiWeChatService;
    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private ClerkAuthService clerkAuthService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private YmTradeService ymTradeService;
    @Autowired
    private YMMemberService yMMemberService;

    /**
     * 查询手机号电子券余额，发送余额短信
     *
     * @param mobileOpenApiDto mobile storeId
     * @return
     */
    @OpenApi
    @ResponseBody
    @RequestMapping(value = "/queryBalance", method = RequestMethod.POST)
    public ResultDto<Object> queryBalance(@RequestBody MobileOpenApiDto mobileOpenApiDto) {
        StoreInfDto storeInfDto = storeService.findByStoreId(Long.valueOf(mobileOpenApiDto.getStoreId()));
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        //判断店铺所在的地区是否是业务配置的地区范围内
        SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getDistrictId()));
        Boolean flag = yMProdService.checkProdArea(EYmProd.JF0001.getCode(), Long.valueOf(storeInfDto.getDistrictId()), sysAreaInfDto.getTreePath());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        CmccOrderDto cmccOrderDto = new CmccOrderDto();
        cmccOrderDto.setMobile(mobileOpenApiDto.getMobile());
        cmccOrderDto.setProvinceId(storeInfDto.getProvinceId());
        cmccOrderDto.setCityId(storeInfDto.getCityId());
        Map<String, String> resultMap = cmccOrderService.queryBalance(cmccOrderDto);
        if (!"0000".equals(resultMap.get("retCode"))) {//接口出错
            return ResultDtoFactory.toNack(resultMap.get("retMsg"));
        }
        return ResultDtoFactory.toAck("发送成功");
    }

    /**
     * 下单，发送验证码
     *
     * @param orderOpenApiDto mobile amount couponAmount coupon storeId
     * @return
     */
    @OpenApi
    @ResponseBody
    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    public ResultDto<Object> saveOrder(@RequestBody OrderOpenApiDto orderOpenApiDto) {
        StoreInfDto storeInfDto = storeService.findByStoreId(Long.valueOf(orderOpenApiDto.getStoreId()));
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        //判断店铺所在的地区是否是业务配置的地区范围内
        SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getDistrictId()));
        Boolean flag = yMProdService.checkProdArea(EYmProd.JF0001.getCode(), Long.valueOf(storeInfDto.getDistrictId()), sysAreaInfDto.getTreePath());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        CmccOrderDto cmccOrderDto = new CmccOrderDto();
        cmccOrderDto.setMobile(orderOpenApiDto.getMobile());
        cmccOrderDto.setAmount(orderOpenApiDto.getAmount());
        cmccOrderDto.setCouponAmount(orderOpenApiDto.getCouponAmount());
        cmccOrderDto.setCoupon(orderOpenApiDto.getCoupon());
        cmccOrderDto.setStoreId(orderOpenApiDto.getStoreId());
        cmccOrderDto.setProvinceId(storeInfDto.getProvinceId());
        cmccOrderDto.setCityId(storeInfDto.getCityId());
        cmccOrderDto.setStoreId(orderOpenApiDto.getStoreId());
        cmccOrderDto.setStoreName(storeInfDto.getStoreName());
        cmccOrderDto.setStoreAddress(storeInfDto.getProvince() + storeInfDto.getCity() + storeInfDto.getArea() + storeInfDto.getStreet());
        Map<String, String> resultMap = cmccOrderService.saveOrder(cmccOrderDto);
        if (!"0000".equals(resultMap.get("retCode"))) {//接口出错
            return ResultDtoFactory.toNack(resultMap.get("retMsg"));
        }
        CmccOrderInfoDto cmccOrderInfoDto = new CmccOrderInfoDto();
        cmccOrderInfoDto.setCmccOrderNo(resultMap.get("orderId"));
        cmccOrderInfoDto.setOutTradeNo(resultMap.get("tradeNo"));
        return ResultDtoFactory.toAck("发送成功", cmccOrderInfoDto);
    }

    /**
     * 确认下单
     *
     * @param orderOpenApiDto orderNo verifyCode storeId
     * @return
     */
    @OpenApi
    @ResponseBody
    @RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
    public ResultDto<Object> confirmOrder(@RequestBody OrderOpenApiDto orderOpenApiDto) {
        StoreInfDto storeInfDto = storeService.findByStoreId(Long.valueOf(orderOpenApiDto.getStoreId()));
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        //判断店铺所在的地区是否是业务配置的地区范围内
        SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getDistrictId()));
        Boolean flag = yMProdService.checkProdArea(EYmProd.JF0001.getCode(), Long.valueOf(storeInfDto.getDistrictId()), sysAreaInfDto.getTreePath());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        CmccOrderDto cmccOrderDto = new CmccOrderDto();
        cmccOrderDto.setOrderNo(orderOpenApiDto.getOrderNo());
        cmccOrderDto.setVerifyCode(orderOpenApiDto.getVerifyCode());
        cmccOrderDto.setProvinceId(storeInfDto.getProvinceId());
        cmccOrderDto.setCityId(storeInfDto.getCityId());
        cmccOrderDto.setStoreId(orderOpenApiDto.getStoreId());
        Map<String, String> resultMap = cmccOrderService.confirmOrder(cmccOrderDto);
        if (!"0000".equals(resultMap.get("retCode"))) {//接口出错
            return ResultDtoFactory.toNack(resultMap.get("retMsg"));
        }
        //插入云码流水表
        YMMemberDto yMMemberDto = yMMemberService.getMemberByStoreId(storeInfDto.getStoreId());
        CmccOrderInfoDto orderInfoDto = cmccOrderService.getOrderByOrderNo(orderOpenApiDto.getOrderNo());
        YmTradeDto ymTradeDto = new YmTradeDto();
        ymTradeDto.setTradeNo(orderInfoDto.getCmccOrderNo());
        ymTradeDto.setMemberNo(yMMemberDto.getMemberNo());
        ymTradeDto.setBizCode(EBizCode.CMCC);
        ymTradeDto.setTransAmt(orderInfoDto.getCouponAmount());
        ymTradeDto.setTransFee(BigDecimal.valueOf(0.00));
        ymTradeDto.setTransTime(StringUtils.EMPTY);
        YMUserInfoDto userInfoDto = (YMUserInfoDto) SecurityContext.getCurrentUser().getParamMap().get("ymUserInfoDto");
        ymTradeDto.setOpenid(userInfoDto.getOpenId());
        ymTradeDto.setStatus(ETradeStatus.SUCCESS);
        ymTradeService.saveTrade(ymTradeDto);
        //推送消息
        //推送店主
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByUserId(storeInfDto.getUserId());
        Map<String, String> map = new HashMap<>();
        map.put("openId", yMUserInfoDto.getOpenId());
        map.put("dateTime", DateHelper.formatDate(new Date(), "yyyy.MM.dd HH:mm"));
        map.put("orderNo", orderOpenApiDto.getOrderNo());
        map.put("price", orderInfoDto.getCouponAmount() + StringUtils.EMPTY);
        map.put("storeName", storeInfDto.getStoreName());
        map.put("orderType", EBizCode.CMCC.getCode());
        apiWeChatService.sendPush(map, EWeChatPushType.PAY);
        //推送店员
        List<ClerkAuthDto> list = clerkAuthService.findByStoreId(String.valueOf(storeInfDto.getStoreId()));
        List<String> clerkIds = new ArrayList<>();
        for (ClerkAuthDto clerkAuthDto : list) {
            clerkIds.add(clerkAuthDto.getClerkId());
        }
        if (!CollectionUtils.isEmpty(clerkIds)) {
            List<ClerkInfDto> clerkInfList = clerkService.findByClerkIds(clerkIds);
            for (ClerkInfDto clerkInfDto : clerkInfList) {
                map.put("openId", clerkInfDto.getOpenId());
                apiWeChatService.sendPush(map, EWeChatPushType.PAY);
            }
        }
        return ResultDtoFactory.toAck("支付成功");
    }

    /**
     * 电子券兑换比例
     *
     * @param orderOpenApiDto couponAmount storeId
     * @return
     */
    @OpenApi
    @ResponseBody
    @RequestMapping(value = "/getCoupon", method = RequestMethod.POST)
    public ResultDto<Object> getCoupon(@RequestBody OrderOpenApiDto orderOpenApiDto) {
        Double CouponAmount = Double.valueOf(orderOpenApiDto.getCouponAmount());
        if (CouponAmount == null || CouponAmount <= 0) {
            return ResultDtoFactory.toNack("金额不正确");
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(Long.valueOf(orderOpenApiDto.getStoreId()));
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        //判断店铺所在的地区是否是业务配置的地区范围内
        SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getDistrictId()));
        Boolean flag = yMProdService.checkProdArea(EYmProd.JF0001.getCode(), Long.valueOf(storeInfDto.getDistrictId()), sysAreaInfDto.getTreePath());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        CmccOrderDto cmccOrderDto = new CmccOrderDto();
        cmccOrderDto.setCouponAmount(orderOpenApiDto.getCouponAmount());
        cmccOrderDto.setProvinceId(storeInfDto.getProvinceId());
        cmccOrderDto.setCityId(storeInfDto.getCityId());
        Map<String, String> resultMap = cmccOrderService.queryVouchers(cmccOrderDto);
        if (!"0000".equals(resultMap.get("retCode"))) {//接口出错
            return ResultDtoFactory.toNack(resultMap.get("retMsg"));
        }
        String amount = resultMap.get("amount");
        return ResultDtoFactory.toAck("获取成功", formatAmount(amount));
    }

    /**
     * 格式化金额 1234：12.34
     *
     * @param amount
     * @return
     */
    public static String formatAmount(String amount) {
        String result;
        if (amount.length() == 0) {
            result = "0.00";
        } else if (amount.length() == 1) {
            result = "0.0" + amount;
        } else if (amount.length() == 2) {
            result = "0." + amount;
        } else {
            String amount1 = amount.substring(0, amount.length() - 2);
            String amount2 = amount.substring(amount.length() - 2, amount.length());
            result = amount1 + "." + amount2;
        }
        return result;
    }

}
