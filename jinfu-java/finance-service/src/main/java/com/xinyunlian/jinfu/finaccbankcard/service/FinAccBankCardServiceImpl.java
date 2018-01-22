package com.xinyunlian.jinfu.finaccbankcard.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.finaccbankcard.dao.FinAccBankCardDao;
import com.xinyunlian.jinfu.finaccbankcard.dto.FinAccBankCardDto;
import com.xinyunlian.jinfu.finaccbankcard.dto.FinAccBankCardSearchDto;
import com.xinyunlian.jinfu.finaccbankcard.entity.FinAccBankCardPo;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/21.
 */
@Service
public class FinAccBankCardServiceImpl implements FinAccBankCardService {

    @Autowired
    private FinAccBankCardDao finAccBankCardDao;

    @Override
    public FinAccBankCardDto getFinAccBankCardByExtTxAccId(String extTxAccId) throws BizServiceException {
        FinAccBankCardPo po = finAccBankCardDao.findByExtTxAccId(extTxAccId);
        FinAccBankCardDto dto = ConverterService.convert(po,FinAccBankCardDto.class);
        return dto;
    }

    @Override
    @Transactional
    public FinAccBankCardDto addFinAccBankCard(FinAccBankCardDto dto) {
        FinAccBankCardPo po = ConverterService.convert(dto, FinAccBankCardPo.class);
        FinAccBankCardPo retPo = finAccBankCardDao.save(po);
        dto.setId(retPo.getId());
        return dto;
    }

    @Override
    public FinAccBankCardDto getFinAccBankCardByBankCardNoUserId(String bankCardNo, String userId, EFinOrg finOrg) {

        FinAccBankCardPo po = finAccBankCardDao.findByBankCardNoAndUserIdAndFinOrg(bankCardNo, userId, finOrg);
        FinAccBankCardDto dto = ConverterService.convert(po, FinAccBankCardDto.class);

        return dto;
    }

    @Override
    public List<FinAccBankCardDto> getFinAccBankCardList(FinAccBankCardSearchDto searchDto) throws BizServiceException{

        Specification<FinAccBankCardPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {
                if (!StringUtils.isEmpty(searchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), searchDto.getUserId()));
                }
                if (searchDto.getFinOrg() != null) {
                    expressions.add(cb.equal(root.get("finOrg"), searchDto.getFinOrg()));
                }
            }
            return predicate;
        };

        List<FinAccBankCardPo> list = finAccBankCardDao.findAll(spec);

        List<FinAccBankCardDto> data = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach( po -> {
                FinAccBankCardDto dto = ConverterService.convert(po, FinAccBankCardDto.class);
                data.add(dto);
            });
        }

        return data;
    }

    @Override
    @Transactional
    public void updateFinAccBankCard(FinAccBankCardDto dto) throws BizServiceException {
        FinAccBankCardPo po = finAccBankCardDao.findOne(dto.getId());
        po.setFinOrg(dto.getFinOrg());
        po.setExtTxAccId(dto.getExtTxAccId());
        po.setUserId(dto.getUserId());
        po.setBankCardNo(dto.getBankCardNo());
        po.setIdCardNo(dto.getIdCardNo());
        po.setBankShortName(dto.getBankShortName());
        po.setFinOrg(dto.getFinOrg());
        po.setUserRealName(dto.getUserRealName());
        po.setReserveMobile(dto.getReserveMobile());
    }

    @Override
    @Transactional
    public void deleteFinAccBankCard(String id) throws BizServiceException {
        finAccBankCardDao.delete(id);
    }

    @Override
    public Boolean checkBankCardExist(String bankCardNo) throws BizServiceException {

        List<FinAccBankCardPo> bankCardList = finAccBankCardDao.findByBankCardNo(bankCardNo);
        if (!CollectionUtils.isEmpty(bankCardList)){
            return true;
        }

        return false;
    }

}
