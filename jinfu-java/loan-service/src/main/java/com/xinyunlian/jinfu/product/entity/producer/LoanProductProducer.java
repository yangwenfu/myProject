package com.xinyunlian.jinfu.product.entity.producer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.annotation.SequenceConsumer;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.product.entity.LoanProductInfoPo;

import java.text.MessageFormat;

public class LoanProductProducer extends DefaultIdProducer<LoanProductInfoPo> {

    private static final String PROD_ID_FORMAT = "L{0}";
    private static final int SEQ_LENGTH = 3;

    @Override
    protected String getSequenceType() {
        return SequenceConsumer.Interval.NO.value();
    }

    @Override
    protected Object doProduceId(Context ctx) {
        LoanProductInfoPo po = (LoanProductInfoPo) ctx.getEntity();
        StringBuilder sb = new StringBuilder();
        sb.append(po.getProductType().getCode());
        sb.append(formatSeq(ctx));
        return MessageFormat.format(PROD_ID_FORMAT, sb);

    }

    @Override
    protected int getSeqLength() {
        return SEQ_LENGTH;
    }

}
