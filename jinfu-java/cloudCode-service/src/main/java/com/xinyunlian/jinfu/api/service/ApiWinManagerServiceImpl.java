package com.xinyunlian.jinfu.api.service;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.HttpUtil;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 赢掌柜
 * Created by menglei on 2016年09月23日.
 */
@Service
public class ApiWinManagerServiceImpl implements ApiWinManagerService {

    @Value("${winmanager.url}")
    private String URL;

    @Value("${winmanager.key}")
    private String KEY;

    @Value("${winmanager.appid}")
    private String APPID;

    @Value("${winmanager.notify.url}")
    private String NOTIFY_URL;

    /**
     * 赢掌柜注册店铺
     *
     * @param params
     * @return
     * @throws BizServiceException
     */
    @Override
    public String getmemberNo(Map<String, String> params) throws BizServiceException {
        String result = null;
        try {
            params.put("notifyUrl", NOTIFY_URL + "/member_callback");
            params.put("businessCatagory1", "专门零售");
            params.put("businessCatagory2", "各类杂货便利店");
            params.put("mcc", "5331");
            String queryString = createLinkString(params);
            queryString = queryString + "&appId=" + APPID + "&signature=" + getSignAture(params);
            result = HttpUtil.doPost(URL + "/open-api/storeInfo/add", "application/x-www-form-urlencoded", queryString);
            //result = unicode2String(result);
            //result = URLDecoder.decode(result, "UTF-8");
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        JSONObject jSONObject = new JSONObject(result);
        if (!"0".equals(jSONObject.get("error").toString())) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, result);
        }
        return jSONObject.getJSONObject("rows").get("memberCode").toString();
    }

    /**
     * 赢掌柜添加银行卡
     *
     * @param params
     * @return
     * @throws BizServiceException
     */
    @Override
    public void saveBankCard(Map<String, String> params) throws BizServiceException {
        String result = null;
        try {
            String queryString = createLinkString(params);
            queryString = queryString + "&appId=" + APPID + "&signature=" + getSignAture(params);
            result = HttpUtil.doPost(URL + "/open-api/applicant/setCard", "application/x-www-form-urlencoded", queryString);
            //result = unicode2String(result);
            //result = URLDecoder.decode(result, "UTF-8");
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        JSONObject jSONObject = new JSONObject(result);
        if (!"0".equals(jSONObject.get("error").toString())) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, result);
        }
    }

    /**
     * 正扫支付
     *
     * @param params
     * @return
     * @throws BizServiceException
     */
    @Override
    public Map<String, String> scanPay(Map<String, String> params, EBizCode bizCode) throws BizServiceException {
        String result;
        try {
            String payUrl;
            if (bizCode.equals(EBizCode.ALLIPAY)) {
                payUrl = "/open-api/unionpay/customerscan/type/alipay";
            } else if (bizCode.equals(EBizCode.WECHAT)) {
                payUrl = "/open-api/unionpay/customerscan/type/wechat";
            } else {
                throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR);
            }
            params.put("notifyUrl", NOTIFY_URL + "/trade_callback");
            String queryString = createLinkString(params);
            queryString = queryString + "&appId=" + APPID + "&signature=" + getSignAture(params);
            result = HttpUtil.doPost(URL + payUrl, "application/x-www-form-urlencoded", queryString);
            //result = unicode2String(result);
            //result = URLDecoder.decode(result, "UTF-8");
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        JSONObject jSONObject = new JSONObject(result);
        if (!"0".equals(jSONObject.get("error").toString())) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, result);
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("respCode", jSONObject.getJSONObject("rows").get("respCode").toString());
        resultMap.put("respDesc", jSONObject.getJSONObject("rows").get("respDesc").toString());
        resultMap.put("orderNo", jSONObject.getJSONObject("rows").get("orderNo").toString());
        resultMap.put("payinfo", jSONObject.getJSONObject("rows").get("payinfo").toString());
        return resultMap;
    }

    /**
     * js支付
     *
     * @param params
     * @return
     * @throws BizServiceException
     */
    @Override
    public Map<String, String> jsPay(Map<String, String> params, EBizCode bizCode) throws BizServiceException {
        String result;
        try {
            String payUrl;
            if (bizCode.equals(EBizCode.ALLIPAY)) {
                payUrl = "/open-api/unionpay/webpay/type/alipay";
            } else if (bizCode.equals(EBizCode.WECHAT)) {
                payUrl = "/open-api/unionpay/webpay/type/wechat";
            } else {
                throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR);
            }
            params.put("notifyUrl", NOTIFY_URL + "/trade_callback");
            String queryString = createLinkString(params);
            queryString = queryString + "&appId=" + APPID + "&signature=" + getSignAture(params);
            result = HttpUtil.doPost(URL + payUrl, "application/x-www-form-urlencoded", queryString);
            //result = unicode2String(result);
            //result = URLDecoder.decode(result, "UTF-8");
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        JSONObject jSONObject = new JSONObject(result);
        if (!"0".equals(jSONObject.get("error").toString())) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, result);
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("respCode", jSONObject.getJSONObject("rows").get("respCode").toString());
        resultMap.put("respDesc", jSONObject.getJSONObject("rows").get("respDesc").toString());
        resultMap.put("orderNo", jSONObject.getJSONObject("rows").get("orderNo").toString());
        resultMap.put("url", jSONObject.getJSONObject("rows").get("url").toString());
        return resultMap;
    }

    /**
     * 扣率初始化
     *
     * @return
     * @throws BizServiceException
     */
    @Override
    public void saveRate(Map<String, String> params) throws BizServiceException {
        String result = null;
        try {
            String queryString = createLinkString(params);
            queryString = queryString + "&appId=" + APPID + "&signature=" + getSignAture(params);
            result = HttpUtil.doPost(URL + "/open-api/store/setRate", "application/x-www-form-urlencoded", queryString);
            //result = unicode2String(result);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        JSONObject jSONObject = new JSONObject(result);
        if (!"0".equals(jSONObject.get("error").toString())) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR);
        }
    }

    /**
     * 卡所在支行信息获取
     *
     * @return
     * @throws BizServiceException
     */
    @Override
    public Map<String, String> getCardbranchNo(Map<String, String> params) throws BizServiceException {
        String result = null;
        try {
            String queryString = createLinkString(params);
            queryString = queryString + "&appId=" + APPID + "&signature=" + getSignAture(params);
            result = HttpUtil.doPost(URL + "/open-api/card/cardbranchNo", "application/x-www-form-urlencoded", queryString);
            //result = unicode2String(result);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        JSONObject jSONObject = new JSONObject(result);
        if (!"0".equals(jSONObject.get("error").toString())) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR);
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("name", jSONObject.getJSONObject("rows").get("name").toString());
        resultMap.put("paySysBankCode", jSONObject.getJSONObject("rows").get("paySysBankCode").toString());
        return resultMap;
    }

    /**
     * 批量进件用
     *
     * @return
     * @throws BizServiceException
     */
    @Override
    public String getBathMemberNo(Map<String, String> params) throws BizServiceException {
        String result = null;
        try {
            params.put("mcc", "5331");
            String queryString = createLinkString(params);
            queryString = queryString + "&appId=" + APPID + "&signature=" + getSignAture(params);
            result = HttpUtil.doPost(URL + "/open-api/storeInfo/storeAdd/type/Pingan", "application/x-www-form-urlencoded", queryString);
            //result = unicode2String(result);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        JSONObject jSONObject = new JSONObject(result);
        if (!"0".equals(jSONObject.get("error").toString())) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR);
        }
        return jSONObject.getJSONObject("rows").get("memberCode").toString();
    }

//    /**
//     * MCC码列表（测试用）
//     *
//     * @return
//     * @throws BizServiceException
//     */
//    @Override
//    public String getMccList() throws BizServiceException {
//        String result = null;
//        try {
//            Map<String, String> params = new HashMap<>();
//            String queryString = "appId=" + APPID + "&signature=" + getSignAture(params);
//            result = HttpUtil.doPost(URL + "/open-api/mcc/lists", "application/x-www-form-urlencoded", queryString);
//            result = unicode2String(result);
//        } catch (Exception e) {
//            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
//        }
//        JSONObject jSONObject = new JSONObject(result);
//        if (!"0".equals(jSONObject.get("error").toString())) {
//            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR);
//        }
//        return result;
//    }

    /**
     * 根据参数获取签名Sign
     *
     * @param params
     * @return
     */
    private String getSignAture(Map<String, String> params) throws BizServiceException {
        String param = createLinkString(params);
        if (StringUtils.isEmpty(param)) {
            param = "appId=" + APPID + "&key=" + KEY;
        } else {
            param = param + "&appId=" + APPID + "&key=" + KEY;
        }
        String signature = null;
        try {
            signature = EncryptUtil.encryptMd5(param).toLowerCase();
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        return signature;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
//            if (StringUtils.isEmpty(value)) {//空参数不签名
//                continue;
//            }
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = sb.append(prestr).append(key).append("=").append(value).toString();
            } else {
                prestr = sb.append(prestr).append(key).append("=").append(value).append("&").toString();
            }
        }
        return prestr;
    }

    /**
     * 接口返回结果转map
     *
     * @param result
     * @return
     */
    public static Map<String, String> resultToMap(String result) {
        Map<String, String> params = new HashMap<>();
        String[] paramsList = result.split("&");
        for (String str : Arrays.asList(paramsList)) {
            String[] param = str.split("=");
            params.put(param[0], param[1]);
        }
        return params;
    }

//    /**
//     * Unicode转中文
//     *
//     * @param unicode
//     * @return
//     */
//    public static String unicode2String(String unicode) {
//        String[] asciis = unicode.split("\\\\u");
//        String nativeValue = asciis[0];
//        try {
//            for (int i = 1; i < asciis.length; i++) {
//                String code = asciis[i];
//                nativeValue += (char) Integer.parseInt(code.substring(0, 4), 16);
//                if (code.length() > 4) {
//                    nativeValue += code.substring(4, code.length());
//                }
//            }
//        } catch (NumberFormatException e) {
//            return unicode;
//        }
//        return nativeValue;
//    }

    public static void main(String[] args) {
        //System.out.println(getMoney("1"));
        //System.out.println("8" + String.valueOf(System.currentTimeMillis()).substring(0, 10) + RandomUtil.getNumberStr(3));
        //System.out.println(resultToMap("{\"error\":0,\"msg\":\"\",\"rows\":[{\"id\":\"1\",\"mcc\":\"5094\",\"name1\":\"珠宝金饰\",\"name2\":\"贵重珠宝、首饰，钟表零售\"}]"));
    }

}
