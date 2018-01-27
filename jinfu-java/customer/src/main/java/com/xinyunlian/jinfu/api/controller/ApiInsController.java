package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.dto.InsureAcquireReq;
import com.xinyunlian.jinfu.api.dto.InsureAcquireResp;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.api.validate.OpenApi;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.enums.*;
import com.xinyunlian.jinfu.insurance.service.InsuranceOrderService;
import com.xinyunlian.jinfu.olduser.dto.OldUserDto;
import com.xinyunlian.jinfu.olduser.service.OldUserService;
import com.xinyunlian.jinfu.pingan.dto.PolicyDto;
import com.xinyunlian.jinfu.prod.enums.EProd;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.ClientSaltDto;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.ClientSaltService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by DongFC on 2016-11-07.
 */
@RestController
@RequestMapping("/open-api/ins")
public class ApiInsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiInsController.class);

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    private ClientSaltService clientSaltService;

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
     * E订单APP店铺保下单接口
     * @param req
     * @param result
     * @return
     */
    @OpenApi
    @RequestMapping(value = "/insuranceorderproxy", method= RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public InsureAcquireResp insOrderProxy(@RequestBody @Valid InsureAcquireReq req, BindingResult result){
        InsureAcquireResp resp=new InsureAcquireResp();

        try {

            //校验serialno
            StringBuilder sb = new StringBuilder("jinfu_serial_no_").append(req.getSerial_no()).append("_app_user_id").append(req.getClient_id());
            String redisKey = sb.toString();
            String val  = redisCacheManager.getCache(CacheType.DEFAULT).get(redisKey, String.class);
            if (val != null){
                LOGGER.error("重复的请求：serial_no=" + req.getSerial_no()+",Client_id="+req.getClient_id());
                resp.setResult_code(EInsureResult.FAIL.getCode());
                resp.setResult_msg("重复的请求");
                return resp;
            }else{
                redisCacheManager.getCache(CacheType.DEFAULT).put(redisKey, "insuranceorderproxy");
            }

            //字段校验
            if (result.hasErrors()){
                LOGGER.error(result.getFieldError().getDefaultMessage());
                resp.setResult_code(EInsureResult.FAIL.getCode());
                resp.setResult_msg(result.getFieldError().getDefaultMessage());
                return resp;
            }

            //验签
            ClientSaltDto clientSaltDto = clientSaltService.getClientSalt(req.getClient_id());
            String salt = clientSaltDto.getSalt();
            String encryptString = EncryptUtil.encryptMd5(req.signSrc() + salt);
            if(!encryptString.equalsIgnoreCase(req.getSign_msg())){
                LOGGER.error("signature验证失败：Client_id="+req.getClient_id()+";content="+req.signSrc());
                resp.setResult_code(EInsureResult.FAIL.getCode());
                resp.setResult_msg("signature验证失败!");
                return resp;
            }

            //产品区域检查
            SysAreaInfDto townDto = sysAreaInfService.getSysAreaByGbCode(req.getTown_code());
            boolean isPermit = false;
            if (townDto != null){
                isPermit = prodService.checkProdArea(EProd.S01001.getCode(), townDto.getId(), TOBACCO_SHOP_IND_MCC);
            }
            if (!isPermit){
                resp.setResult_code(EInsureResult.FAIL.getCode());
                resp.setResult_msg(req.getTown()+req.getTown_code()+"区域未开放");
                return resp;
            }

            //正式开始处理数据

            //检查用户是否已经存在
            OldUserDto oldUserDto = oldUserService.findByTobaccoCertificateNo(req.getLicence_no());
            StringBuilder areaTreePath = new StringBuilder();

            if (oldUserDto == null){
                //插入old_user表
                oldUserDto = new OldUserDto();
                oldUserDto.setMobile(req.getContact_no());
                oldUserDto.setUserName(req.getContact_name());
                oldUserDto.setTobaccoCertificateNo(req.getLicence_no());
                oldUserDto.setIdentityAuth(false);
                oldUserDto = oldUserService.addOldUser(oldUserDto);
            }

            //检查店铺是否已经存在
            StoreInfDto storeInfDto = storeService.findByTobaccoCertificateNo(req.getLicence_no());
            if (storeInfDto == null){
                //插入store_info表
                storeInfDto = new StoreInfDto();
                storeInfDto.setTobaccoCertificateNo(req.getLicence_no());
                storeInfDto.setAddress(req.getDetail_address());
                storeInfDto.setStoreName(req.getStore_name());
                storeInfDto.setProvince(req.getProvince());
                storeInfDto.setCity(req.getCity());
                storeInfDto.setArea(req.getTown());
                storeInfDto.setSource(ESource.IMPORT);

                SysAreaInfDto provinceDto = sysAreaInfService.getSysAreaByGbCode(req.getProvince_code());
                String districtId = "-1";
                if (provinceDto != null){
                    storeInfDto.setProvinceId(provinceDto.getId().toString());
                    areaTreePath.append(",").append(provinceDto.getId());
                    districtId = provinceDto.getId().toString();

                    SysAreaInfDto cityDto = sysAreaInfService.getSysAreaByGbCode(req.getCity_code());
                    if (cityDto != null){
                        areaTreePath.append(",").append(cityDto.getId());
                        districtId = cityDto.getId().toString();

                        storeInfDto.setCityId(cityDto.getId().toString());
                        if (townDto != null){
                            areaTreePath.append(",").append(townDto.getId());
                            districtId = townDto.getId().toString();

                            storeInfDto.setAreaId(townDto.getId().toString());
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
            perInsuranceInfoDto.setOperatorName(req.getAgent_id());
            perInsuranceInfoDto.setOrderDate(new Date());
            perInsuranceInfoDto.setTobaccoPermiNo(req.getLicence_no());
            perInsuranceInfoDto.setStoreAreaTreePath(areaTreePath.toString());
            perInsuranceInfoDto.setStoreId(storeInfDto.getStoreId());
            perInsuranceInfoDto.setStoreName(req.getStore_name());
            perInsuranceInfoDto.setOrderStatus(EPerInsOrderStatus.INPROCESS);
            perInsuranceInfoDto.setDealType(EPerInsDealType.MANAGERSERVICE);
            perInsuranceInfoDto.setDealSource(EPerInsDealSource.EORDER);
            perInsuranceInfoDto.setProductId(EProd.S01001.getCode());
            perInsuranceInfoDto.setProductName(EProd.S01001.getText());

            String insOrderNo = insuranceOrderService.addInsOrderInfo(perInsuranceInfoDto);

            //生成平安跳转url
            PolicyDto policyDto = new PolicyDto();
            policyDto.setAssuranceOrderNo(insOrderNo);
            policyDto.setAddress(req.getDetail_address());
            policyDto.setCityCnName(req.getCity());
            policyDto.setCityCode(req.getCity_code());
            policyDto.setContactMobile(req.getContact_no());
            policyDto.setContactName(req.getContact_name());
            policyDto.setCountyCnName(req.getTown());
            policyDto.setCountyCode(req.getTown_code());
            policyDto.setStoreName(req.getStore_name());
            policyDto.setProvinceCnName(req.getProvince());
            policyDto.setProvinceCode(req.getProvince_code());
            policyDto.setTobaccoCertificateNo(req.getLicence_no());

            String pinganUrl = insuranceOrderService.getPinganUrl(policyDto, EInvokerType.APP);

            //拼装返回信息
            resp.setResult_code(EInsureResult.SUCCESS.getCode());
            resp.setSign_type("1");
            resp.setUser_id(oldUserDto.getUserId());
            resp.setHttp_url(pinganUrl);
            String respSignMsg = EncryptUtil.encryptMd5(resp.signSrc() + salt);
            resp.setSign_msg(respSignMsg);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            resp.setResult_code(EInsureResult.FAIL.getCode());
            resp.setResult_msg(e.getMessage());
        }

        return resp;
    }

}
