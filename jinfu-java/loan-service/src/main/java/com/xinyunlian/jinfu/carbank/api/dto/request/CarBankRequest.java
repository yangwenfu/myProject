package com.xinyunlian.jinfu.carbank.api.dto.request;

import com.xinyunlian.jinfu.carbank.api.dto.response.CarBankResponse;

/**
 * @author willwang
 */
public interface CarBankRequest<T extends CarBankResponse>{

    T send();

    String getRequestUrl();

}
