package com.xinyunlian.jinfu.api.service;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.HttpUtil;
import com.xinyunlian.jinfu.trade.dto.YmTradeDto;
import com.xinyunlian.jinfu.yunma.dao.YmThirdConfigDao;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.entity.YmThirdConfigPo;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 赢掌柜
 * Created by menglei on 2016年09月23日.
 */
@Service
public class ApiNotifyServiceImpl implements ApiNotifyService {

    @Autowired
    private YmThirdConfigDao ymThirdConfigDao;

    /**
     * 回传
     *
     * @param ymTradeDto
     * @return
     * @throws BizServiceException
     */
    @Override
    public String tradeCallback(YmTradeDto ymTradeDto, YMMemberDto yMMemberDto) throws BizServiceException {
        String result;

        //判断云码来源，花啦，新商盟
        String regEx = "[^a-z]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(yMMemberDto.getQrcodeNo());

        YmThirdConfigPo ymThirdConfigPo = ymThirdConfigDao.findByAppId(m.replaceAll(""));
        if (ymThirdConfigPo == null) {//是否已录入
            return "SUCCESS";
        }
        try {
            SortedMap<String, String> sortedMap = new TreeMap<>();
            sortedMap.put("tradeNo", ymTradeDto.getTradeNo());
            sortedMap.put("transAmt", ymTradeDto.getTransAmt() + StringUtils.EMPTY);
            sortedMap.put("createTs", ymTradeDto.getCreateTs() + StringUtils.EMPTY);

            String queryString = "tradeNo=" + ymTradeDto.getTradeNo() + "&transAmt=" + ymTradeDto.getTransAmt() + "&createTs=" + ymTradeDto.getCreateTs() + "&sign=" + createSign(sortedMap, ymThirdConfigPo.getKey());
            result = HttpUtil.doPost(ymThirdConfigPo.getNotifyUrl(), "application/x-www-form-urlencoded", queryString);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        if (!"SUCCESS".equals(result)) {
            return "FAILED";
        }
        return result;
    }

    /**
     * Description: 创建签名
     *
     * @param parameters
     * @param apiKey
     * @return
     */
    public static String createSign(SortedMap<String, String> parameters, String apiKey) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String v = entry.getValue();
            if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(v) && !"sign".equals(entry.getKey()) && !"key".equals(entry.getKey())) {
                sb.append(entry.getKey()).append("=").append(v).append("&");
            }
        }
        sb.append("key=" + apiKey);
        String sign = null;
        try {
            sign = EncryptUtil.encryptMd5(sb.toString()).toUpperCase();
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "签名失败", e);
        }
        return sign;

    }

}
