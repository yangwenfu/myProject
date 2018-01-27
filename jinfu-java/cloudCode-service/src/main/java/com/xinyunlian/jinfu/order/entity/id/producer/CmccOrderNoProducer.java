package com.xinyunlian.jinfu.order.entity.id.producer;

import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.producer.DefaultIdProducer;
import com.xinyunlian.jinfu.order.entity.CmccOrderInfoPo;
import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;
import java.util.Calendar;

/**
 * Created by menglei on 2016年11月20日.
 */
public class CmccOrderNoProducer extends DefaultIdProducer<CmccOrderInfoPo> {

    private static final String CMCC_ID_FORMAT = "{0}";

    @Override
    protected Object doProduceId(Context ctx) {
        StringBuilder sb = new StringBuilder();
        Calendar c = Calendar.getInstance();
        sb.append(c.get(Calendar.YEAR));//年
        sb.append(StringUtils.leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0'));//月
        sb.append(StringUtils.leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), 2, '0'));//日
        sb.append(StringUtils.leftPad(String.valueOf(c.get(Calendar.HOUR_OF_DAY)), 2, '0'));//时
        sb.append(StringUtils.leftPad(String.valueOf(c.get(Calendar.MINUTE)), 2, '0'));//分
        sb.append(StringUtils.leftPad(String.valueOf(c.get(Calendar.SECOND)), 2, '0'));//秒
        sb.append("01");//业务单元 金服商城
        sb.append("02");//支付渠道 中移积分
        sb.append("00");//是否拆分多笔支付订单 未拆分
        sb.append(StringUtils.leftPad(ctx.getSequence().getSeq().toString(), 4, '0'));
        return MessageFormat.format(CMCC_ID_FORMAT, sb);
    }

}
