package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.dealer.dao.DealerSendDao;
import com.xinyunlian.jinfu.dealer.dto.DealerSendDto;
import com.xinyunlian.jinfu.dealer.entity.DealerSendPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by menglei on 2016年08月26日.
 */
@Service
public class DealerSendServiceImpl implements DealerSendService {

    @Autowired
    private DealerSendDao dealerSendDao;

    @Transactional
    @Override
    public void createNote(DealerSendDto dealerSendDto) {
        if (dealerSendDto != null) {
            DealerSendPo dealerSendPo = ConverterService.convert(dealerSendDto, DealerSendPo.class);
            dealerSendDao.save(dealerSendPo);
        }
    }

}
