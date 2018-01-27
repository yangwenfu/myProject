package com.xinyunlian.jinfu.pay.entity.id.producer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.Sequence;
import com.xinyunlian.jinfu.common.entity.id.SequenceProducer;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import com.xinyunlian.jinfu.pay.entity.PayRecvOrdPo;
import org.apache.commons.lang.time.DateFormatUtils;

import java.text.MessageFormat;

public class OrdIdProducer extends DefaultIdProducer<PayRecvOrdPo> {

    private static final String ORD_ID_FORMAT = "PR{0}";

    private static final String PREFIX = "yyMMdd";

    @Override
    protected Object doProduceId(Context ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(DateFormatUtils.format(ctx.getSequence().getDate(), PREFIX));
        sb.append(formatSeq(ctx));
        return MessageFormat.format(ORD_ID_FORMAT, sb);

    }

}
