package com.xinyunlian.jinfu.service.inner;

import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.domain.req.NYRealAuthRequest;
import com.xinyunlian.jinfu.domain.resp.NYRealAuthResponse;
import com.xinyunlian.jinfu.dto.RealAuthResponseDto;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import com.xinyunlian.jinfu.service.UserRealAuthService;

/**
 * Created by dell on 2016/11/2.
 */
public class NyUserRealAuthDelayServiceImpl implements UserRealAuthService{
    @Override
    public boolean realAuth(UserRealAuthDto realAuthDto) {
        try {
            Thread.sleep(3500L);
        } catch (InterruptedException e) {
        }
        return false;
    }

    @Override
    public RealAuthResponseDto realAuthWithResponse(UserRealAuthDto realAuthDto) {
        try {
            Thread.sleep(3500L);
        } catch (InterruptedException e) {
        }
        return null;
    }
}
