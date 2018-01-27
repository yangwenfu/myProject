package com.xinyunlian.jinfu; /**
 * Created by Admin on 2016/8/10.
 */

import com.xinyunlian.jinfu.promo.dao.PromoDao;
import com.xinyunlian.jinfu.promo.enmus.EPromoType;
import com.xinyunlian.jinfu.promo.entity.PromoPo;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @author willwang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PromoDao promoDao;

    @Test
    public void test() {
        scheduleService.generate("123", "123");
    }

    @Test
    public void test2(){
        ScheduleDto dto = scheduleService.getCurrentSchedule("L301131754354092088");
        System.exit(1);
    }

    @Test
    public void test4(){
        ScheduleDto schedule = scheduleService.getCurrentSchedule("L302031633047147039");
    }


    @Test
    public void test3() {
//        PromoPo promoPo = new PromoPo();
//        promoPo.setLoanId("11");
//        promoPo.setPromoId(111L);
//        promoPo.setPromoLen(11);
//        promoPo.setPromoType(EPromoType.MONEY);
//        promoPo.setPromoValue(BigDecimal.ZERO);
//        promoDao.save(promoPo);

        PromoPo promoPo = promoDao.findOne(4L);
        System.out.println(promoPo.getPromoValue().compareTo(BigDecimal.ZERO));
    }
}
