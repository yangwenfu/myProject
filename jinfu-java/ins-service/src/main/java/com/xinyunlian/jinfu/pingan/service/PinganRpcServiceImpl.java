package com.xinyunlian.jinfu.pingan.service;

import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.ByteToPdf;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.insurance.dao.PerInsuranceInfoDao;
import com.xinyunlian.jinfu.insurance.entity.PerInsuranceInfoPo;
import com.xinyunlian.jinfu.pingan.dao.PinganInsuredGradeDao;
import com.xinyunlian.jinfu.pingan.dao.PinganRegionDao;
import com.xinyunlian.jinfu.pingan.dao.PinganTyphoonControlRegionDao;
import com.xinyunlian.jinfu.pingan.dao.PreOrderUserInfoDao;
import com.xinyunlian.jinfu.pingan.dto.*;
import com.xinyunlian.jinfu.pingan.entity.PinganInsuredGradePo;
import com.xinyunlian.jinfu.pingan.entity.PinganRegionPo;
import com.xinyunlian.jinfu.pingan.entity.PinganTyphoonControlRegionPo;
import com.xinyunlian.jinfu.pingan.entity.PreOrderUserInfoPo;
import com.xinyunlian.jinfu.trans.dto.TransHistoryDto;
import com.xinyunlian.jinfu.trans.enums.EInsTransType;
import com.xinyunlian.jinfu.trans.service.TransHistoryInnerHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
@Service
public class PinganRpcServiceImpl implements PinganRpcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PinganRpcServiceImpl.class);

    @Autowired
    private PinganInsuredGradeDao pinganInsuredGradeDao;

    @Autowired
    private PinganRegionDao pinganRegionDao;

    @Autowired
    private PinganTyphoonControlRegionDao pinganTyphoonControlRegionDao;

    @Autowired
    private PreOrderUserInfoDao preOrderUserInfoDao;

    @Value("${pingan.openapi.base}")
    private String pinganOpenApiBase;

    @Value("${pingan.openapi.partnerName}")
    private String pinganOpenApiPartnerName;

    @Value("${pingan.account.no}")
    private String pinganAccountNo;

    @Value("${pingan.account.type}")
    private String pinganAccountType;

    @Value("${pingan.openapi.oauth.url}")
    private String pinganOpenApiOauthUrl;

    private static final String QUOTE = "QUOTE";
    private static final String APPLY = "APPLY";
    private static final String PRINT = "PRINT";
    private static final String DEPARTMENT_CODE = "2269303";
    private static final String INPUT_NETWORK_FLAG = "internet";

    private static final String POLICY_HOLDER = "网新新云联金融信息服务（浙江）有限公司";

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private TransHistoryInnerHistoryService transHistoryInnerHistoryService;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    private PerInsuranceInfoDao perInsuranceInfoDao;

    @Override
    public List<PinganInsuredGradeDto> getAllInsuredGrade() throws BizServiceException {
        List<PinganInsuredGradeDto> retList = new ArrayList<>();

        List<PinganInsuredGradePo> poList = pinganInsuredGradeDao.findAll();
        if (!CollectionUtils.isEmpty(poList)){
            poList.forEach(item -> {
                PinganInsuredGradeDto dto = ConverterService.convert(item, PinganInsuredGradeDto.class);
                retList.add(dto);
            });
        }

        return retList;
    }

    @Override
    public Boolean isTyphoonControlRegion(Long regionId) throws BizServiceException {
        Boolean isTyphoonControlRegion = false;

        PinganTyphoonControlRegionPo po = pinganTyphoonControlRegionDao.findByRegionId(regionId);
        if (po != null){
            isTyphoonControlRegion = true;
        }else {
            PinganRegionPo regionPo = pinganRegionDao.findOne(regionId);
            if (regionPo == null){
                LOGGER.error("台控区查询的指定地区不存在， regionId = {}", regionId);
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
            }else {
                String areaTreePath = regionPo.getTreePath();
                String[] areaTreePathArray = areaTreePath.split(",");
                if (areaTreePathArray != null && areaTreePathArray.length > 0){

                    Set<String> pathSet = new HashSet<>();
                    StringBuilder pathBuilder = new StringBuilder(",");
                    for(int i = 1; i< areaTreePathArray.length; i++){
                        pathBuilder.append(areaTreePathArray[i]).append(",");
                        pathSet.add(pathBuilder.toString());
                    }

                    String tmpPath = "," + areaTreePathArray[1] + ",%";
                    List<PinganTyphoonControlRegionPo> typhoonControlRegionList = pinganTyphoonControlRegionDao.findByTreePathLike(tmpPath);
                    if (!CollectionUtils.isEmpty(typhoonControlRegionList)){
                        Optional<PinganTyphoonControlRegionPo> optional =
                                typhoonControlRegionList.stream().filter(item -> pathSet.contains(item.getTreePath())).findFirst();
                        isTyphoonControlRegion = optional.isPresent();
                    }
                }
            }
        }

        return isTyphoonControlRegion;
    }

    @Override
    public PinganRegionDto getPinganRegion(String gbCode, Integer level) throws BizServiceException {
        PinganRegionPo regionPo = pinganRegionDao.findByGbCodeAndLevel(gbCode, level);
        return ConverterService.convert(regionPo, PinganRegionDto.class);
    }

    @Override
    public List<PinganRegionDto> getPinganRegionList(Long parent) throws BizServiceException {

        Specification<PinganRegionPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (!StringUtils.isEmpty(parent)) {
                expressions.add(cb.equal(root.get("parent"), parent));
            }else {
                expressions.add(cb.isNull(root.get("parent")));
            }
            return predicate;
        };

        List<PinganRegionPo> list = pinganRegionDao.findAll(spec);
        List<PinganRegionDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                PinganRegionDto dto = ConverterService.convert(po, PinganRegionDto.class);
                result.add(dto);
            });
        }

        return result;
    }

    @Override
    @Transactional
    public PinganQuotePriceRetDto getPinganQuotePrice(String insuredGradeCode, Long regionId) throws BizServiceException {

        try {
            QuotePriceRespBase respBase = sendQuotePriceRequest(insuredGradeCode, regionId, false);
            if (PinganOpenApiRetCode.ILLEGAL_TOKEN.equals(respBase.getRet()) || PinganOpenApiRetCode.INVALID_TOKEN.equals(respBase.getRet())){
                respBase = sendQuotePriceRequest(insuredGradeCode, regionId, true);
            }
            if (PinganOpenApiRetCode.RET_SUCCESS.equals(respBase.getRet())){
                QuotePriceResponse data = respBase.getData();
                QuotePriceDataResultResponse dataResult = data.getResult();
                if (dataResult == null){
                    throw new BizServiceException(EErrorCode.INS_PINGAN_QUOTE_PRICE_NO_DATA);
                }

                if (PinganOpenApiRetCode.SUCCESS.equals(data.getResponseCode()) && !CollectionUtils.isEmpty(dataResult.getResultDTOlist())){

                    PinganQuotePriceRetDto quotePriceRetDto = new PinganQuotePriceRetDto();
                    quotePriceRetDto.setProductCode(dataResult.getProductCode());
                    for (QuotePriceResultResponse item:dataResult.getResultDTOlist()) {

                        if (CollectionUtils.isEmpty(quotePriceRetDto.getPlanCodeList())){
                            List<String> planCodeList = new ArrayList<>();
                            planCodeList.add(item.getPlanCode());
                            quotePriceRetDto.setPlanCodeList(planCodeList);
                        }else {
                            quotePriceRetDto.getPlanCodeList().add(item.getPlanCode());
                        }

                        if ("1".equalsIgnoreCase(item.getIsMainRisk())){
                            quotePriceRetDto.setInsuredAmount(item.getInsuredAmount());
                            quotePriceRetDto.setInsuredPremium(item.getInsuredPremium());
                        }
                    }

                    return quotePriceRetDto;
                }else {
                    LOGGER.error("获取平安报价失败，responseCode = {}, responseMsg = {}", data.getResponseCode(), data.getResponseMsg());
                    throw new BizServiceException(EErrorCode.INS_PINGAN_QUOTE_PRICE_ERROR);
                }
            }else {
                LOGGER.error("获取平安报价失败，responseCode = {}, responseMsg = {}", respBase.getRet(), respBase.getMsg());
                throw new BizServiceException(EErrorCode.INS_PINGAN_API_INVOKE_FAILED);
            }
        } catch (Exception e) {
            LOGGER.error("获取平安报价失败", e);
            throw new BizServiceException(EErrorCode.INS_PINGAN_API_INVOKE_FAILED);
        }
    }

    private QuotePriceRespBase sendQuotePriceRequest(String insuredGradeCode, Long regionId, Boolean refreshToken) throws Exception{
        String accessToken = getPinganOauthToken(refreshToken);
        String requestUrl = pinganOpenApiBase + "/appsvr/property/standardGroupPropertyQuotedPrice?access_token=" + accessToken
                + "&request_id=" + System.currentTimeMillis();

        TransHistoryDto retTrans = addTransHistory(EInsTransType.PINGAN_QUOTE_PRICE, requestUrl, null);

        String isTyphoonControlRegion = isTyphoonControlRegion(regionId)?"Y":"N";
        QuotePriceRequest quotePriceRequest = new QuotePriceRequest();
        quotePriceRequest.setPartnerName(pinganOpenApiPartnerName);
        quotePriceRequest.setTransferCode(QUOTE);
        quotePriceRequest.setTransSerialNo(retTrans.getTransSerialNo());
        QuotePriceSubRequest subRequest = new QuotePriceSubRequest();
        subRequest.setInsuredGradeCode(insuredGradeCode);
        subRequest.setIsTyphoonControlArea(isTyphoonControlRegion);

        quotePriceRequest.setQuotePriceSubRequest(subRequest);

        //获取报价
        String response = OkHttpUtil.postAndGetJson(requestUrl, JsonUtil.toJson(quotePriceRequest), true);

        //更新交易表
        updateTransHistory(retTrans, JsonUtil.toJson(quotePriceRequest), response);

        return JsonUtil.toObject(QuotePriceRespBase.class, response);
    }

    @Override
    @Transactional
    public PreOrderUserInfoDto addPreOrderUserInfo(PreOrderUserInfoDto dto) throws BizServiceException {

        PreOrderUserInfoPo preOrderUserInfoPo = ConverterService.convert(dto, PreOrderUserInfoPo.class);
        if (preOrderUserInfoPo != null){
            if (!StringUtils.isEmpty(preOrderUserInfoPo.getApplyPolicyNo())){
                preOrderUserInfoPo.setApplyPolicyNo(null);
            }
            if (!StringUtils.isEmpty(preOrderUserInfoPo.getPolicyNo())){
                preOrderUserInfoPo.setPolicyNo(null);
            }
        }
        PreOrderUserInfoPo po = preOrderUserInfoDao.save(preOrderUserInfoPo);

        return ConverterService.convert(po, PreOrderUserInfoDto.class);
    }

    @Override
    @Transactional
    public QunarApplyResultDto qunarApply(PreOrderUserInfoDto preOrderUserInfoDto) throws BizServiceException {
        try {
            QunarApplyRespBase qunarApplyRespBase = sendQunarApplyRequest(preOrderUserInfoDto, false);

            if (PinganOpenApiRetCode.ILLEGAL_TOKEN.equals(qunarApplyRespBase.getRet())
                    || PinganOpenApiRetCode.INVALID_TOKEN.equals(qunarApplyRespBase.getRet())){
                qunarApplyRespBase = sendQunarApplyRequest(preOrderUserInfoDto, true);
            }

            if (PinganOpenApiRetCode.RET_SUCCESS.equals(qunarApplyRespBase.getRet())){
                //更新orderuserinfo表的投保单号和保单号
                QunarApplyResponse data = qunarApplyRespBase.getData();
                if (PinganOpenApiRetCode.SUCCESS.equals(data.getResponseCode())){
                    PreOrderUserInfoPo dbOne = preOrderUserInfoDao.findOne(preOrderUserInfoDto.getId());
                    if (dbOne != null){
                        dbOne.setApplyPolicyNo(data.getResult().getApplyPolicyNo());
                        dbOne.setPolicyNo(data.getResult().getPolicyNo());
                    }
                }

                QunarApplyResultDto retDto = new QunarApplyResultDto();
                if (data.getResult() != null){
                    retDto = ConverterService.convert(data.getResult(), QunarApplyResultDto.class);
                }
                retDto.setResponseCode(data.getResponseCode());
                retDto.setResponseMsg(data.getResponseMsg());

                return retDto;
            }else {
                LOGGER.error("投保平安店铺保失败，responseCode = {}, responseMsg = {}", qunarApplyRespBase.getRet(), qunarApplyRespBase.getMsg());
                throw new BizServiceException(EErrorCode.INS_PINGAN_API_INVOKE_FAILED);
            }
        } catch (Exception e) {
            LOGGER.error("投保平安店铺保失败", e);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }
    }

    private QunarApplyRespBase sendQunarApplyRequest(PreOrderUserInfoDto preOrderUserInfoDto, Boolean refreshToken) throws Exception{
        String accessToken = getPinganOauthToken(refreshToken);
        String requestUrl = pinganOpenApiBase + "/appsvr/property/standardGroupQunarApply?access_token=" + accessToken
                + "&request_id=" + System.currentTimeMillis();

        TransHistoryDto retTrans = addTransHistory(EInsTransType.PINGAN_QUNAR_APPLY, requestUrl, preOrderUserInfoDto.getPreInsuranceOrderNo());

        QunarApplyRequest request = new QunarApplyRequest();
        request.setPartnerName(pinganOpenApiPartnerName);
        request.setTransferCode(APPLY);
        request.setTransSerialNo(retTrans.getTransSerialNo());
        request.setDepartmentCode(DEPARTMENT_CODE);

        QunarApplyContReq contReq = new QunarApplyContReq();

        //设置被保人信息
        QunarApplyContInsInfoReq contInsInfoReq = new QunarApplyContInsInfoReq();
        contInsInfoReq.setPersonnelType("0");
        contInsInfoReq.setName(preOrderUserInfoDto.getStoreName());
        contInsInfoReq.setCertificateType("99");
        contInsInfoReq.setCertificateNo(preOrderUserInfoDto.getTobaccoCertificateNo());
        contInsInfoReq.setLinkManName(preOrderUserInfoDto.getLinkmanName());
        contInsInfoReq.setEmail(preOrderUserInfoDto.getEmail());
        contInsInfoReq.setMobileTelephone(preOrderUserInfoDto.getMobile());
        StringBuilder detailAddress = new StringBuilder();
        detailAddress.append(preOrderUserInfoDto.getProvinceName())
                .append(preOrderUserInfoDto.getCityName())
                .append(preOrderUserInfoDto.getCountyName())
                .append(preOrderUserInfoDto.getDetailAddress());
        contInsInfoReq.setAddress(detailAddress.toString());
        List<QunarApplyContInsInfoReq> insurantInfoList = new ArrayList<>();
        insurantInfoList.add(contInsInfoReq);
        contReq.setInsurantInfoList(insurantInfoList);

        //设置基础信息
        QunarApplyContBaseInfoReq contBaseInfoReq = new QunarApplyContBaseInfoReq();
        contBaseInfoReq.setProductCode(preOrderUserInfoDto.getProductCode());
        contBaseInfoReq.setTotalInsuredAmount(preOrderUserInfoDto.getTotalInsuredAmount());
        contBaseInfoReq.setInputNetworkFlag(INPUT_NETWORK_FLAG);
        contBaseInfoReq.setAmountCurrencyCode("01");
        contBaseInfoReq.setTotalActualPremium(preOrderUserInfoDto.getTotalActualPreium());
        contBaseInfoReq.setPremiumCurrencyCode("01");
        contBaseInfoReq.setInsuranceBeginDate(preOrderUserInfoDto.getInsuranceBeginDate());
        contReq.setBaseInfo(contBaseInfoReq);

        //设置扩展信息
        QunarApplyContExtInfoReq contExtInfoReq = new QunarApplyContExtInfoReq();
        contExtInfoReq.setIsPolicyBeforePayfee("1");
        contReq.setExtendInfo(contExtInfoReq);

        //设置地区信息
        List<QunarApplyContRiskAddressInfoReq> riskAddressInfoList = new ArrayList<>();
        QunarApplyContRiskAddressInfoReq contRiskAddressInfoReq = new QunarApplyContRiskAddressInfoReq();
        contRiskAddressInfoReq.setCountry("142");
        contRiskAddressInfoReq.setProvince(preOrderUserInfoDto.getProvinceGbCode());
        contRiskAddressInfoReq.setCity(preOrderUserInfoDto.getCityGbCode());
        contRiskAddressInfoReq.setCounty(preOrderUserInfoDto.getCountyGbCode());
        contRiskAddressInfoReq.setAddress(preOrderUserInfoDto.getDetailAddress());
        riskAddressInfoList.add(contRiskAddressInfoReq);
        contReq.setRiskAddressInfoList(riskAddressInfoList);

        //设置险种信息
        List<QunarApplyContRiskGroupInfoReq> riskGroupInfoList = new ArrayList<>();
        QunarApplyContRiskGroupInfoReq riskGroupInfoReq = new QunarApplyContRiskGroupInfoReq();
        List<QunarApplyContRiskGroupInfoPlanReq> planInfoList = new ArrayList<>();
        if (!StringUtils.isEmpty(preOrderUserInfoDto.getPlanCode())){
            String[] planCodes = preOrderUserInfoDto.getPlanCode().split(",");
            for (String item:planCodes) {
                QunarApplyContRiskGroupInfoPlanReq planInfo = new QunarApplyContRiskGroupInfoPlanReq();
                planInfo.setPlanCode(item);
                planInfoList.add(planInfo);
            }
        }
        riskGroupInfoReq.setPlanInfoList(planInfoList);
        riskGroupInfoList.add(riskGroupInfoReq);
        contReq.setRiskGroupInfoList(riskGroupInfoList);

        request.setContract(contReq);

        //设置开单信息
        QunarApplyMoreInfoReq moreInfoReq = new QunarApplyMoreInfoReq();
        QunarApplyMoreInfoNoticeDetailReq noticeDetailReq = new QunarApplyMoreInfoNoticeDetailReq();
        noticeDetailReq.setAccountNo(pinganAccountNo);
        noticeDetailReq.setAccountType(pinganAccountType);
        moreInfoReq.setNoticeDetail(noticeDetailReq);

        request.setMoreInfo(moreInfoReq);

        //投保
        String response = OkHttpUtil.postAndGetJson(requestUrl, JsonUtil.toJson(request), true);

        //更新交易历史表
        updateTransHistory(retTrans, JsonUtil.toJson(request), response);

        return JsonUtil.toObject(QunarApplyRespBase.class, response);
    }

    @Override
    @Transactional
    public void pasElectronicPolicy(String preInsuranceOrderNo) throws BizServiceException {
        try {
            LOGGER.debug("开始获取电子保单，订单号: {}", preInsuranceOrderNo);

            PreOrderUserInfoPo orderUserInfoPo = preOrderUserInfoDao.findByPreInsuranceOrderNo(preInsuranceOrderNo);

            PasElectronicPolicyRespBase respBase = pasElectronicPolicy(orderUserInfoPo, false);
            if (PinganOpenApiRetCode.ILLEGAL_TOKEN.equals(respBase.getRet())
                    || PinganOpenApiRetCode.INVALID_TOKEN.equals(respBase.getRet())){
                respBase = pasElectronicPolicy(orderUserInfoPo, true);
            }

            if (PinganOpenApiRetCode.RET_SUCCESS.equals(respBase.getRet())){
                PasElectronicPolicyDataResponse respBaseData = respBase.getData();
                if (PinganOpenApiRetCode.SUCCESS.equals(respBaseData.getResponseCode()) && respBaseData.getResult() != null){

                    String policyValue = respBaseData.getResult().getPolicyValue();
                    if (!StringUtils.isEmpty(policyValue)){
                        InputStream is = ByteToPdf.decodeToInputStream(policyValue);
                        //上传文件到ftp
                        if (AppConfigUtil.isProdEnv()){
                            fileStoreService.uploadDirect("", is, "zhongyan_" + preInsuranceOrderNo + ".pdf");
                        }else {
                            fileStoreService.uploadDirect("/INSURANCE", is, "zhongyan_" + preInsuranceOrderNo + ".pdf");
                        }
                        orderUserInfoPo.setPolicyFileDownloaded(true);
                    }
                }
            }

            LOGGER.debug("完成获取电子保单");

        } catch (Exception e) {
            LOGGER.error("获取电子保单失败", e);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }
    }

    private PasElectronicPolicyRespBase pasElectronicPolicy(PreOrderUserInfoPo orderUserInfoPo, Boolean refreshToken) throws Exception{
        String accessToken = getPinganOauthToken(refreshToken);
        String requestUrl = pinganOpenApiBase + "/appsvr/property/ePasElectronicPolicy?access_token=" + accessToken
                + "&request_id=" + System.currentTimeMillis();

        //构造请求体
        PasElectronicPolicyReq request = new PasElectronicPolicyReq();
        request.setPartnerCode(pinganOpenApiPartnerName);
        request.setPolicyNo(orderUserInfoPo.getPolicyNo());
        request.setDocumentType("4");
        request.setApplyPolicyNo(orderUserInfoPo.getApplyPolicyNo());
        request.setIsElectronic("1");
        request.setLanguagePrint("0");

        //新增交易历史
        TransHistoryDto transHistory = addTransHistory(EInsTransType.PINGAN_EPASELECPOLICY, requestUrl, orderUserInfoPo.getPreInsuranceOrderNo());

        String json = OkHttpUtil.postAndGetJson(requestUrl, JsonUtil.toJson(request), true);

        //更新交易历史
        updateTransHistory(transHistory, JsonUtil.toJson(request), "");

        return JsonUtil.toObject(PasElectronicPolicyRespBase.class, json);
    }

    @Override
    public PreOrderUserInfoDto getPreOrderUserInfo(String preInsOrderNo) throws BizServiceException {
        PreOrderUserInfoPo po = preOrderUserInfoDao.findByPreInsuranceOrderNo(preInsOrderNo);
        return ConverterService.convert(po, PreOrderUserInfoDto.class);
    }

    private TransHistoryDto addTransHistory(EInsTransType transType, String requestUrl, String orderNo){
        TransHistoryDto addTransHistoryDto = new TransHistoryDto();
        addTransHistoryDto.setRequestUrl(requestUrl);
        addTransHistoryDto.setTransType(transType);
        addTransHistoryDto.setOrderNo(orderNo);
        return transHistoryInnerHistoryService.addTransHistory(addTransHistoryDto);
    }

    private void updateTransHistory(TransHistoryDto retTrans, String requestJson, String responseJson){
        retTrans.setTransRequest(requestJson);
        retTrans.setTransResponse(responseJson);
        transHistoryInnerHistoryService.updateTransHistory(retTrans);
    }

    /**
     * 获取平安oauth toekn
     * @return
     * @throws BizServiceException
     */
    @Override
    public String getPinganOauthToken(Boolean refreshToken) throws BizServiceException{

        String accessToken = redisCacheManager.getCache(CacheType.PINGAN_OAUTH_TOKEN).get("accessToken", String.class);
        try {
            if (refreshToken || StringUtils.isEmpty(accessToken)){
                byte[] bytes = OkHttpUtil.getBytes(pinganOpenApiOauthUrl, true);
                String json = new String(bytes, "UTF-8");

                OauthTokenResponse body = JsonUtil.toObject(OauthTokenResponse.class, json);

                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug("getPinganOauthToken return: {}", json);
                    LOGGER.debug("getPinganOauthToken转换后的对象：{}", JsonUtil.toJson(body));
                }
                if ("0".equals(body.getRet())){
                    accessToken = body.getData().getAccessToken();
                    redisCacheManager.getCache(CacheType.PINGAN_OAUTH_TOKEN).put("accessToken", accessToken);
                }else {
                    LOGGER.error("获取平安oauth token异常，msg={}, ret={}", body.getMsg(), body.getRet());
                    throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
                }
            }
        } catch (IOException e) {
            LOGGER.error("获取平安token失败", e);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }

        return accessToken;
    }

    @Override
    @Transactional
    public void pasElectronicPolicyAll() throws BizServiceException {
        List<PerInsuranceInfoPo> noPolicyFileRecord = perInsuranceInfoDao.findNoPolicyFileRecord();
        if (!CollectionUtils.isEmpty(noPolicyFileRecord)){
            for (PerInsuranceInfoPo item : noPolicyFileRecord) {
                pasElectronicPolicy(item.getPerInsuranceOrderNo());
            }
        }
    }

    @Override
    @Transactional
    public PreOrderUserInfoDto updatePreOrderUserInfo(PreOrderUserInfoDto dto) throws BizServiceException {
        PreOrderUserInfoPo one = preOrderUserInfoDao.findOne(dto.getId());
        if (one != null){
            ConverterService.convert(dto, one);
            preOrderUserInfoDao.save(one);
        }
        return ConverterService.convert(one, PreOrderUserInfoDto.class);
    }

}
