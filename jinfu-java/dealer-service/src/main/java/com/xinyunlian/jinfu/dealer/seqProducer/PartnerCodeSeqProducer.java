package com.xinyunlian.jinfu.dealer.seqProducer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.common.util.DateHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.xinyunlian.jinfu.common.util.DateHelper.SIMPLE_DATE_YMD;

/**
 * Created by jl062 on 2017/2/8.
 */
@Component
public class PartnerCodeSeqProducer extends DefaultIdProducer<String> {

    @Override
    protected Object doProduceId(Context ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(DateHelper.formatDate(new Date(), SIMPLE_DATE_YMD).substring(2,8));
        sb.append(ctx.getParam().toString());
        Long seq = ctx.getSequence().getSeq() % 100;
        sb.append(StringUtils.leftPad(seq.toString(), 2, '0'));
        return sb.toString();
    }

}
