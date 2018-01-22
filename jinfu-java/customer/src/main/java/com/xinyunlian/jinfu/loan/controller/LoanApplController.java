package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.audit.dto.LoanAuditDto;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.audit.service.LoanAuditService;
import com.xinyunlian.jinfu.common.dto.BasePagingDto;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.dto.appl.LoanApplStartResp;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplListDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplRespDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanCustomerApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyDetailCusDto;
import com.xinyunlian.jinfu.loan.enums.EApplChannel;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.PrivateApplService;
import com.xinyunlian.jinfu.mq.sender.TopicSender;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.service.LoanProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author willwang
 */
@Controller
@RequestMapping(value = "loan/apply")
public class LoanApplController {

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private PrivateApplService privateApplService;

    @Autowired
    private LoanProductService loanProductService;

    @Autowired
    private LoanAuditService loanAuditService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApplController.class);

    /**
     * 小贷2.0申请贷款接口
     *
     * @param applDto
     * @return
     */
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public Object start(@RequestBody LoanCustomerApplDto applDto, @RequestHeader(value = "channel", required = false) EApplChannel channel) {
        String userId = SecurityContext.getCurrentUserId();
        String applId = privateApplService.start(userId, channel, applDto);
        LoanApplStartResp item = new LoanApplStartResp();
        item.setApplId(applId);
        item.setPerfectInfo(true);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), item);
    }

    /**
     * BTest申请页面
     *
     * @return
     */
    @RequestMapping(value = "btest", method = RequestMethod.GET)
    @ResponseBody
    public Object bTest() {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateApplService.bTest());
    }

    /**
     * BTest申请开始
     *
     * @param applDto
     * @return
     */
    @RequestMapping(value = "btest/start", method = RequestMethod.POST)
    @ResponseBody
    public Object bTestStart(@RequestBody LoanCustomerApplDto applDto) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateApplService.bTestStart(applDto));
    }

    /**
     * 重新发起贷款申请
     *
     * @return
     */
    @RequestMapping(value = "/restart", method = RequestMethod.GET)
    @ResponseBody
    public Object restart(@RequestParam String applId) {
        privateApplService.restart(applId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @RequestMapping(value = "check", method = RequestMethod.POST)
    @ResponseBody
    public Object check(@RequestBody LoanCustomerApplDto applDto) {
        String userId = SecurityContext.getCurrentUserId();
        //是否可以申请贷款
        LoanProductDetailDto product = loanProductService.getProdDtl(applDto.getProductId());
        applDto.setProduct(product);
        loanApplService.checkStart(userId, applDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 小贷2.0 贷款记录
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<LoanApplListDto> list(@RequestBody BasePagingDto basePagingDto) {
        String userId = SecurityContext.getCurrentUserId();
        List<LoanApplRespDto> rs = new ArrayList<>();

        LoanApplListDto list = loanApplService.list(userId, basePagingDto);

        for (LoanApplRespDto loanApplRespDto : list.getList()) {
            loanApplRespDto.setAmt(NumberUtil.roundTwo(loanApplRespDto.getAmt()));
            rs.add(loanApplRespDto);
        }

        list.setList(rs);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    /**
     * 贷款详情
     *
     * @param applId
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<LoanApplyDetailCusDto> detail(@RequestParam String applId) {
        String userId = SecurityContext.getCurrentUserId();

        LoanApplyDetailCusDto detail = loanApplService.detail(userId, applId);

        detail.setAmt(NumberUtil.roundTwo(detail.getAmt()));
        detail.setSurplus(NumberUtil.roundTwo(detail.getSurplus()));

        detail = privateApplService.completePromo(detail);
        detail = privateApplService.completeBank(detail);

        detail.setAuditServiceFeeMonthRt(detail.getAuditServiceFeeMonthRt().add(detail.getIntrRt()));
        detail.setServiceFeeMonthRt(detail.getServiceFeeMonthRt().add(detail.getIntrRt()));
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), detail);
    }

    /**
     * 测试环境生成贷款申请脚本
     *
     * @return
     */
    @RequestMapping(value = "test/createApply", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto test(@RequestParam String code, @RequestParam Integer cnt) {

        if (cnt == null) {
            cnt = 2000;
        }

        if (AppConfigUtil.isUnProdEnv() && "10000".equals(code)) {
            String userId = null;
            String mgtUserId = "UM0000000001";
            if (AppConfigUtil.isDevEnv()) {
                userId = "UC0000000404";
            } else if (AppConfigUtil.isQAEnv()) {
                userId = "UC0000001556";
            }

            LoanCustomerApplDto applDto = new LoanCustomerApplDto();
            applDto.setProductId("L01001");
            applDto.setChannel(EApplChannel.UNION);
            applDto.setTermLen("6");
            applDto.setApplAmt(new BigDecimal(10000));
            LoanProductDetailDto product = loanProductService.getProdDtl(applDto.getProductId());
            applDto.setProduct(product);

            for (int i = 0; i < cnt; i++) {
                LoanApplDto loanApplDto = loanApplService.apply(userId, applDto);
                LOGGER.info("{},appl_id:{} apply success ", i, loanApplDto.getApplId());

                //初审领取
                loanAuditService.trialAcquire(loanApplDto.getApplId(), mgtUserId);
                LOGGER.info("{},appl_id:{} trial acquire success ", i, loanApplDto.getApplId());


                //初审建议通过
                LoanAuditDto auditDto = new LoanAuditDto();
                auditDto.setAuditType(EAuditType.TRIAL);
                auditDto.setApplId(loanApplDto.getApplId());
                auditDto.setAuditStatus(EAuditStatus.ADVISE_PASS);
                auditDto.setLoanAmt(new BigDecimal(10000));
                auditDto.setPeriod(6);
                auditDto.setReason("测试建议通过222222");
                auditDto.setRemark("测试建议通过");
                loanAuditService.trial(mgtUserId, auditDto);
                LOGGER.info("{},appl_id:{} trial success ", i, loanApplDto.getApplId());

                //终审领取
                Set<String> applIds = new HashSet<>();
                applIds.add(loanApplDto.getApplId());
                loanAuditService.reviewAcquire(mgtUserId, applIds);
                LOGGER.info("{},appl_id:{} review acquire success ", i, loanApplDto.getApplId());


                //终审通过
                auditDto = new LoanAuditDto();
                auditDto.setAuditType(EAuditType.REVIEW);
                auditDto.setApplId(loanApplDto.getApplId());
                auditDto.setAuditStatus(EAuditStatus.SUCCEED);
                auditDto.setLoanAmt(new BigDecimal(10000));
                auditDto.setPeriod(6);
                auditDto.setReason("终审通过");
                auditDto.setRemark("终审通过");
                loanAuditService.review(mgtUserId, auditDto);
                LOGGER.info("{},appl_id:{} review success ", i, loanApplDto.getApplId());
            }
        }

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

}
