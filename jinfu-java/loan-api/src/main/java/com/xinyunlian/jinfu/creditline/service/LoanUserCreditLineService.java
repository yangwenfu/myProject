package com.xinyunlian.jinfu.creditline.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.creditline.dto.LoanUserCreditLineDto;
import com.xinyunlian.jinfu.creditline.enums.ELoanUserCreditLineStatus;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author willwang
 */
public interface LoanUserCreditLineService {

    /**
     * 用户额度信息获取
     * @param userId
     * @return
     */
    LoanUserCreditLineDto get(String userId);

    /**
     * 批量获取用户可用额度
     * @param userIds
     * @return
     */
    HashMap<String, BigDecimal> getAvaliable(Collection<String> userIds);

    /**
     * 申请额度
     * @param userId
     */
    void apply(String userId);

    /**
     * 授信操作，额度变更
     * @param userId
     * @param creditLine
     */
    void credit(String userId, BigDecimal creditLine);

    /**
     * 使用额度
     */
    void use(String userId) throws BizServiceException;

    /**
     * 释放湿度
     */
    void back(String userId) throws BizServiceException;

    /**
     * 响应用户额度变更请求
     * @param userId
     * @throws BizServiceException
     */
    void updated(String userId) throws BizServiceException;

    /**
     * 由外部保证结清检测
     * @param userId
     * @throws BizServiceException
     */
    void updateWithoutCheck(String userId) throws BizServiceException;

    /**
     * 重置额度
     * @param userId
     * @param amt
     * @param status
     * @throws BizServiceException
     */
    void reset(String userId, BigDecimal amt, ELoanUserCreditLineStatus status) throws BizServiceException;

}
