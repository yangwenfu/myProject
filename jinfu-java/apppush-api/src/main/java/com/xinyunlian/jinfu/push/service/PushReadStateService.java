package com.xinyunlian.jinfu.push.service;

import java.util.List;

/**
 * @author Sephy
 * @since: 2017-01-16
 */
public interface PushReadStateService {

    void updatePushStatus(Integer pushStates, List<Long> ids);

    void updatePushStatesAndRetryTimes(Integer pushStates, Long id);
}
