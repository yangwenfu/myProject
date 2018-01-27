package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;

/**
 * Created by godslhand on 2017/6/21.
 */
public interface ExternalContextService {

     <T extends ExternalService> T getExternalService(EFinanceSourceType code);
}
