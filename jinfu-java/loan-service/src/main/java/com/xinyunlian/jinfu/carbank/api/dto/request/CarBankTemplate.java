package com.xinyunlian.jinfu.carbank.api.dto.request;

import com.xinyunlian.jinfu.carbank.api.CarBankAbstract;
import com.xinyunlian.jinfu.carbank.api.dto.response.CarBankResponse;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
public abstract class CarBankTemplate<response extends CarBankResponse> extends CarBankAbstract {

    @Override
    public response send() {
        return (response) this.execute(this);
    }

}
