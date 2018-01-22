package com.xinyunlian.jinfu.carbank.entity.id.producer;

import com.xinyunlian.jinfu.carbank.entity.LoanCarbankOrderPo;
import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.Sequence;
import com.xinyunlian.jinfu.common.entity.id.SequenceProducer;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;
import java.util.Calendar;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public class LoanCarbankOrderNoProcuder extends DefaultIdProducer<LoanCarbankOrderPo> {

    private static final String CARBANK_ORDER_NO_FORMAT = "CB{0}";

    @Override
    protected Object doProduceId(Context ctx) {
        StringBuilder sb = new StringBuilder();
        Calendar c = Calendar.getInstance();
        sb.append(c.get(Calendar.YEAR) - 2015);
        sb.append(StringUtils.leftPad(Integer.toOctalString(c.get(Calendar.MONTH) + 1), 2, '0'));
        sb.append(StringUtils.leftPad(Integer.toString(c.get(Calendar.DAY_OF_MONTH)), 2, '0'));
        sb.append(String.valueOf(System.currentTimeMillis()).substring(5));
        sb.append(StringUtils.leftPad(ctx.getSequence().getSeq().toString(), 3, '0'));
        return MessageFormat.format(CARBANK_ORDER_NO_FORMAT, sb);
    }

    @Override
    protected void doProduceSequence(Context ctx) {
        SequenceProducer sequenceProducer = ApplicationContextUtil
                .getBean("redisSequenceProducer", SequenceProducer.class);
        Sequence seq = sequenceProducer.produce(getSequenceName(getEntity(ctx)), getSequenceType());
        ctx.setSequence(seq);
    }
}
