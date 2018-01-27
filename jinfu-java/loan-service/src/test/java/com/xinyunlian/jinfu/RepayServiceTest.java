package com.xinyunlian.jinfu; /**
 * Created by Admin on 2016/8/10.
 */

import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.enums.EFineType;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;
import com.xinyunlian.jinfu.product.enums.EViolateType;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.promo.enmus.EPromoType;
import com.xinyunlian.jinfu.repay.domain.InterestPerDiem;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author willwang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class RepayServiceTest {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanApplService loanApplService;

    @Test
    public void test2(){
        getDuedate();
    }

    @Test
    public void test3(){
        String duedate = addMonth("2016-9-30", 0);
        String duedate2 = addMonth("2016-9-30", 1);
        String duedate3 = addMonth("2016-9-30", 2);
        String duedate4 = addMonth("2016-9-30", 3);
        String duedate5 = addMonth("2016-9-30", 4);
        String duedate6 = addMonth("2016-9-30", 5);
        String duedate7 = addMonth("2016-9-30", 6);
    }

    @Test
    public void test4(){
        NumberUtil.formatMoney(new BigDecimal("2131231231231.1261231312312"));
    }

    @Test
    public void test10(){
        ScheduleDto current = scheduleService.getCurrentSchedule("L302021635905605023");
        System.out.println(current);
    }

    @Test
    public void test() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse("2017-1-3 23:29:28");
        Date date2 = sdf.parse("2017-1-2 00:29:28");

        Long days = getIntervalDays(date2, date1);
        Long expected = Long.parseLong("2");
        Assert.assertEquals(expected, days);

        days = getIntervalDays(date1, date2);
        expected = Long.parseLong("2");
        Assert.assertEquals(expected, days);

        date1 = sdf.parse("2016-4-1 23:29:28");
        date2 = sdf.parse("2016-2-1 00:29:28");

        days = getIntervalDays(date1, date2);
        expected = Long.parseLong("61");
        Assert.assertEquals(expected, days);

        days = getIntervalDays(date2, date1);
        expected = Long.parseLong("61");
        Assert.assertEquals(expected, days);

        date1 = sdf.parse("2016-12-30 23:29:28");
        date2 = sdf.parse("2017-1-4 00:29:28");

        days = getIntervalDays(date2, date1);
        expected = Long.parseLong("6");
        Assert.assertEquals(expected, days);

        days = getIntervalDays(date1, date2);
        expected = Long.parseLong("6");
        Assert.assertEquals(expected, days);


        date1 = sdf.parse("2017-1-4 23:29:28");
        date2 = sdf.parse("2017-1-4 00:29:28");

        days = getIntervalDays(date2, date1);
        expected = Long.parseLong("1");
        Assert.assertEquals(expected, days);

        date1 = sdf.parse("2017-1-4 23:29:28");
        date2 = sdf.parse("2017-2-4 00:29:28");

        days = getIntervalDays(date2, date1);
        expected = Long.parseLong("32");
        Assert.assertEquals(expected, days);

    }

    @Test
    public void test5(){

        LoanProductDetailDto product = new LoanProductDetailDto();

        product.setProductId("L01002");
        product.setProductName("云联易贷");
        product.setIntrRateType(EIntrRateType.DAY);
        product.setIntrRate(BigDecimal.valueOf(0.0005));
        product.setFineType(EFineType.CAPITAL_EVERY_DAY);
        product.setFineValue(BigDecimal.valueOf(0.00075));
        product.setProductType(ELoanProductType.CREDIT);
        product.setLoanAmtMin(BigDecimal.valueOf(10000));
        product.setLoanAmtMax(BigDecimal.valueOf(500000));
        product.setMinIntrDays(7);
        product.setTermType(ETermType.DAY);
        product.setTermLen("90");
        product.setPrepay(true);
        product.setRepayAmtMin(BigDecimal.ZERO);
        product.setRepayMode(ERepayMode.INTR_PER_DIEM);
        product.setViolateType(EViolateType.CAPITAL);
        product.setViolateValue(BigDecimal.ZERO);

    }

    @Test
    public void test6(){
        LoanDtlDto loan = loanService.get("L301141903268138019");
        LoanProductDetailDto product = loanApplService.getProduct(loan.getApplId());
        product.setIntrRate(new BigDecimal("0.0003"));
        loan.setLoanRt(new BigDecimal("0.0003"));
        loan.setTermLen(90);
        product.setIntrRateType(EIntrRateType.DAY);
        BigDecimal surplus = new BigDecimal("10");
        loan.setLoanAmt(surplus);
        loan.setTransferDate(DateHelper.getDate("2016-12-02"));

        PromoDto promoDto = new PromoDto();
        promoDto.setPromoType(EPromoType.RATE);
        promoDto.setPromoLen(3);
        promoDto.setPromoValue(new BigDecimal("0.5"));
        InterestPerDiem diem = new InterestPerDiem(loan, promoDto, "2017-03-02", product);
        BigDecimal interest = diem.getInterest();
        BigDecimal total = surplus.add(interest);
        System.out.println(total);
    }

    @Test
    public void test7(){
        String amt = "1132123asda99999";
        BigDecimal aa = new BigDecimal(amt);
        aa = NumberUtil.roundTwo(aa);
        amt = aa.toString();
    }

    public static long getIntervalDays(Date fDate, Date oDate) throws ParseException {
        return Math.abs((int)((DateHelper.getStartDate(fDate).getTime() / 86400000L)
            - (DateHelper.getStartDate(oDate).getTime() / 86400000L))) + 1L;
    }

    public static String getDuedate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //制定日期格式
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        c.add(Calendar.MONTH,1);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        if(dayOfMonth > 28){
            c.set(Calendar.DAY_OF_MONTH, 28);
        }
        return df.format(c.getTime());
    }

    public String addMonth(String dateStr, Integer cnt){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(dateStr);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, cnt);
            return df.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
