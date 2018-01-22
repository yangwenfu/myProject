package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.dto.InsureCallBackReq;
import com.xinyunlian.jinfu.api.dto.InsureCallBackResp;
import com.xinyunlian.jinfu.api.dto.VehicleInsDetailStringReq;
import com.xinyunlian.jinfu.api.dto.ZhonganReturnMsg;
import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.dealer.dto.DealerUserOrderDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserOrderStatus;
import com.xinyunlian.jinfu.dealer.service.DealerUserOrderService;
import com.xinyunlian.jinfu.insurance.dto.InsureCallBackRespDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.enums.EInsureResult;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;
import com.xinyunlian.jinfu.insurance.service.InsuranceOrderService;
import com.xinyunlian.jinfu.insurance.service.VehicleInsDetailService;
import com.xinyunlian.jinfu.user.dto.ClientSaltDto;
import com.xinyunlian.jinfu.user.service.ClientSaltService;
import com.xinyunlian.jinfu.zhongan.dto.VehInsDetailNotifyDto;
import com.xinyunlian.jinfu.zhongan.dto.VehInsNotifyDto;
import com.xinyunlian.jinfu.zhongan.dto.VehicleInsDetailDto;
import com.xinyunlian.jinfu.zhongan.dto.VehicleInsDetailStringDto;
import com.xinyunlian.jinfu.zhongan.enums.EZaNotifyType;
import com.xinyunlian.jinfu.zhongan.enums.EZaVehicleType;
import com.xinyunlian.jinfu.zhongan.enums.EZhonganRetStatus;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by DongFC on 2016-10-08.
 */
@RestController
@RequestMapping("/open-api")
public class ApiInsureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiInsureController.class);

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    private InsuranceOrderService insuranceOrderService;

    @Autowired
    private ClientSaltService clientSaltService;

    @Autowired
    private DealerUserOrderService dealerUserOrderService;

    @Autowired
    private VehicleInsDetailService vehicleInsDetailService;

    /**
     * 平安店铺保保单回传
     * @param res
     * @param callBackReq
     * @param result
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "insurance_callback", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public Object insureCallBack(HttpServletResponse res, @RequestBody @Valid InsureCallBackReq callBackReq, BindingResult result) throws IOException {
        LOGGER.info("insurance_callback请求报文:"+ JsonUtil.toJson(callBackReq));

        InsureCallBackResp resp = new InsureCallBackResp();
        try{
            resp.setSign_type(callBackReq.getSign_type());
            //验签
            ClientSaltDto clientSaltDto = clientSaltService.getClientSalt(callBackReq.getClient_id());
            String salt = clientSaltDto.getSalt();

            //校验参数
            if (result.hasErrors()){
                resp.setResult(EInsureResult.FAIL.getCode());
                resp.setMsg(result.getFieldError().getDefaultMessage());
                String respEncryptString = EncryptUtil.encryptMd5(resp.signSrc() + salt);
                resp.setSignature(respEncryptString);
                sendResult(resp, res);
                return null;
            }

            String encryptString = EncryptUtil.encryptMd5(callBackReq.signSrc() + salt);
            if(!encryptString.equalsIgnoreCase(callBackReq.getSignature())){
                LOGGER.error("签名验证失败：Client_id="+callBackReq.getClient_id()+";content="+callBackReq.signSrc());
                resp.setResult(EInsureResult.FAIL.getCode());
                resp.setMsg("signature验证失败");
                String respEncryptString = EncryptUtil.encryptMd5(resp.signSrc() + salt);
                resp.setSignature(respEncryptString);
                sendResult(resp, res);
                return null;
            }

            //校验serialno
            String redisKey = getSerialNoKey(callBackReq.getSerialno(), callBackReq.getClient_id());
            String val  = redisCacheManager.getCache(CacheType.DEFAULT).get(redisKey, String.class);
            if (val != null){
                LOGGER.error("重复的请求：serial_no="+callBackReq.getSerialno()+",Client_id="+callBackReq.getClient_id());
                resp.setResult(EInsureResult.FAIL.getCode());
                resp.setMsg("重复的请求");
                String respEncryptString = EncryptUtil.encryptMd5(resp.signSrc() + salt);
                resp.setSignature(respEncryptString);
                sendResult(resp, res);
                return null;
            }else{
                redisCacheManager.getCache(CacheType.DEFAULT).put(redisKey, "insurance_call_back");
            }

            //数据处理
            PerInsuranceInfoDto dto = new PerInsuranceInfoDto();
            dto.setPerInsuranceOrderNo(callBackReq.getPolicy_no());
            dto.setTobaccoPermiNo(callBackReq.getLicence_no());
            dto.setStoreName(callBackReq.getStore_name());
            dto.setStoreAddress(callBackReq.getStore_addr());
            dto.setPhoneNo(callBackReq.getPhone());
            dto.setPolicyHolder(callBackReq.getPolicy_holder());
            dto.setInsuredPerson(callBackReq.getInsured_person());
            dto.setInsuranceAmt(Long.parseLong(callBackReq.getTotal_premium()));
            dto.setInsuranceFee(Long.parseLong(callBackReq.getPremium()));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (!StringUtils.isEmpty(callBackReq.getEffective_date())){
                dto.setOrderStartTime(sdf.parse(callBackReq.getEffective_date()));
            }
            if (!StringUtils.isEmpty(callBackReq.getExpiry_date())){
                dto.setOrderStopTime(sdf.parse(callBackReq.getExpiry_date()));
            }

            InsureCallBackRespDto respDto = insuranceOrderService.updateInsOrderByOrderId(dto);

            //更新分销中的订单状态
            if (respDto != null && EInsureResult.SUCCESS.getCode().equals(respDto.getResult())){
                try {
                    DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
                    dealerUserOrderDto.setOrderNo(dto.getPerInsuranceOrderNo());
                    dealerUserOrderDto.setStatus(EDealerUserOrderStatus.SUCCESS);
                    dealerUserOrderService.updateOrderStatus(dealerUserOrderDto);
                } catch (Exception e) {
                }
            }

            ConverterService.convert(respDto, resp);
            String respEncryptString = EncryptUtil.encryptMd5(resp.signSrc() + salt);
            respDto.setSignature(respEncryptString);
            sendResult(resp, res);
            return null;
        } catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            resp.setSign_type(callBackReq.getSign_type());
            resp.setMsg(e.getMessage());
            sendResult(resp, res);
            return null;
        }
    }

    /**
     * 针对平安特殊处理返回数据
     * @param resp
     * @param res
     * @throws IOException
     */
    private void sendResult(InsureCallBackResp resp, HttpServletResponse res) throws IOException{
        LOGGER.info(JsonUtil.toJson(resp));
        PrintWriter out = null;
        res.setContentType("text/html");
        try {
            out = res.getWriter();
            out.append(JsonUtil.toJson(resp));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
        }
    }

    public static String getSerialNoKey(String serialno, String appUserId){
        StringBuilder sb = new StringBuilder("jinfu_serial_no_");
        sb.append(serialno);
        sb.append("_app_user_id");
        sb.append(appUserId);
        return sb.toString();
    }

    /**
     * 众安保骉车险回传接口
     * @param request
     * @return
     */
    @RequestMapping(value = "za_vehicle_callback", method = RequestMethod.POST)
    public void zaVehicleDetailCallback(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String[]> map = request.getParameterMap();
            VehInsNotifyDto vehInsNotifyDto = insuranceOrderService.getZhonganData(map);
            LOGGER.debug("==解析后的众安数据==");
            LOGGER.debug(JsonUtil.toJson(vehInsNotifyDto));

            if (vehInsNotifyDto != null && vehInsNotifyDto.getVehInsDetailNotifyDto() != null
                    && !StringUtils.isEmpty(vehInsNotifyDto.getVehInsDetailNotifyDto().getUserId())){

                String perInsOrderNo = vehInsNotifyDto.getVehInsDetailNotifyDto().getUserId();
                PerInsuranceInfoDto perInsuranceInfoDto = insuranceOrderService.getInsOrderByOrderId(perInsOrderNo);
                if (perInsuranceInfoDto == null){
                    LOGGER.error("众安回调保单号不存在，保单号：" + vehInsNotifyDto.getVehInsDetailNotifyDto().getUserId());
                    response.getWriter().print("success");
                    return;
                }

                synchronized(this){
                    //更新或保存车险表中的信息
                    VehicleInsDetailDto vehDetailDto = saveVehInsDetail(vehInsNotifyDto);
                    /*LOGGER.debug("==映射到车险数据库后的结构==");
                    LOGGER.debug(JsonUtil.toJson(vehDetailDto));*/

                    //更新保险总表中的信息
                    PerInsuranceInfoDto insInfoDto = new PerInsuranceInfoDto();
                    insInfoDto.setPerInsuranceOrderNo(perInsOrderNo);
                    //保费 = 商业险保费 + 交强险保费 + 车船税
                    long insFee = 0;
                    if (vehDetailDto.getBusinessPremiumInterval() != null){
                        insFee += vehDetailDto.getBusinessPremiumInterval();
                    }
                    if (vehDetailDto.getCompelPremiumInterval() != null){
                        insFee += vehDetailDto.getCompelPremiumInterval();
                    }
                    if (vehDetailDto.getTaxPreimum() != null){
                        insFee += vehDetailDto.getTaxPreimum();
                    }
                    insInfoDto.setInsuranceFee(insFee);
                    Long insuranceAmt = perInsuranceInfoDto.getInsuranceAmt()==null?0l:perInsuranceInfoDto.getInsuranceAmt();
                    if (vehInsNotifyDto.getSumInsured() != null){
                        insuranceAmt = (long)(vehInsNotifyDto.getSumInsured()*1000);
                    }
                    insInfoDto.setInsuranceAmt(insuranceAmt);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    insInfoDto.setOrderStartTime(sdf.parse(vehDetailDto.getVehiclePolicyEffectiveDate()));
                    insInfoDto.setOrderStopTime(sdf.parse(vehDetailDto.getVehiclePolicyExpiryDate()));
                    insInfoDto.setPhoneNo(vehDetailDto.getPolicyHolderPhoneNo());
                    insInfoDto.setPolicyHolder(vehDetailDto.getPolicyHolderName());
                    insInfoDto.setInsuredPerson(vehDetailDto.getInsurantName());
                    insInfoDto.setOrderStatus(EPerInsOrderStatus.SUCCESS);
                    insInfoDto.setRemark(vehDetailDto.getVehicleLicencePlateNo());
                    /*LOGGER.debug("==映射到保险数据库后的结构==");
                    LOGGER.debug(JsonUtil.toJson(insInfoDto));*/
                    insuranceOrderService.updateVehInsOrderByOrderId(insInfoDto);

                }

                //更新分销中的订单
                try {
                    DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
                    dealerUserOrderDto.setOrderNo(perInsOrderNo);
                    dealerUserOrderDto.setStatus(EDealerUserOrderStatus.SUCCESS);
                    dealerUserOrderService.updateOrderStatus(dealerUserOrderDto);
                } catch (Exception e) {
                    LOGGER.error("更新分销订单失败！");
                }

            }

            response.getWriter().print("success");
        } catch (Exception e) {
            LOGGER.error("回传失败", e);
            try {
                response.getWriter().print("success");
            } catch (IOException e1) {
                LOGGER.error("IO异常", e1);
            }
        }

    }

    /**
     * 获取空的属性数组
     * @param source
     * @return
     */
    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    //由于投保回调同时会触发商业和交强两次并进行计算，所以需要加锁
    private VehicleInsDetailDto saveVehInsDetail(VehInsNotifyDto vehInsNotifyDto){
        String notifyType = vehInsNotifyDto.getNotifyType();
        String vehicleType = vehInsNotifyDto.getVehicleType();
        VehInsDetailNotifyDto vehInsDetailNotifyDto = vehInsNotifyDto.getVehInsDetailNotifyDto();
        String perInsOrderNo = vehInsDetailNotifyDto.getUserId();

        VehicleInsDetailDto dbDetailInfo = vehicleInsDetailService.getVehicleInsDetailByOrderId(perInsOrderNo);
        if (dbDetailInfo == null){//没有就先插入一条只带保单号的记录
            VehicleInsDetailDto tmpDetailInfo = new VehicleInsDetailDto();
            tmpDetailInfo.setPerInsuranceOrderNo(perInsOrderNo);
            dbDetailInfo = vehicleInsDetailService.addVehicleInsDetail(tmpDetailInfo);
        }
        if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getPolicyHolderName())){
            dbDetailInfo.setPolicyHolderName(vehInsDetailNotifyDto.getPolicyHolderName());
        }
        if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getPolicyHolderPhoneNo())){
            dbDetailInfo.setPolicyHolderPhoneNo(vehInsDetailNotifyDto.getPolicyHolderPhoneNo());
        }
        if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getVehicleLicencePlateNo())){
            dbDetailInfo.setVehicleLicencePlateNo(vehInsDetailNotifyDto.getVehicleLicencePlateNo());
        }
        if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getVehicleVIN())){
            dbDetailInfo.setVinNo(vehInsDetailNotifyDto.getVehicleVIN());
        }
        if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getVehicleEngineNo())){
            dbDetailInfo.setVehicleEngineNo(vehInsDetailNotifyDto.getVehicleEngineNo());
        }
        if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getRecipienterName())){
            dbDetailInfo.setReceiverName(vehInsDetailNotifyDto.getRecipienterName());
        }
        if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getRecipienterPhone())){
            dbDetailInfo.setReceiverPhoneNo(vehInsDetailNotifyDto.getRecipienterPhone());
        }
        if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getRecipienterAddress())){
            dbDetailInfo.setReceiverAddress(vehInsDetailNotifyDto.getRecipienterAddress());
        }

        if (EZaNotifyType.INSURE.getCode().equals(notifyType) && EZaVehicleType.BIZINS.getCode().equals(vehicleType)){//投保，商业
            Long bizPreInterval = 0l;
            if (vehInsNotifyDto.getPremium() != null){
                bizPreInterval = (long)(vehInsNotifyDto.getPremium()*1000);
            }
            dbDetailInfo.setSrcBizPremiumInterval(bizPreInterval);
            dbDetailInfo.setBusinessPremiumInterval(bizPreInterval);

            Long taxPreimum = dbDetailInfo.getSrcTaxPreimum()==null?0l:dbDetailInfo.getSrcTaxPreimum();
            if (vehInsDetailNotifyDto.getTaxPreimum() != null){
                taxPreimum = (long)(vehInsDetailNotifyDto.getTaxPreimum()*1000);
            }
            dbDetailInfo.setSrcTaxPreimum(taxPreimum);
            dbDetailInfo.setTaxPreimum(taxPreimum);

            if (!StringUtils.isEmpty(vehInsNotifyDto.getPolicyNo())){
                dbDetailInfo.setBusinessPolicyNo(vehInsNotifyDto.getPolicyNo());
            }

            if (!StringUtils.isEmpty(vehInsNotifyDto.getEffectiveDate())){
                dbDetailInfo.setVehiclePolicyEffectiveDate(vehInsNotifyDto.getEffectiveDate());
            }

            if (!StringUtils.isEmpty(vehInsNotifyDto.getExpiryDate())){
                dbDetailInfo.setVehiclePolicyExpiryDate(vehInsNotifyDto.getExpiryDate());
            }
        }else if (EZaNotifyType.INSURE.getCode().equals(notifyType) && EZaVehicleType.COMPELINS.getCode().equals(vehicleType)){//投保，交强
            Long compelPreInterval = 0l;
            if (vehInsNotifyDto.getPremium() != null){
                compelPreInterval = (long)(vehInsNotifyDto.getPremium()*1000);
            }
            dbDetailInfo.setSrcCompelPremiumInterval(compelPreInterval);
            dbDetailInfo.setCompelPremiumInterval(compelPreInterval);

            Long taxPreimum = dbDetailInfo.getSrcTaxPreimum()==null?0l:dbDetailInfo.getSrcTaxPreimum();
            if (vehInsDetailNotifyDto.getTaxPreimum() != null){
                taxPreimum = (long)(vehInsDetailNotifyDto.getTaxPreimum()*1000);
            }
            dbDetailInfo.setSrcTaxPreimum(taxPreimum);
            dbDetailInfo.setTaxPreimum(taxPreimum);

            if (!StringUtils.isEmpty(vehInsNotifyDto.getPolicyNo())){
                dbDetailInfo.setCompelPolicyNo(vehInsNotifyDto.getPolicyNo());
            }
            if (!StringUtils.isEmpty(vehInsNotifyDto.getEffectiveDate())){
                dbDetailInfo.setCompelEffectiveDate(vehInsNotifyDto.getEffectiveDate());
            }
            if (!StringUtils.isEmpty(vehInsNotifyDto.getExpiryDate())){
                dbDetailInfo.setCompelExpiryDate(vehInsNotifyDto.getExpiryDate());
            }
        }else if (EZaNotifyType.CORRECTING.getCode().equals(notifyType) && EZaVehicleType.BIZINS.getCode().equals(vehicleType)){//批改，商业
            Long bizPreInterval = dbDetailInfo.getBusinessPremiumInterval();
            if (vehInsNotifyDto.getPremium() != null){
                bizPreInterval = (long)(vehInsNotifyDto.getPremium()*1000);
            }
            dbDetailInfo.setBusinessPremiumInterval(bizPreInterval);

            Long taxPreimum = dbDetailInfo.getTaxPreimum()==null?0l:dbDetailInfo.getTaxPreimum();
            if (vehInsDetailNotifyDto.getTaxPreimum() != null){
                taxPreimum = (long)(vehInsDetailNotifyDto.getTaxPreimum()*1000);
            }
            dbDetailInfo.setTaxPreimum(taxPreimum);

            if (!StringUtils.isEmpty(vehInsNotifyDto.getPolicyNo())){
                dbDetailInfo.setBusinessPolicyNo(vehInsNotifyDto.getPolicyNo());
            }
            if (!StringUtils.isEmpty(vehInsNotifyDto.getEffectiveDate())){
                dbDetailInfo.setVehiclePolicyEffectiveDate(vehInsNotifyDto.getEffectiveDate());
            }
            if (!StringUtils.isEmpty(vehInsNotifyDto.getExpiryDate())){
                dbDetailInfo.setVehiclePolicyExpiryDate(vehInsNotifyDto.getExpiryDate());
            }
            if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getBusinessTpEndorsementNo())){
                dbDetailInfo.setCorrectVehicleEndoNo(vehInsDetailNotifyDto.getBusinessTpEndorsementNo());
            }
            Long bizFee = dbDetailInfo.getCorrectVehicleDeltaPremium()==null?0l:dbDetailInfo.getCorrectVehicleDeltaPremium();
            if (vehInsDetailNotifyDto.getBusinessFee() != null){
                bizFee = (long)(vehInsDetailNotifyDto.getBusinessFee()*1000);
            }
            dbDetailInfo.setCorrectVehicleDeltaPremium(bizFee);
            if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getBusinessApplyTime())){
                dbDetailInfo.setCorrectVehicleApplyDate(vehInsDetailNotifyDto.getBusinessApplyTime());
            }
            if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getBusinessEffectiveDate())){
                dbDetailInfo.setCorrectVehicleEffectiveDate(vehInsDetailNotifyDto.getBusinessEffectiveDate());
            }
        }else if (EZaNotifyType.CORRECTING.getCode().equals(notifyType) && EZaVehicleType.COMPELINS.getCode().equals(vehicleType)){//批改，交强
            Long compelPreInterval = dbDetailInfo.getCompelPremiumInterval();
            if (vehInsNotifyDto.getPremium() != null){
                compelPreInterval = (long)(vehInsNotifyDto.getPremium()*1000);
            }
            dbDetailInfo.setCompelPremiumInterval(compelPreInterval);

            Long taxPreimum = dbDetailInfo.getTaxPreimum()==null?0l:dbDetailInfo.getTaxPreimum();
            if (vehInsDetailNotifyDto.getTaxPreimum() != null){
                taxPreimum = (long)(vehInsDetailNotifyDto.getTaxPreimum()*1000);
            }
            dbDetailInfo.setTaxPreimum(taxPreimum);
            if (!StringUtils.isEmpty(vehInsNotifyDto.getPolicyNo())){
                dbDetailInfo.setCompelPolicyNo(vehInsNotifyDto.getPolicyNo());
            }
            if (!StringUtils.isEmpty(vehInsNotifyDto.getEffectiveDate())){
                dbDetailInfo.setCompelEffectiveDate(vehInsNotifyDto.getEffectiveDate());
            }
            if (!StringUtils.isEmpty(vehInsNotifyDto.getExpiryDate())){
                dbDetailInfo.setCompelExpiryDate(vehInsNotifyDto.getExpiryDate());
            }
            if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getCompelTpEndorsementNo())){
                dbDetailInfo.setCorrectCompelEndoNo(vehInsDetailNotifyDto.getCompelTpEndorsementNo());
            }
            Long compelFee = dbDetailInfo.getCorrectCompelDeltaPremium()==null?0l:dbDetailInfo.getCorrectCompelDeltaPremium();
            if (vehInsDetailNotifyDto.getCompelFee() != null){
                compelFee = (long)(vehInsDetailNotifyDto.getCompelFee()*1000);
            }
            dbDetailInfo.setCorrectCompelDeltaPremium(compelFee);
            if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getCompelApplyTime())){
                dbDetailInfo.setCorrectCompelApplyDate(vehInsDetailNotifyDto.getCompelApplyTime());
            }
            if (!StringUtils.isEmpty(vehInsDetailNotifyDto.getCompelEndorEffectiveDate())){
                dbDetailInfo.setCorrectCompelEffectiveDate(vehInsDetailNotifyDto.getCompelEndorEffectiveDate());
            }
        }
        vehicleInsDetailService.updateVehicleInsDetailById(dbDetailInfo);

        return dbDetailInfo;
    }

}
