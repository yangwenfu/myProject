package com.xinyunlian.jinfu.finbank.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.finbank.dao.FinBankDao;
import com.xinyunlian.jinfu.finbank.dto.FinBankDto;
import com.xinyunlian.jinfu.finbank.dto.FinBankSearchDto;
import com.xinyunlian.jinfu.finbank.entity.FinBankPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/24/0024.
 */
@Service
public class FinBankServiceImpl implements FinBankService {

    @Autowired
    private FinBankDao finBankDao;

    @Value("${file.addr}")
    private String fileAddr;

    @Override
    public List<FinBankDto> getFinBankList(FinBankSearchDto searchDto) throws BizServiceException {

        Specification<FinBankPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (!StringUtils.isEmpty(searchDto.getBankCode())) {
                    expressions.add(cb.equal(root.get("bankCode"), searchDto.getBankCode()));
                }
                if (!StringUtils.isEmpty(searchDto.getBankName())) {
                    expressions.add(cb.equal(root.get("bankName"), searchDto.getBankName()));
                }
                if (!CollectionUtils.isEmpty(searchDto.getBankShortNames())){
                    expressions.add(cb.in(root.get("bankShortName")).value(searchDto.getBankShortNames()));
                }
            }
            return predicate;
        };
        List<FinBankPo> list = finBankDao.findAll(spec);
        List<FinBankDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                FinBankDto dto = ConverterService.convert(po, FinBankDto.class);
                dto.setBankLogo(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getBankLogo()));
                result.add(dto);
            });
        }

        return result;
    }
}
