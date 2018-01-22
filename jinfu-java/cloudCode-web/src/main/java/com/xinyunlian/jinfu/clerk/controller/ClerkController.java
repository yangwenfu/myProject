package com.xinyunlian.jinfu.clerk.controller;

import com.xinyunlian.jinfu.clerk.dto.*;
import com.xinyunlian.jinfu.clerk.enums.EClerkApplyStatus;
import com.xinyunlian.jinfu.clerk.service.ClerkApplyService;
import com.xinyunlian.jinfu.clerk.service.ClerkAuthService;
import com.xinyunlian.jinfu.clerk.service.ClerkService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.wechat.enums.EWeChatPushType;
import com.xinyunlian.jinfu.wechat.service.ApiWeChatService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 店员授权
 * Created by menglei on 2017-01-05.
 */
@RestController
@RequestMapping(value = "clerk")
public class ClerkController {

    @Autowired
    private ClerkService clerkService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClerkApplyService clerkApplyService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ClerkAuthService clerkAuthService;
    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private ApiWeChatService apiWeChatService;
    @Autowired
    private YMMemberService yMMemberService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClerkController.class);

    /**
     * 短信发送
     *
     * @param clerkDto mobile codeType
     * @return
     */
    @RequestMapping(value = "/getVerifyCode", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getVerifyCode(ClerkDto clerkDto) {
        if (StringUtils.isEmpty(clerkDto.getMobile()) || StringUtils.isEmpty(clerkDto.getCodeType())) {
            return ResultDtoFactory.toNack("参数不正确");
        }
        String verifyCode = "";
        if (StringUtils.equals(clerkDto.getCodeType(), "register")) {
            ClerkInfDto clerkInfDto = clerkService.findByMobile(clerkDto.getMobile());
            if (clerkInfDto != null) {
                return ResultDtoFactory.toNack("该店员已注册");
            }
            UserInfoDto userInfoDto = userService.findUserByMobile(clerkDto.getMobile());
            if (userInfoDto != null) {
                return ResultDtoFactory.toNack("您是会员，请通过“我是会员”登录");
            }
            verifyCode = smsService.getVerifyCode(clerkDto.getMobile(), ESmsSendType.REGISTER);
        } else if (StringUtils.equals(clerkDto.getCodeType(), "auth")) {
            UserInfoDto userInfoDto = userService.findUserByMobile(clerkDto.getMobile());
            if (userInfoDto == null) {
                return ResultDtoFactory.toNack("该店主不存在");
            }
            verifyCode = smsService.getVerifyCode(clerkDto.getMobile(), ESmsSendType.CLERK_AUTH);
        }
        System.out.println(verifyCode);
        return ResultDtoFactory.toAck("验证码发送成功");
    }

    /**
     * 店员注册
     *
     * @param clerkDto name mobile verifyCode
     * @return
     */
    @RequestMapping(value = "/addClerk", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> addClerk(@RequestBody ClerkDto clerkDto) {
        if (StringUtils.isEmpty(clerkDto.getName()) || StringUtils.isEmpty(clerkDto.getMobile())) {
            return ResultDtoFactory.toNack("店员必填信息不能为空");
        }
        Boolean flag = smsService.confirmVerifyCode(clerkDto.getMobile(), clerkDto.getVerifyCode(), ESmsSendType.REGISTER);
        if (!flag) {
            return ResultDtoFactory.toNack("验证码错误");
        }
        YMUserInfoDto yMUserInfoDto = (YMUserInfoDto) SecurityContext.getCurrentUser().getParamMap().get("ymUserInfoDto");
        clerkDto.setOpenId(yMUserInfoDto.getOpenId());
        ClerkInfDto clerkInfDto = ConverterService.convert(clerkDto, ClerkInfDto.class);
        clerkInfDto = clerkService.addClerk(clerkInfDto);
        //清除验证码
        smsService.clearVerifyCode(clerkDto.getMobile(), ESmsSendType.REGISTER);
        return ResultDtoFactory.toAck("添加店员成功", clerkInfDto);
    }

    /**
     * 店员申请
     *
     * @param clerkDto mobile verifyCode
     * @return
     */
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> apply(@RequestBody ClerkDto clerkDto) {
        if (StringUtils.isEmpty(clerkDto.getMobile())) {
            return ResultDtoFactory.toNack("店主必填信息不能为空");
        }
        Boolean flag = smsService.confirmVerifyCode(clerkDto.getMobile(), clerkDto.getVerifyCode(), ESmsSendType.CLERK_AUTH);
        if (!flag) {
            return ResultDtoFactory.toNack("验证码错误");
        }
        YMUserInfoDto userDto = (YMUserInfoDto) SecurityContext.getCurrentUser().getParamMap().get("ymUserInfoDto");
        ClerkInfDto clerkInfDto = clerkService.findByOpenId(userDto.getOpenId());
        if (clerkInfDto == null) {
            return ResultDtoFactory.toNack("该店员不存在");
        }
        if (StringUtils.equals(clerkInfDto.getMobile(), clerkDto.getMobile())) {
            return ResultDtoFactory.toNack("该店员手机号不能与店主手机号相同");
        }
        UserInfoDto userInfoDto = userService.findUserByMobile(clerkDto.getMobile());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("该店主不存在");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByUserId(userInfoDto.getUserId());
        if (yMUserInfoDto == null) {
            return ResultDtoFactory.toNack("该店主尚未绑定微信");
        }
        List<ClerkApplyDto> list = clerkApplyService.findByClerkId(clerkInfDto.getClerkId());
        if (!CollectionUtils.isEmpty(list)) {
            return ResultDtoFactory.toNack("您已申请成功，无法再次申请");
        }
        ClerkApplyDto dto = new ClerkApplyDto();
        dto.setClerkId(clerkInfDto.getClerkId());
        dto.setUserId(userInfoDto.getUserId());
        dto.setStatus(EClerkApplyStatus.APPLY);
        clerkApplyService.addApply(dto);
        //清除验证码
        smsService.clearVerifyCode(clerkDto.getMobile(), ESmsSendType.CLERK_AUTH);
        //推送
        Map<String, String> map = new HashMap<>();
        map.put("openId", yMUserInfoDto.getOpenId());
        map.put("name", clerkInfDto.getName());
        map.put("mobile", clerkInfDto.getMobile());
        map.put("dateTime", DateHelper.formatDate(new Date(), "yyyy.MM.dd HH:mm"));
        apiWeChatService.sendPush(map, EWeChatPushType.APPLY);
        return ResultDtoFactory.toAck("申请成功");
    }

    /**
     * 获取店员信息
     *
     * @return
     */
    @RequestMapping(value = "/getClerk", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getClerk() {
        YMUserInfoDto yMUserInfoDto = (YMUserInfoDto) SecurityContext.getCurrentUser().getParamMap().get("ymUserInfoDto");
        ClerkInfDto dto = clerkService.findByOpenId(yMUserInfoDto.getOpenId());
        if (dto != null) {
            List<ClerkApplyDto> list = clerkApplyService.findByClerkId(dto.getClerkId());
            if (CollectionUtils.isEmpty(list)) {
                dto.setApply(true);
            } else {
                dto.setApply(false);
                ClerkApplyDto clerkApplyDto = list.get(0);
                dto.setStatus(clerkApplyDto.getStatus());
                if (clerkApplyDto.getStatus().equals(EClerkApplyStatus.SUCCESS)) {
                    dto.setCreateTime(clerkApplyDto.getUpdateTime());
                } else {
                    dto.setCreateTime(clerkApplyDto.getCreateTime());
                }
            }
        }
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 根据店主手机号获取店员申请列表
     *
     * @return
     */
    @RequestMapping(value = "/applyList", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getApplyList() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("该店主不存在");
        }
        List<ClerkApplyDto> list = clerkApplyService.findByUserId(userInfoDto.getUserId());
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 申请拒绝
     *
     * @param clerkDto clerkId
     * @return
     */
    @RequestMapping(value = "/applyRefuse", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> applyRefuse(@RequestBody ClerkDto clerkDto) {
        if (StringUtils.isEmpty(clerkDto.getClerkId())) {
            return ResultDtoFactory.toNack("店员id必传");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("该店主不存在");
        }
        ClerkInfDto dto = clerkApplyService.applyRefuse(userInfoDto.getUserId(), clerkDto.getClerkId());
        //推送
        Map<String, String> map = new HashMap<>();
        map.put("openId", dto.getOpenId());
        map.put("message", "授权拒绝");
        map.put("dateTime", DateHelper.formatDate(new Date(), "yyyy.MM.dd HH:mm"));
        apiWeChatService.sendPush(map, EWeChatPushType.AUTH);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 申请通过
     *
     * @param clerkDto clerkId storeIds
     * @return
     */
    @RequestMapping(value = "/applyPass", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> applyPass(@RequestBody ClerkDto clerkDto) {
        if (StringUtils.isEmpty(clerkDto.getClerkId())) {
            return ResultDtoFactory.toNack("店员id必传");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("该店主不存在");
        }
        if (StringUtils.isEmpty(clerkDto.getStoreIds())) {
            return ResultDtoFactory.toNack("所属店铺必选");
        }
        ClerkInfDto dto = clerkApplyService.applyPass(userInfoDto.getUserId(), clerkDto.getClerkId(), clerkDto.getStoreIds());
        //推送
        Map<String, String> map = new HashMap<>();
        map.put("openId", dto.getOpenId());
        map.put("message", "授权通过");
        map.put("dateTime", DateHelper.formatDate(new Date(), "yyyy.MM.dd HH:mm"));
        apiWeChatService.sendPush(map, EWeChatPushType.AUTH);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 所属店铺列表
     *
     * @return
     */
    @RequestMapping(value = "/storeList", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getStoreList() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("该店主不存在");
        }
        List<YMMemberDto> memberList = yMMemberService.getMemberListByUserId(userInfoDto.getUserId());
        List<Long> storeIds = new ArrayList<>();
        for (YMMemberDto yMMemberDto : memberList) {
            storeIds.add(yMMemberDto.getStoreId());
        }
        if (CollectionUtils.isEmpty(storeIds)) {
            return ResultDtoFactory.toAck("获取成功", null);
        }
        List<StoreInfDto> list = storeService.findByStoreIds(storeIds);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 根据店主手机号获取店员授权列表
     *
     * @return
     */
    @RequestMapping(value = "/authList", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getAuthList() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("该店主不存在");
        }
        List<StoreInfDto> storeList = storeService.findByUserId(userInfoDto.getUserId());
        Map<String, String> storeMap = new HashMap<>();
        for (StoreInfDto storeInfDto : storeList) {
            storeMap.put(storeInfDto.getStoreId() + StringUtils.EMPTY, storeInfDto.getStoreName());
        }
        List<ClerkAuthDto> clerkAuthList = clerkAuthService.findByUserId(userInfoDto.getUserId());
        List<ClerkInfAuthDto> list = new ArrayList<>();
        for (ClerkAuthDto clerkAuthDto : clerkAuthList) {
            ClerkInfAuthDto clerkInfAuthDto = ConverterService.convert(clerkAuthDto, ClerkInfAuthDto.class);
            StringBuilder sb = new StringBuilder();//店铺id转店铺name
            for (String storeId : clerkAuthDto.getStoreIds().split(",")) {
                sb.append(",").append(storeMap.get(storeId));
            }
            clerkInfAuthDto.setStoreNames(sb.substring(1));
            list.add(clerkInfAuthDto);
        }
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 授权注销
     *
     * @param clerkDto clerkId
     * @return
     */
    @RequestMapping(value = "/deleteAuth", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> deleteAuth(@RequestBody ClerkDto clerkDto) {
        if (StringUtils.isEmpty(clerkDto.getClerkId())) {
            return ResultDtoFactory.toNack("店员id必传");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("该店主不存在");
        }
        ClerkInfDto dto = clerkAuthService.deleteAuth(userInfoDto.getUserId(), clerkDto.getClerkId());
        //推送
        Map<String, String> map = new HashMap<>();
        map.put("openId", dto.getOpenId());
        map.put("message", "授权注销");
        map.put("dateTime", DateHelper.formatDate(new Date(), "yyyy.MM.dd HH:mm"));
        apiWeChatService.sendPush(map, EWeChatPushType.AUTH);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 查询店员已授权的店铺(微信账单流水)
     *
     * @return
     */
    @RequestMapping(value = "/clerkStoreList", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getClerkStoreList() {
        YMUserInfoDto yMUserInfoDto = (YMUserInfoDto) SecurityContext.getCurrentUser().getParamMap().get("ymUserInfoDto");
        ClerkInfDto dto = clerkService.findByOpenId(yMUserInfoDto.getOpenId());
        if (dto == null) {
            return ResultDtoFactory.toNack("该店员不存在");
        }
        List<ClerkAuthDto> list = clerkAuthService.findByClerkId(dto.getClerkId());
        if (list.isEmpty()) {
            return ResultDtoFactory.toAck("获取成功", null);
        }
        List<Long> storeIds = new ArrayList<>();
        for (ClerkAuthDto clerkAuthDto : list) {
            storeIds.add(Long.valueOf(clerkAuthDto.getStoreId()));
        }
        if (CollectionUtils.isEmpty(storeIds)) {
            return ResultDtoFactory.toAck("获取成功", null);
        }
        List<StoreInfDto> storeList = storeService.findByStoreIds(storeIds);
        return ResultDtoFactory.toAck("获取成功", storeList);
    }

//    /**
//     * 根据店铺id获取店员openId(微信推送)
//     *
//     * @param clerkOpenApiDto storeId
//     * @return
//     */
//    @OpenApi
//    @RequestMapping(value = "/clerkListByStoreId", method = RequestMethod.POST)
//    public ResultDto<Object> getClerkListByStoreId(@RequestBody ClerkOpenApiDto clerkOpenApiDto) {
//        if (StringUtils.isEmpty(clerkOpenApiDto.getStoreId())) {
//            return ResultDtoFactory.toNack("storeId必传");
//        }
//        List<ClerkInfDto> clerkInfList = new ArrayList<>();
//        List<ClerkAuthDto> list = clerkAuthService.findByStoreId(clerkOpenApiDto.getStoreId());
//        List<String> clerkIds = new ArrayList<>();
//        for (ClerkAuthDto clerkAuthDto : list) {
//            clerkIds.add(clerkAuthDto.getClerkId());
//        }
//        if (clerkIds.size() > 0) {
//            clerkInfList = clerkService.findByClerkIds(clerkIds);
//        }
//        return ResultDtoFactory.toAck("获取成功", clerkInfList);
//    }

    /**
     * 店员自己授权注销
     *
     * @return
     */
    @RequestMapping(value = "/deleteClker", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> deleteClker() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        ClerkInfDto clerkInfDto = clerkService.findByOpenId(yMUserInfoDto.getOpenId());
        if (clerkInfDto == null) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        //获取店主openid
        List<ClerkApplyDto> clerkApplyList = clerkApplyService.findByClerkId(clerkInfDto.getClerkId());
        YMUserInfoDto dto = new YMUserInfoDto();
        if (!clerkApplyList.isEmpty()) {//店员有店铺绑定关系
            dto = yMUserInfoService.findByUserId(clerkApplyList.get(0).getUserId());
        }
        clerkService.deleteClerk(clerkInfDto.getClerkId());
        if (dto != null) {//没有店铺绑定关系解除微信不推送
            //推送
            Map<String, String> map = new HashMap<>();
            map.put("openId", dto.getOpenId());
            map.put("message", "授权注销-" + clerkInfDto.getName());
            map.put("dateTime", DateHelper.formatDate(new Date(), "yyyy.MM.dd HH:mm"));
            apiWeChatService.sendPush(map, EWeChatPushType.AUTH);
        }
        SecurityContext.logout();
        return ResultDtoFactory.toAck("注销成功");
    }

}
