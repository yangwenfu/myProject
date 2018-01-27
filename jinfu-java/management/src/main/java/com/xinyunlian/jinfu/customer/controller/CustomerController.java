package com.xinyunlian.jinfu.customer.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.authc.ESourceType;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.MaskUtil;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.customer.dto.CustomerDealerDto;
import com.xinyunlian.jinfu.customer.dto.CustomerDetailDto;
import com.xinyunlian.jinfu.customer.dto.CustomerStoreDto;
import com.xinyunlian.jinfu.customer.dto.UserBusHasDto;
import com.xinyunlian.jinfu.customer.service.CustomerService;
import com.xinyunlian.jinfu.dealer.dto.DealerUserLogDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.dealer.service.DealerUserLogService;
import com.xinyunlian.jinfu.insurance.service.InsuranceOrderService;
import com.xinyunlian.jinfu.report.dealer.dto.UserReportDto;
import com.xinyunlian.jinfu.report.dealer.dto.UserReportSearchDto;
import com.xinyunlian.jinfu.report.dealer.service.DealerReportService;
import com.xinyunlian.jinfu.spider.dto.resp.RiskUserInfoDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.enums.EDelReason;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.UserLabelDto;
import com.xinyunlian.jinfu.user.dto.UserLinkmanDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.dto.req.UserSearchDto;
import com.xinyunlian.jinfu.user.service.UserLabelService;
import com.xinyunlian.jinfu.user.service.UserLinkmanService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * 商城前台用户controller
 * Created by DongFC on 2016-08-29.
 */
@RestController
@RequestMapping("customer")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private DealerReportService dealerReportService;
    @Autowired
    private InsuranceOrderService insuranceOrderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private YMUserInfoService ymUserInfoService;
    @Autowired
    private RiskUserInfoService riskUserInfoService;
    @Autowired
    private BankService bankService;
    @Autowired
    private UserLinkmanService userLinkmanService;
    @Autowired
    private YMMemberService ymMemberService;
    @Autowired
    private DealerUserLogService dealerUserLogService;
    @Autowired
    private UserLabelService userLabelService;

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView exportUser(UserReportSearchDto searchDto){
        List<UserReportDto> data = dealerReportService.getUserReport(searchDto);
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName","用户记录.xls");
        model.put("tempPath","/templates/用户记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

    /**
     * 分页获取商城前台用户列表
     * @param userSearchDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<UserSearchDto> getUserPage(UserSearchDto userSearchDto){
        UserSearchDto dto = userService.getUserPage(userSearchDto);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 获取商城用户详情
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}/detail", method = RequestMethod.GET)
    public ResultDto<CustomerDetailDto> getUserDetail(@PathVariable String userId){
        CustomerDetailDto customerDetailDto = new CustomerDetailDto();

        UserInfoDto userInfoDto = userService.findUserByUserId(userId);
        customerDetailDto.setUserDto(userInfoDto);


        RiskUserInfoDto riskUserInfoDto = riskUserInfoService.getUserInfo(userId);
        if(null != riskUserInfoDto){
            customerDetailDto.setHasRisk(true);
        }

        YMUserInfoDto ymUserInfoDto = ymUserInfoService.findByUserId(userId);
        if(null != ymUserInfoDto){
            customerDetailDto.setHasOpenId(true);
        }

        List<BankCardDto> bankCardDtos = bankService.findByUserId(userId);
        if(!CollectionUtils.isEmpty(bankCardDtos)){
            customerDetailDto.setBankCardDtos(bankCardDtos);
        }

        List<StoreInfDto> storeInfDtos = storeService.findByUserId(userId);
        if(!CollectionUtils.isEmpty(storeInfDtos)){
            storeInfDtos.forEach(storeInfDto -> {
                CustomerStoreDto customerStoreDto = ConverterService.convert(storeInfDto,CustomerStoreDto.class);
                YMMemberDto ymMemberDto = ymMemberService.getMemberByStoreId(storeInfDto.getStoreId());
                if(null != ymMemberDto && !StringUtils.isEmpty(ymMemberDto.getQrcodeUrl())){
                    customerStoreDto.setQrId(ymMemberDto.getId());
                    customerStoreDto.setQrCodeNo(ymMemberDto.getQrcodeNo());
                    customerStoreDto.setQrStatus(ymMemberDto.getMemberStatus());
                    customerStoreDto.setQrBindTime(ymMemberDto.getBindTime());
                }
                customerDetailDto.getCustomerStoreDtos().add(customerStoreDto);
            });
        }

        List<UserLinkmanDto> userLinkmanDtos = userLinkmanService.findByUserId(userId);
        if(!CollectionUtils.isEmpty(userLinkmanDtos)){
            customerDetailDto.setUserLinkmanDtos(userLinkmanDtos);
        }

        List<DealerUserLogDto> dealerUserLogDtos = dealerUserLogService
                .findByStoreUserIdAndType(userId, EDealerUserLogType.REGISTER);
        if(!CollectionUtils.isEmpty(dealerUserLogDtos)){
            CustomerDealerDto customerDealerDto = new CustomerDealerDto();
            DealerUserLogDto dealerUserLogDto = dealerUserLogDtos.get(0);

            if(dealerUserLogDto.getDealerUserDto() != null) {
                customerDealerDto.setDealerUserName(dealerUserLogDto.getDealerUserDto().getName());
            }
            if(dealerUserLogDto.getDealerDto() != null) {
                customerDealerDto.setDealerName(dealerUserLogDto.getDealerDto().getDealerName());
            }
            customerDetailDto.setCustomerDealerDto(customerDealerDto);
        }

        List<UserLabelDto> userLabelDtos = userLabelService.listUserLabel(userId);
        if(!CollectionUtils.isEmpty(userLabelDtos)){
            customerDetailDto.setUserLabelDtos(userLabelDtos);
        }

        return ResultDtoFactory.toAck("获取成功", customerDetailDto);
    }

    /**
     * 修改用户手机号
     * @param userInfoDto
     * @return
     */
    @RequiresPermissions({"MALL_USER_EDIT"})
    @RequestMapping(value = "/updateMobile", method = RequestMethod.POST)
    public ResultDto<Object> updateMobile(@RequestBody UserInfoDto userInfoDto){
        UserInfoDto user = userService.findUserByMobile(userInfoDto.getMobile());
        if(user != null){
            return ResultDtoFactory.toNack("该手机号已存在");
        }

        userService.updateUser(userInfoDto);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 检查店铺已办业务
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/checkStore", method = RequestMethod.GET)
    public ResultDto<Object> checkStore(@RequestParam Long storeId){
        UserBusHasDto userBusHasDto = customerService.CheckStoreOrderHas(storeId);
        return ResultDtoFactory.toAck("获取成功",userBusHasDto);
    }

    /**
     * 检查用户已办业务
     * @param userId
     * @return
     */
    @RequestMapping(value = "/checkUser", method = RequestMethod.GET)
    public ResultDto<Object> checkUser(@RequestParam String userId){
        UserBusHasDto userBusHasDto = customerService.CheckUserOrderHas(userId);
        return ResultDtoFactory.toAck("获取成功",userBusHasDto);
    }

    /**
     * openid解绑
     * @param userId
     * @return
     */
    @RequestMapping(value = "/unbindOpenId", method = RequestMethod.POST)
    public ResultDto<Object> unbindOpenId(@RequestParam String userId){
        ymUserInfoService.deleteByUserId(userId);
        SecurityContext.clearAuthcCacheByUserId(userId, ESourceType.WECHAT);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 店铺解绑
     * @param storeId
     * @return
     */
    @RequiresPermissions({"MALL_USER_EDIT"})
    @RequestMapping(value = "/unbindStore", method = RequestMethod.POST)
    public ResultDto<Object> unbindStore(@RequestParam Long storeId){
        //解绑云码
        ymMemberService.unbindByStoreId(storeId);
        //删除店铺
        storeService.deleteStore(storeId, EDelReason.UNBIND);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 获取个人银行卡信息
     * @return
     */
    @RequestMapping(value = "/getBankCard", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getBankCard(@RequestParam String userId) {
        List<BankCardDto> bankCardDtoList = bankService.findByUserId(userId);
        bankCardDtoList.forEach(bankCardDto -> {
            bankCardDto.setBankCardNo(MaskUtil.maskMiddleValue(0, 4, bankCardDto.getBankCardNo()));
            bankCardDto.setBankCardName(MaskUtil.maskFirstName(bankCardDto.getBankCardName()));
            bankCardDto.setMobileNo(MaskUtil.maskMiddleValue(3, 4, bankCardDto.getMobileNo()));
        });
        return  ResultDtoFactory.toAck("获取成功",bankCardDtoList);
    }

    /**
     * 重置商城用户密码
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}/resetpwd", method = RequestMethod.POST)
    public ResultDto<String> resetUserPwd(@PathVariable String userId){

        try {
            String newPassword = RandomUtil.getNumberStr(8);

            PasswordDto passwordDto = new PasswordDto();
            passwordDto.setUserId(userId);
            passwordDto.setNewPassword(EncryptUtil.encryptMd5(newPassword));
            userService.updatePassword(passwordDto);

            //TODO 重置密码发短信
            UserInfoDto userInfoDto = userService.findUserByUserId(userId);
//        SmsUtil.send();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOGGER.error("重置用户密码失败", e);
            return ResultDtoFactory.toNack("重置用户密码失败");
        }

        return ResultDtoFactory.toAck("重置密码成功");

    }

}
