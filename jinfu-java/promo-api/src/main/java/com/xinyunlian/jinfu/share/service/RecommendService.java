package com.xinyunlian.jinfu.share.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.share.dto.RecommendDto;

/**
 * 推荐Service
 * @author jll
 * @version 
 */
public interface RecommendService {
    void add(RecommendDto recommendDto) throws BizServiceException;

    public void giveRefereeCoupon(String value);
}
