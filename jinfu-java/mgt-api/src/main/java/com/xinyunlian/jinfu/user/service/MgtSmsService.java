package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.user.enums.EMgtSmsType;

/**
 * Created by dongfangchao on 2016/12/26/0026.
 */
public interface MgtSmsService {

    /**
     * 获取短信验证码
     * @param mobile
     * @return
     */
    String getVerifyCode(String mobile,EMgtSmsType type);

    /**
     * 验证短信验证码
     * @param mobile
     * @param verifyCode
     * @return
     */
    Boolean checkVerifyCode(String mobile,String verifyCode,EMgtSmsType type);

    /**
     * 清除短信验证码
     * @param mobile
     * @param type
     */
    void clearVerifyCode(String mobile,EMgtSmsType type);

}
