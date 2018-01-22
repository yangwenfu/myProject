package com.xinyunlian.jinfu.service;

import com.xinyunlian.jinfu.dto.RealAuthResponseDto;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;

/**
 * Created by JL on 2016/9/12.
 */
public interface UserRealAuthService {

    /**
     * 实名认证接口
     *
     * @param realAuthDto
     * @return
     */
    boolean realAuth(UserRealAuthDto realAuthDto);

    /**
     * 只给后台查询用，请别随便调用！！！！！
     * @param realAuthDto
     * @return
     */
    RealAuthResponseDto realAuthWithResponse(UserRealAuthDto realAuthDto);
}
