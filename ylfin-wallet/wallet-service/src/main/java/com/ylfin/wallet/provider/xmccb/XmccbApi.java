package com.ylfin.wallet.provider.xmccb;

import com.ylfin.wallet.provider.xmccb.req.OpenAccountReq;
import com.ylfin.wallet.provider.xmccb.req.UserQueryReq;
import com.ylfin.wallet.provider.xmccb.resp.OpenAccountResp;
import com.ylfin.wallet.provider.xmccb.resp.UserQueryResp;

public interface XmccbApi {

    OpenAccountResp openAccount(OpenAccountReq req);

    UserQueryResp queryUser(UserQueryReq req);
}
