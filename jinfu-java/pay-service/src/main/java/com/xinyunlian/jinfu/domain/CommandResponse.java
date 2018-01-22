package com.xinyunlian.jinfu.domain;

import com.xinyunlian.jinfu.enums.ETrxType;
import org.apache.commons.lang.StringUtils;

public abstract class CommandResponse {

    /**
     * get return message, should be override in sub class.
     *
     * @return
     */
    public String getRetMsg() {
        return StringUtils.EMPTY;
    }

    /**
     * get return code, should be override in sub class.
     *
     * @return
     */
    public String getRetCode() {
        return StringUtils.EMPTY;
    }

    public abstract ETrxType getTrxType();
}
