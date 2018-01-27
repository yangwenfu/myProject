package com.xinyunlian.jinfu.loan.audit.service;

import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.audit.dto.LoanAuditDto;
import com.xinyunlian.jinfu.audit.dto.req.LoanAuditNoteSearchDto;
import com.xinyunlian.jinfu.audit.dto.resp.LoanAuditNoteRespDto;
import com.xinyunlian.jinfu.audit.enums.EAuditNoteType;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.audit.service.LoanAuditService;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.sms.SmsUtil;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.dealer.dto.DealerUserOrderDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserOrderStatus;
import com.xinyunlian.jinfu.dealer.service.DealerUserOrderService;
import com.xinyunlian.jinfu.external.dto.ExternalLoanApplyDto;
import com.xinyunlian.jinfu.external.dto.LoanApplOutDto;
import com.xinyunlian.jinfu.external.dto.req.RegisterReq;
import com.xinyunlian.jinfu.external.service.ExternalService;
import com.xinyunlian.jinfu.external.service.LoanApplOutService;
import com.xinyunlian.jinfu.loan.appl.service.PrivateLoanApplService;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.log.enums.ELoanAuditLogType;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.risk.dto.req.UserCreditInfoReqDto;
import com.xinyunlian.jinfu.risk.dto.resp.UserCreditInfoDto;
import com.xinyunlian.jinfu.risk.service.UserCreditService;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.dto.FinanceSourceLoanDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.spider.dto.resp.RiskUserInfoDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.UserLinkmanDto;
import com.xinyunlian.jinfu.user.dto.ext.UserExtFinDto;
import com.xinyunlian.jinfu.user.service.MgtUserService;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserLinkmanService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Willwang on 2017/2/15.
 */
@Service
public class PrivateLoanAuditService {

    @Autowired
    private LoanAuditService loanAuditService;

    @Autowired
    private MgtUserService mgtUserService;

    @Autowired
    private DealerUserOrderService dealerUserOrderService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private PrivateLoanApplService privateLoanApplService;

    @Autowired
    private RiskUserInfoService riskUserInfoService;

    @Autowired
    private SysAreaInfService sysAreaInfService;

    @Autowired
    private UserLinkmanService userLinkmanService;

    @Autowired
    private UserCreditService userCreditService;

    @Autowired
    private UserService userService;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Autowired
    private ExternalService externalService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private LoanApplOutService loanApplOutService;

    @Autowired
    private StoreService storeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateLoanAuditService.class);

    /**
     * 初审
     *
     * @param mgtUserId
     * @param audit
     */
    public void trial(String mgtUserId, LoanAuditDto audit) throws BizServiceException{
        audit.setAuditType(EAuditType.TRIAL);
        LoanApplDto apply = loanAuditService.trial(mgtUserId, audit);
        this.afterAudit(mgtUserId, apply, audit);
        try {
            this.saveUserFin(apply, audit);
        } catch (Exception e) {
            LOGGER.warn("update user fin error", e);
        }
    }

    /**
     * 复审
     *
     * @param mgtUserId
     * @param audit
     */
    public void review(String mgtUserId, LoanAuditDto audit) throws BizServiceException {
        audit.setAuditType(EAuditType.REVIEW);
        LoanApplDto apply = loanAuditService.review(mgtUserId, audit);

        this.afterAudit(mgtUserId, apply, audit);
    }

    /**
     * 初审/终审重新分配
     * @param applId
     * @param assignUserId
     * @param auditType
     */
    public void assign(String applId, String assignUserId, EAuditType auditType) throws BizServiceException {
        //操作人
        String mgtUserId = SecurityContext.getCurrentOperatorId();
        LoanApplDto apply = loanApplService.get(applId);

        Set<String> userIds = new HashSet<>();
        userIds.add(mgtUserId);
        userIds.add(assignUserId);

        String operatorUser = "", beforeUser = "", assignUser = "";
        if (apply != null) {
            if (StringUtils.isNotEmpty(apply.getTrialUserId())) {
                userIds.add(apply.getTrialUserId());
            }
            if (StringUtils.isNotEmpty(apply.getReviewUserId())) {
                userIds.add(apply.getReviewUserId());
            }
        }

        Map<String, MgtUserDto> map = new HashMap<>();

        List<MgtUserDto> list = mgtUserService.findByMgtUserIds(userIds);
        if (CollectionUtils.isNotEmpty(list)) {
            for (MgtUserDto mgtUserDto : list) {
                map.put(mgtUserDto.getUserId(), mgtUserDto);
            }
        }

        if (map.get(mgtUserId) != null) {
            operatorUser = map.get(mgtUserId).getName();
        }

        if (map.get(assignUserId) != null) {
            assignUser = map.get(assignUserId).getName();
        }

        switch (auditType) {
            case TRIAL:
                loanAuditService.trialAssign(applId, assignUserId);
                if (apply != null && StringUtils.isNotEmpty(apply.getTrialUserId()) && map.get(apply.getTrialUserId()) != null) {
                    beforeUser = map.get(apply.getTrialUserId()).getName();
                }
                break;
            case REVIEW:
                loanAuditService.reviewAssign(applId, assignUserId);
                if (apply != null && StringUtils.isNotEmpty(apply.getReviewUserId()) && map.get(apply.getReviewUserId()) != null) {
                    beforeUser = map.get(apply.getReviewUserId()).getName();
                }
                break;
            default:
                break;
        }

        privateLoanApplService.saveAuditLog(mgtUserId, applId, ELoanAuditLogType.AUDIT_ASSIGN, operatorUser, applId, auditType.getText(), beforeUser, assignUser);
    }

    /**
     * 获取烟草爬虫数据
     *
     * @param userId
     * @return
     */
    public RiskUserInfoDto getTobaccoSpider(String userId) {
        RiskUserInfoDto riskUserInfoDto = riskUserInfoService.getUserInfo(userId);
        if (Objects.nonNull(riskUserInfoDto)) {
            SysAreaInfDto province = sysAreaInfService.getSysAreaInfById(riskUserInfoDto.getProvinceId());
            if (province != null) {
                riskUserInfoDto.setProvince(province.getName());
            }
            SysAreaInfDto city = sysAreaInfService.getSysAreaInfById(riskUserInfoDto.getCityId());
            if (city != null) {
                riskUserInfoDto.setCity(city.getName());
            }
            SysAreaInfDto area = sysAreaInfService.getSysAreaInfById(riskUserInfoDto.getAreaId());
            if (area != null) {
                riskUserInfoDto.setArea(area.getName());
            }
        }
        return riskUserInfoDto;
    }

    /**
     * 获取征信信息
     *
     * @param userId
     * @param forceRetrieve
     * @return
     */
    public UserCreditInfoDto getCreditSpider(String userId, Boolean forceRetrieve) {
        UserCreditInfoDto userCreditInfoDto = null;
        try {
            userCreditInfoDto = userCreditService.getUserCreditInfo(userId);
            if (forceRetrieve || null == userCreditInfoDto) {
                UserInfoDto userInfo = userService.findUserByUserId(userId);
                UserCreditInfoReqDto reqDto = new UserCreditInfoReqDto();
                reqDto.setUserId(userId);
                reqDto.setIdCard(userInfo.getIdCardNo());
                reqDto.setPhone(userInfo.getMobile());
                reqDto.setQueryDate(DateHelper.formatDate(new Date()));
                reqDto.setName(userInfo.getUserName());
                // TODO 目前不调用银行卡接口，在银行卡接口上线前需要确认取用户的哪张银行卡

                List<UserLinkmanDto> linkedMen = userLinkmanService.findByUserId(userId);
                for (UserLinkmanDto linkedMan : linkedMen) {
                    reqDto.getLinkedContacts().add(linkedMan.getMobile());
                }
                userCreditInfoDto = userCreditService.retrieveUserCreditInfo(reqDto);
                userCreditService.saveUserCreditInfo(userCreditInfoDto);
                // 多取一次，防止保存失败
                userCreditInfoDto = userCreditService.getUserCreditInfo(userId);
            }
        } catch (Exception e) {
            LOGGER.error("获取征信数据失败", e);
        }

        return userCreditInfoDto;
    }

    /**
     * 电话核实记录列表
     *
     * @param applId
     * @return
     */
    public List<LoanAuditNoteRespDto> listNotes(String applId) {
        LoanAuditNoteSearchDto searchDto = new LoanAuditNoteSearchDto();
        searchDto.setApplId(applId);
        searchDto.setAuditNoteType(EAuditNoteType.ALL);
        List<LoanAuditNoteRespDto> list = loanAuditService.listNotes(searchDto);

        //管理员名字补全
        list = this.completeMgtUser(list);
        return list;
    }

    /**
     * 审批信息记录
     *
     * @param applId
     * @return
     */
    public List<LoanAuditDto> list(String applId) {
        List<LoanAuditDto> audits = loanAuditService.list(applId);
        audits = this.completeFin(audits);
        audits = this.completeAuditUsername(audits);
        return audits;
    }

    /**
     * 提取审核人员编号
     *
     * @param list
     * @return
     */
    private Set<String> extractAuditUserIds(List<LoanAuditDto> list) {
        Set<String> rs = new HashSet<>();
        for (LoanAuditDto item : list) {
            if (StringUtils.isNotEmpty(item.getAuditUserId())) {
                rs.add(item.getAuditUserId());
            }
        }
        return rs;
    }

    /**
     * 从查询列表中提取mgtUserIds
     *
     * @param list
     * @return
     */
    private Set<String> extractMgtUserIds(List<LoanAuditNoteRespDto> list) {
        Set<String> rs = new HashSet<>();
        for (LoanAuditNoteRespDto item : list) {
            if (StringUtils.isNotEmpty(item.getMgtUserId())) {
                rs.add(item.getMgtUserId());
            }
        }
        return rs;
    }

    /**
     * 补全添加人的姓名
     */
    private List<LoanAuditNoteRespDto> completeMgtUser(List<LoanAuditNoteRespDto> list) {
        Set<String> userIds = this.extractMgtUserIds(list);

        List<MgtUserDto> mgtUserDtos = mgtUserService.findByMgtUserIds(userIds);

        Map<String, MgtUserDto> map = new HashMap<>();

        for (MgtUserDto mgtUserDto : mgtUserDtos) {
            map.put(mgtUserDto.getUserId(), mgtUserDto);
        }

        for (LoanAuditNoteRespDto item : list) {
            MgtUserDto mgtUserDto = map.get(item.getMgtUserId());
            item.setMgtUserName("");
            if (mgtUserDto != null) {
                item.setMgtUserName(mgtUserDto.getName());
            }
        }

        return list;
    }

    /**
     * 补全添加人的姓名
     */
    private List<LoanAuditDto> completeAuditUsername(List<LoanAuditDto> list) {
        Set<String> userIds = this.extractAuditUserIds(list);

        List<MgtUserDto> mgtUserDtos = mgtUserService.findByMgtUserIds(userIds);

        Map<String, MgtUserDto> map = new HashMap<>();

        for (MgtUserDto mgtUserDto : mgtUserDtos) {
            map.put(mgtUserDto.getUserId(), mgtUserDto);
        }

        for (LoanAuditDto item : list) {
            MgtUserDto mgtUserDto = map.get(item.getAuditUserId());
            item.setAuditUsername("");
            if (mgtUserDto != null) {
                item.setAuditUsername(mgtUserDto.getName());
            }
        }

        return list;
    }

    /**
     * 更新用户负债信息
     *
     * @param apply
     * @param audit
     */
    private void saveUserFin(LoanApplDto apply, LoanAuditDto audit) {
        //暂存状态不做更新
        if (audit.getTemp()) {
            return;
        }

        UserExtFinDto userExtFinDto = new UserExtFinDto();
        userExtFinDto.setUserId(apply.getUserId());
        userExtFinDto = (UserExtFinDto) userExtService.getUserExtPart(userExtFinDto);

        userExtFinDto.setLoanAmount(audit.getLoanAmount());
        userExtFinDto.setLoanNum(audit.getLoanNum());
        userExtFinDto.setRepayMonth(audit.getRepayMonth());
        userExtService.saveUserExtPart(userExtFinDto);
    }

    /**
     * 初审、终审日志记录
     *
     * @param mgtUserId
     * @param audit
     */
    private void saveAuditLog(String mgtUserId, LoanAuditDto audit) {
        if (audit.getTemp()) {
            return;
        }

        LoanApplDto apply = loanApplService.get(audit.getApplId());

        if (apply == null) {
            return;
        }
        if (audit.getAuditStatus() == null) {
            return;
        }
        if (audit.getAuditType() == null) {
            return;
        }

        ELoanAuditLogType auditLogType = null;
        if (audit.getAuditType() == EAuditType.TRIAL) {
            auditLogType = ELoanAuditLogType.TRIAL_COMMIT;
        } else if (audit.getAuditType() == EAuditType.REVIEW) {
            auditLogType = ELoanAuditLogType.REVIEW_COMMIT;
        }

        String loanAmt = "", periodDec = "";
        if (audit.getLoanAmt() != null) {
            loanAmt = audit.getLoanAmt().toString();
        }
        if (audit.getPeriod() != null) {
            periodDec = audit.getPeriod().toString() + apply.getTermType().getUnit();
        }

        privateLoanApplService.saveAuditLog(mgtUserId, audit.getApplId(), auditLogType,
                audit.getAuditStatus().getText(), loanAmt, periodDec
        );
    }

    /**
     * 终结分销订单
     *
     * @param apply
     */
    private void endDealerOrder(LoanApplDto apply) {

        if (!this.isFailEnding(apply.getApplStatus())) {
            return;
        }

        DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
        dealerUserOrderDto.setOrderNo(apply.getApplId());
        dealerUserOrderDto.setStatus(EDealerUserOrderStatus.ERROR);
        dealerUserOrderService.updateOrderStatus(dealerUserOrderDto);
    }

    private boolean isFailEnding(EApplStatus status) {
        return status == EApplStatus.CANCEL
                || status == EApplStatus.REJECT
                || status == EApplStatus.FALLBACK;
    }

    private boolean isSuccessEnding(EApplStatus status) {
        return status == EApplStatus.SUCCEED;
    }

    /**
     * 补全审批列表的负债信息
     */
    private List<LoanAuditDto> completeFin(List<LoanAuditDto> audits) {
        if (audits == null || audits.isEmpty()) {
            return audits;
        }
        String applId = audits.get(0).getApplId();
        LoanApplDto loanApplDto = loanApplService.get(applId);
        if (loanApplDto == null) {
            return audits;
        }

        UserExtFinDto userExtFinDto = new UserExtFinDto();
        userExtFinDto.setUserId(loanApplDto.getUserId());
        userExtFinDto = (UserExtFinDto) userExtService.getUserExtPart(userExtFinDto);
        if (userExtFinDto != null) {
            for (LoanAuditDto audit : audits) {
                audit.setLoanAmount(userExtFinDto.getLoanAmount());
                audit.setRepayMonth(userExtFinDto.getRepayMonth());
                audit.setLoanNum(userExtFinDto.getLoanNum());
            }
        }

        return audits;
    }


    private void afterAudit(String mgtUserId, LoanApplDto apply, LoanAuditDto audit) {
        this.endDealerOrder(apply);

        //只有终身通过才会分配资金路由
        FinanceSourceDto financeSourceDto = this.sureFinanceSource(audit, apply);

        //会有两种情况,审核没通过，暂未分配路由或已分配自有资金路由,此时，逻辑按照正常走
        if(financeSourceDto == null || financeSourceDto.getType() == EFinanceSourceType.OWN){
            this.sms(apply);
        }else{
            //成功，则分发订单
            if(EAuditStatus.SUCCEED.equals(audit.getAuditStatus())){
                //先通知外部渠道，如果没问题再记录日志，如果有问题，回滚贷款审核状态和终审记录
                boolean status = this.sendToExternal(apply, audit, financeSourceDto);
                if(!status){
                    throw new BizServiceException(EErrorCode.LOAN_ATZ_APPLY_ERROR, "外部贷款申请失败");
                }
            }else{
                this.sms(apply);
            }

        }

        this.saveAuditLog(mgtUserId, audit);
    }


    /**
     * 审核之后发短信的操作
     *
     * @param apply
     */
    private void sms(LoanApplDto apply) {
        UserInfoDto userInfoDto = userService.findUserByUserId(apply.getUserId());
        if (userInfoDto == null || StringUtils.isEmpty(userInfoDto.getMobile())) {
            return;
        }

        if (apply.getApplStatus() == EApplStatus.REJECT || apply.getApplStatus() == EApplStatus.FALLBACK) {
            SmsUtil.send("110", userInfoDto.getMobile());
        } else if (this.isSuccessEnding(apply.getApplStatus())) {
            SmsUtil.send("138", userInfoDto.getMobile());
        }
    }

    /**
     * 根据审核结果进行资金路由确定
     * @param loanAuditDto
     * @param loanApplDto
     * @return
     */
    private FinanceSourceDto sureFinanceSource(LoanAuditDto loanAuditDto, LoanApplDto loanApplDto){
        FinanceSourceDto financeSourceDto = null;

        //终审非暂存通过，此刻决定资金路由配置
        if(loanAuditDto.getAuditType() == EAuditType.REVIEW
                && loanAuditDto.getAuditStatus() == EAuditStatus.SUCCEED
                && (loanAuditDto.getTemp() == null || !loanAuditDto.getTemp())){

            List<StoreInfDto> stores = storeService.findByUserId(loanApplDto.getUserId());
            StoreInfDto store = null;
            if(CollectionUtils.isNotEmpty(stores)){
                store = stores.get(0);
            }

            FinanceSourceLoanDto financeSourceLoanDto = new FinanceSourceLoanDto();

            financeSourceLoanDto.setApplId(loanApplDto.getApplId());
            financeSourceLoanDto.setProdId(loanApplDto.getProductId());
            financeSourceLoanDto.setLoanAmt(loanApplDto.getApprAmt());
            if(store != null && store.getProvinceId() != null && store.getCityId() != null){
                financeSourceLoanDto.setProvinceId(Integer.parseInt(store.getProvinceId()));
                financeSourceLoanDto.setCityId(Integer.parseInt(store.getCityId()));
            }
            financeSourceDto = financeSourceService.getActive(financeSourceLoanDto);

            if(financeSourceDto != null){
                loanApplDto.setFinanceSourceId(Integer.parseInt(Long.toString(financeSourceDto.getId())));
            }

            loanApplService.save(loanApplDto);
        }

        return financeSourceDto;
    }

    private boolean sendToExternal(LoanApplDto apply, LoanAuditDto audit, FinanceSourceDto financeSourceDto){

        String mgtUserId = SecurityContext.getCurrentOperatorId();

        ExternalLoanApplyDto externalLoanApplyDto = new ExternalLoanApplyDto();
        externalLoanApplyDto.setUserId(apply.getUserId());
        externalLoanApplyDto.setApplId(apply.getApplId());
        externalLoanApplyDto.setRegisterReq(this.buildRegisterRequest(apply.getUserId()));

        try{
            String outTrandeNo = externalService.loanApply(externalLoanApplyDto);
            LoanApplOutDto loanApplOutDto = new LoanApplOutDto();
            loanApplOutDto.setApplId(apply.getApplId());
            loanApplOutDto.setOutTradeNo(outTrandeNo);
            loanApplOutDto.setType(EApplOutType.AITOUZI);
            loanApplOutService.save(loanApplOutDto);
        }catch(RuntimeException e){
            LOGGER.error("loan apply external error, rollback", e);
            apply.setApplStatus(EApplStatus.REVIEW_CLAIMED);
            apply.setFinanceSourceId(null);
            loanApplService.save(apply);
            audit.setTemp(true);
            loanAuditService.review(mgtUserId, audit);
            return false;
        }

        return true;
    }

    private RegisterReq buildRegisterRequest(String userId){
        RegisterReq request = new RegisterReq();

        UserInfoDto userInfoDto = userService.findUserByUserId(userId);

        request.setIdNo(userInfoDto.getIdCardNo());
        request.setUserName(userInfoDto.getUserName());
        request.setContactTel(userInfoDto.getMobile());
        request.setRegisterTime(DateHelper.formatDate(userInfoDto.getCreateTs(), ApplicationConstant.TIMESTAMP_FORMAT));
        request.setUserIdResource(userInfoDto.getUserId());

        List<StoreInfDto> stores = storeService.findByUserId(userId);

        if(stores.size() > 0){
            StoreInfDto store = stores.get(0);

            request.setRegisterName(store.getStoreName());
            request.setLegalRepName(userInfoDto.getUserName());
            request.setLegalRepId(userInfoDto.getIdCardNo());
            request.setLegalRepMobile(userInfoDto.getMobile());
            request.setActualAdd(store.getAddress());

            List<PictureDto> userPictures = pictureService.list(Long.toString(store.getStoreId()));
            if(!userPictures.isEmpty()){
                userPictures.forEach(storePicture -> {
                    if(storePicture.getPictureType() == EPictureType.STORE_TOBACCO){
                        request.setTaxRegistrationPhoto(this.getPicutreUrl(storePicture.getPicturePath()));
                    }
                    if(storePicture.getPictureType() == EPictureType.STORE_HEADER){
                        request.setOutsidePhoto(this.getCompressPicutreUrl(storePicture.getPicturePath()));
                    }
                    if(storePicture.getPictureType() == EPictureType.STORE_INNER){
                        request.setInsidePhoto(this.getCompressPicutreUrl(storePicture.getPicturePath()));
                    }
                    if(storePicture.getPictureType() == EPictureType.STORE_LICENCE){
                        request.setBussinesLicencePhoto(this.getPicutreUrl(storePicture.getPicturePath()));
                    }

                });
            }

        }

        UserExtDto userExtDto = userExtService.findUserByUserId(userId);

        if(userExtDto != null){
            request.setLegalRepAdd(userExtDto.getAddress());
        }

        List<PictureDto> userPictures = pictureService.list(userId);
        if(!userPictures.isEmpty()){
            userPictures.forEach(userPicture -> {
                if(userPicture.getPictureType() == EPictureType.CARD_FRONT){
                    request.setOrganizationCodePhoto(this.getPicutreUrl(userPicture.getPicturePath()));
                }
                if(userPicture.getPictureType() == EPictureType.HELD_ID_CARD){
                    request.setIdPhotoFront(this.getCompressPicutreUrl(userPicture.getPicturePath()));
                }
            });
        }

        return request;
    }

    private String getPicutreUrl(String path){
        return AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(path);
    }


    private String getCompressPicutreUrl(String path){
        path = path.replace(".jpg", ".jpg_420x420.jpg");
        return AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(path);
    }
}
