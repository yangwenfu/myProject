package com.xinyunlian.jinfu.clerk.entity.id.producer;

import com.xinyunlian.jinfu.clerk.entity.ClerkInfPo;
import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.annotation.SequenceConsumer;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;

import java.text.MessageFormat;

/**
 * Created by DongFC on 2016-11-03.
 */
public class ClerkIdProducer extends DefaultIdProducer<ClerkInfPo> {

    private static final String CLERK_ID_FORMAT = "UCL{0}";
    private static final int SEQ_LENGTH = 10;

    @Override
    protected String getSequenceType() {
        return SequenceConsumer.Interval.NO.value();
    }

    @Override
    protected Object doProduceId(Context ctx) {
        return MessageFormat.format(CLERK_ID_FORMAT, formatSeq(ctx));
    }

    @Override
    protected int getSeqLength() {
        return SEQ_LENGTH;
    }

}
