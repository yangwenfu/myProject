package com.xinyunlian.jinfu;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Created by jl062 on 2017/2/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class DateJava8Test {
    @Test
    public void addAllTest(){
        Instant time = Instant.now();
        LocalDate localDate = LocalDate.now();
    }

}
