package com.xinyunlian.jinfu.loan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bright on 2017/6/2.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/jinfu.spring-collect.xml"})
public class NBCBJobTest  {

    @Autowired
    private NBCBJob nbcbJob;

    @Test
    public void sync(){
        nbcbJob.pushRiskItem();
    }
}
