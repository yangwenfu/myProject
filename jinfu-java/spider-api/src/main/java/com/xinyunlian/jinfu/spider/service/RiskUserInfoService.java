package com.xinyunlian.jinfu.spider.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.spider.dto.req.AuthLoginDto;
import com.xinyunlian.jinfu.spider.dto.resp.RiskUserInfoDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by menglei on 2016年11月07日.
 */
public interface RiskUserInfoService {

    boolean authLogin(AuthLoginDto authLoginDto) throws BizServiceException;

    void spiderUserInfo(String userId) throws BizServiceException;

    RiskUserInfoDto getUserInfo(String userId);

    BigDecimal getYearlyOrderAmout(String userId);

    /**
     * 根据userId查询烟草爬虫数据(只是查询，不会有重爬机制)
     * @param userId
     * @return
     */
    RiskUserInfoDto findByUserId(String userId);

    AuthLoginDto getAuthLoginData(String userId);

    List<AuthLoginDto> getFailedUserList();

    Double getOrderAmtForThisMonth(String userId);

    Double getGrowthRate(String userId);

    Boolean canSpider(AuthLoginDto authLoginDto);

    Boolean isAuthed(String userId);
}
