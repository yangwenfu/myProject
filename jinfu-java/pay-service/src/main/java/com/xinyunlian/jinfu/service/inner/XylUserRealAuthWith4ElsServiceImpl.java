package com.xinyunlian.jinfu.service.inner;

import com.xinyunlian.jinfu.domain.req.RealAuthRequest;
import com.xinyunlian.jinfu.domain.resp.RealAuthResponse;
import com.xinyunlian.jinfu.dto.RealAuthResponseDto;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import com.xinyunlian.jinfu.service.UserRealAuthService;

/**
 * Created by dell on 2016/11/2.
 */
public class XylUserRealAuthWith4ElsServiceImpl implements UserRealAuthService{
    @Override
    public boolean realAuth(UserRealAuthDto realAuthDto) {

        RealAuthRequest acctAuthRequest = new RealAuthRequest();
        acctAuthRequest.setAcctNo(realAuthDto.getBankCardNo());
        acctAuthRequest.setMobileNo(realAuthDto.getPhone());
        acctAuthRequest.setCertifyNo(realAuthDto.getIdCardNo());
        acctAuthRequest.setCustName(realAuthDto.getName());
        RealAuthResponse acctAuthResponse = acctAuthRequest.execute();
        if (acctAuthResponse.getResult() == PayRecvResult.SUCCESS) {
            return true;
        }
        return false;
    }

    @Override
    public RealAuthResponseDto realAuthWithResponse(UserRealAuthDto realAuthDto) {
        throw new UnsupportedOperationException();
    }
}
