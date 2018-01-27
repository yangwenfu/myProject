package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by godslhand on 2017/6/17.
 */
//@Component
public class ExternalContextServiceImpl implements ApplicationContextAware,ExternalContextService {

    private  Map<EFinanceSourceType, ExternalService> extMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ExternalService> map = applicationContext.getBeansOfType(ExternalService.class);
        extMap = new HashMap();
        map.forEach((key, value) -> extMap.put(value.getCode(), value));
    }

    public  <T extends ExternalService> T getExternalService(EFinanceSourceType code) {
        if (extMap.get(code) == null)
            throw new RuntimeException(code.getText() + "external adapter not support yet !");
        return (T) extMap.get(code);
    }

}
