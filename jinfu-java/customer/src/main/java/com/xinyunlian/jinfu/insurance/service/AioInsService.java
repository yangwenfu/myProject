package com.xinyunlian.jinfu.insurance.service;

import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.enums.ESysAreaLevel;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.insurance.dto.AioInsOrderReq;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.enums.EInvokerType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealSource;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;
import com.xinyunlian.jinfu.olduser.dto.OldUserDto;
import com.xinyunlian.jinfu.olduser.service.OldUserService;
import com.xinyunlian.jinfu.pingan.dto.PolicyDto;
import com.xinyunlian.jinfu.prod.enums.EProd;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.enums.ESource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by dongfangchao on 2017/3/3/0003.
 * 聚合APP 保险下单服务
 */
@Service
public class AioInsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AioInsService.class);

    @Autowired
    private ProdService prodService;

    @Autowired
    private SysAreaInfService sysAreaInfService;

    @Autowired
    private OldUserService oldUserService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private InsuranceOrderService insuranceOrderService;

    @Autowired
    private ApiBaiduService apiBaiduService;

    private static String TOBACCO_SHOP_IND_MCC = "5227";

    /**
     * 获取平安跳转url
     *
     * @param req
     * @return
     */
    public String getPinganUrl(AioInsOrderReq req) {
        LOGGER.debug("==开始处理聚合平安跳转==");

        String errorPinganUrl = AppConfigUtil.getConfig("error.pingan.url");
        String username = req.getContactName();

        try {

            //校验字段
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<AioInsOrderReq>> constraintViolations = validator.validate(req);
            for (ConstraintViolation<AioInsOrderReq> constraintViolation : constraintViolations) {
                LOGGER.error("user base check:" + constraintViolation.getPropertyPath().toString());
                return errorPinganUrl;
            }

            //映射金服库中的地区信息（省、市、区）
            SysAreaInfDto provinceDto = null;
            List<SysAreaInfDto> provinceDtoList = sysAreaInfService.getSysAreaByLvlAndName(ESysAreaLevel.PROVINCE, req.getProvince(), null);
            if (CollectionUtils.isEmpty(provinceDtoList) || provinceDtoList.size() > 1){
                LOGGER.error("指定的省不存在或存在多个匹配的省，province=" + req.getProvince());
                return errorPinganUrl;
            }else {
                provinceDto = provinceDtoList.get(0);
            }

            SysAreaInfDto cityDto = null;
            List<SysAreaInfDto> cityDtoList = sysAreaInfService.getSysAreaByLvlAndName(ESysAreaLevel.CITY, req.getCity(), provinceDto.getId());
            if (CollectionUtils.isEmpty(cityDtoList) || cityDtoList.size() > 1){
                LOGGER.error("指定的市不存在或存在多个匹配的市，city=" + req.getCity());
                return errorPinganUrl;
            }else {
                cityDto = cityDtoList.get(0);
            }

            SysAreaInfDto townDto = null;
            List<SysAreaInfDto> townDtoList = sysAreaInfService.getSysAreaByLvlAndName(ESysAreaLevel.COUNTY, req.getTown(), cityDto.getId());
            if (CollectionUtils.isEmpty(townDtoList) || townDtoList.size() > 1){
                LOGGER.error("指定的区不存在或存在多个匹配的区，town=" + req.getTown());
                return errorPinganUrl;
            }else {
                townDto = townDtoList.get(0);
            }

            //产品区域检查
            boolean isPermit = false;
            if (townDto != null){
                isPermit = prodService.checkProdArea(EProd.S01001.getCode(), townDto.getId(), TOBACCO_SHOP_IND_MCC);
            }
            if (!isPermit){
                LOGGER.error(provinceDto.getName() + cityDto.getName() + townDto.getName() + "地区未授权");
                return errorPinganUrl;
            }

            //正式开始处理数据

            //检查用户是否已经存在
            OldUserDto oldUserDto = oldUserService.findByTobaccoCertificateNo(req.getTobaccoCertificateNo());
            if (oldUserDto == null){
                //插入old_user表
                oldUserDto = new OldUserDto();
                oldUserDto.setMobile(req.getContactMobile());
                oldUserDto.setUserName(req.getContactName());
                oldUserDto.setTobaccoCertificateNo(req.getTobaccoCertificateNo());
                oldUserDto.setIdentityAuth(false);
                oldUserService.addOldUser(oldUserDto);
            }

            //检查店铺是否已经存在
            StringBuilder areaTreePath = new StringBuilder();
            StoreInfDto storeInfDto = storeService.findByTobaccoCertificateNo(req.getTobaccoCertificateNo());
            if (storeInfDto == null){
                //插入store_info表
                storeInfDto = new StoreInfDto();
                storeInfDto.setTobaccoCertificateNo(req.getTobaccoCertificateNo());
                storeInfDto.setAddress(req.getDetailAddress());
                storeInfDto.setStoreName(req.getStoreName());
                storeInfDto.setProvince(req.getProvince());
                storeInfDto.setCity(req.getCity());
                storeInfDto.setArea(req.getTown());
                storeInfDto.setSource(ESource.IMPORT);

                String districtId = "-1";
                if (provinceDto != null){
                    storeInfDto.setProvinceId(String.valueOf(provinceDto.getId()));
                    areaTreePath.append(",").append(provinceDto.getId());
                    districtId = String.valueOf(provinceDto.getId());

                    if (cityDto != null){
                        areaTreePath.append(",").append(cityDto.getId());
                        districtId = String.valueOf(cityDto.getId());

                        storeInfDto.setCityId(String.valueOf(cityDto.getId()));
                        if (townDto != null){
                            areaTreePath.append(",").append(townDto.getId());
                            districtId = String.valueOf(townDto.getId());

                            storeInfDto.setAreaId(String.valueOf(townDto.getId()));
                        }
                    }
                }
                areaTreePath.append(",");
                storeInfDto.setDistrictId(districtId);
                storeInfDto = storeService.save(storeInfDto);
                apiBaiduService.updatePoint(storeInfDto);
            }

            //插入保单记录表
            PerInsuranceInfoDto perInsuranceInfoDto = new PerInsuranceInfoDto();
            perInsuranceInfoDto.setOperatorName(username);
            perInsuranceInfoDto.setOrderDate(new Date());
            perInsuranceInfoDto.setTobaccoPermiNo(req.getTobaccoCertificateNo());
            perInsuranceInfoDto.setStoreAreaTreePath(areaTreePath.toString());
            perInsuranceInfoDto.setStoreId(storeInfDto.getStoreId());
            perInsuranceInfoDto.setStoreName(req.getStoreName());
            perInsuranceInfoDto.setOrderStatus(EPerInsOrderStatus.INPROCESS);
            perInsuranceInfoDto.setDealType(EPerInsDealType.MANAGERSERVICE);
            perInsuranceInfoDto.setDealSource(EPerInsDealSource.EORDER);
            perInsuranceInfoDto.setProductId(EProd.S01001.getCode());
            perInsuranceInfoDto.setProductName(EProd.S01001.getText());
            String insOrderNo = insuranceOrderService.addInsOrderInfo(perInsuranceInfoDto);

            //生成平安跳转url
            PolicyDto policyDto = new PolicyDto();
            policyDto.setAssuranceOrderNo(insOrderNo);
            policyDto.setProvinceCnName(provinceDto.getName());
            policyDto.setProvinceCode(provinceDto.getGbCode());
            policyDto.setCityCnName(cityDto.getName());
            policyDto.setCityCode(cityDto.getGbCode());
            policyDto.setCountyCnName(townDto.getName());
            policyDto.setCountyCode(townDto.getGbCode());

            StringBuilder address = new StringBuilder();
            if (StringUtils.isEmpty(req.getDetailAddress())){
                address.append(provinceDto.getName())
                        .append(cityDto.getName())
                        .append(townDto.getName());
            }else {
                address.append(req.getDetailAddress());
            }

            policyDto.setAddress(address.toString());
            policyDto.setContactName(req.getContactName());
            policyDto.setContactMobile(req.getContactMobile());
            policyDto.setStoreName(req.getStoreName());
            policyDto.setTobaccoCertificateNo(req.getTobaccoCertificateNo());

            String pinganUrl = insuranceOrderService.getPinganUrl(policyDto, EInvokerType.APP);
            LOGGER.debug("==聚合返回平安跳转链接地址==");
            LOGGER.debug(pinganUrl);
            return pinganUrl;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return errorPinganUrl;
        }
    }

}
