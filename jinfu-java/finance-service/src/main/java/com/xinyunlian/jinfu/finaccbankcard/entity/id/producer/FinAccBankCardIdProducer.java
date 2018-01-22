package com.xinyunlian.jinfu.finaccbankcard.entity.id.producer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.annotation.SequenceConsumer;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.finaccbankcard.entity.FinAccBankCardPo;

import java.text.MessageFormat;

/**
 * Created by dongfangchao on 2017/1/6/0006.
 */
public class FinAccBankCardIdProducer extends DefaultIdProducer<FinAccBankCardPo> {

    private static final String FIN_ID_FORMAT = "FIN{0}";
    private static final int SEQ_LENGTH = 10;

    @Override
    protected String getSequenceType() {
        return SequenceConsumer.Interval.NO.value();
    }

    @Override
    protected Object doProduceId(Context ctx) {
        return MessageFormat.format(FIN_ID_FORMAT, formatSeq(ctx));
    }

    @Override
    protected int getSeqLength() {
        return SEQ_LENGTH;
    }

}
