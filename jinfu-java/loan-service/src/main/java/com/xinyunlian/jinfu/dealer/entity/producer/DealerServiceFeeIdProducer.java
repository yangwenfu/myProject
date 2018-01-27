package com.xinyunlian.jinfu.dealer.entity.producer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.dealer.entity.DealerServiceFeePo;
import org.apache.commons.lang.time.DateFormatUtils;

public class DealerServiceFeeIdProducer extends DefaultIdProducer<DealerServiceFeePo> {

    @Override
    protected Object doProduceId(Context ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("DSF");
        sb.append(DateFormatUtils.format(ctx.getSequence().getDate(), "yyMMdd"));
        sb.append(this.formatSeq(ctx));
        return sb.toString();
    }

}
