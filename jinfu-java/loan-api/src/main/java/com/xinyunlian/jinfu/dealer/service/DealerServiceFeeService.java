package com.xinyunlian.jinfu.dealer.service;

import java.util.HashMap;
import java.util.Map;

public interface DealerServiceFeeService {


    /**
     * 分销商服务费处理
     *
     * @param map
     */
    String withholdCallBack(Map<String, String> map);
}
