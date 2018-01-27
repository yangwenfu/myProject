package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.appl.enums.*;
import com.xinyunlian.jinfu.audit.entity.LoanAuditPo;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.external.entity.LoanApplOutAuditPo;
import com.xinyunlian.jinfu.loan.dao.LoanApplDao;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplySearchDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyListEachDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutAuditType;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.product.dao.ProductInfoDao;
import com.xinyunlian.jinfu.product.entity.LoanProductInfoPo;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InnerLoanApplQueryServiceImpl implements InnerLoanApplQueryService {

    @Autowired
    private LoanApplDao loanApplDao;
    @Autowired
    private ProductInfoDao productInfoDao;

    @Autowired
    private FinanceSourceService financeSourceService;

    private List<List<String>> chunk(List<String> list, int chunkSize) {
        List<List<String>> chunks = new ArrayList<>();
        int size = list.size() / chunkSize + 1;
        for (int i = 0; i < size; i++) {
            int toIndex = (i + 1) == size ? list.size() : (i + 1) * chunkSize;
            chunks.add(list.subList(i * chunkSize, toIndex));
        }
        return chunks;
    }

    @Override
    @Transactional(readOnly = true)
    public LoanApplySearchDto listLoanAppl(LoanApplySearchDto search) {
        Specification<LoanApplPo> spec = (Root<LoanApplPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if (null != search) {
                if (!StringUtils.isBlank(search.getApplId())) {
                    expressions.add(cb.like(root.get("applId"), BizUtil.filterString(search.getApplId())));
                }
                if (!StringUtils.isBlank(search.getProdId())) {
                    expressions.add(cb.like(root.get("productId"), BizUtil.filterStringRight(search.getProdId())));
                }
                if (StringUtils.isNotEmpty(search.getApplStartDate())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(search.getApplStartDate())));
                }
                if (StringUtils.isNotEmpty(search.getApplEndDate())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(search.getApplEndDate())));
                }

                if (StringUtils.isNotEmpty(search.getSignStartDate())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("signDate"), DateHelper.getStartDate(search.getSignStartDate())));
                }
                if (StringUtils.isNotEmpty(search.getSignEndDate())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("signDate"), DateHelper.getEndDate(search.getSignEndDate())));
                }

                if (search.getApplStatus() != null && search.getApplStatus() != EApplStatus.ALL) {
                    expressions.add(cb.equal(root.get("applStatus"), search.getApplStatus()));
                }
                if (!search.getUserIds().isEmpty()) {
                    expressions.add(root.get("userId").in(search.getUserIds()));
                }
                if (!search.getAppIds().isEmpty()) {
                    List<String> applIds = new ArrayList<>(search.getAppIds());

                    int chunkSize = AppConfigUtil.isUnProdEnv() ? 2 : 500;
                    List<List<String>> chunks = this.chunk(applIds, chunkSize);
                    Predicate[] ss = new Predicate[chunks.size()];
                    for (int i = 0; i < chunks.size(); i++) {
                        ss[i] = cb.in(root.get("applId")).value(chunks.get(i));
                    }
                    expressions.add(cb.or(ss));
                }

                if (search.getTrialClaimedType() != null) {
                    Predicate claimed = cb.equal(root.get("applStatus"), EApplStatus.TRIAL_CLAIMED);
                    Predicate unClaimed = cb.equal(root.get("applStatus"), EApplStatus.TRIAL_UNCLAIMED);

                    switch (search.getTrialClaimedType()) {
                        case ALL:
                            expressions.add(cb.or(claimed, unClaimed));
                            break;
                        case CLAIMED:
                            expressions.add(claimed);
                            break;
                        case UNCLAIMED:
                            expressions.add(unClaimed);
                            break;
                        default:
                            break;
                    }
                }

                if(CollectionUtils.isNotEmpty(search.getReviewUserIds())){
                    expressions.add(cb.in(root.get("reviewUserId")).value(search.getReviewUserIds()));
                }

                if(CollectionUtils.isNotEmpty(search.getTrialUserIds())){
                    expressions.add(cb.in(root.get("trialUserId")).value(search.getTrialUserIds()));
                }

                if (search.getDealerType() != null && search.getDealerType() != ELoanApplDealerType.ALL) {
                    if (search.getDealerType() == ELoanApplDealerType.SELF) {
                        expressions.add(this.isEmpty(cb, root.get("dealerUserId")));
                    } else if (search.getDealerType() == ELoanApplDealerType.DEALER) {
                        expressions.add(this.isNotEmpty(cb, root.get("dealerUserId")));
                    }
                }

                if(search.getHangup() != null){
                    expressions.add(cb.equal(root.get("hangUp"), search.getHangup()));
                }

                if (search.getTrialType() != null && search.getTrialType() != ELoanApplTrialType.ALL) {
                    switch (search.getTrialType()) {
                        case WAIT:
                            expressions.add(cb.equal(root.get("applStatus"), EApplStatus.TRIAL_CLAIMED));
                            break;
                        case ALREADY:
                            ListJoin<LoanApplPo, LoanAuditPo> join = root.join(root.getModel().getList("audits", LoanAuditPo.class), JoinType.LEFT);
                            Predicate already = cb.and(
                                    cb.equal(join.get("auditType"), EAuditType.TRIAL),
                                    cb.equal(join.get("temp"), false),
                                    cb.isNotNull(join.get("auditId"))
                            );
                            expressions.add(already);
                            break;
                        case FALLBACK:
                            expressions.add(cb.equal(root.get("applStatus"), EApplStatus.FALLBACK));
                            break;
                        default:
                            break;
                    }
                }

                if (StringUtils.isNotEmpty(search.getTrialUserId())) {
                    expressions.add(cb.equal(root.get("trialUserId"), search.getTrialUserId()));
                }

                if (StringUtils.isNotEmpty(search.getReviewUserId())) {
                    expressions.add(cb.equal(root.get("reviewUserId"), search.getReviewUserId()));
                }

                if(search.getFinanceSourceType() != null && search.getFinanceSourceType() != EFinanceSourceType.ALL){

                    List<FinanceSourceDto> financeSourceDtos = financeSourceService.getAll();
                    Map<EFinanceSourceType, FinanceSourceDto> map = new HashMap<>();
                    financeSourceDtos.forEach(financeSourceDto -> map.put(financeSourceDto.getType(), financeSourceDto));


                    FinanceSourceDto item = map.get(search.getFinanceSourceType());


                    if(search.getFinanceSourceType() == EFinanceSourceType.OWN){
                        if(item != null){
                            expressions.add(cb.or(cb.equal(root.get("financeSourceId"), item.getId()), cb.isNull(root.get("financeSourceId"))));
                        }else{
                            expressions.add(cb.isNull(root.get("financeSourceId")));
                        }
                    }else{
                        if(item != null){
                            expressions.add(cb.equal(root.get("financeSourceId"), item.getId()));
                        }
                    }
                }

                if (search.getTrialStatus() != null && search.getTrialStatus() != EAuditStatus.ALL) {
                    switch (search.getTrialStatus()) {
                        case ADVISE_PASS:
                        case ADVISE_REJECT:
                        case REJECT:
                        case CANCEL:
                            ListJoin<LoanApplPo, LoanAuditPo> join = root.join(root.getModel().getList("audits", LoanAuditPo.class), JoinType.INNER);
                            expressions.add(cb.equal(join.get("auditStatus"), search.getTrialStatus()));
                            expressions.add(cb.equal(join.get("auditType"), EAuditType.TRIAL));
                            expressions.add(cb.equal(join.get("temp"), false));
                            break;
                    }
                }

                if (search.getReviewType() != null) {
                    //已终审等同于，状态走向与终结态，且存在非暂存的终审记录
                    ListJoin<LoanApplPo, LoanAuditPo> join = root.join(root.getModel().getList("audits", LoanAuditPo.class), JoinType.LEFT);
                    Predicate unClaimed  = cb.equal(root.get("applStatus"),(EApplStatus.REVIEW_UNCLAIMED));
                    Predicate wait = cb.equal(root.get("applStatus"),(EApplStatus.REVIEW_CLAIMED));
                    Predicate already = cb.and(
                            cb.equal(join.get("auditType"), EAuditType.REVIEW),
                            cb.equal(join.get("temp"), false),
                            cb.isNotNull(join.get("auditId"))
                    );
                    switch (search.getReviewType()) {
                        case ALL:
                            expressions.add(cb.or(wait, already, unClaimed));
                            break;
                        case UNCLAIMED:
                            expressions.add(unClaimed);
                            break;
                        case WAIT:
                            expressions.add(wait);
                            break;
                        case ALREADY:
                            expressions.add(already);
                            break;
                    }
                }

                if (search.getReviewStatus() != null && search.getReviewStatus() != EAuditStatus.ALL) {
                    switch (search.getReviewStatus()) {
                        case SUCCEED:
                        case REJECT:
                            ListJoin<LoanApplPo, LoanAuditPo> join = root.join(root.getModel().getList("audits", LoanAuditPo.class), JoinType.INNER);
                            expressions.add(cb.equal(join.get("auditStatus"), search.getReviewStatus()));
                            expressions.add(cb.equal(join.get("auditType"), EAuditType.REVIEW));
                            expressions.add(cb.equal(join.get("temp"), false));
                            break;
                    }
                }

                if (search.getSignType() != null) {

                    List<FinanceSourceDto> financeSourceDtos = financeSourceService.getAll();
                    Map<EFinanceSourceType, FinanceSourceDto> map = new HashMap<>();
                    financeSourceDtos.forEach(financeSourceDto -> map.put(financeSourceDto.getType(), financeSourceDto));

                    ListJoin<LoanApplPo, LoanApplOutAuditPo> join = root.join(root.getModel().getList("loanApplOutAuditPos", LoanApplOutAuditPo.class), JoinType.LEFT);

                    Predicate own = cb.and(
                        cb.equal(root.get("applStatus"), EApplStatus.SUCCEED),
                            cb.equal(root.get("financeSourceId"), map.get(EFinanceSourceType.OWN).getId())
                    );

                    Predicate aitouzi = cb.and(
                            cb.equal(root.get("applStatus"), EApplStatus.SUCCEED),
                            cb.equal(join.get("auditType"), EApplOutAuditType.SUCCESS),
                            cb.equal(root.get("financeSourceId"), map.get(EFinanceSourceType.AITOUZI).getId())
                    );

                    switch(search.getFinanceSourceType()){
                        case ALL:
                            expressions.add(cb.or(own, aitouzi));
                            break;
                        case AITOUZI:
                            expressions.add(aitouzi);
                            break;
                        case OWN:
                            expressions.add(own);
                            break;
                    }

                    switch (search.getSignType()) {
                        case WAIT:
                            expressions.add(cb.or(
                                    cb.equal(root.get("signed"), false),
                                    cb.isNull(root.get("signed"))
                            ));
                            break;
                        case ALREADY:
                            expressions.add(cb.equal(root.get("signed"), true));
                            break;
                    }
                }

                if (
                        (search.getTransferStat() != null)
                        || (search.getTransferStartDate() != null || search.getTransferEndDate() != null)
                    ) {
                    Join<LoanApplPo, LoanDtlPo> join = root.join("loanDtlPo", JoinType.LEFT);
                    expressions.add(cb.isNotNull(join.get("loanId")));

                    if(search.getTransferStat() != null){
                        switch (search.getTransferStat()) {
                            case SUCCESS:
                            case FAILED:
                            case PROCESS:
                                expressions.add(cb.equal(join.get("transferStat"), search.getTransferStat()));
                                break;
                            default:
                                break;
                        }
                    }

                    if (StringUtils.isNotEmpty(search.getTransferStartDate())) {
                        expressions.add(cb.greaterThanOrEqualTo(join.get("transferDate"), DateHelper.getStartDate(search.getTransferStartDate())));
                    }
                    if (StringUtils.isNotEmpty(search.getTransferEndDate())) {
                        expressions.add(cb.lessThanOrEqualTo(join.get("transferDate"), DateHelper.getEndDate(search.getTransferEndDate())));
                    }
                }
            }
            return predicate;
        };

        Sort sort = null;

        if (search.getSortType() == null || search.getSortType() == ELoanApplSortType.DESC) {
            sort = new Sort(Sort.Direction.DESC, "createTs");
        } else if (search.getSortType() == ELoanApplSortType.ASC) {
            sort = new Sort(Sort.Direction.ASC, "createTs");
        }

        List<LoanApplPo> list;

        if (search.getPageSize() != null && search.getPageSize().compareTo(0) > 0) {
            Pageable pageable = new PageRequest(search.getCurrentPage() - 1, search.getPageSize(), sort);
            Page<LoanApplPo> page = loanApplDao.findAll(spec, pageable);
            list = page.getContent();
            search.setTotalPages(page.getTotalPages());
            search.setTotalRecord(page.getTotalElements());
        } else {
            list = loanApplDao.findAll(spec, sort);
        }

        List<LoanApplyListEachDto> data = new ArrayList<>();
        for (LoanApplPo po : list) {
            LoanApplyListEachDto each = ConverterService.convert(po, LoanApplyListEachDto.class);

            each.setApplDate(po.getApplDate());
            each.setRateType(po.getIntrRateType());
            each.setRate(po.getLoanRt());
            each.setStatus(po.getApplStatus());
            each.setCreateDate(po.getCreateTs());
            each.setUserId(po.getUserId());
            each.setHangup(po.getHangUp());
            each.setLoanAmt(po.getApprAmt());
            each.setLoanPeriod(po.getApprTermLen());
            each.setFinanceSourceId(po.getFinanceSourceId());

            //产品信息转换
            if (StringUtils.isNotBlank(po.getExtraParams())) {
                LoanProductInfoPo loanProductInfoPo = productInfoDao.findOne(each.getProductId());
                each.setProductName(loanProductInfoPo.getProductName());
                each.setProductType(loanProductInfoPo.getProductType());
            }

            if (StringUtils.isNotBlank(po.getDealerUserId())) {
                each.setDealerType(ELoanApplDealerType.DEALER);
            } else {
                each.setDealerType(ELoanApplDealerType.SELF);
            }

            if(each.getStatus() == EApplStatus.TRIAL_CLAIMED ){
                each.setTrialClaimedType(ELoanApplClaimedType.CLAIMED);
            }else if(each.getStatus() == EApplStatus.TRIAL_UNCLAIMED){
                each.setTrialClaimedType(ELoanApplClaimedType.UNCLAIMED);
            }

            if(po.getSigned() != null && po.getSigned()){
                each.setSignType(ELoanApplSignType.ALREADY);
            }else{
                each.setSignType(ELoanApplSignType.WAIT);
            }

            each.setSignDate(DateHelper.formatDate(po.getSignDate()));

            each.setPeriod(po.getTermLen());
            each.setUnit(po.getTermType().getUnit());

            data.add(each);
        }

        search.setList(data);
        search.setPageSize(search.getPageSize());
        return search;
    }

    private Predicate isEmpty(CriteriaBuilder cb, Expression expression) {
        return cb.or(cb.equal(expression, ""), cb.isNull(expression));
    }

    private Predicate isNotEmpty(CriteriaBuilder cb, Expression expression) {
        return cb.and(cb.notEqual(expression, ""), cb.isNotNull(expression));
    }

}
