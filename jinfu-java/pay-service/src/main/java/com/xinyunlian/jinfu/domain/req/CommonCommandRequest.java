package com.xinyunlian.jinfu.domain.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinyunlian.jinfu.common.entity.id.IdUtil;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.domain.CommandRequest;
import com.xinyunlian.jinfu.domain.CommandResponse;
import com.xinyunlian.jinfu.domain.SignatureIgnore;

/**
 * Created by cong on 2016/5/29.
 */
public abstract class CommonCommandRequest<F extends CommandResponse> extends CommandRequest<F> {

    @SignatureIgnore
    private static final String VERSION = "1.0";

    @SignatureIgnore
    private static final String PARTNER_ID = AppConfigUtil.getConfig("pay.parentId");

    @SignatureIgnore
    private static final String DEFAULT_SIGNTYPE = "1";

    @SignatureIgnore
    private static final String DEFAULT_CHARSET = "1";

    private String version = VERSION;

    @JsonProperty("serial_id")
    private String serialId = IdUtil.produceUUID();

    @JsonProperty("partner_id")
    private String partnerId = PARTNER_ID;

    @JsonProperty("sign_type")
    private String signType = DEFAULT_SIGNTYPE;

    private String charset = DEFAULT_CHARSET;

    @SignatureIgnore
    @JsonProperty("sign_msg")
    private String signMsg;

    public abstract String getExecuteUrl();

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }
}
