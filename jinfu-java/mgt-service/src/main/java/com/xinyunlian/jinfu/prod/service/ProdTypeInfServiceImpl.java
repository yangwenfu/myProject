package com.xinyunlian.jinfu.prod.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.prod.dao.ProdTypeInfDao;
import com.xinyunlian.jinfu.prod.dto.ProdTypeInfDto;
import com.xinyunlian.jinfu.prod.dto.ProdTypeInfSerachDto;
import com.xinyunlian.jinfu.prod.entity.ProdTypeInfPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DongFC on 2016-09-18.
 */
@Service
public class ProdTypeInfServiceImpl implements ProdTypeInfService {

    @Autowired
    private ProdTypeInfDao prodTypeInfDao;

    @Override
    public List<ProdTypeInfDto> getProdTypeList(ProdTypeInfSerachDto prodTypeInfSerachDto) {

        Specification<ProdTypeInfPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (prodTypeInfSerachDto != null){
                if (null != prodTypeInfSerachDto.getParent()) {
                    expressions.add(cb.equal(root.get("parent"), prodTypeInfSerachDto.getParent()));
                }else {
                    expressions.add(cb.isNull(root.get("parent")));
                }
            }else{
                expressions.add(cb.isNull(root.get("parent")));
            }

            return predicate;
        };

        List<ProdTypeInfPo> list = prodTypeInfDao.findAll(spec);
        List<ProdTypeInfDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                ProdTypeInfDto dto = ConverterService.convert(po, ProdTypeInfDto.class);
                result.add(dto);
            });
        }

        return result;

    }

    @Transactional
    @Override
    public boolean updateProdTypeInf(ProdTypeInfDto prodTypeInfDto) throws BizServiceException{

        ProdTypeInfPo prodTypeInfPo = prodTypeInfDao.findOne(prodTypeInfDto.getProdTypeId());
        if (!prodTypeInfPo.getProdTypeCode().equals(prodTypeInfDto.getProdTypeCode())){
            ProdTypeInfPo existsPo = prodTypeInfDao.findByProdTypeCode(prodTypeInfDto.getProdTypeCode());
            if (existsPo != null){
                return false;
            }
            prodTypeInfPo.setProdTypeCode(prodTypeInfDto.getProdTypeCode());
        }
        prodTypeInfPo.setProdTypeName(prodTypeInfDto.getProdTypeName());
        prodTypeInfPo.setProdTypeAlias(prodTypeInfDto.getProdTypeAlias());

        return true;
    }

    @Transactional
    @Override
    public boolean deleteProdTypeInf(Long prodTypeId) {
        boolean canRemove = false;

        Specification<ProdTypeInfPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.equal(root.get("parent"), prodTypeId));
            return predicate;
        };
        long count = prodTypeInfDao.count(spec);

        if (count == 0){
            canRemove = true;
            prodTypeInfDao.delete(prodTypeId);
        }

        return canRemove;
    }

    @Transactional
    @Override
    public boolean saveProdTypeInf(ProdTypeInfDto prodTypeInfDto) throws BizServiceException {

        ProdTypeInfPo existsPo = prodTypeInfDao.findByProdTypeCode(prodTypeInfDto.getProdTypeCode());
        if (existsPo != null){
            return false;
        }

        ProdTypeInfPo prodTypeInfPo = ConverterService.convert(prodTypeInfDto, ProdTypeInfPo.class);
        prodTypeInfDao.save(prodTypeInfPo);
        String prodTypePath = null;
        if (prodTypeInfDto.getParent() == null){
            prodTypePath = "," + prodTypeInfPo.getProdTypeId() + ",";
        }else{
            ProdTypeInfPo parent = prodTypeInfDao.findOne(prodTypeInfDto.getParent());
            prodTypeInfDao.save(prodTypeInfPo);
            prodTypePath = parent.getProdTypePath() + prodTypeInfPo.getProdTypeId() + ",";
        }
        prodTypeInfPo.setProdTypePath(prodTypePath);

        return true;
    }

    @Override
    public ProdTypeInfDto getProdTypeById(Long prodTypeId) {
        ProdTypeInfPo prodTypeInfPo = prodTypeInfDao.findOne(prodTypeId);
        return ConverterService.convert(prodTypeInfPo, ProdTypeInfDto.class);
    }

    @Override
    public ProdTypeInfDto getProdTypeByCode(String prodTypeCode) {
        ProdTypeInfPo prodTypeInfPo = prodTypeInfDao.findByProdTypeCode(prodTypeCode);
        return ConverterService.convert(prodTypeInfPo, ProdTypeInfDto.class);
    }
}
