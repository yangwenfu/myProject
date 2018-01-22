package com.xinyunlian.jinfu.sign.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.Date;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
public interface UserSignInLogService {

    /**
     * 检查指定日期有没有签到过
     * @param userId
     * @param signInDate
     * @return true：已签到，false：未签到
     * @throws BizServiceException
     */
    Boolean checkSignIn(String userId, Date signInDate) throws BizServiceException;

    /**
     * 指定日期签到
     * @param userId
     * @param signInDate
     * @throws BizServiceException
     */
    void signIn(String userId, Date signInDate) throws BizServiceException;

}
