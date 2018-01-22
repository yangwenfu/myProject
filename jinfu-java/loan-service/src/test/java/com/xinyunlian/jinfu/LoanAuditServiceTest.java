package com.xinyunlian.jinfu; /**
 * Created by Admin on 2016/8/10.
 */

import com.xinyunlian.jinfu.acct.dao.AcctDao;
import com.xinyunlian.jinfu.acct.entity.AcctPo;
import com.xinyunlian.jinfu.audit.dto.LoanAuditDto;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.audit.service.LoanAuditService;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.dao.LoanApplDao;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.enums.EFineType;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;
import com.xinyunlian.jinfu.product.enums.EViolateType;
import com.xinyunlian.jinfu.repay.service.RepayService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author willwang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class LoanAuditServiceTest {

    @Autowired
    private LoanAuditService loanAuditService;

    @Autowired
    private LoanApplDao loanApplDao;

    @Autowired
    private AcctDao acctDao;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private LoanService loanService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanAuditServiceTest.class);

    private static final String APPLY_ID = "302010693946785010";

    private static final String USER_ID = "UM0000000004";

    @Before
    public void beforeTest(){
        LOGGER.info("初始化申请单状态");
        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        apply.setApplStatus(EApplStatus.TRIAL_UNCLAIMED);
        apply.setTrialUserId("");
        apply.setReviewUserId("");
        apply.setHangUp(false);
        loanApplDao.save(apply);

        LOGGER.info("初始化账户保留金额");
        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        acct.setCreditLineRsrv(new BigDecimal("5000"));
        acctDao.save(acct);

        LOGGER.info("初始化审核记录");
        loanAuditService.deleteByApplId(APPLY_ID);
    }

    @Test
    public void testTrialAcquire(){
        this.trialAcquire();
        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.TRIAL_CLAIMED);
        Assert.assertEquals(apply.getTrialUserId(), USER_ID);
        LOGGER.info("订单已进行初审领取");
    }

    @Test
    public void testTrialAdvisePass(){
        this.trialAcquire();
        this.trialAdvisePass();
        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.REVIEW_UNCLAIMED);
        Assert.assertEquals(apply.getTrialUserId(), USER_ID);

        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("5000")) == 0);

        LOGGER.info("建议通过");
    }

    @Test
    public void testTrialAdviseReject(){
        this.trialAcquire();
        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.ADVISE_REJECT);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("测试建议拒绝");
        auditDto.setRemark("测试建议拒绝");
        loanAuditService.trial(USER_ID, auditDto);

        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.REVIEW_UNCLAIMED);
        Assert.assertEquals(apply.getTrialUserId(), USER_ID);

        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("5000")) == 0);

        LOGGER.info("建议拒绝");
    }

    @Test
    public void testTrialSucceed(){
        this.trialAcquire();
        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.SUCCEED);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("测试初审同意");
        auditDto.setRemark("测试初审同意");
        loanAuditService.trial(USER_ID, auditDto);

        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.REVIEW_UNCLAIMED);
        Assert.assertEquals(apply.getTrialUserId(), USER_ID);

        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("5000")) == 0);

        LOGGER.info("初审同意，不会直接变成同意，会流向用户终审");
    }

    @Test
    public void testTrialReject(){
        this.trialAcquire();
        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.REJECT);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("测试初审拒绝");
        auditDto.setRemark("测试初审拒绝");
        loanAuditService.trial(USER_ID, auditDto);

        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.REJECT);
        Assert.assertEquals(apply.getTrialUserId(), USER_ID);

        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("0")) == 0);

        LOGGER.info("初审拒绝");
    }

    @Test
    public void testTrialCancel(){
        this.trialAcquire();
        this.trialCancel();

        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.CANCEL);
        Assert.assertEquals(apply.getTrialUserId(), USER_ID);

        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("0")) == 0);

        LOGGER.info("初审拒绝");
    }

    @Test
    public void testTrialFallback(){
        this.trialAcquire();
        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.FALLBACK);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("测试初审回退");
        auditDto.setRemark("测试初审回退");
        loanAuditService.trial(USER_ID, auditDto);

        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.FALLBACK);
        Assert.assertEquals(apply.getTrialUserId(), USER_ID);

        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("0")) == 0);

        LOGGER.info("初审回退");
    }

    @Test
    public void testReviewAcquire(){
        this.beforeReview();
        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.REVIEW_CLAIMED);
        Assert.assertEquals(apply.getReviewUserId(), USER_ID);
        LOGGER.info("订单已进行终审领取");
    }

    /**
     * 终审退回
     */
    @Test
    public void testReviewFallback(){
        this.beforeReview();
        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.FALLBACK);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("测试终审回退");
        auditDto.setRemark("测试终审回退");
        loanAuditService.review(USER_ID, auditDto);

        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.TRIAL_CLAIMED);
        Assert.assertEquals(apply.getReviewUserId(), USER_ID);

        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("5000")) == 0);

        LOGGER.info("终审回退");
    }

    @Test
    public void testReviewFallback2(){
        this.beforeReview();
        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.FALLBACK);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("测试终审回退复杂情况");
        auditDto.setRemark("测试终审回退复杂情况");
        loanAuditService.review(USER_ID, auditDto);

        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.TRIAL_CLAIMED);
        Assert.assertEquals(apply.getReviewUserId(), USER_ID);

        //到达初审后，再次初审
        this.trialAdvisePass();

        apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.REVIEW_CLAIMED);

        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("5000")) == 0);

        LOGGER.info("终审回退后进行初审后");
    }

    @Test
    public void testReviewPass(){
        LOGGER.info("终审通过");
        this.beforeReview();
        this.reviewPass();

        //申请处于通过状态
        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.SUCCEED);

        //保留额度释放为0
        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("0")) == 0);

        //存在一条终审记录
        LoanAuditDto reviewAudit = this.getAudit(EAuditType.REVIEW);
        Assert.assertNotNull(reviewAudit);
        Assert.assertTrue(this.validAudit(reviewAudit));
    }

    private void reviewPass(){
        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.SUCCEED);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("终审通过");
        auditDto.setRemark("终审通过");
        loanAuditService.review(USER_ID, auditDto);
    }

    @Test
    public void reviewReject(){
        this.beforeReview();

        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.REJECT);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("终审拒绝");
        auditDto.setRemark("终审拒绝");
        loanAuditService.review(USER_ID, auditDto);

        //申请处于通过状态
        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.REJECT);

        //保留额度释放为0
        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("0")) == 0);

        //存在一条终审记录,且状态为非暂存
        LoanAuditDto reviewAudit = this.getAudit(EAuditType.REVIEW);
        Assert.assertNotNull(reviewAudit);
        Assert.assertNotNull(reviewAudit.getTemp());
        Assert.assertFalse(reviewAudit.getTemp());
    }

    @Test
    public void reviewRevoke(){
        LOGGER.info("终审撤销");
        this.beforeReview();
        this.reviewPass();
        loanAuditService.reviewRevoke(APPLY_ID);

        //申请处于通过状态
        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(EApplStatus.REVIEW_CLAIMED, apply.getApplStatus());

        //保留额度重置为5000
        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("5000")) == 0);

        //存在一条终审记录，且状态为暂存
        LoanAuditDto reviewAudit = this.getAudit(EAuditType.REVIEW);
        Assert.assertNotNull(reviewAudit);
        Assert.assertEquals(reviewAudit != null && reviewAudit.getTemp() != null && reviewAudit.getTemp(), true);

    }

    @Test
    public void trialTemp(){
        LOGGER.info("初审挂起审核");

        this.trialAcquire();
        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.ADVISE_REJECT);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("初审挂起审核");
        auditDto.setRemark("初审挂起审核");
        auditDto.setTemp(true);
        loanAuditService.trial(USER_ID, auditDto);

        //申请状态不变
        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.TRIAL_CLAIMED);

        //保留额度不做变化
        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("5000")) == 0);

        //存在一条终审记录
        LoanAuditDto trialAudit = this.getAudit(EAuditType.TRIAL);
        Assert.assertNotNull(trialAudit);
        Assert.assertNotNull(trialAudit.getTemp());
        Assert.assertTrue(trialAudit.getTemp());
    }

    @Test
    public void reviewTemp(){
        LOGGER.info("终审挂起审核");

        this.beforeReview();

        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.SUCCEED);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("终审挂起审核");
        auditDto.setRemark("终审挂起审核");
        auditDto.setTemp(true);
        loanAuditService.review(USER_ID, auditDto);

        //申请处于通过状态
        LoanApplPo apply = loanApplDao.findOne(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.REVIEW_CLAIMED);

        //保留额度不做变化
        AcctPo acct = acctDao.findOne(apply.getAcctNo());
        Assert.assertTrue(acct.getCreditLineRsrv().compareTo(new BigDecimal("5000")) == 0);

        //存在一条终审记录
        LoanAuditDto reviewAudit = this.getAudit(EAuditType.REVIEW);
        Assert.assertNotNull(reviewAudit);
        Assert.assertTrue(reviewAudit.getTemp() != null || reviewAudit.getTemp()); //NOSONAR

    }

    @Test
    public void trialHangup(){
        LOGGER.info("初审挂起");

        this.trialAcquire();

        LoanApplDto apply = loanApplService.get(APPLY_ID);
        apply.setHangUp(true);
        loanApplService.save(apply);

        //申请处于通过状态
        apply = loanApplService.get(APPLY_ID);
        Assert.assertEquals(apply.getApplStatus(), EApplStatus.TRIAL_CLAIMED);
        Assert.assertEquals(apply.getHangUp(), true);

        //如果挂起状态是不能正常操作的
        try{
            this.trialCancel();
        }catch(BizServiceException e){
            LOGGER.info("", e);
            Assert.assertTrue(e.getMessage().contains("is hang up"));
        }

    }

    private void trialAdvisePass(){
        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.ADVISE_PASS);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("测试建议通过222222");
        auditDto.setRemark("测试建议通过");
        loanAuditService.trial(USER_ID, auditDto);
    }

    private void trialCancel(){
        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(APPLY_ID);
        auditDto.setAuditStatus(EAuditStatus.CANCEL);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("测试初审取消");
        auditDto.setRemark("测试初审取消");
        loanAuditService.trial(USER_ID, auditDto);
    }

    private void trialAcquire(){
        loanAuditService.trialAcquire(USER_ID);
    }

    private void reviewAcquire(){
        Set<String> applIds = new HashSet<>();
        applIds.add(APPLY_ID);
        loanAuditService.reviewAcquire(USER_ID, applIds);
    }

    private void beforeReview(){
        this.trialAcquire();
        this.trialAdvisePass();
        this.reviewAcquire();
    }

    private LoanAuditDto getAudit(EAuditType auditType){
        List<LoanAuditDto> audits = loanAuditService.list(APPLY_ID);

        for (LoanAuditDto audit : audits) {
            if(audit.getAuditType() == auditType){
                return audit;
            }
        }

        return null;
    }

    @Test
    public void initApplStatus(){
        String userId = "UC2000000342";
        String overdueApplId = "302020662591434093";
        Long cardId = 22L;
        LOGGER.info("初始化申请单状态");
        LoanApplPo apply = loanApplDao.findOne(overdueApplId);
        apply.setApplStatus(EApplStatus.TRIAL_UNCLAIMED);
        apply.setTrialUserId("");
        apply.setReviewUserId("");
        apply.setHangUp(false);
        loanApplDao.save(apply);

        LOGGER.info("初始化审核记录");
        loanAuditService.deleteByApplId(overdueApplId);

        this.trialAcquire();

        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setApplId(overdueApplId);
        auditDto.setAuditStatus(EAuditStatus.ADVISE_REJECT);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("测试建议通过222222");
        auditDto.setRemark("测试建议通过");
        loanAuditService.trial(USER_ID, auditDto);

        Set<String> applIds = new HashSet<>();
        applIds.add(overdueApplId);
        loanAuditService.reviewAcquire(USER_ID, applIds);

        auditDto = new LoanAuditDto();
        auditDto.setApplId(overdueApplId);
        auditDto.setAuditStatus(EAuditStatus.SUCCEED);
        auditDto.setLoanAmt(new BigDecimal("10000"));
        auditDto.setPeriod(6);
        auditDto.setReason("终审通过");
        auditDto.setRemark("终审通过");
        loanAuditService.review(USER_ID, auditDto);

    }
    
    private boolean validAudit(LoanAuditDto audit){
        return audit != null && (audit.getTemp() == null || !audit.getTemp());
    }

}
