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
public class NyUserRealAuthWith3ElsServiceImpl implements UserRealAuthService{
    @Override
    public boolean realAuth(UserRealAuthDto realAuthDto) {
        NYRealAuthResponse nyRealAuthResponse = this.execute(realAuthDto);
        if(nyRealAuthResponse.getResult() == PayRecvResult.SUCCESS){
            return true;
        }
        return false;
    }

    @Override
    public RealAuthResponseDto realAuthWithResponse(UserRealAuthDto realAuthDto) {
        NYRealAuthResponse nyRealAuthResponse = this.execute(realAuthDto);
        RealAuthResponseDto rs = new RealAuthResponseDto();
        rs.setPass(nyRealAuthResponse.getResult() == PayRecvResult.SUCCESS);
        rs.setMessage(nyRealAuthResponse.getRespCode() + "," + nyRealAuthResponse.getRespMsg());
        return rs;
    }

    private NYRealAuthResponse execute(UserRealAuthDto realAuthDto){
        NYRealAuthRequest nyRealAuthRequest = new NYRealAuthRequest();
        nyRealAuthRequest.setCardNo(realAuthDto.getBankCardNo());
        nyRealAuthRequest.setCertNo(realAuthDto.getIdCardNo());
        nyRealAuthRequest.setName(realAuthDto.getName());
        nyRealAuthRequest.setTransType(NYRealAuthRequest.AUTH_WITH_3ELS);
        nyRealAuthRequest.setExecuteUrl(AppConfigUtil.getConfig("ny.domain")+"/verif/Verification3");
        return nyRealAuthRequest.execute();
    }
}
