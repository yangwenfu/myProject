package com.xinyunlian.jinfu.yunma.controller;

import com.alibaba.fastjson.JSON;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.CorpBankDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.bank.service.CorpBankService;
import com.xinyunlian.jinfu.bscontract.dto.YmMemberSignDto;
import com.xinyunlian.jinfu.bscontract.service.YmMemberSignService;
import com.xinyunlian.jinfu.cashier.dto.BankMappingDto;
import com.xinyunlian.jinfu.cashier.dto.CloudCodeMerchantDto;
import com.xinyunlian.jinfu.cashier.service.BankMappingService;
import com.xinyunlian.jinfu.cashier.service.CloudCodeService;
import com.xinyunlian.jinfu.cashier.service.SettlementMerchantService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.*;
import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by King on 2017/1/6.
 */
@Controller
@RequestMapping(value = "yunma/member")
public class YmMemberController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YmMemberController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private YMMemberService memberService;
    @Autowired
    private BankService bankService;
    @Autowired
    private CloudCodeService cloudCodeService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private YmMemberSignService ymMemberSignService;
    @Autowired
    private SettlementMerchantService settlementMerchantService;
    @Autowired
    private BankMappingService bankMappingService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private CorpBankService corpBankService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private IndustryService industryService;

    /**
     * 改变商户状态启用或停用
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "alterStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> alterStatus(@RequestParam Long id, @RequestParam EMemberStatus status) {
        memberService.alterStatus(id, status);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 更改银行卡
     *
     * @param id
     * @param bankCardId
     * @return
     */
    /**
     @RequestMapping(value = "alterBankCard", method = RequestMethod.POST)
     @ResponseBody public ResultDto<Object> alterBankCard(@RequestParam Long id, @RequestParam Long bankCardId) {
     YMMemberDto ymMemberDto = memberService.get(id);
     StoreInfDto storeDto = storeService.findByStoreId(ymMemberDto.getStoreId());
     if (null == storeDto) {
     ResultDtoFactory.toNack("店铺不存在");
     }
     UserInfoDto userDto = userService.findUserByUserId(ymMemberDto.getUserId());
     if (null == userDto) {
     ResultDtoFactory.toNack("用户不存在");
     }
     BankCardDto bankCardDto = bankService.getBankCard(bankCardId);
     if (null == bankCardDto) {
     ResultDtoFactory.toNack("银行卡不存在");
     }
     CloudCodeMerchantDto cloudCodeMerchantDto = new CloudCodeMerchantDto();
     YmMemberBizDto alipayBiz = memberService.getMemberBizByMemberNoAndBizCode(ymMemberDto.getMemberNo(), EBizCode.ALLIPAY);
     YmMemberBizDto wechatBiz = memberService.getMemberBizByMemberNoAndBizCode(ymMemberDto.getMemberNo(), EBizCode.WECHAT);
     if (alipayBiz == null || wechatBiz == null) {
     ResultDtoFactory.toNack("费率不存在");
     }
     SysAreaInfDto provinceArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeDto.getProvinceId()));
     SysAreaInfDto cityArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeDto.getCityId()));
     cloudCodeMerchantDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));//partnerid
     cloudCodeMerchantDto.setOutMerchantId(String.valueOf(storeDto.getStoreId()));//storeId
     //cloudCodeMerchantDto.setEmail();//空
     cloudCodeMerchantDto.setMerchantName(storeDto.getStoreName());//店铺名
     //cloudCodeMerchantDto.setMerchantShortName();//不传
     cloudCodeMerchantDto.setAddress(storeDto.getProvince() + storeDto.getCity());
     cloudCodeMerchantDto.setLegalPerson(userDto.getUserName());//姓名
     cloudCodeMerchantDto.setLegalPersonID(userDto.getIdCardNo());//身份证
     cloudCodeMerchantDto.setFirstName(userDto.getUserName());//姓名
     cloudCodeMerchantDto.setCellPhone(userDto.getMobile());//手机
     BankMappingDto bankMappingDto = bankMappingService.getBankMappingByShortName(bankCardDto.getBankCode());
     if (bankMappingDto == null) {
     return ResultDtoFactory.toNack("该银行不支持");
     }
     cloudCodeMerchantDto.setBankCode(bankMappingDto.getBankCnapsCode());
     cloudCodeMerchantDto.setBankName(bankCardDto.getBankName());
     //cloudCodeMerchantDto.setSubbranchName();//支行名 空
     cloudCodeMerchantDto.setBankProvinceCode(provinceArea.getGbCode());//省 code
     cloudCodeMerchantDto.setBankProvince(storeDto.getProvince());//
     cloudCodeMerchantDto.setBankCityCode(cityArea.getGbCode());
     cloudCodeMerchantDto.setBankCity(storeDto.getCity());
     cloudCodeMerchantDto.setCardName(userDto.getUserName());//银行卡姓名
     cloudCodeMerchantDto.setIdNo(userDto.getIdCardNo());//身份证
     cloudCodeMerchantDto.setCardNo(bankCardDto.getBankCardNo());
     cloudCodeMerchantDto.setBankCardPhone(bankCardDto.getMobileNo());//预留手机号
     cloudCodeMerchantDto.setAlipayRate(alipayBiz.getRate());
     cloudCodeMerchantDto.setWechatRate(wechatBiz.getRate());
     cloudCodeMerchantDto.setSettleDays(Integer.valueOf(ymMemberDto.getSettlement().getCode()));//t+1=1 d+0=0
     cloudCodeMerchantDto.setBizLicenseCode(storeDto.getBizLicence());
     //身份证
     PictureDto idCardFrontPicDto = pictureService.get(userDto.getUserId(), EPictureType.CARD_FRONT);
     PictureDto idCardBackPicDto = pictureService.get(userDto.getUserId(), EPictureType.CARD_BACK);
     if (idCardFrontPicDto == null || idCardBackPicDto == null) {
     return ResultDtoFactory.toNack("请先上传身份证照");
     }
     cloudCodeMerchantDto.setLegalPersonIDImgFront(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPicDto.getPicturePath()));
     cloudCodeMerchantDto.setLegalPersonIDImgBack(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardBackPicDto.getPicturePath()));
     //营业执照
     PictureDto bizLicensePicDto = pictureService.get(String.valueOf(storeDto.getStoreId()), EPictureType.STORE_LICENCE);
     if (bizLicensePicDto == null) {
     return ResultDtoFactory.toNack("请先上传营业执照");
     }
     cloudCodeMerchantDto.setBizLicenseImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicensePicDto.getPicturePath()));
     //门头照
     PictureDto bizPlaceSnapshot1PicDto = pictureService.get(String.valueOf(storeDto.getStoreId()), EPictureType.STORE_HEADER);
     if (bizPlaceSnapshot1PicDto == null) {
     return ResultDtoFactory.toNack("请先上传门头照");
     }
     cloudCodeMerchantDto.setBizPlaceSnapshot1Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizPlaceSnapshot1PicDto.getPicturePath()));
     //合同
     YmMemberSignDto ymMemberSignDto = ymMemberSignService.getYmMemberSignByStoreId(storeDto.getStoreId());
     if (ymMemberSignDto == null || StringUtils.isEmpty(ymMemberSignDto.getFirstPageFilePath()) || StringUtils.isEmpty(ymMemberSignDto.getLastPageFilePath()) || !ymMemberSignDto.getSigned()) {
     return ResultDtoFactory.toNack("请先上传合同");
     }
     cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getFirstPageFilePath()));
     cloudCodeMerchantDto.setBizPlaceSnapshot3Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getLastPageFilePath()));
     //cloudCodeMerchantDto.setOtherCertImage();
     cloudCodeMerchantDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
     cloudCodeService.updateMerchant(cloudCodeMerchantDto);

     memberService.alterBankCard(id, bankCardId);
     return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
     }

     @RequestMapping(value = "unbind", method = RequestMethod.POST)
     @ResponseBody public ResultDto<Object> unbind(@RequestParam Long id) {
     YMMemberDto ymMemberDto = memberService.get(id);
     try{
     Long.valueOf(ymMemberDto.getMemberNo());
     settlementMerchantService.cancelMerchant(Long.valueOf(ymMemberDto.getMemberNo()));
     }catch (NumberFormatException e){

     }

     memberService.unbind(id);

     if (ymMemberDto != null){
     LOGGER.debug("开始删除解绑");
     LOGGER.debug("storeId=" + ymMemberDto.getStoreId());
     LOGGER.debug(JsonUtil.toJson(ymMemberDto));
     YmMemberSignDto ymMemberSignDto = ymMemberSignService.getYmMemberSignByStoreId(ymMemberDto.getStoreId());
     if (ymMemberSignDto != null) {
     ymMemberSignService.deleteByStoreId(ymMemberDto.getStoreId());
     UserContractDto userContractDto = contractService.getUserContractByBizId(ymMemberDto.getUserId(), ECntrctTmpltType.YM01001, ymMemberSignDto.getId().toString());
     if (userContractDto != null){
     contractService.deleteUserContractByContId(userContractDto.getContractId());
     }
     }
     }

     return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
     }**/

    /**
     * 云码商户列表查询
     *
     * @param memberSearchDto
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> getList(@RequestBody YMMemberSearchDto memberSearchDto) {
        //查询云码商户列表
        memberSearchDto = memberService.getMemberViewPage(memberSearchDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), memberSearchDto);
    }

    /**
     * 获取云码商户详情
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> getDetail(@RequestParam Long memberId) {
        YMMemberDto memberDto = memberService.get(memberId);
        CloudCodeDetail detail = ConverterService.convert(memberDto, CloudCodeDetail.class);
        detail.setMemberBizDtos(memberDto.getMemberBizDtos());

        StoreInfDto storeDto = storeService.findByStoreId(memberDto.getStoreId());
        if (storeDto != null) {
            ConverterService.convert(storeDto, detail);
        }

        if (!StringUtils.isEmpty(memberDto.getUserId())) {
            UserInfoDto userDto = userService.findUserByUserId(memberDto.getUserId());
            if (userDto != null) {
                ConverterService.convert(userDto, detail);
            }
        }

        if (null != memberDto.getBankCardId()) {
            BankCardDto bankCardDto = bankService.getBankCard(memberDto.getBankCardId());
            if (bankCardDto != null) {
                ConverterService.convert(bankCardDto, detail);
            }
        }

        if (null != memberDto.getCorpBankId()) {
            CorpBankDto corpBankDto = corpBankService.getCorpBankById(memberDto.getCorpBankId());
            if (corpBankDto != null) {
                ConverterService.convert(corpBankDto, detail);
            }
        }

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), detail);

    }

    /**
     * 修改云码费率
     *
     * @param memberBizDtos
     * @return
     */
    /**
     * @RequestMapping(value = "updateMemberBiz", method = RequestMethod.POST)
     * @ResponseBody public ResultDto<Object> updateMemberBiz(@RequestBody List<YmMemberBizDto> memberBizDtos) {
     * YMMemberDto ymMemberDto = memberService.getMemberByMemberNo(memberBizDtos.get(0).getMemberNo());
     * StoreInfDto storeDto = storeService.findByStoreId(ymMemberDto.getStoreId());
     * if (null == storeDto) {
     * ResultDtoFactory.toNack("店铺不存在");
     * }
     * UserInfoDto userDto = userService.findUserByUserId(ymMemberDto.getUserId());
     * if (null == userDto) {
     * ResultDtoFactory.toNack("用户不存在");
     * }
     * BankCardDto bankCardDto = bankService.getBankCard(ymMemberDto.getBankCardId());
     * if (null == bankCardDto) {
     * ResultDtoFactory.toNack("银行卡不存在");
     * }
     * CloudCodeMerchantDto cloudCodeMerchantDto = new CloudCodeMerchantDto();
     * YmMemberBizDto alipayBiz = new YmMemberBizDto();
     * YmMemberBizDto wechatBiz = new YmMemberBizDto();
     * for (YmMemberBizDto ymMemberBizDto : memberBizDtos) {
     * if (ymMemberBizDto.getBizCode() == EBizCode.ALLIPAY) {
     * alipayBiz = ymMemberBizDto;
     * } else if (ymMemberBizDto.getBizCode() == EBizCode.WECHAT) {
     * wechatBiz = ymMemberBizDto;
     * }
     * }
     * if (alipayBiz == null || wechatBiz == null) {
     * ResultDtoFactory.toNack("费率不存在");
     * }
     * SysAreaInfDto provinceArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeDto.getProvinceId()));
     * SysAreaInfDto cityArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeDto.getCityId()));
     * cloudCodeMerchantDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));//partnerid
     * cloudCodeMerchantDto.setOutMerchantId(String.valueOf(storeDto.getStoreId()));//storeId
     * //cloudCodeMerchantDto.setEmail();//空
     * cloudCodeMerchantDto.setMerchantName(storeDto.getStoreName());//店铺名
     * //cloudCodeMerchantDto.setMerchantShortName();//不传
     * cloudCodeMerchantDto.setAddress(storeDto.getProvince() + storeDto.getCity());
     * cloudCodeMerchantDto.setLegalPerson(userDto.getUserName());//姓名
     * cloudCodeMerchantDto.setLegalPersonID(userDto.getIdCardNo());//身份证
     * cloudCodeMerchantDto.setFirstName(userDto.getUserName());//姓名
     * cloudCodeMerchantDto.setCellPhone(userDto.getMobile());//手机
     * BankMappingDto bankMappingDto = bankMappingService.getBankMappingByShortName(bankCardDto.getBankCode());
     * if (bankMappingDto == null) {
     * return ResultDtoFactory.toNack("该银行不支持");
     * }
     * cloudCodeMerchantDto.setBankCode(bankMappingDto.getBankCnapsCode());
     * cloudCodeMerchantDto.setBankName(bankCardDto.getBankName());
     * //cloudCodeMerchantDto.setSubbranchName();//支行名 空
     * cloudCodeMerchantDto.setBankProvinceCode(provinceArea.getGbCode());//省 code
     * cloudCodeMerchantDto.setBankProvince(storeDto.getProvince());//
     * cloudCodeMerchantDto.setBankCityCode(cityArea.getGbCode());
     * cloudCodeMerchantDto.setBankCity(storeDto.getCity());
     * cloudCodeMerchantDto.setCardName(userDto.getUserName());//银行卡姓名
     * cloudCodeMerchantDto.setIdNo(userDto.getIdCardNo());//身份证
     * cloudCodeMerchantDto.setCardNo(bankCardDto.getBankCardNo());
     * cloudCodeMerchantDto.setBankCardPhone(bankCardDto.getMobileNo());//预留手机号
     * cloudCodeMerchantDto.setAlipayRate(alipayBiz.getRate());
     * cloudCodeMerchantDto.setWechatRate(wechatBiz.getRate());
     * cloudCodeMerchantDto.setSettleDays(Integer.valueOf(ymMemberDto.getSettlement().getCode()));//t+1=1 d+0=0
     * cloudCodeMerchantDto.setBizLicenseCode(storeDto.getBizLicence());
     * //身份证
     * PictureDto idCardFrontPicDto = pictureService.get(userDto.getUserId(), EPictureType.CARD_FRONT);
     * PictureDto idCardBackPicDto = pictureService.get(userDto.getUserId(), EPictureType.CARD_BACK);
     * if (idCardFrontPicDto == null || idCardBackPicDto == null) {
     * return ResultDtoFactory.toNack("请先上传身份证照");
     * }
     * cloudCodeMerchantDto.setLegalPersonIDImgFront(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPicDto.getPicturePath()));
     * cloudCodeMerchantDto.setLegalPersonIDImgBack(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardBackPicDto.getPicturePath()));
     * //营业执照
     * PictureDto bizLicensePicDto = pictureService.get(String.valueOf(storeDto.getStoreId()), EPictureType.STORE_LICENCE);
     * if (bizLicensePicDto == null) {
     * return ResultDtoFactory.toNack("请先上传营业执照");
     * }
     * cloudCodeMerchantDto.setBizLicenseImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicensePicDto.getPicturePath()));
     * //门头照
     * PictureDto bizPlaceSnapshot1PicDto = pictureService.get(String.valueOf(storeDto.getStoreId()), EPictureType.STORE_HEADER);
     * if (bizPlaceSnapshot1PicDto == null) {
     * return ResultDtoFactory.toNack("请先上传门头照");
     * }
     * cloudCodeMerchantDto.setBizPlaceSnapshot1Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizPlaceSnapshot1PicDto.getPicturePath()));
     * //合同
     * YmMemberSignDto ymMemberSignDto = ymMemberSignService.getYmMemberSignByStoreId(storeDto.getStoreId());
     * if (ymMemberSignDto == null || StringUtils.isEmpty(ymMemberSignDto.getFirstPageFilePath()) || StringUtils.isEmpty(ymMemberSignDto.getLastPageFilePath()) || !ymMemberSignDto.getSigned()) {
     * return ResultDtoFactory.toNack("请先上传合同");
     * }
     * cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getFirstPageFilePath()));
     * cloudCodeMerchantDto.setBizPlaceSnapshot3Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getLastPageFilePath()));
     * //cloudCodeMerchantDto.setOtherCertImage();
     * cloudCodeMerchantDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
     * cloudCodeService.updateMerchant(cloudCodeMerchantDto);
     * <p>
     * memberService.updateMemberBiz(memberBizDtos);
     * return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
     * }
     **/

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView export(YMMemberSearchDto memberSearchDto) {
        List<YMMemberSearchDto> list = memberService.getMemberExportList(memberSearchDto);
        List<YMMemberReportDto> data = new ArrayList<>();

        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(ymMemberSearchDto -> {
                YMMemberReportDto ymMemberReportDto = ConverterService.convert(ymMemberSearchDto, YMMemberReportDto.class);
                ymMemberReportDto.setFullArea(ymMemberSearchDto.getProvince()
                        + ymMemberSearchDto.getCity()
                        + ymMemberSearchDto.getArea());
                data.add(ymMemberReportDto);
            });
        }

        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName", "云码商户记录.xls");
        model.put("tempPath", "/templates/云码商户记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

    @RequestMapping(value = "/batchInto", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> batchInto(@RequestParam String ids) {
        List<Long> idList = new ArrayList<>();
        if (StringUtils.isNotEmpty(ids) && !"0".equals(ids)) {
            String[] memberIdList = ids.split(",");
            for (String id : Arrays.asList(memberIdList)) {
                idList.add(Long.valueOf(id));
            }
        }
        YMMemberSearchDto searchDto = new YMMemberSearchDto();
        searchDto.setIds(idList);
        List<YMMemberDto> memberList = memberService.getMemberList(searchDto);
        for (YMMemberDto memberDto : memberList) {
            //已解绑已删除不进件
            if (EMemberStatus.DELETE.equals(memberDto.getMemberStatus()) || EMemberStatus.UNBIND.equals(memberDto.getMemberStatus())) {
                continue;
            }
            //店铺信息
            StoreInfDto storeInfDto = storeService.findByStoreId(memberDto.getStoreId());
            if (storeInfDto == null) {
                LOGGER.info("member into store error ;{}", JSON.toJSONString(memberDto));
                continue;
            }
            //行业信息
            IndustryDto industryDto = industryService.getByMcc(storeInfDto.getIndustryMcc());
            if (industryDto == null) {
                LOGGER.info("member into industry error ;{}", JSON.toJSONString(memberDto));
                continue;
            }
            //用户信息
            UserInfoDto userInfoDto = userService.findUserByUserId(memberDto.getUserId());
            if (userInfoDto == null) {
                LOGGER.info("member into user error ;{}", JSON.toJSONString(memberDto));
                continue;
            }
//            else if (!userInfoDto.getIdentityAuth()) {
//                LOGGER.info("member into user identityauth error ;{}", JSON.toJSONString(memberDto));
//                continue;
//            }
            //银行卡信息
            BankCardDto bankCardDto = bankService.getBankCard(memberDto.getBankCardId());
            if (bankCardDto == null) {
                LOGGER.info("member into bankcard error ;{}", JSON.toJSONString(memberDto));
                continue;
            }
            //支持银行
            BankMappingDto bankMappingDto = bankMappingService.getBankMappingByShortName(bankCardDto.getBankCode());
//            if (bankMappingDto == null) {
//                LOGGER.info("member into bankMapping error ;{}", JSON.toJSONString(memberDto));
//                continue;
//            }
            //查费率
            YmMemberBizDto alipayBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.ALLIPAY);
            YmMemberBizDto whchatBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.WECHAT);
            YmMemberBizDto jdpayBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.JDPAY);
            YmMemberBizDto bestpayBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.BESTPAY);
            if (alipayBiz == null || whchatBiz == null || jdpayBiz == null || bestpayBiz == null) {
                LOGGER.info("member into biz error ;{}", JSON.toJSONString(memberDto));
                continue;
            }
            //省市区
            SysAreaInfDto provinceArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getProvinceId()));
            if (provinceArea == null) {
                LOGGER.info("member into area province error ;{}", JSON.toJSONString(memberDto));
                continue;
            }
            SysAreaInfDto cityArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getCityId()));
            if (cityArea == null) {
                LOGGER.info("member into area city error ;{}", JSON.toJSONString(memberDto));
                continue;
            }
            SysAreaInfDto areaArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getAreaId()));
            if (areaArea == null) {
                LOGGER.info("member into area area error ;{}", JSON.toJSONString(memberDto));
                continue;
            }
            //身份证图片
            PictureDto idCardFrontPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
            PictureDto idCardBackPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
            if (idCardFrontPicDto == null || idCardBackPicDto == null) {
                LOGGER.info("member into pic idcard error ;{}", JSON.toJSONString(memberDto));
                continue;
            }
            //营业执照
            PictureDto bizLicencePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
            if (bizLicencePicDto == null) {
                LOGGER.info("member into pic bizlicence error ;{}", JSON.toJSONString(memberDto));
                continue;
            }
            //门头照
            PictureDto storeHeaderPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
            if (storeHeaderPicDto == null) {
                LOGGER.info("member into pic storeheader error ;{}", JSON.toJSONString(memberDto));
                continue;
            }
            //店内照
            PictureDto storeInnerPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
//            if (storeInnerPicDto == null) {
//                LOGGER.info("member into pic error ;{}", JSON.toJSONString(memberDto));
//                continue;
//            }
            //银行卡照
            PictureDto bankCardPicDto = pictureService.get(String.valueOf(memberDto.getBankCardId()), EPictureType.BANK_CARD_FRONT);
//            if (bankCardPicDto == null) {
//                LOGGER.info("member into pic bankcard error ;{}", JSON.toJSONString(memberDto));
//                continue;
//            }
            MemberIntoDto memberIntoDto = new MemberIntoDto();
            memberIntoDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));//partnerid
            memberIntoDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));

            memberIntoDto.setMerchantId(memberDto.getMemberNo());
            memberIntoDto.setOutMerchantId(String.valueOf(storeInfDto.getStoreId()));//storeId
            memberIntoDto.setMerchantName(storeInfDto.getStoreName());//店铺名
            memberIntoDto.setAddress(storeInfDto.getProvince() + storeInfDto.getCity());//地址
            memberIntoDto.setLicenseDate(DateHelper.formatDate(storeInfDto.getBizEndDate()));//营业执照到期日
            memberIntoDto.setBizLicenseCode(storeInfDto.getBizLicence());//营业执照号
            memberIntoDto.setProvinceCode(provinceArea.getGbCode());//省市区
            memberIntoDto.setCityCode(cityArea.getGbCode());
            memberIntoDto.setAreaCode(areaArea.getGbCode());
            memberIntoDto.setProvince(storeInfDto.getProvince());
            memberIntoDto.setCity(storeInfDto.getCity());
            memberIntoDto.setArea(storeInfDto.getArea());
            memberIntoDto.setBusinessCatagory1(industryDto.getIndType());//行业
            memberIntoDto.setBusinessCatagory2(industryDto.getIndDesc());
            memberIntoDto.setMcc(industryDto.getMcc());

            memberIntoDto.setLegalPerson(userInfoDto.getUserName());//姓名
            memberIntoDto.setLegalPersonID(userInfoDto.getIdCardNo());//身份证
            memberIntoDto.setFirstName(userInfoDto.getUserName());//姓名
            memberIntoDto.setCellPhone(userInfoDto.getMobile());//手机
            //cloudCodeMerchantDto.setEmail();//空
            //cloudCodeMerchantDto.setMerchantShortName();//不传

            memberIntoDto.setBankName(bankCardDto.getBankName());//银行名
            if (bankMappingDto != null) {
                memberIntoDto.setBankCode(bankMappingDto.getBankCnapsCode());//行号
            }
            memberIntoDto.setSubbranchName(bankCardDto.getBankName());//支行名 空
            memberIntoDto.setBankProvinceCode(provinceArea.getGbCode());//省 code
            memberIntoDto.setBankProvince(storeInfDto.getProvince());//
            memberIntoDto.setBankCityCode(cityArea.getGbCode());
            memberIntoDto.setBankCity(storeInfDto.getCity());
            memberIntoDto.setCardName(bankCardDto.getBankCardName());//银行卡姓名
            memberIntoDto.setIdNo(userInfoDto.getIdCardNo());//身份证
            memberIntoDto.setCardNo(bankCardDto.getBankCardNo());
            memberIntoDto.setBankCardPhone(bankCardDto.getMobileNo());//预留手机号

            memberIntoDto.setAlipayRate(alipayBiz.getRate());//费率
            memberIntoDto.setWechatRate(whchatBiz.getRate());
            memberIntoDto.setJdpayRate(jdpayBiz.getRate());
            memberIntoDto.setBestpayRate(bestpayBiz.getRate());
            memberIntoDto.setSettleDays(Integer.valueOf(memberDto.getSettlement().getCode()));//t+1=1 d+0=0

            memberIntoDto.setLegalPersonIDImgFront(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPicDto.getPicturePath()));//身份证
            memberIntoDto.setLegalPersonIDImgBack(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardBackPicDto.getPicturePath()));
            memberIntoDto.setBizLicenseImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicencePicDto.getPicturePath()));//营业执照
            memberIntoDto.setBizPlaceSnapshot1Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeHeaderPicDto.getPicturePath()));//门头照
            if (storeInnerPicDto != null) {
                memberIntoDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeInnerPicDto.getPicturePath()));//店内照
            }
            memberIntoDto.setHoldCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPicDto.getPicturePath()));//手持身份证
            if (bankCardPicDto != null) {
                memberIntoDto.setBankCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bankCardPicDto.getPicturePath()));//银行卡照
            }

            //cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getFirstPageFilePath()));//合同
            //cloudCodeMerchantDto.setBizPlaceSnapshot3Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getLastPageFilePath()));//合同
            //cloudCodeMerchantDto.setOtherCertImage();

            memberService.memberIntoToCenter(memberIntoDto);
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

}
