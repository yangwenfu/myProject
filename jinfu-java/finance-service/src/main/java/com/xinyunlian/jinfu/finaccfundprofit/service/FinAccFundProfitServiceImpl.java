package com.xinyunlian.jinfu.finaccfundprofit.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import com.xinyunlian.jinfu.finaccfundprofit.dao.FinAccFundProfitDao;
import com.xinyunlian.jinfu.finaccfundprofit.dto.FinAccFundProfitDto;
import com.xinyunlian.jinfu.finaccfundprofit.entity.FinAccFundProfitPo;
import com.xinyunlian.jinfu.finprofithistory.dao.FinProfitHistoryDao;
import com.xinyunlian.jinfu.finprofithistory.entity.FinProfitHistoryPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/23.
 */
@Service
public class FinAccFundProfitServiceImpl implements FinAccFundProfitService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinAccFundProfitServiceImpl.class);

    @Autowired
    private FinAccFundProfitDao finAccFundProfitDao;

    @Autowired
    private FinProfitHistoryDao finProfitHistoryDao;

    @Override
    public List<FinAccFundProfitDto> getFinAccProdProfits(FinAccFundProfitDto searchDto) {

        Specification<FinAccFundProfitPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (!StringUtils.isEmpty(searchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), searchDto.getUserId()));
                }
                if (!StringUtils.isEmpty(searchDto.getExtTxAccId())){
                    expressions.add(cb.equal(root.get("extTxAccId"), searchDto.getExtTxAccId()));
                }
                if (searchDto.getFinFundId() != null){
                    expressions.add(cb.equal(root.get("finFundId"), searchDto.getFinFundId()));
                }
                if (searchDto.getFinOrg() != null){
                    expressions.add(cb.equal(root.get("finOrg"), searchDto.getFinOrg()));
                }
            }
            return predicate;
        };

        List<FinAccFundProfitPo> list = finAccFundProfitDao.findAll(spec);
        List<FinAccFundProfitDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                FinAccFundProfitDto dto = ConverterService.convert(po, FinAccFundProfitDto.class);
                result.add(dto);
            });
        }

        return result;
    }

    @Override
    @Transactional
    public void updateFinAccProdProfit(FinAccFundProfitDto dto) {
        FinAccFundProfitPo po =
                finAccFundProfitDao.findByUindex(dto.getUserId(), dto.getExtTxAccId(), dto.getFinFundId(), dto.getFinOrg().getCode());
        FinProfitHistoryPo historyPo =
                finProfitHistoryDao.findByLatest(dto.getUserId(), dto.getFinOrg().getCode(), dto.getExtTxAccId(), dto.getFinFundId());

        BigDecimal holdAsset = new BigDecimal("0");
        BigDecimal totalProfit = new BigDecimal("0");
        if (historyPo != null){
            holdAsset = historyPo.getAssetAmt();
            totalProfit = historyPo.getTotalProfit();
        }

        if (po != null){
            po.setHoldAsset(holdAsset);
            po.setTotalProfit(totalProfit);
        }else {
            po = new FinAccFundProfitPo();
            po.setUserId(dto.getUserId());
            po.setExtTxAccId(dto.getExtTxAccId());
            po.setFinFundId(dto.getFinFundId());
            po.setFinOrg(dto.getFinOrg());
            po.setHoldAsset(holdAsset);
            po.setTotalProfit(totalProfit);
            finAccFundProfitDao.save(po);
        }
    }

    @Override
    public FinAccFundProfitDto getFinAccHoldAsset(String extTxAccId) throws BizServiceException{
        FinAccFundProfitPo po = null;
        try {
            po = finAccFundProfitDao.findByFinOrgAndExtTxAccId(EFinOrg.ZRFUNDS, extTxAccId);
            if (po == null){
                throw new BizServiceException(EErrorCode.FIN_ACC_NOT_EXISTS);
            }
            FinAccFundProfitDto dto = ConverterService.convert(po, FinAccFundProfitDto.class);
            return dto;
        } catch (Exception e) {
            LOGGER.error("查询理财账号资产失败");
            throw e;
        }
    }
}
