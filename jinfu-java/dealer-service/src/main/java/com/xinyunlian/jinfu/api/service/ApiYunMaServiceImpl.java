package com.xinyunlian.jinfu.api.service;

import com.alibaba.fastjson.JSON;
import com.xinyunlian.jinfu.api.dto.*;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by menglei on 2016年09月23日.
 */
@Service
public class ApiYunMaServiceImpl implements ApiYunMaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiYunMaServiceImpl.class);

    @Value("${yunma.url}")
    private String YUNMA_URL;

    @Value("${yunma.manage.url}")
    private String YM_URL;

    @Value("${yunma.key}")
    private String KEY;

    /**
     * 根据openId获取手机号
     *
     * @param openid
     * @return
     * @throws BizServiceException
     */
    public String getUserMobile(String openid) throws BizServiceException {
        String result = null;
        try {
            String queryString = "?token=" + getToken(openid) + "&openid=" + openid;
            result = HttpUtil.doGetToString(YUNMA_URL + "/index.php/OpenApi/getUserMobile" + queryString);
        } catch (IOException e) {
            LOGGER.error("根据openId获取手机号失败", e);
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
        }
        ApiUserDto apiUserDto = JSON.parseObject(result, ApiUserDto.class);
        if (apiUserDto == null || !"0".equals(apiUserDto.getError())) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
        }
        return apiUserDto.getMobile();
    }

    /**
     * 根据storeId获取某段日期的流水统计
     *
     * @param tradeCountSearchDto
     * @return
     * @throws BizServiceException
     */
    public TradeCountSearchDto getTradeCountByStoreId(TradeCountSearchDto tradeCountSearchDto) throws BizServiceException {
        String result = null;
        if (StringUtils.isEmpty(tradeCountSearchDto.getDatebegin())) {
            tradeCountSearchDto.setDatebegin(StringUtils.EMPTY);
        }
        if (StringUtils.isEmpty(tradeCountSearchDto.getDateend())) {
            tradeCountSearchDto.setDateend(StringUtils.EMPTY);
        }
        try {
            String queryString = "?token=" + getToken(tradeCountSearchDto.getStoreId()) + "&storeId=" + tradeCountSearchDto.getStoreId()
                    + "&datebegin=" + tradeCountSearchDto.getDatebegin() + "&dateend=" + tradeCountSearchDto.getDateend()
                    + "&pagesize=" + tradeCountSearchDto.getPageSize() + "&p=" + tradeCountSearchDto.getCurrentPage();
            result = HttpUtil.doGetToString(YM_URL + "/OpenApi/getCountBymonth" + queryString);
        } catch (IOException e) {
            LOGGER.error("根据storeId获取某段日期的流水统计失败", e);
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
        }
        ApiTradeCountDto apiTradeCountDto = JSON.parseObject(result, ApiTradeCountDto.class);
        if (apiTradeCountDto == null) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
        } else if ("4".equals(apiTradeCountDto.getError())) {
            tradeCountSearchDto.setList(new ArrayList<>());
            return tradeCountSearchDto;
        } else if (!"0".equals(apiTradeCountDto.getError())) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
        }
        tradeCountSearchDto.setList(apiTradeCountDto.getTradeList());
        tradeCountSearchDto.setTotalPages(apiTradeCountDto.getPagetotal());
        tradeCountSearchDto.setTotalRecord(apiTradeCountDto.getTotal());
        return tradeCountSearchDto;
    }

    /**
     * 根据storeId获取某天流水
     *
     * @param tradeSearchDto
     * @return
     * @throws BizServiceException
     */
    public TradeSearchDto getTradeByStoreId(TradeSearchDto tradeSearchDto) throws BizServiceException {
        String result = null;
        try {
            String queryString = "?token=" + getToken(tradeSearchDto.getStoreId()) + "&storeId=" + tradeSearchDto.getStoreId()
                    + "&date=" + tradeSearchDto.getDate() + "&pagesize=" + tradeSearchDto.getPageSize() + "&p=" + tradeSearchDto.getCurrentPage();
            result = HttpUtil.doGetToString(YM_URL + "/OpenApi/getOneday" + queryString);
        } catch (IOException e) {
            LOGGER.error("根据storeId获取某天流水失败", e);
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
        }
        ApiTradeDto apiTradeDto = JSON.parseObject(result, ApiTradeDto.class);
        if (apiTradeDto == null) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
        } else if ("4".equals(apiTradeDto.getError())) {
            apiTradeDto.setData(new ArrayList<>());
            return tradeSearchDto;
        } else if (!"0".equals(apiTradeDto.getError())) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
        }
        tradeSearchDto.setList(apiTradeDto.getData());
        tradeSearchDto.setTotalPages(apiTradeDto.getPagetotal());
        tradeSearchDto.setTotalRecord(apiTradeDto.getTotal());
        return tradeSearchDto;
    }

    /**
     * 根据参数获取签名token
     *
     * @param param
     * @return
     */
    private String getToken(String param) throws BizServiceException {
        String token = null;
        try {
            token = EncryptUtil.encryptMd5(param + KEY).toUpperCase();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOGGER.error("签名失败", e);
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
        }
        return token;//throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
    }

}
