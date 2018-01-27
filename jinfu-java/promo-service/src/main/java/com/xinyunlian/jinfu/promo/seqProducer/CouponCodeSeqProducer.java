package com.xinyunlian.jinfu.promo.seqProducer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.common.util.DateHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by jll on 2017/2/8.
 */
@Component
public class CouponCodeSeqProducer extends DefaultIdProducer<String> {

    @Override
    protected Object doProduceId(Context ctx) {
        String date =DateHelper.formatDate(new Date(), "yyyyMMddhhmmss");
        StringBuilder sb = new StringBuilder();

        //产品编号 + 年-2015 +月日时分秒 + 自增三位
        sb.append(ctx.getParam().toString());
        sb.append(StringUtils.leftPad(String.valueOf(Integer.valueOf(date.substring(0,4)) - 2015), 2, '0'));
        sb.append(date.substring(4,14));
        sb.append(StringUtils.leftPad(ctx.getSequence().getSeq().toString(), 3, '0'));
        return sb.toString();
    }

}
