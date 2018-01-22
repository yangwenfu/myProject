package com.xinyunlian.jinfu.store.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.store.dao.StoreWhiteListDao;
import com.xinyunlian.jinfu.store.dao.StoreWhiteSignDao;
import com.xinyunlian.jinfu.store.dto.StoreWhiteSignDto;
import com.xinyunlian.jinfu.store.entity.StoreWhiteListPo;
import com.xinyunlian.jinfu.store.entity.StoreWhiteSignPo;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by menglei on 2017年07月05日.
 */
@Service
public class StoreWhiteSignServiceImpl implements StoreWhiteSignService {

    @Autowired
    private StoreWhiteSignDao storeWhiteSignDao;
    @Autowired
    private StoreWhiteListDao storeWhiteListDao;

    @Transactional
    @Override
    public void createSignIn(StoreWhiteSignDto storeWhiteSignDto) throws BizServiceException {
        if (storeWhiteSignDto != null) {
            StoreWhiteListPo storeWhiteListPo = storeWhiteListDao.findOne(storeWhiteSignDto.getStoreId());
            if (EStoreWhiteListStatus.NOSIGNIN.equals(storeWhiteListPo.getStatus())) {
                storeWhiteListPo.setStatus(EStoreWhiteListStatus.SIGNEDIN);
                storeWhiteListDao.save(storeWhiteListPo);
                StoreWhiteSignPo storeWhiteSignPo = ConverterService.convert(storeWhiteSignDto, StoreWhiteSignPo.class);
                storeWhiteSignDao.save(storeWhiteSignPo);
            }
        }
    }

}
