package com.xinyunlian.jinfu.user.entity.id.producer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.annotation.SequenceConsumer;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.user.entity.MgtUserPo;

import java.text.MessageFormat;


public class MgtUserIdProducer extends DefaultIdProducer<MgtUserPo> {

    private static final String USER_ID_FORMAT = "UM{0}";
    private static final int SEQ_LENGTH = 10;

    @Override
    protected String getSequenceType() {
        return SequenceConsumer.Interval.NO.value();
    }

    @Override
    protected Object doProduceId(Context ctx) {
        return MessageFormat.format(USER_ID_FORMAT, formatSeq(ctx));
    }

    @Override
    protected int getSeqLength() {
        return SEQ_LENGTH;
    }

}
