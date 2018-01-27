package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.user.enums.ESmsSendType;

/**
 * 用户Service
 * @author KimLL
 * @version 
 */

public interface SmsService {
    /**
     * 获取短信验证码
     * @param mobile
     * @return
     */
    String getVerifyCode(String mobile,ESmsSendType type);

    /**
     * 验证短信验证码
     * @param mobile
     * @param verifyCode
     * @return
     */
    Boolean confirmVerifyCode(String mobile,String verifyCode,ESmsSendType type);

    /**
     * 清除短信验证码
     * @param mobile
     * @param type
     */
    void clearVerifyCode(String mobile,ESmsSendType type);
}
