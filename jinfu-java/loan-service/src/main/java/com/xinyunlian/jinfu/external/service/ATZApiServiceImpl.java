package com.xinyunlian.jinfu.external.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.external.dto.OverDueLoanDtl;
import com.xinyunlian.jinfu.external.dto.RefundDto;
import com.xinyunlian.jinfu.external.dto.RefundsAdvanceDto;
import com.xinyunlian.jinfu.external.dto.RequsetInfo;
import com.xinyunlian.jinfu.external.dto.req.LoanApplyReq;
import com.xinyunlian.jinfu.external.dto.req.RegisterReq;
import com.xinyunlian.jinfu.external.enums.ConfirmationType;
import com.xinyunlian.jinfu.external.util.GsonUtil;
import com.xinyunlian.jinfu.external.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by godslhand on 2017/6/17.
 */
@Service(value = "atzApiServiceImpl")
public class ATZApiServiceImpl implements ATZApiService {

    @Value("${aitz.serviceURL}")
    private String serviceURL ;//= AppConfigUtil.getConfig("aitz.channelId");
    @Value("${aitz.channelId}")
    private String channelId ;//=AppConfigUtil.getConfig("aitz.channelId")  ;     //各个平台所分配的channelId
    @Value("${aitz.md5key}")
    private String md5key ;//= AppConfigUtil.getConfig("aitz.md5key") ;                //平台的md5 key
    private String ver = "2.0";
    @Value("${aitz.productId}")
    private String productId ;//= AppConfigUtil.getConfig("aitz.productId");
    @Value("${aitz.rsa}")
    private String rsa ;


//    private String serviceURL="http://api2.shansudai.cn:8080/execute" ;//= AppConfigUtil.getConfig("aitz.channelId");
//    private String channelId="34" ;//=AppConfigUtil.getConfig("aitz.channelId")  ;     //各个平台所分配的channelId
//    private String md5key="yunlianjishu" ;//= AppConfigUtil.getConfig("aitz.md5key") ;                //平台的md5 key
//    private String ver = "2.0";
//    private String productId="c911c61e-9d67-4c66-9e63-a56d3d9f7073" ;//= AppConfigUtil.getConfig("aitz.productId");
//    private String rsa="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDRT81CpnhWDnLutAsOfEtWF5EJrpddPsRcqxzBtbEtybmpcRyRpKL+Xx2ivha3k+kVOoWezVIldNwDtJbqm1Ns1USn/j+V8d/BikxybgDvthoQGMRt/LRgikKyzv+97vn6n+Fl5hwBh4iT85afuZCkRBopGGK/wMD6LDpNuO7OpQIDAQAB" ;
    private static final Logger logger = LoggerFactory.getLogger(ATZApiServiceImpl.class);



    @Override
    public void register(RegisterReq registerReq) {
        String method = "register";
        try {
            JSONObject result = sendHttpPost(method, channelId, registerReq);
            String loanId = result.getString("idNo");
            if (loanId == null || "-1".equals(loanId)) {
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, result.getString("reason"));
            }
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "用户注册失败", e);
        }
    }

    @Override
    public String loanApply(LoanApplyReq loanApply) {
        String method = "loanApply";
        loanApply.setProductId(productId);
        try {
            JSONObject result = sendHttpPost(method, channelId, loanApply);
            String loanId = result.getString("loanId");
            if (loanId == null || "-1".equals(loanId)) {
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, result.getString("reason"));
            }
            return loanId;
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "贷款申请失败", e);
        }
    }

    @Override
    public boolean loanContractConfirm(String loanId, ConfirmationType confirmationType) {
        String method = "loanContractConfirm";
        try {
            HashMap<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("loanId", loanId);
            paramsMap.put("confirmation", Integer.valueOf(confirmationType.getCode()));
            sendHttpPost(method, channelId, paramsMap);

        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "确认贷款失败", e);
        }
        return true;
    }

    @Override
    public OverDueLoanDtl getOverDueLoanDetailInfo(String loanId) {
        String method = "getOverDueLoanDetailInfo";
        try {
            HashMap<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("loanId", loanId);
            JSONObject result = sendHttpPost(method, channelId, paramsMap);
            return JSON.parseObject(result.toJSONString(), OverDueLoanDtl.class);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "查看逾期还款计划出错", e);
        }
    }

    @Override
    public RefundsAdvanceDto loanRefundInAdvance(String loanId) {
        String method = "loanRefundInAdvance";
        try {
            HashMap<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("loanId", loanId);
            JSONObject result = sendHttpPost(method, channelId, paramsMap);
            String status = result.getString("loanRefuandInAdvanceId");
            if (status == null || status.equals("-1")) {
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, result.getString("reason"));
            } else
                return JSON.parseObject(result.toJSONString(), RefundsAdvanceDto.class);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "提前还款申请失败", e);
        }
    }

    @Override
    public boolean loanRefuandInAdvanceConfirm(String loanId, String loanRefuandInAdvanceId, ConfirmationType confirmationType) {
        String method = "loanRefuandInAdvanceConfirm";
        try {
            HashMap<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("loanId", loanId);
            paramsMap.put("loanRefuandInAdvanceId", loanRefuandInAdvanceId);
            paramsMap.put("confirmation", confirmationType.getCode());

            JSONObject result = sendHttpPost(method, channelId, paramsMap);

        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "还款申请确认失败", e);
        }
        return true;
    }

    @Override
    public List<RefundDto> getLoanPaymentPlan(String loanId) {
        String method = "getLoanPaymentPlan";
        try {
            HashMap<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("loanId", loanId);
            JSONObject result = sendHttpPost(method, channelId, paramsMap);
            //TODO check paramss
//            Integer status = result.getInteger("loanRefuandInAdvanceId");
            return JSON.parseArray(result.getJSONArray("refunds").toJSONString(), RefundDto.class);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "查询最还款计划据失败", e);
        }
    }

    @Override
    public String signResponese(RequsetInfo requsetInfo) {

        requsetInfo.setSignType("MD5");  //使用MD5签名
        String sign = SignUtil.generateMD5Sign(requsetInfo.getMethod(), ver, channelId, md5key, requsetInfo.getParams());
        requsetInfo.setSign(sign);
        requsetInfo.setVer(ver);
        requsetInfo.setChannelId(channelId);
        return GsonUtil.getGson().toJson(requsetInfo);
    }

    @Override
    public boolean checkSign(String request) {
        JSONObject json = JSON.parseObject(request);
        String signType = json.getString("signType");
        if ("MD5".equals(signType)) {
            return SignUtil.verifySign(request, md5key);
        } else
            return SignUtil.verifySign(request, rsa);
    }

    /**
     * 统一发送Http请求
     *
     * @param method
     * @param channelId
     * @param requestParamsObj
     * @return
     * @throws IOException
     */
    private JSONObject sendHttpPost(String method, String channelId,
                                    Object requestParamsObj) throws IOException {

        String json = getRequestJson(method, channelId, requestParamsObj);
        String uuid = UUID.randomUUID().toString();
        logger.info("uuid:{},serviceURL:{}, post request json :{}", uuid, serviceURL, json);
        String returnResponse = OkHttpUtil.postJson(serviceURL, json, false);
        logger.info("uuid:{},response :{}", uuid, returnResponse);
        if(!checkSign(returnResponse))
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, method+":返回数据签名错误！");
        JSONObject result = getResponseJSON(method, returnResponse);
        return result;
    }


    /****
     * @param requestParamsObj： 方法消息体对象
     * @param method： 方法名
     * @param channelId：渠道编号
     * @return 请求的json字符串
     */
    private String getRequestJson(String method, String channelId, Object requestParamsObj) {
        RequsetInfo<Object> requsetInfo = new RequsetInfo();

        requsetInfo.setSignType("MD5");  //使用MD5签名
        String sign = SignUtil.generateMD5Sign(method, ver, channelId, md5key, requestParamsObj);
        requsetInfo.setSign(sign);
        requsetInfo.setVer(ver);
        requsetInfo.setChannelId(channelId);
        requsetInfo.setMethod(method);
        requsetInfo.setParams(requestParamsObj);
        return GsonUtil.getGson().toJson(requsetInfo);
    }

    /**
     * 统一返回结果错误处理
     *
     * @param method
     * @param response
     * @return
     */
    private JSONObject getResponseJSON(String method, String response) {

        JSONObject result = JSON.parseObject(response);
        if (result.getString("errMsg") != null)
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, method + ":" + result.getString("errMsg"));
        JSONObject params = result.getJSONObject("params");
        if (params == null)
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, method + ":返回数据异常");
        if (params.getString("result") != null && params.getInteger("result") != 1)
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, params.getString("reason"));
        return params;
    }

}
