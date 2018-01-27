package com.xinyunlian.jinfu.executer.impl;

import com.xinyunlian.jinfu.domain.CommandRequest;
import com.xinyunlian.jinfu.domain.CommandResponse;
import com.xinyunlian.jinfu.domain.req.PayRecvRequest;
import com.xinyunlian.jinfu.domain.resp.PayRecvResponse;
import com.xinyunlian.jinfu.executer.CommandExecuter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class MockExecuter implements CommandExecuter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockExecuter.class);

    @Override
    public boolean isSupportedRequestType(Class<?> reqClass) {
        return true;
    }

    @Override
    public CommandResponse execute(CommandRequest<?> request) {
        Class<?> clazz = request.getClass();
        if (PayRecvRequest.class.isAssignableFrom(clazz)) {
            PayRecvResponse response = new PayRecvResponse();
            if (getRandomBoolean()) {
                LOGGER.debug("pay receive success");
                response.setRetCode("0000");
                response.setTranStatus("00");
            } else {
                LOGGER.debug("pay receive failed");
            }
            return response;
        } else {
            throw new UnsupportedOperationException("can not mock response for request:" + clazz.getSimpleName());
        }
    }

    private boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
