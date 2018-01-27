package com.xinyunlian.jinfu.ins.controller;

import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.ins.dto.PinganQuotePriceDto;
import com.xinyunlian.jinfu.ins.dto.PreOrderUserInfoEditReq;
import com.xinyunlian.jinfu.ins.dto.PreOrderUserInfoReq;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;
import com.xinyunlian.jinfu.insurance.service.InsuranceOrderService;
import com.xinyunlian.jinfu.pingan.dto.*;
import com.xinyunlian.jinfu.pingan.service.PinganRpcService;
import com.xinyunlian.jinfu.prod.enums.EProd;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.MgtUserService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
@RestController
@RequestMapping(value = "/ins/pingan")
public class PinganInsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PinganInsController.class);

    @Autowired
    private PinganRpcService pinganRpcService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;

    @Autowired
    private SysAreaInfService sysAreaInfService;

    @Autowired
    private InsuranceOrderService insuranceOrderService;

    @Autowired
    private MgtUserService mgtUserService;

    /**
     * 获取所有的保额档次列表
     * @return
     */
    @GetMapping(value = "/getInsuredGrade")
    public ResultDto getInsuredGrade(){
        List<PinganInsuredGradeDto> list = pinganRpcService.getAllInsuredGrade();
        return ResultDtoFactory.toAckData(list);
    }

    /**
     * 根据父节点获取地区列表
     * @param parent
     * @return
     */
    @GetMapping(value = "/getPinganRegionList")
    public ResultDto getPinganRegionList(Long parent){
        List<PinganRegionDto> list = pinganRpcService.getPinganRegionList(parent);
        return ResultDtoFactory.toAckData(list);
    }

    /**
     * 根据烟草证号查询店铺信息
     * @param tobaccoCertificateNo
     * @return
     */
    @GetMapping(value = "/getStoreByTbc")
    public ResultDto getStoreByTbc(@RequestParam String tobaccoCertificateNo){
        if (StringUtils.isEmpty(tobaccoCertificateNo)){
            return ResultDtoFactory.toNack("烟草证号不能为空");
        }

        StoreInfDto storeInfDto = storeService.findByTobaccoCertificateNo(tobaccoCertificateNo);
        if (storeInfDto != null){
            UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
            if (userInfoDto != null){
                PreOrderUserInfoDto retInfo = new PreOrderUserInfoDto();
                retInfo.setTobaccoCertificateNo(storeInfDto.getTobaccoCertificateNo());
                retInfo.setStoreName(storeInfDto.getStoreName());
                retInfo.setUserName(userInfoDto.getUserName());
                retInfo.setLinkmanName(userInfoDto.getUserName());
                retInfo.setMobile(userInfoDto.getMobile());
                retInfo.setEmail(userInfoDto.getEmail());
                retInfo.setStoreId(storeInfDto.getStoreId());

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 1);
                retInfo.setInsuranceBeginDate(DateHelper.getStartDate(calendar.getTime()));

                //省
                Boolean mappingProvince = false;
                if (!StringUtils.isEmpty(storeInfDto.getProvinceId())){
                    SysAreaInfDto province = sysAreaInfService.getSysAreaInfById(Long.parseLong(storeInfDto.getProvinceId()));
                    if (province != null){
                        PinganRegionDto provinceRegion = pinganRpcService.getPinganRegion(province.getGbCode(), 1);
                        if (provinceRegion != null) {
                            retInfo.setProvinceGbCode(provinceRegion.getGbCode());
                            retInfo.setProvinceId(provinceRegion.getId());
                            retInfo.setProvinceName(provinceRegion.getRegionName());
                            mappingProvince = true;
                        }
                    }
                }

                //市
                Boolean mappingCity = false;
                if (!StringUtils.isEmpty(storeInfDto.getCityId()) && mappingProvince){
                    SysAreaInfDto city = sysAreaInfService.getSysAreaInfById(Long.parseLong(storeInfDto.getCityId()));
                    if (city != null){
                        PinganRegionDto cityRegion = pinganRpcService.getPinganRegion(city.getGbCode(), 2);
                        if (cityRegion != null) {
                            retInfo.setCityGbCode(cityRegion.getGbCode());
                            retInfo.setCityId(cityRegion.getId());
                            retInfo.setCityName(cityRegion.getRegionName());
                            mappingCity = true;
                        }
                    }
                }

                //区或县
                if (!StringUtils.isEmpty(storeInfDto.getAreaId()) && mappingCity){
                    SysAreaInfDto area = sysAreaInfService.getSysAreaInfById(Long.parseLong(storeInfDto.getAreaId()));
                    if (area != null){
                        PinganRegionDto countyRegion = pinganRpcService.getPinganRegion(area.getGbCode(), 3);
                        if (countyRegion != null) {
                            retInfo.setCountyGbCode(countyRegion.getGbCode());
                            retInfo.setCountyId(countyRegion.getId());
                            retInfo.setCountyName(countyRegion.getRegionName());
                        }
                    }
                }

                //详细地址
                StringBuilder detailAddress = new StringBuilder();
                if (!StringUtils.isEmpty(storeInfDto.getStreet())){
                    detailAddress.append(storeInfDto.getStreet());
                }
                if (!StringUtils.isEmpty(storeInfDto.getAddress())){
                    detailAddress.append(storeInfDto.getAddress());
                }
                retInfo.setDetailAddress(detailAddress.toString());

                return ResultDtoFactory.toAckData(retInfo);
            }else {
                return ResultDtoFactory.toNack("不存在用户信息");
            }
        }
        return ResultDtoFactory.toNack("没有查询到此店铺");
    }

    /**
     * 查询平安报价
     * @param quotePriceDto
     * @return
     */
    @PostMapping(value = "/quotePrice")
    public ResultDto quotePrice(@RequestBody @Valid PinganQuotePriceDto quotePriceDto, BindingResult result){
        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        try {
            PinganQuotePriceRetDto quotePrice =
                    pinganRpcService.getPinganQuotePrice(quotePriceDto.getInsuredGradeCode(), quotePriceDto.getCountyId());
            return ResultDtoFactory.toAckData(quotePrice);
        } catch (BizServiceException e) {
            LOGGER.error("报价查询失败");
            return ResultDtoFactory.toNack("报价查询失败");
        }

    }

    /**
     * 确认投保
     * @param req
     * @return
     */
    @PostMapping(value = "/confirmInsured")
    public ResultDto confirmInsured(@RequestBody @Valid PreOrderUserInfoReq req, BindingResult result){
        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        String userId = SecurityContext.getCurrentUserId();
        MgtUserDto mgtUserInf = mgtUserService.getMgtUserInf(userId);
        if (mgtUserInf == null){
            return ResultDtoFactory.toNack("当前用户不存在");
        }

        StoreInfDto storeInfo = storeService.findByStoreId(req.getStoreId());
        if (storeInfo == null){
            return ResultDtoFactory.toNack("当前店铺不存在");
        }

        UserInfoDto userInfo = userService.findUserByUserId(storeInfo.getUserId());
        if (userInfo == null){
            return ResultDtoFactory.toNack("被保用户不存在");
        }

        PerInsuranceInfoDto dbInsOrderDto = null;
        try {
            //插入订单表
            PerInsuranceInfoDto perInsuranceInfoDto = new PerInsuranceInfoDto();
            perInsuranceInfoDto.setDealType(req.getDealType());
            perInsuranceInfoDto.setDealSource(req.getDealSource());
            perInsuranceInfoDto.setOperatorName(mgtUserInf.getName());
            perInsuranceInfoDto.setStoreId(req.getStoreId());
            perInsuranceInfoDto.setOrderDate(new Date());
            perInsuranceInfoDto.setTobaccoPermiNo(req.getTobaccoCertificateNo());
            perInsuranceInfoDto.setStoreName(req.getStoreName());
            perInsuranceInfoDto.setPhoneNo(req.getMobile());
            perInsuranceInfoDto.setStoreAddress(req.getDetailAddress());
            perInsuranceInfoDto.setOrderStatus(EPerInsOrderStatus.INPROCESS);
            perInsuranceInfoDto.setProductId(EProd.S01001.getCode());
            perInsuranceInfoDto.setProductName(EProd.S01001.getText());

            StringBuilder storeAreaTreePath = new StringBuilder();
            if (!StringUtils.isEmpty(storeInfo.getProvinceId())){
                storeAreaTreePath.append(",").append(storeInfo.getProvinceId()).append(",");
            }
            if (!StringUtils.isEmpty(storeAreaTreePath) && !StringUtils.isEmpty(storeInfo.getCityId())){
                storeAreaTreePath.append(storeInfo.getCityId()).append(",");
            }
            if (!StringUtils.isEmpty(storeAreaTreePath) && !StringUtils.isEmpty(storeInfo.getAreaId())){
                storeAreaTreePath.append(storeInfo.getAreaId()).append(",");
            }
            if (!StringUtils.isEmpty(storeAreaTreePath) && !StringUtils.isEmpty(storeInfo.getStreetId())){
                storeAreaTreePath.append(storeInfo.getStreetId()).append(",");
            }
            perInsuranceInfoDto.setStoreAreaTreePath(storeAreaTreePath.toString());

            dbInsOrderDto = insuranceOrderService.addAndReturnInsOrderInfo(perInsuranceInfoDto);

            //插入订单用户信息表，关联保险订单
            PreOrderUserInfoDto preOrderUserInfoDto = ConverterService.convert(req, PreOrderUserInfoDto.class);
            preOrderUserInfoDto.setPreInsuranceOrderNo(dbInsOrderDto.getPerInsuranceOrderNo());
            preOrderUserInfoDto.setIdCardNo(userInfo.getIdCardNo());
            preOrderUserInfoDto.setPlanCode(getPlanCodes(req.getPlanCodeList()));

            PreOrderUserInfoDto dbPreOrderUserInfoDto = pinganRpcService.addPreOrderUserInfo(preOrderUserInfoDto);

            //去平安投保
            QunarApplyResultDto response = pinganRpcService.qunarApply(dbPreOrderUserInfoDto);

            //更新保单信息
            return updateInsuredOrder(response, dbInsOrderDto);
        } catch (Exception e) {
            if (dbInsOrderDto != null){
                dbInsOrderDto.setOrderStatus(EPerInsOrderStatus.FAILURE);
                insuranceOrderService.updateInsOrder(dbInsOrderDto);
            }
            LOGGER.error("投保失败", e);
            return ResultDtoFactory.toNack("投保失败");
        }
    }

    /**
     * 编辑后再投保
     * @param req
     * @param result
     * @return
     */
    @PostMapping(value = "/editToConfirmInsured")
    public ResultDto editToConfirmInsured(@RequestBody @Valid PreOrderUserInfoEditReq req, BindingResult result){
        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        String userId = SecurityContext.getCurrentUserId();
        MgtUserDto mgtUserInf = mgtUserService.getMgtUserInf(userId);
        if (mgtUserInf == null){
            return ResultDtoFactory.toNack("当前用户不存在");
        }

        StoreInfDto storeInfo = storeService.findByStoreId(req.getStoreId());
        if (storeInfo == null){
            return ResultDtoFactory.toNack("当前店铺不存在");
        }

        UserInfoDto userInfo = userService.findUserByUserId(storeInfo.getUserId());
        if (userInfo == null){
            return ResultDtoFactory.toNack("被保用户不存在");
        }

        PerInsuranceInfoDto dbInsOrderDto = null;
        try {
            //更新订单表
            PerInsuranceInfoDto updateInsOrder = new PerInsuranceInfoDto();
            updateInsOrder.setPerInsuranceOrderNo(req.getPreInsuranceOrderNo());
            updateInsOrder.setDealType(req.getDealType());
            updateInsOrder.setDealSource(req.getDealSource());
            updateInsOrder.setOperatorName(mgtUserInf.getName());
            updateInsOrder.setStoreId(req.getStoreId());
            updateInsOrder.setOrderDate(new Date());
            updateInsOrder.setTobaccoPermiNo(req.getTobaccoCertificateNo());
            updateInsOrder.setStoreName(req.getStoreName());
            updateInsOrder.setPhoneNo(req.getMobile());
            updateInsOrder.setStoreAddress(req.getDetailAddress());
            updateInsOrder.setOrderStatus(EPerInsOrderStatus.INPROCESS);
            updateInsOrder.setProductId(EProd.S01001.getCode());
            updateInsOrder.setProductName(EProd.S01001.getText());

            StringBuilder storeAreaTreePath = new StringBuilder();
            if (!StringUtils.isEmpty(storeInfo.getProvinceId())){
                storeAreaTreePath.append(",").append(storeInfo.getProvinceId()).append(",");
            }
            if (!StringUtils.isEmpty(storeAreaTreePath) && !StringUtils.isEmpty(storeInfo.getCityId())){
                storeAreaTreePath.append(storeInfo.getCityId()).append(",");
            }
            if (!StringUtils.isEmpty(storeAreaTreePath) && !StringUtils.isEmpty(storeInfo.getAreaId())){
                storeAreaTreePath.append(storeInfo.getAreaId()).append(",");
            }
            if (!StringUtils.isEmpty(storeAreaTreePath) && !StringUtils.isEmpty(storeInfo.getStreetId())){
                storeAreaTreePath.append(storeInfo.getStreetId()).append(",");
            }
            updateInsOrder.setStoreAreaTreePath(storeAreaTreePath.toString());

            dbInsOrderDto = insuranceOrderService.updateInsOrder(updateInsOrder);

            //更新订单用户信息表，关联保险订单
            PreOrderUserInfoDto oldOrderUserInfo = pinganRpcService.getPreOrderUserInfo(req.getPreInsuranceOrderNo());
            if (oldOrderUserInfo == null){
                return ResultDtoFactory.toNack("原订单信息不存在");
            }

            Long oldOrderUserInfoId = oldOrderUserInfo.getId();
            ConverterService.convert(req, oldOrderUserInfo);
            oldOrderUserInfo.setId(oldOrderUserInfoId);
            oldOrderUserInfo.setIdCardNo(userInfo.getIdCardNo());
            oldOrderUserInfo.setPlanCode(getPlanCodes(req.getPlanCodeList()));

            PreOrderUserInfoDto dbPreOrderUserInfoDto = pinganRpcService.updatePreOrderUserInfo(oldOrderUserInfo);

            //去平安投保
            QunarApplyResultDto response = pinganRpcService.qunarApply(dbPreOrderUserInfoDto);

            //更新保单信息
            return updateInsuredOrder(response, dbInsOrderDto);
        } catch (Exception e) {
            if (dbInsOrderDto != null){
                dbInsOrderDto.setOrderStatus(EPerInsOrderStatus.FAILURE);
                insuranceOrderService.updateInsOrder(dbInsOrderDto);
            }
            LOGGER.error("投保失败", e);
            return ResultDtoFactory.toNack("投保失败");
        }
    }

    /**
     * 获取下单信息
     * @param preInsOrderNo 订单号
     * @return 成功失败标志
     */
    @GetMapping(value = "/getPreOrderUserInfo")
    public ResultDto getPreOrderUserInfo(String preInsOrderNo){
        PreOrderUserInfoDto preOrderUserInfo = pinganRpcService.getPreOrderUserInfo(preInsOrderNo);
        return ResultDtoFactory.toAckData(preOrderUserInfo);
    }

    /**
     * 更新保单信息
     * @param response 平安投保响应
     * @param dbInsOrderDto 订单对象
     * @return
     */
    private ResultDto updateInsuredOrder(QunarApplyResultDto response, PerInsuranceInfoDto dbInsOrderDto){
        if (PinganOpenApiRetCode.SUCCESS.equals(response.getResponseCode())){
            dbInsOrderDto.setInsuredPerson(response.getInsurantName());
            Long insuranceAmt = 0l;
            BigDecimal oneThousand = new BigDecimal("1000");
            if (response.getTotalInsuredAmount() != null){
                insuranceAmt = response.getTotalInsuredAmount().multiply(oneThousand).longValue();
            }
            dbInsOrderDto.setInsuranceAmt(insuranceAmt);
            Long insuranceFee = 0l;
            if (response.getTotalActualPremium() != null){
                insuranceFee = response.getTotalActualPremium().multiply(oneThousand).longValue();
            }
            dbInsOrderDto.setInsuranceFee(insuranceFee);
            dbInsOrderDto.setPolicyHolder(response.getApplicantName());
            dbInsOrderDto.setOrderStartTime(response.getInsuranceBeginDate());
            dbInsOrderDto.setOrderStopTime(response.getInsuranceEndDate());
            dbInsOrderDto.setOrderStatus(EPerInsOrderStatus.SUCCESS);
            insuranceOrderService.updateInsOrder(dbInsOrderDto);

            LOGGER.debug("投保处理完成");
            return ResultDtoFactory.toAck("投保成功");
        }else {
            dbInsOrderDto.setOrderStatus(EPerInsOrderStatus.FAILURE);
            dbInsOrderDto.setOrderStatusCause(response.getResponseMsg());
            insuranceOrderService.updateInsOrder(dbInsOrderDto);
            return ResultDtoFactory.toNack("投保失败，" + response.getResponseMsg());
        }
    }

    /**
     * 拼接险种code
     * @param list
     * @return
     */
    private String getPlanCodes(List<String> list){
        if (!CollectionUtils.isEmpty(list)){
            StringBuilder planCodeBuilder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                planCodeBuilder.append(list.get(i));
                if (i != list.size() - 1){
                    planCodeBuilder.append(",");
                }
            }
            return planCodeBuilder.toString();
        }
        return null;
    }

}
