package com.xinyunlian.jinfu.logback.appender;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import com.xinyunlian.jinfu.mq.sender.QueueSender;

/**
 * Created by carrot on 2017/8/16.
 */
public class GAJmsAppender<E> extends UnsynchronizedAppenderBase<E> {

    private static final String GA_QUEUE_NAME = "logstash.ga";
    private static final String ADDRESSBOOK_QUEUE_NAME = "logstash.jinfugather.addressbook";
    private static final String APPINFO_QUEUE_NAME = "logstash.jinfugather.appinfo";
    private static final String CALL_QUEUE_NAME = "logstash.jinfugather.callhistory";
    private static final String MESSAGE_QUEUE_NAME = "logstash.jinfugather.message";

    @Override
    protected void append(E event) {
        try {
            QueueSender queueSender = ApplicationContextUtil.getBean(QueueSender.class);
            if (queueSender == null)
                return;
            String message = ((LoggingEvent) event).getMessage();
            JSONObject gaData = (JSONObject) JSONObject.parse(message);
            String gatherType = gaData.getString("gatherType");
            String queueName = null;

            if (gatherType == null)
                queueName = GA_QUEUE_NAME;
            else if ("gaMessage".equals(gatherType))
                queueName = MESSAGE_QUEUE_NAME;
            else if ("gaCallHistory".equals(gatherType))
                queueName = CALL_QUEUE_NAME;
            else if ("gaAppInfo".equals(gatherType))
                queueName = APPINFO_QUEUE_NAME;
            else if ("gaAddressBook".equals(gatherType))
                queueName = ADDRESSBOOK_QUEUE_NAME;

            //失败重试次数3
            for (int retry = 0; retry < 3; retry++) {
                try {
                    queueSender.send(queueName, message);
                    break;
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
        }
    }

}
