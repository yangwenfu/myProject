package com.xinyunlian.jinfu.contract.entity.id.producer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.contract.entity.UserContractPo;
import org.springframework.stereotype.Component;

/**
 * Created by JL on 2016/9/26.
 */
@Component
public class ContractIdProducer extends DefaultIdProducer<UserContractPo> {

    @Override
    protected Object doProduceId(Context ctx) {
        return ctx.getEntity().toString() + super.doProduceId(ctx);
    }

    @Override
    protected int getSeqLength() {
        return 6;
    }


}
