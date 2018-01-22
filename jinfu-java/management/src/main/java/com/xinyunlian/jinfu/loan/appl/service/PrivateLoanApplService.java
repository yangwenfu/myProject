package com.xinyunlian.jinfu.loan.appl.service;

import com.xinyunlian.jinfu.appl.dto.BeforeTrialDetailDto;
import com.xinyunlian.jinfu.appl.enums.ELoanApplDealerType;
import com.xinyunlian.jinfu.appl.enums.ELoanApplReviewType;
import com.xinyunlian.jinfu.appl.enums.ELoanApplSignType;
import com.xinyunlian.jinfu.appl.enums.ELoanApplTrialType;
import com.xinyunlian.jinfu.audit.dto.LoanAuditDto;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.audit.service.LoanAuditService;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.service.DealerService;
import com.xinyunlian.jinfu.external.dto.LoanApplOutAuditDto;
import com.xinyunlian.jinfu.external.dto.LoanRecordMqDto;
import com.xinyunlian.jinfu.external.service.LoanApplOutAuditService;
import com.xinyunlian.jinfu.loan.LoanApplExtDto;
import com.xinyunlian.jinfu.loan.appl.dto.LoanApplEachExcelDto;
import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplySearchDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyListEachDto;
import com.xinyunlian.jinfu.loan.dto.user.LoanUserDto;
import com.xinyunlian.jinfu.loan.dto.user.LoanUserLinkmanEachDto;
import com.xinyunlian.jinfu.loan.dto.user.LoanUserStoreEachDto;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.loan.service.*;
import com.xinyunlian.jinfu.log.dto.LoanAuditLogDto;
import com.xinyunlian.jinfu.log.enums.ELoanAuditLogType;
import com.xinyunlian.jinfu.log.service.LoanAuditLogService;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.service.PayRecvOrdService;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.promo.dto.PromotionDto;
import com.xinyunlian.jinfu.promo.service.LoanPromoService;
import com.xinyunlian.jinfu.promo.service.PromotionService;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.*;
import com.xinyunlian.jinfu.user.dto.ext.UserExtAllDto;
import com.xinyunlian.jinfu.user.dto.ext.UserExtIdDto;
import com.xinyunlian.jinfu.user.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Willwang on 2017/2/10.
 */
@Service
public class PrivateLoanApplService {

    @Autowired
    private StoreService storeService;

    @Autowired
    private MgtUserService mgtUserService;

    @Autowired
    private DealerService dealerService;

    @Autowired
    private DealerUserService dealerUserService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserLinkmanService userLinkmanService;

    @Autowired
    private LoanPromoService loanPromoService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private LoanApplQueryService loanApplQueryService;

    @Autowired
    private LoanAuditService loanAuditService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private PayRecvOrdService payRecvOrdService;

    @Autowired
    private LoanAuditLogService loanAuditLogService;

    @Autowired
    private LoanApplExtService loanApplExtService;

    @Autowired
    private LoanApplUserService loanApplUserService;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Autowired
    private LoanApplOutAuditService loanApplOutAuditService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateLoanApplService.class);


    /**
     * 获取贷前进件详情
     * @param applId
     * @return
     */
    public BeforeTrialDetailDto getBeforeTrialDetail(String applId){
        BeforeTrialDetailDto detail = loanApplService.getBeforeTrialDetail(applId);
        if(detail == null){
            return null;
        }
        //获取用户数据
        UserInfoDto user = userService.findUserByUserId(detail.getUserId());
        detail.setUserName(user.getUserName());
        detail.setIdCardNo(user.getIdCardNo());
        detail.setMobile(user.getMobile());

        //活动信息补充
        PromoDto promoDto = loanPromoService.get(detail.getLoanId());
        if(promoDto != null){
            PromotionDto promotionDto = promotionService.getByPromotionId(promoDto.getPromoId());
            if(promotionDto != null && promotionDto.getPromoInfDto() != null){
                detail.setPromoId(promotionDto.getPromoInfDto().getPromoId());
                detail.setPromoName(promotionDto.getPromoInfDto().getName());
                detail.setPromoDesc(promotionDto.getPromoInfDto().getDescribe());
            }
        }

        DealerDto dealerDto = this.getDealer(detail.getDealerId());
        if(dealerDto != null){
            detail.setDealerName(dealerDto.getDealerName());
        }

        DealerUserDto dealerUserDto = this.getDealerUser(detail.getDealerUserId());
        if(dealerUserDto != null){
            detail.setDealerUserName(dealerUserDto.getName());
        }

        if(dealerDto != null && dealerUserDto != null){
            detail.setDealerType(ELoanApplDealerType.DEALER);
        }else{
            detail.setDealerType(ELoanApplDealerType.SELF);
        }

        LoanApplDto apply = loanApplService.get(applId);
        List<LoanAuditDto> audits = loanAuditService.list(applId);

        detail.setCanTrialRevoke(this.canTrialRevoke(audits));
        detail.setCanTrialAssign(this.canTrialAssign(apply));
        detail.setCanReviewAssign(this.canReviewAssign(apply));
        detail.setCanReviewRevoke(this.canReviewRevoke(audits));


        if(apply.getFinanceSourceId() != null){
            FinanceSourceDto financeSourceDto = financeSourceService.findById(apply.getFinanceSourceId());
            if(financeSourceDto != null){
                detail.setFinanceSourceType(financeSourceDto.getType());
            }

        }

        return detail;
    }

    private Boolean canTrialRevoke(List<LoanAuditDto> audits){
        LoanAuditDto trial = this.getAudit(audits, EAuditType.TRIAL);

        //存在一条有效的初审信息，且状态满足
        if(trial != null && !trial.getTemp() && Arrays.asList(EAuditStatus.CANCEL, EAuditStatus.REJECT).contains(trial.getAuditStatus())){
            return true;
        }
        return false;
    }

    private Boolean canReviewRevoke(List<LoanAuditDto> audits){
        LoanAuditDto review = this.getAudit(audits, EAuditType.REVIEW);

        //存在一条有效的初审信息，且状态满足
        if(review != null && !review.getTemp() && Arrays.asList(EAuditStatus.SUCCEED, EAuditStatus.REJECT).contains(review.getAuditStatus())){
            return true;
        }
        return false;
    }

    private Boolean canTrialAssign(LoanApplDto apply){
        return apply != null && StringUtils.isNotEmpty(apply.getTrialUserId()) && apply.getApplStatus() == EApplStatus.TRIAL_CLAIMED;
    }

    private Boolean canReviewAssign(LoanApplDto apply){
        return apply != null && StringUtils.isNotEmpty(apply.getReviewUserId()) && apply.getApplStatus() == EApplStatus.REVIEW_CLAIMED;
    }

    /**
     * 列表
     * @param search
     * @return
     */
    public LoanApplySearchDto list(LoanApplySearchDto search){
        Set<String> usernameCondition = this.getUserUserIds(search.getUserName());
        Set<String> tobaccoCondition = this.getTobaccoUserIds(search.getTobacco());
        Set<String> mobileCondition = this.getMobileUserIds(search.getMobile());

        Set<String> userIds = new HashSet<>();

        userIds = this.retianAll(userIds, usernameCondition);
        userIds = this.retianAll(userIds, tobaccoCondition);
        userIds = this.retianAll(userIds, mobileCondition);

        search.setUserIds(userIds);

        LoanApplySearchDto rs = loanApplQueryService.listLoanAppl(search);
        List<LoanApplyListEachDto> list = rs.getList();
        list = this.completeUser(list);
        list = this.completeAudit(list);
        list = this.completeStore(list);
        list = this.completeAuditUser(list);

        rs.setList(list);

        return rs;
    }

    /**
     * 贷款申请综合业务查询
     * @param search
     * @return
     */
    public LoanApplySearchDto general(LoanApplySearchDto search){
        Set<String> userIds = new HashSet<>();
        Set<String> partUserIds;

        boolean hasRetianed = false;

        partUserIds = this.getUserUserIds(search.getUserName());
        if(!partUserIds.isEmpty()){
            hasRetianed = true;
            userIds = this.retianAll(userIds, partUserIds);
        }
        partUserIds = this.getUserIdsByIdCardNo(search.getIdCardNo());
        if(!partUserIds.isEmpty()){
            hasRetianed = true;
            userIds = this.retianAll(userIds, partUserIds);
        }
        partUserIds = this.getUserIdsByLinkmanMobile(search.getLinkmanMobile());
        if(!partUserIds.isEmpty()){
            hasRetianed = true;
            userIds = this.retianAll(userIds, partUserIds);
        }
        partUserIds = this.getTobaccoUserIds(search.getTobacco());
        if(!partUserIds.isEmpty()){
            hasRetianed = true;
            userIds = this.retianAll(userIds, partUserIds);
        }
        partUserIds = this.getMobileUserIds(search.getMobile());
        if(!partUserIds.isEmpty()){
            hasRetianed = true;
            userIds = this.retianAll(userIds, partUserIds);
        }
        partUserIds = this.getUserIdsByStoreName(search.getStoreName());
        if(!partUserIds.isEmpty()){
            hasRetianed = true;
            userIds = this.retianAll(userIds, partUserIds);
        }

        if(userIds.isEmpty() && hasRetianed){
            userIds.add("XXX");
        }

        search.setUserIds(userIds);

        LoanApplySearchDto rs = loanApplQueryService.listLoanAppl(search);
        List<LoanApplyListEachDto> list = rs.getList();
        list = this.completeUser(list);
        list = this.completeAudit(list);
        list = this.completeStore(list);
        list = this.completeLoan(list);
        list = this.completeFinanceSourceType(list);
        list = this.completeOutAudit(list);
        rs.setList(list);

        return rs;
    }

    /**
     * 小贷综合业务查询导出
     * @param search
     * @return
     */
    public List<LoanApplEachExcelDto> generalExport(LoanApplySearchDto search){
        search = this.general(search);
        List<LoanApplEachExcelDto> rs = new ArrayList<>();
        List<LoanApplyListEachDto> list = search.getList();
        if(CollectionUtils.isEmpty(list)){
            return rs;
        }

        Set<String> applIds = new HashSet();


        for (LoanApplyListEachDto loanApplyListEachDto : list) {
            if(applIds.contains(loanApplyListEachDto.getApplId())){
                continue;
            }

            LoanApplEachExcelDto loanApplEachExcelDto = ConverterService.convert(loanApplyListEachDto, LoanApplEachExcelDto.class);

            loanApplEachExcelDto.setApplPeriod(loanApplyListEachDto.getPeriod() + loanApplyListEachDto.getUnit());
            if(loanApplyListEachDto.getLoanPeriod() != null){
                loanApplEachExcelDto.setReviewPeriod(loanApplyListEachDto.getLoanPeriod() + loanApplyListEachDto.getUnit());
            }

            String repayModeString = loanApplyListEachDto.getRepayMode() == null ? "" : loanApplyListEachDto.getRepayMode().getText();
            loanApplEachExcelDto.setRepayModeString(repayModeString);
            String dealerTypeString = loanApplyListEachDto.getDealerType() == null ? "" : loanApplyListEachDto.getDealerType().getText();
            loanApplEachExcelDto.setDealerTypeString(dealerTypeString);
            String claimedTypeString = loanApplyListEachDto.getTrialClaimedType() == null ? "" : loanApplyListEachDto.getTrialClaimedType().getText();
            loanApplEachExcelDto.setTrialClaimedTypeString(claimedTypeString);
            String trialTypeString = loanApplyListEachDto.getTrialType() == null ? "" : loanApplyListEachDto.getTrialType().getText();
            loanApplEachExcelDto.setTrialTypeString(trialTypeString);
            String trialStatusString = loanApplyListEachDto.getTrialStatus() == null ? "" : loanApplyListEachDto.getTrialStatus().getText();
            loanApplEachExcelDto.setTrialStatusString(trialStatusString);
            String reviewType = loanApplyListEachDto.getReviewType() == null ? "" : loanApplyListEachDto.getReviewType().getText();
            loanApplEachExcelDto.setReviewTypeString(reviewType);
            String reviewStatus = loanApplyListEachDto.getReviewStatus() == null ? "" : loanApplyListEachDto.getReviewStatus().getText();
            loanApplEachExcelDto.setReviewStatusString(reviewStatus);
            String signType = loanApplyListEachDto.getSignType() == null ? "" : loanApplyListEachDto.getSignType().getText();
            loanApplEachExcelDto.setSignTypeString(signType);
            String transferStat = loanApplyListEachDto.getTransferStat() == null ? "" : loanApplyListEachDto.getTransferStat().getText();
            loanApplEachExcelDto.setTransferStatString(transferStat);

            StringBuilder sb = new StringBuilder();
            if(StringUtils.isNotEmpty(loanApplyListEachDto.getProvince())){
                sb.append(loanApplyListEachDto.getProvince());
            }
            sb.append("-");
            if(StringUtils.isNotEmpty(loanApplyListEachDto.getCity())){
                sb.append(loanApplyListEachDto.getCity());
            }

            loanApplEachExcelDto.setProvinceAndCity(sb.toString());
            rs.add(loanApplEachExcelDto);
            applIds.add(loanApplyListEachDto.getApplId());
        }

        return rs;
    }

    /**
     * 初审列表
     * @param search
     * @return
     */
    public LoanApplySearchDto trialList(LoanApplySearchDto search){
        Set<String> mgtUserIds = this.getMgtUserIds(search.getTrialName());
        search.setTrialUserIds(mgtUserIds);
        return this.list(search);
    }

    /**
     * 终审列表
     * @param search
     * @return
     */
    public LoanApplySearchDto reviewList(LoanApplySearchDto search){
        Set<String> mgtUserIds = this.getMgtUserIds(search.getReviewName());
        search.setReviewUserIds(mgtUserIds);
        return this.list(search);
    }

    /**
     * 签约列表
     * @param search
     * @return
     */
    public LoanApplySearchDto signList(LoanApplySearchDto search){
        LoanApplySearchDto rs = this.list(search);
        List<LoanApplyListEachDto> list = rs.getList();
        list = this.completeFinanceSourceType(list);
        rs.setList(list);
        return rs;
    }

    /**
     * 待终审领取的列表
     * @return
     */
    public LoanApplySearchDto reviewUnclaimedList(){
        LoanApplySearchDto search = new LoanApplySearchDto();
        search.setCurrentPage(1);
        search.setPageSize(null);
        search.setApplStatus(EApplStatus.REVIEW_UNCLAIMED);
        LoanApplySearchDto rs = loanApplQueryService.listLoanAppl(search);
        List<LoanApplyListEachDto> list = rs.getList();
        list = this.completeUser(list);
        list = this.completeAuditUser(list);
        list = this.completeAudit(list);
        rs.setList(list);
        return rs;
    }

    /**
     * 贷中-放款-列表
     * @param search
     * @return
     */
    public LoanApplySearchDto transferList(LoanApplySearchDto search){
        Set<String> usernameCondition = this.getUserUserIds(search.getUserName());
        Set<String> tobaccoCondition = this.getTobaccoUserIds(search.getTobacco());
        Set<String> mobileCondition = this.getMobileUserIds(search.getMobile());

        Set<String> userIds = new HashSet<>();

        userIds = this.retianAll(userIds, usernameCondition);
        userIds = this.retianAll(userIds, tobaccoCondition);
        userIds = this.retianAll(userIds, mobileCondition);

        search.setUserIds(userIds);

        LoanApplySearchDto rs = loanApplQueryService.listLoanAppl(search);
        List<LoanApplyListEachDto> list = rs.getList();
        list = this.completeUser(list);
        list = this.completeLoan(list);

        list = this.completeFinanceSourceType(list);

        rs.setList(list);

        return rs;
    }

    /**
     * 贷前申请列表
     * @param search
     * @return
     */
    public LoanApplySearchDto applyList(LoanApplySearchDto search){
        LoanApplySearchDto rs = loanApplQueryService.listLoanAppl(search);
        List<LoanApplyListEachDto> list = rs.getList();
        list = this.completeUser(list);
        list = this.completeStore(list);
        list = this.completeAuditUser(list);
        list = this.completeDealer(list);
        rs.setList(list);
        return rs;
    }

    private Set<String> retianAll(Set<String> a, Set<String> b){
        if(a.size() == 0){
            return b;
        }

        if(b.size() == 0){
            return a;
        }

        a.retainAll(b);

        return a;
    }

    public Set<String> getTobaccoUserIds(String tobacco){
        Set<String> userIds = new HashSet<>();
        if(StringUtils.isNotEmpty(tobacco)){
            tobacco = tobacco.trim();
        }
        if(StringUtils.isEmpty(tobacco)){
            return userIds;
        }
        List<StoreInfDto> stores = storeService.findByTobaccoCertificateNoLike(tobacco);
        stores.forEach(store -> userIds.add(store.getUserId()));

        if(userIds.size() <= 0){
            userIds.add("XXX");
        }

        return userIds;
    }

    /**
     * 根据后台人员名字进行模糊查询
     * @param mgtName
     * @return
     */
    public Set<String> getMgtUserIds(String mgtName){
        Set<String> userIds = new HashSet<>();
        if(StringUtils.isNotEmpty(mgtName)){
            mgtName = mgtName.trim();
        }
        if(StringUtils.isEmpty(mgtName)){
            return userIds;
        }

        List<MgtUserDto> users = mgtUserService.findByNameLike(mgtName);
        users.forEach(user -> userIds.add(user.getUserId()));

        if(userIds.size() <= 0){
            userIds.add("XXX");
        }
        return userIds;
    }

    public Set<String> getUserUserIds(String username){
        Set<String> userIds = new HashSet<>();
        if(StringUtils.isNotEmpty(username)){
            username = username.trim();
        }
        if(StringUtils.isEmpty(username)){
            return userIds;
        }
        List<UserInfoDto> userInfos = userService.findByUserNameLike(username);
        userInfos.forEach(userInfoDto -> userIds.add(userInfoDto.getUserId()));
        if(userIds.size() <= 0){
            userIds.add("XXX");
        }
        return userIds;
    }

    /**
     * 根据身份证搜用户编号
     * @param idCardNo
     * @return
     */
    public Set<String> getUserIdsByIdCardNo(String idCardNo){
        Set<String> userIds = new HashSet<>();
        if(StringUtils.isNotEmpty(idCardNo)){
            idCardNo = idCardNo.trim();
        }
        if(StringUtils.isEmpty(idCardNo)){
            return userIds;
        }

        List<UserInfoDto> userInfos = userService.findByIdCardNoLike(idCardNo);
        userInfos.forEach(userInfoDto -> userIds.add(userInfoDto.getUserId()));
        if(userIds.size() <= 0){
            userIds.add("XXX");
        }
        return userIds;
    }

    /**
     * 根据联系人手机搜用户编号
     * @param mobile
     * @return
     */
    public Set<String> getUserIdsByLinkmanMobile(String mobile){
        Set<String> userIds = new HashSet<>();
        if(StringUtils.isNotEmpty(mobile)){
            mobile = mobile.trim();
        }
        if(StringUtils.isEmpty(mobile)){
            return userIds;
        }

        List<String> listUserIds = userLinkmanService.findUserIdByLinkmanPhone(mobile);
        listUserIds.forEach(userId -> userIds.add(userId));
        if(userIds.size() <= 0){
            userIds.add("XXX");
        }
        return userIds;
    }

    /**
     * 根据店铺名搜用户编号
     * @param storeName
     * @return
     */
    public Set<String> getUserIdsByStoreName(String storeName){
        Set<String> userIds = new HashSet<>();
        if(StringUtils.isNotEmpty(storeName)){
            storeName = storeName.trim();
        }
        if(StringUtils.isEmpty(storeName)){
            return userIds;
        }

        List<StoreInfDto> storeInfDtos = storeService.findByStoreNameLike(storeName);
        storeInfDtos.forEach(storeInfDto -> userIds.add(storeInfDto.getUserId()));
        if(userIds.size() <= 0){
            userIds.add("XXX");
        }
        return userIds;
    }


    private Set<String> getMobileUserIds(String mobile){
        Set<String> rs = new HashSet<>();
        if(StringUtils.isNotEmpty(mobile)){
            mobile = mobile.trim();
        }
        if(StringUtils.isEmpty(mobile)){
            return rs;
        }

        UserInfoDto user = userService.findUserByMobile(mobile);

        if(user != null){
            rs.add(user.getUserId());
        }else{
            rs.add("XXX");
        }

        return rs;
    }

    /**
     * 补全店铺信息
     * @param list
     * @return
     */
    public List<LoanApplyListEachDto> completeStore(List<LoanApplyListEachDto> list){

        Set<String> userIds = this.extractUserIds(list);
        List<StoreInfDto> stores = storeService.findByUserIds(userIds);

        Map<String, StoreInfDto> map = new HashMap<>();

        for (StoreInfDto store : stores) {
            if(map.get(store.getUserId()) == null){
                map.put(store.getUserId(), store);
            }else if(map.get(store.getUserId()) != null){
                //如果该用户已经存在店铺，而且店铺的编号更小，则取老的那家
                if(store.getStoreId() < map.get(store.getUserId()).getStoreId()){
                    map.put(store.getUserId(), store);
                }
            }
        }

        for (LoanApplyListEachDto item : list) {
            StoreInfDto store = map.get(item.getUserId()) != null ? map.get(item.getUserId()) : null;
            item.setCity("");
            item.setProvince("");
            if(store != null){
                item.setCity(store.getCity());
                item.setProvince(store.getProvince());
            }
        }

        return list;
    }

    /**
     * 补全用户信息
     * @param list
     * @return
     */
    public List<LoanApplyListEachDto> completeUser(List<LoanApplyListEachDto> list){

        Set<String> userIds = this.extractUserIds(list);

        List<UserInfoDto> users = new ArrayList<>();

        if(userIds.size() > 0){
            users = userService.findUserByUserId(userIds);
        }
        Map<String, UserInfoDto> map = new HashMap<>();

        for (UserInfoDto user : users) {
            map.put(user.getUserId(), user);
        }

        for (LoanApplyListEachDto item : list) {
            UserInfoDto user = map.get(item.getUserId());
            item.setUserName("");
            if(user != null){
                item.setUserName(user.getUserName());
                item.setMobile(user.getMobile());
                item.setIdCardNo(user.getIdCardNo());
            }
        }

        return list;
    }

    /**
     * 补充初审、终审详情
     * @param list
     * @return
     */
    private List<LoanApplyListEachDto> completeAudit(List<LoanApplyListEachDto> list){
        Set<String> applIds = this.extraApplIds(list);

        Map<String, List<LoanAuditDto>> audits = this.getAudits(applIds);

        for (LoanApplyListEachDto item : list) {
            item.setTrialAmt(BigDecimal.ZERO);
            item.setReviewAmt(BigDecimal.ZERO);

            List<LoanAuditDto> ll = audits.get(item.getApplId());

            item.setTrialType(this.getTrialType(item, ll));
            item.setReviewType(this.getReviewType(item, ll));

            LoanAuditDto review = this.getAudit(ll, EAuditType.REVIEW);

            //存在一条真实有效的终审信息,如果不是通过状态
            if(review != null && !review.getTemp()
                    && EAuditStatus.SUCCEED.equals(review.getAuditStatus())
                    && EApplStatus.SUCCEED.equals(item.getStatus())){
            }else{
                item.setSignType(null);
            }

            if(ll != null && ll.size() > 0){
                for (LoanAuditDto loanAuditDto : ll) {
                    if(loanAuditDto == null || loanAuditDto.getTemp()){
                        continue;
                    }
                    //初审、终审状态处理
                    if(loanAuditDto.getAuditType() == EAuditType.TRIAL){
                        item.setTrialStatus(loanAuditDto.getAuditStatus());
                    }
                    if(loanAuditDto.getAuditType() == EAuditType.REVIEW){
                        item.setReviewStatus(loanAuditDto.getAuditStatus());
                    }

                    //初审、终审金额处理
                    if(loanAuditDto.getLoanAmt() == null || loanAuditDto.getLoanAmt().compareTo(BigDecimal.ZERO) <= 0){
                        continue;
                    }
                    if(loanAuditDto.getAuditType() == EAuditType.TRIAL){
                        item.setTrialAmt(loanAuditDto.getLoanAmt());
                    }

                    if(loanAuditDto.getAuditType() == EAuditType.REVIEW){
                        item.setReviewAmt(loanAuditDto.getLoanAmt());
                    }

                }
            }
        }

        return list;
    }


    /**
     * 资金路由信息补充
     * @return
     */
    private List<LoanApplyListEachDto> completeFinanceSourceType(List<LoanApplyListEachDto> list){
        List<FinanceSourceDto> financeSourceDtos = financeSourceService.getAll();
        Map<Integer, EFinanceSourceType> map = new HashMap<>();
        financeSourceDtos.forEach(financeSourceDto -> map.put(financeSourceDto.getId(), financeSourceDto.getType()));

        list.forEach(loanApplyListEachDto -> {
            if(loanApplyListEachDto.getFinanceSourceId() == null){
                loanApplyListEachDto.setFinanceSourceType(EFinanceSourceType.OWN);
            }else{
                loanApplyListEachDto.setFinanceSourceType(
                    map.get(loanApplyListEachDto.getFinanceSourceId()) != null ?
                        map.get(loanApplyListEachDto.getFinanceSourceId()) : EFinanceSourceType.OWN
                );
            }
        });

        return list;
    }

    /**
     * 资金端审核意见
     * @return
     */
    private List<LoanApplyListEachDto> completeOutAudit(List<LoanApplyListEachDto> list){

        if(list.size() <= 0){
            return list;
        }

        Set<String> applIds = (Set<String>) this.extraSet(list, "applId");
        List<LoanApplOutAuditDto> audits = loanApplOutAuditService.findByApplIdIn(applIds);
        Map<String, LoanApplOutAuditDto> map = new HashMap<>();
        audits.forEach(audit -> map.put(audit.getApplId(), audit));


        list.forEach(item -> {
            if(map.get(item.getApplId()) != null){
                StringBuffer sb = new StringBuffer();
                LoanApplOutAuditDto loanApplOutAuditDto = map.get(item.getApplId());
                if(loanApplOutAuditDto != null){
                    item.setOutAuditLoanAmt(loanApplOutAuditDto.getLoanAmt());
                    item.setOutAuditResult(loanApplOutAuditDto.getAuditType());
                    item.setOutAuditReason(loanApplOutAuditDto.getReason());
                }
            }
        });

        return list;
    }

    /**
     * 补充贷款放款状态、放款日期、放款失败原因
     * @param list
     * @return
     */
    private List<LoanApplyListEachDto> completeLoan(List<LoanApplyListEachDto> list){

        Set<String> applIds = this.extraApplIds(list);

        List<LoanDtlDto> loans = loanService.findByApplIds(applIds);

        Map<String, LoanDtlDto> map = new HashMap<>();

        for (LoanDtlDto loan : loans) {
            map.put(loan.getApplId(), loan);
        }

        for (LoanApplyListEachDto item : list) {
            LoanDtlDto loan = map.get(item.getApplId());

            if(loan == null){ continue;}

            item.setTransferStat(loan.getTransferStat());
            item.setTransferDate(DateHelper.formatDate(loan.getTransferDate(), ApplicationConstant.DATE_FORMAT));
            item.setLoanId(loan.getLoanId());
            if(loan.getTransferStat() != null && loan.getTransferStat() == ETransferStat.FAILED){
                PayRecvOrdDto payRecvOrdDto = payRecvOrdService.findByBizId(loan.getLoanId());
                item.setPayRecvOrd(payRecvOrdDto);
            }
        }

        return list;
    }

    private Map<String, List<LoanAuditDto>> getAudits(Set<String> applIds){
        List<LoanAuditDto> audits = loanAuditService.findByApplIds(applIds);
        Map<String, List<LoanAuditDto>> map = new HashMap<>();

        if(audits.size() <= 0){
            return map;
        }

        for (LoanAuditDto audit : audits) {
            List<LoanAuditDto> ll = map.get(audit.getApplId());
            if(ll == null){
                ll = new ArrayList<>();
            }
            ll.add(audit);
            map.put(audit.getApplId(), ll);
        }

        return map;
    }

    /**
     * 补全审核人名字信息
     */
    public List<LoanApplyListEachDto> completeAuditUser(List<LoanApplyListEachDto> list){
        Set<String> userIds = new HashSet<>();
        userIds.addAll(this.extractTrialUserIds(list));
        userIds.addAll(this.extractReviewUserIds(list));

        List<MgtUserDto> mgtUserDtos = mgtUserService.findByMgtUserIds(userIds);

        Map<String, MgtUserDto> map = new HashMap<>();

        for (MgtUserDto mgtUserDto : mgtUserDtos) {
            map.put(mgtUserDto.getUserId(), mgtUserDto);
        }

        for (LoanApplyListEachDto item : list) {
            MgtUserDto trialUser = map.get(item.getTrialUserId());
            MgtUserDto reviewUser = map.get(item.getReviewUserId());
            item.setTrialName("");
            item.setReviewName("");
            if(trialUser != null){
                item.setTrialName(trialUser.getName());
            }
            if(reviewUser != null){
                item.setReviewName(reviewUser.getName());
            }
        }

        return list;
    }

    /**
     * 补全分销信息
     */
    public List<LoanApplyListEachDto> completeDealer(List<LoanApplyListEachDto> list){
        List<String> dealerIds = this.extractDealerIds(list);
        List<String> dealerUserIds = this.extractDealerUserIds(list);

        List<DealerDto> dealerDtos = dealerService.findByDealerIds(dealerIds);
        List<DealerUserDto> dealerUserDtos = dealerUserService.findByDealerUserIds(dealerUserIds);


        Map<String, DealerDto> dealerDtoMap = new HashMap<>();
        Map<String, DealerUserDto> dealerUserDtoMap = new HashMap<>();

        for (DealerDto dealerDto : dealerDtos) {
            dealerDtoMap.put(dealerDto.getDealerId(), dealerDto);
        }
        for (DealerUserDto dealerUserDto : dealerUserDtos) {
            dealerUserDtoMap.put(dealerUserDto.getUserId(), dealerUserDto);
        }

        for (LoanApplyListEachDto item : list) {
            DealerDto dealerDto = dealerDtoMap.get(item.getDealerId()) != null ? dealerDtoMap.get(item.getDealerId()) : null;
            DealerUserDto dealerUserDto = dealerUserDtoMap.get(item.getDealerUserId()) != null ? dealerUserDtoMap.get(item.getDealerUserId()) : null;

            item.setDealerName("");
            item.setDealerUserName("");
            if(dealerDto != null){
                item.setDealerName(dealerDto.getDealerName());
            }
            if(dealerUserDto != null){
                item.setDealerUserName(dealerUserDto.getName());
            }
        }

        return list;
    }

    /**
     * 审核日志
     * @param mgtUserId
     * @param applId
     * @param auditLogType
     * @param args
     */
    public void saveAuditLog(String mgtUserId, String applId, ELoanAuditLogType auditLogType, String... args){
        MgtUserDto user = mgtUserService.getMgtUserInf(mgtUserId);
        if(user == null){
            return;
        }

        switch (auditLogType){
            case TRIAL_PULL:
            case REVIEW_PULL:
                args = new String[]{user.getName(), applId};
                break;
            case TRIAL_HANG_UP:
            case TRIAL_CANCEL_HANG_UP:
            case REVIEW_CANCEL:
            case TRIAL_REVOKE:
                args = new String[]{user.getName()};
                break;
            case TRIAL_FALLBACK:
            case REVIEW_FALLBACK:
                args = (String[]) ArrayUtils.addAll(new String[]{user.getName()}, args);
                break;
            default:
                break;
        }

        LoanAuditLogDto log = new LoanAuditLogDto(user.getName(), applId, auditLogType, args);
        loanAuditLogService.save(log);
    }

    public LoanAuditDto getAudit(String applId, EAuditType auditType){
        List<LoanAuditDto> audits = loanAuditService.list(applId);
        for (LoanAuditDto auditDto : audits) {
            if(auditDto.getAuditType() == auditType){
                return auditDto;
            }
        }
        return null;
    }

    /**
     * 获取初审状态
     * @param item
     * @param audits
     * @return
     */
    private ELoanApplTrialType getTrialType(LoanApplyListEachDto item, List<LoanAuditDto> audits){
        if(item.getStatus() == EApplStatus.TRIAL_CLAIMED){
            return ELoanApplTrialType.WAIT;
        }else if(item.getStatus() == EApplStatus.FALLBACK){
            return ELoanApplTrialType.FALLBACK;
        }
        LoanAuditDto audit = this.getAudit(audits, EAuditType.TRIAL);
        if(audit == null){
            return null;
        }
        if(!audit.getTemp()){
            return ELoanApplTrialType.ALREADY;
        }
        return null;
    }

    /**
     * 获取终审状态
     * @param item
     * @param audits
     * @return
     */
    private ELoanApplReviewType getReviewType(LoanApplyListEachDto item, List<LoanAuditDto> audits){
        if(item.getStatus() == EApplStatus.REVIEW_UNCLAIMED){
            return ELoanApplReviewType.UNCLAIMED;
        }
        if(item.getStatus() == EApplStatus.REVIEW_CLAIMED){
            return ELoanApplReviewType.WAIT;
        }
        LoanAuditDto audit = this.getAudit(audits, EAuditType.REVIEW);
        if(audit == null){
            return null;
        }
        if(!audit.getTemp()){
            return ELoanApplReviewType.ALREADY;
        }
        return null;
    }

    /**
     * 根据审批列表、审批类型获取单一的一条审批记录
     * @param list
     * @param auditType
     * @return
     */
    private LoanAuditDto getAudit(List<LoanAuditDto> list, EAuditType auditType){
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        for (LoanAuditDto auditDto : list) {
            if(auditDto.getAuditType() == auditType){
                return auditDto;
            }
        }
        return null;
    }

    /**
     * 从查询列表中提取userId
     * @param list
     * @return
     */
    private Set<String> extractUserIds(List<LoanApplyListEachDto> list){
        Set<String> rs = new HashSet<>();
        for (LoanApplyListEachDto loanApplyListEachDto : list) {
            if(StringUtils.isNotEmpty(loanApplyListEachDto.getUserId())){
                rs.add(loanApplyListEachDto.getUserId());
            }
        }
        return rs;
    }

    /**
     * 从查询列表中提取trialUserId
     * @param list
     * @return
     */
    private Set<String> extractTrialUserIds(List<LoanApplyListEachDto> list){
        Set<String> rs = new HashSet<>();
        for (LoanApplyListEachDto loanApplyListEachDto : list) {
            if(StringUtils.isNotEmpty(loanApplyListEachDto.getTrialUserId())){
                rs.add(loanApplyListEachDto.getTrialUserId());
            }
        }
        return rs;
    }

    /**
     * 从查询列表中提取reviewUserId
     * @param list
     * @return
     */
    private Set<String> extractReviewUserIds(List<LoanApplyListEachDto> list){
        Set<String> rs = new HashSet<>();
        for (LoanApplyListEachDto loanApplyListEachDto : list) {
            if(StringUtils.isNotEmpty(loanApplyListEachDto.getReviewUserId())){
                rs.add(loanApplyListEachDto.getReviewUserId());
            }
        }
        return rs;
    }

    private Set<String> extraApplIds(List<LoanApplyListEachDto> list){
        Set<String> rs = new HashSet<>();
        list.forEach(item -> rs.add(item.getApplId()));
        return rs;
    }

    /**
     * 从查询列表中提取dealerUserIds
     * @param list
     * @return
     */
    private List<String> extractDealerUserIds(List<LoanApplyListEachDto> list){
        List<String> rs = new ArrayList<>();
        for (LoanApplyListEachDto loanApplyListEachDto : list) {
            if(StringUtils.isNotEmpty(loanApplyListEachDto.getDealerUserId()) && !rs.contains(loanApplyListEachDto.getDealerUserId())){
                rs.add(loanApplyListEachDto.getDealerUserId());
            }
        }
        return rs;
    }

    /**
     * 从查询列表中提取dealerIds
     * @param list
     * @return
     */
    private List<String> extractDealerIds(List<LoanApplyListEachDto> list){
        List<String> rs = new ArrayList<>();
        for (LoanApplyListEachDto loanApplyListEachDto : list) {
            if(StringUtils.isNotEmpty(loanApplyListEachDto.getDealerId()) && !rs.contains(loanApplyListEachDto.getDealerId())){
                rs.add(loanApplyListEachDto.getDealerId());
            }
        }
        return rs;
    }

    private DealerDto getDealer(String dealerId){
        if(StringUtils.isEmpty(dealerId)){
            return null;
        }

        List<String> dealerIds = new ArrayList<>();
        dealerIds.add(dealerId);
        List<DealerDto> dealerDtos = dealerService.findByDealerIds(dealerIds);

        if(dealerDtos.size() > 0){
            return dealerDtos.get(0);
        }
        return null;
    }

    private DealerUserDto getDealerUser(String dealerUserId){
        if(StringUtils.isEmpty(dealerUserId)){
            return null;
        }
        List<String> dealerUserIds = new ArrayList<>();
        dealerUserIds.add(dealerUserId);
        List<DealerUserDto> dealerUserDtos = dealerUserService.findByDealerUserIds(dealerUserIds);

        if(dealerUserDtos.size() > 0){
            return dealerUserDtos.get(0);
        }
        return null;
    }


    @Deprecated
    @Async
    public void applyExtMigrate(){
        Integer current = 1;
        List<LoanApplExtDto> list;
        int counter = 1;
        do{
            list = loanApplExtService.listAll(current);
            for (LoanApplExtDto loanApplExtDto : list) {
                try{
                    LoanUserDto user = JsonUtil.toObject(LoanUserDto.class, loanApplExtDto.getUserExtra());

                    LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(loanApplExtDto.getApplId());
                    if(loanApplUserDto == null){
                        loanApplUserDto = new LoanApplUserDto();
                        loanApplUserDto.setApplId(loanApplExtDto.getApplId());
                    }
                    loanApplUserDto.setApplId(loanApplExtDto.getApplId());

                    UserExtIdDto userExtIdDto = ConverterService.convert(user.getBase(), UserExtAllDto.class);
                    loanApplUserDto.setUserBase(JsonUtil.toJson(userExtIdDto));

                    List<UserLinkmanDto> linkmen = new ArrayList<>();
                    for (LoanUserLinkmanEachDto loanUserLinkmanEachDto : user.getLinkman()) {
                        UserLinkmanDto linkman = ConverterService.convert(loanUserLinkmanEachDto, UserLinkmanDto.class);
                        linkmen.add(linkman);
                    }
                    loanApplUserDto.setUserLinkman(JsonUtil.toJson(linkmen));

                    List<StoreInfDto> stores = new ArrayList<>();
                    for (LoanUserStoreEachDto loanUserStoreEachDto : user.getStore()) {
                        StoreInfDto store = ConverterService.convert(loanUserStoreEachDto, StoreInfDto.class);
                        stores.add(store);
                    }
                    loanApplUserDto.setStoreBase(JsonUtil.toJson(stores));

                    loanApplUserDto.setCarBase("[]");
                    loanApplUserDto.setHouseBase("[]");
                    loanApplUserDto.setBankBase("{}");
                    loanApplUserDto.setUserTobacco("{}");
                    loanApplUserDto.setCreditPhone("{}");
                    loanApplUserDto.setCreditOverdue("{}");
                    loanApplUserDto.setCreditLoan("{}");
                    loanApplUserDto.setCreditStatistics("{}");
                    loanApplUserDto.setCreditBlacklist("{}");

                    loanApplUserService.save(loanApplUserDto);

                    LOGGER.info("migrate:appl_user, counter:{}, applId:{}, success", counter++, loanApplExtDto.getApplId());

                }catch(Exception e){
                    LOGGER.error("migrate:appl_user, exception:{}", e);
                }
            }

            current++;
        }while(list.size() > 0);

        LOGGER.info("migrate:appl_user, finish");
    }

    private List<?> extra(Collection<?> list, String fieldName) {
        List<Object> rs = new ArrayList<>();
        Iterator iter = list.iterator();
        if (!iter.hasNext() || fieldName.isEmpty() || list.isEmpty()) {
            return rs;
        }

        Field[] fields = iter.next().getClass().getDeclaredFields();

        list.forEach(item -> {
            for (int i = 0; i < fields.length; i++) {
                if (fieldName.equals(fields[i].getName())) {
                    try {
                        if (!fields[i].isAccessible()) {
                            fields[i].setAccessible(true);
                        }

                        if(fields[i].get(item) == null){
                            continue;
                        }
                        rs.add(fields[i].get(item));
                    } catch (IllegalAccessException e) {
                    }
                }
            }
        });

        return rs;
    }

    private Set<?> extraSet(Collection<?> list, String fieldName){
        return new HashSet<>(this.extra(list, fieldName));
    }

}
