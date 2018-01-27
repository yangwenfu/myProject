package com.xinyunlian.jinfu.prod.entity.id.producer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.annotation.SequenceConsumer;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.prod.entity.ProductPo;

import java.text.MessageFormat;

/**
 * Created by DongFC on 2016-09-18.
 */
public class ProductIdProducer extends DefaultIdProducer<ProductPo> {

    private static final String PROD_ID_FORMAT = "PROD{0}";
    private static final int SEQ_LENGTH = 10;

    @Override
    protected String getSequenceType() {
        return SequenceConsumer.Interval.NO.value();
    }

    @Override
    protected Object doProduceId(Context ctx) {
        return MessageFormat.format(PROD_ID_FORMAT, formatSeq(ctx));
    }

    @Override
    protected int getSeqLength() {
        return SEQ_LENGTH;
    }

}
