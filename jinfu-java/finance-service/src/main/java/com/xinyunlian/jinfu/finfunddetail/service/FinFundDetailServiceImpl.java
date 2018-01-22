package com.xinyunlian.jinfu.finfunddetail.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.finfunddetail.dao.FinFundDetailDao;
import com.xinyunlian.jinfu.finfunddetail.dto.FinFundDetailDto;
import com.xinyunlian.jinfu.finfunddetail.entity.FinFundDetailPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/21.
 */
@Service
public class FinFundDetailServiceImpl implements FinFundDetailService {

    @Autowired
    private FinFundDetailDao finFundDetailDao;

    @Override
    public List<FinFundDetailDto> getFinFundDetailList(FinFundDetailDto searchDto) {

        Specification<FinFundDetailPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (!StringUtils.isEmpty(searchDto.getFinFundId())) {
                    expressions.add(cb.equal(root.get("finFundId"), searchDto.getFinFundId()));
                }
            }
            return predicate;
        };

        List<FinFundDetailPo> list = finFundDetailDao.findAll(spec);
        List<FinFundDetailDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                FinFundDetailDto dto = ConverterService.convert(po, FinFundDetailDto.class);
                result.add(dto);
            });
        }

        return result;
    }

    @Override
    public FinFundDetailDto getFinFundDetailById(Long fundId) {
        FinFundDetailPo po = finFundDetailDao.findOne(fundId);
        FinFundDetailDto dto = ConverterService.convert(po, FinFundDetailDto.class);
        return dto;
    }

    @Override
    @Transactional
    public void updateFinFundDetail(FinFundDetailDto dto) {
        FinFundDetailPo po = finFundDetailDao.findByFinFundCodeAndFinOrg(dto.getFinFundCode(), dto.getFinOrg());
        if (po == null){
            po = ConverterService.convert(dto, FinFundDetailPo.class);
            finFundDetailDao.save(po);
        }else {
            po.setYield(dto.getYield());
            po.setFoundIncome(dto.getFoundIncome());
            po.setUpdateDate(dto.getUpdateDate());
        }
    }


}
