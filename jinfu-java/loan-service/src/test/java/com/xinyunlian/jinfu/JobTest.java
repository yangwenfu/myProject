package com.xinyunlian.jinfu; /**
 * Created by Admin on 2016/8/10.
 */

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.sms.SmsUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.repay.service.RepayService;
import com.xinyunlian.jinfu.schedule.service.InnerScheduleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author willwang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class JobTest {

    @Autowired
    private RepayService repayService;

    @Autowired
    private LoanApplService loanApplService;

    @Test
    public void test() {
        RepayMethod repayMethod =
                new MonthAverageCapitalPlusInterest(new BigDecimal("10000"), new BigDecimal("0.005"), EIntrRateType.MONTH, ETermType.YEAR, 1);

        System.out.print("termlen:");
        System.out.println(repayMethod.getTermLen());
        for (int period = 1; period <= repayMethod.getTermLen(); period++) {
            repayMethod.setPeriod(period);
            System.out.print("period:");
            System.out.print(repayMethod.getPeriod());
            System.out.print(",capital：");
            System.out.print(repayMethod.getCapital());
            System.out.print(",interest:");
            System.out.println(repayMethod.getInterest());
        }
    }

    @Test
    public void test2() {
        int days = DateHelper.betweenDays(DateHelper.getDate("2016-12-03"), DateHelper.getDate("2016-12-01"));
        System.out.println(days);
    }

    @Test
    public void testSms() {
        //发送防欺诈短信

        Map<String, String> params = new HashMap<>();
        params.put("code", "1234");
        SmsUtil.send("139", params, "18767104422");
    }

    @Test
    public void test4() {
        int days = DateHelper.betweenDays(new Date(), DateHelper.getDate("2016-12-01"));

        System.out.println(days);

        int days2 = DateHelper.betweenDays(new Date(), DateHelper.getDate("2017-01-05"));
        System.out.println(days2);

        int days5 = DateHelper.betweenDays(DateHelper.getDate("2016-12-31"), DateHelper.getDate("2017-01-01"));

        System.out.println(days5);

        int days6 = DateHelper.betweenDays(DateHelper.getDate("2016-12-31"), DateHelper.getDate("2016-12-31"));
        System.out.println(days6);

        int days3 = DateHelper.betweenDaysNew(new Date(), DateHelper.getDate("2016-12-01"));

        System.out.println(days3);

        int days4 = DateHelper.betweenDaysNew(new Date(), DateHelper.getDate("2017-01-05"));

        System.out.println(days4);
    }

    @Test
    public void testCancelApply() {
        loanApplService.closeLongtimeNoused();
    }

    @Test
    public void testExtra() {
        List<TTT> list = new ArrayList<>();

        TTT a = new TTT("aaaa", "bbbbb");
        TTT b = new TTT("cccc", "dddd");

        list.add(a);
        list.add(b);

        List<?> rs = this.extra(list, "a");
        List<?> rs1 = this.extra(list, "b");
        List<?> rs2 = this.extra(list, "c");
    }

    private List<?> extra(List<?> list, String fieldName) {
        List<Object> rs = new ArrayList<>();
        if (fieldName.isEmpty() || list.isEmpty()) {
            return rs;
        }
        Field[] fields = list.get(0).getClass().getDeclaredFields();

        list.forEach(item -> {
            for (int i = 0; i < fields.length; i++) {
                if (fieldName.equals(fields[i].getName())) {
                    try {
                        if (!fields[i].isAccessible()) {
                            fields[i].setAccessible(true);
                        }
                        rs.add(fields[i].get(item));
                    } catch (IllegalAccessException e) {
                    }
                }
            }
        });

        return rs;
    }

}

class TTT {
    private String a;

    private String b;

    public TTT(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}

/**
 * 还款方式入口
 */
abstract class RepayMethod {

    /**
     * 期数，以不同的还款类型可变的定义
     */
    private Integer period;

    /**
     * 贷款金额
     */
    private BigDecimal loanAmt;

    /**
     * 利率
     */
    private BigDecimal rt;

    /**
     * 利率类型
     */
    private EIntrRateType intrRateType;

    /**
     * 贷款期限类型
     */
    private ETermType termType;

    /**
     * 贷款期限
     */
    private Integer termLen;

    public RepayMethod(BigDecimal loanAmt, BigDecimal rt, EIntrRateType intrRateType, ETermType termType, Integer termLen) {
        this.loanAmt = loanAmt;
        this.rt = rt;
        this.intrRateType = intrRateType;
        this.termType = termType;
        this.termLen = termLen;
    }

    public void setRt(BigDecimal rt) {
        this.rt = rt;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public BigDecimal getRt() {
        return rt;
    }

    public EIntrRateType getIntrRateType() {
        return intrRateType;
    }

    public ETermType getTermType() {
        return termType;
    }

    public Integer getTermLen() {
        return termLen;
    }

    public void setTermLen(Integer termLen) {
        this.termLen = termLen;
    }

    abstract BigDecimal getCapital();

    abstract BigDecimal getInterest();

    /**
     * 统一计算方法
     */
    abstract void cal();

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
        this.cal();
    }

    protected void setPurePeriod(Integer period) {
        this.period = period;
    }
}

/**
 * 按月等额本息的还款方式
 */
class MonthAverageCapitalPlusInterest extends RepayMethod {

    private BigDecimal _averageCapitalPlusInterest;

    private BigDecimal _capital;

    private BigDecimal _interest;

    public MonthAverageCapitalPlusInterest(BigDecimal loanAmt, BigDecimal rt, EIntrRateType intrRateType, ETermType termType, Integer termLen) {
        super(loanAmt, rt, intrRateType, termType, termLen);
        this.setRt(intrRateType.getMonth(rt));
        this.setTermLen(termType.getMonth(this.getTermLen()));
    }

    void calcAverageCapitalPlusInterest() {
        this._averageCapitalPlusInterest = NumberUtil.roundTwo((getLoanAmt().multiply(getRt())
                .multiply((BigDecimal.ONE.add(getRt())).pow(getTermLen()))).divide(
                ((BigDecimal.ONE.add(getRt()).pow(getTermLen()).subtract(BigDecimal.ONE))), 2));
    }

    void calcCapital() {
        this._capital = NumberUtil.roundTwo(this._averageCapitalPlusInterest.subtract(this._interest));
    }

    void calcInterest() {
        this._interest = NumberUtil.roundTwo((getLoanAmt().multiply(getRt()).subtract(this._averageCapitalPlusInterest))
                .multiply((BigDecimal.ONE.add(getRt())).pow(this.getPeriod() - 1)
                ).add(this._averageCapitalPlusInterest));
    }

    BigDecimal getCapital() {
        return _capital.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    BigDecimal getInterest() {
        return _interest.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setCapital(BigDecimal capital) {
        this._capital = capital;
    }

    /**
     * @param period 期数
     */
    public void setPeriod(Integer period) {
        if (period > this.getTermLen()) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID);
        }
        super.setPeriod(period);
    }

    @Override
    void cal() {
        calcAverageCapitalPlusInterest();
        calcInterest();
        //本金计算，存在尾差，最后一笔要按往期推算
        if (this.getPeriod() < this.getTermLen()) {
            calcCapital();
        } else {
            BigDecimal surplus = this.getLoanAmt();
            BigDecimal lastInterest = this.getInterest();
            Integer previousPeriod = this.getPeriod();
            //计算尾差
            for (int i = 1; i < this.getTermLen(); i++) {
                this.setPeriod(i);
                calcCapital();
                surplus = surplus.subtract(this.getCapital().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            this.setCapital(surplus);
            this.setPurePeriod(previousPeriod);
            //尾差的利率保存
            this._interest = lastInterest;
        }
    }
}