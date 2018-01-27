package com.xinyunlian.jinfu.push.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xinyunlian.jinfu.push.dao.PushReadStateDao;

/**
 * @author Sephy
 * @since: 2017-01-17
 */
@Service
public class PushReadStateServiceImpl implements PushReadStateService {

    @Autowired
    private PushReadStateDao pushReadStateDao;

    @Transactional
    @Override
    public void updatePushStatus(Integer pushStates, List<Long> ids) {
        pushReadStateDao.updatePushStates(pushStates, ids);
    }

    @Transactional
    @Override
    public void updatePushStatesAndRetryTimes(Integer pushStates, Long id) {
        pushReadStateDao.updatePushStatesAndRetryTimes(pushStates, id);
    }
}
