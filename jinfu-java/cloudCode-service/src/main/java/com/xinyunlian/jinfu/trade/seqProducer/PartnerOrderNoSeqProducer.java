package com.xinyunlian.jinfu.trade.seqProducer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by menglei on 2017/9/7.
 */
@Component
public class PartnerOrderNoSeqProducer extends DefaultIdProducer<String> {

    @Override
    protected Object doProduceId(Context ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("8");
        sb.append(String.valueOf(System.currentTimeMillis()).substring(0, 10));
        Long seq = ctx.getSequence().getSeq() % 100;
        sb.append(StringUtils.leftPad(seq.toString(), 3, '0'));
        return sb.toString();
    }

}
