package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.appl.enums.*;
import com.xinyunlian.jinfu.audit.entity.LoanAuditPo;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.loan.LoanApplExtDto;
import com.xinyunlian.jinfu.loan.dao.LoanApplExtDao;
import com.xinyunlian.jinfu.loan.dao.LoanApplUserDao;
import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyListEachDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplExtPo;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.entity.LoanApplUserPo;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;

/**
 * @author willwang
 */
@Service
public class LoanApplExtServiceImpl implements LoanApplExtService {

    @Autowired
    private LoanApplExtDao loanApplExtDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApplUserService.class);

    @Override
    public LoanApplExtDto findByApplId(String applId) {
        LoanApplExtPo po = loanApplExtDao.findByApplId(applId);
        return ConverterService.convert(po, LoanApplExtDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanApplExtDto> listAll(Integer currentPage) {
        Sort sort = new Sort(Sort.Direction.DESC, "applId");
        Pageable pageable = new PageRequest(currentPage - 1, 50, sort);
        Page<LoanApplExtPo> page = loanApplExtDao.findAll(pageable);
        List<LoanApplExtDto> data = new ArrayList<>();
        for (LoanApplExtPo po : page.getContent()) {
            LoanApplExtDto each = ConverterService.convert(po, LoanApplExtDto.class);
            data.add(each);
        }

        return data;
    }
}
