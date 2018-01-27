package com.xinyunlian.jinfu.executer;

import com.xinyunlian.jinfu.domain.CommandRequest;
import com.xinyunlian.jinfu.domain.CommandResponse;

public interface CommandExecuter {

    boolean isSupportedRequestType(Class<?> reqClass);

    CommandResponse execute(CommandRequest<?> request);

}
