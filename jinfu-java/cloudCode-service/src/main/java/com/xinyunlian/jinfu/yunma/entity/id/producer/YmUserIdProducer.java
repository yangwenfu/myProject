package com.xinyunlian.jinfu.yunma.entity.id.producer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.annotation.SequenceConsumer;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.yunma.entity.YMUserInfoPo;

import java.text.MessageFormat;

/**
 * Created by menglei on 2017-01-13.
 */
public class YmUserIdProducer extends DefaultIdProducer<YMUserInfoPo> {

    private static final String CLERK_ID_FORMAT = "UW{0}";
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
