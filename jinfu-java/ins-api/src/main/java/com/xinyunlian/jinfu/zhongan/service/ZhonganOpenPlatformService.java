package com.xinyunlian.jinfu.zhongan.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.zhongan.dto.VehInsDetailNotifyDto;
import com.xinyunlian.jinfu.zhongan.dto.VehInsNotifyDto;
import com.xinyunlian.jinfu.zhongan.dto.ZhongAnRequestDto;

/**
 * Created by dongfangchao on 2017/3/13/0013.
 */
public interface ZhonganOpenPlatformService {

    /**
     * RC4加密bizContent
     * @param req
     * @return
     * @throws BizServiceException
     */
    String rc4Encrypt(ZhongAnRequestDto req) throws BizServiceException;

    /**
     * @param json
     * @return
     * @throws BizServiceException
     */
    VehInsNotifyDto translateToVehNotify(String json) throws BizServiceException;

    /**
     *
     * @param json
     * @return
     * @throws BizServiceException
     */
    VehInsDetailNotifyDto translateToVehDetailNofity(String json) throws BizServiceException;

}
