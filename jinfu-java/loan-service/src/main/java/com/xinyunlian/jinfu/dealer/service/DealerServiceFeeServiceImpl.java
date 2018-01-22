package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.dealer.dao.DealerServiceFeeDao;
import com.xinyunlian.jinfu.dealer.entity.DealerServiceFeePo;
import com.xinyunlian.jinfu.dealer.enums.EDealerServiceFeeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class DealerServiceFeeServiceImpl implements DealerServiceFeeService {

    @Autowired
    private DealerServiceFeeDao dealerServiceFeeDao;

    @Override
    @Transactional
    public String withholdCallBack(Map<String, String> map) {
        String id = map.get("outTradeNo");
        DealerServiceFeePo dealerServiceFeePo = dealerServiceFeeDao.findOne(id);
        if (dealerServiceFeePo != null) {
            String result = map.get("resultCode");
            if (result.equals("SUCCESS")) {
                dealerServiceFeePo.setStatus(EDealerServiceFeeStatus.SUCCESS);
            } else {
                dealerServiceFeePo.setStatus(EDealerServiceFeeStatus.FAILED);
            }
            dealerServiceFeeDao.save(dealerServiceFeePo);
        }
        return "SUCCESS";
    }
}
