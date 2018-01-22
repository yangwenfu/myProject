package com.xinyunlian.jinfu.qrcode.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.qrcode.dao.ProdInfDao;
import com.xinyunlian.jinfu.qrcode.dto.ProdInfDto;
import com.xinyunlian.jinfu.qrcode.dto.ProdInfSearchDto;
import com.xinyunlian.jinfu.qrcode.entity.ProdInfPo;
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
import java.util.List;

/**
 * Created by menglei on 2017年03月08日.
 */
@Service
public class ProdInfServiceImpl implements ProdInfService {

    @Autowired
    private ProdInfDao prodInfDao;

    @Override
    @Transactional(readOnly = true)
    public ProdInfSearchDto getProdInfPage(ProdInfSearchDto prodInfSearchDto) {

        Specification<ProdInfPo> spec = (Root<ProdInfPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != prodInfSearchDto) {
                if (StringUtils.isNotEmpty(prodInfSearchDto.getName())) {
                    expressions.add(cb.like(root.get("name"), "%" + prodInfSearchDto.getName() + "%"));
                }
                if (StringUtils.isNotEmpty(prodInfSearchDto.getSku())) {
                    expressions.add(cb.like(root.get("sku"), "%" + prodInfSearchDto.getSku() + "%"));
                }
                if (StringUtils.isNotEmpty(prodInfSearchDto.getType())) {
                    expressions.add(cb.like(root.get("type"), "%" + prodInfSearchDto.getType() + "%"));
                }
                if (StringUtils.isNotEmpty(prodInfSearchDto.getName())) {
                    expressions.add(cb.like(root.get("name"), "%" + prodInfSearchDto.getName() + "%"));
                }
                if (!StringUtils.isEmpty(prodInfSearchDto.getStartCreateTs())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(prodInfSearchDto.getStartCreateTs())));
                }
                if (!StringUtils.isEmpty(prodInfSearchDto.getEndCreateTs())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(prodInfSearchDto.getEndCreateTs())));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(prodInfSearchDto.getCurrentPage() - 1, prodInfSearchDto.getPageSize(), Sort.Direction.DESC, "prodId");
        Page<ProdInfPo> page = prodInfDao.findAll(spec, pageable);

        List<ProdInfDto> data = new ArrayList<>();
        for (ProdInfPo po : page.getContent()) {
            ProdInfDto prodInfDto = ConverterService.convert(po, ProdInfDto.class);
            data.add(prodInfDto);
        }

        prodInfSearchDto.setList(data);
        prodInfSearchDto.setTotalPages(page.getTotalPages());
        prodInfSearchDto.setTotalRecord(page.getTotalElements());

        return prodInfSearchDto;
    }

    @Transactional
    @Override
    public ProdInfDto getOne(Long prodId) {
        ProdInfPo po = prodInfDao.findOne(prodId);
        if (po == null) {
            return null;
        }
        ProdInfDto dto = ConverterService.convert(po, ProdInfDto.class);
        return dto;
    }

    @Transactional
    @Override
    public ProdInfDto getBySku(String sku) {
        ProdInfPo po = prodInfDao.findBySku(sku);
        if (po == null) {
            return null;
        }
        ProdInfDto dto = ConverterService.convert(po, ProdInfDto.class);
        return dto;
    }

    @Transactional
    @Override
    public List<ProdInfDto> getAll() {
        List<ProdInfPo> poList = prodInfDao.findAll();
        List<ProdInfDto> list = new ArrayList<>();
        for (ProdInfPo po : poList) {
            ProdInfDto dto = ConverterService.convert(po, ProdInfDto.class);
            list.add(dto);
        }
        return list;
    }

    @Transactional
    @Override
    public void addProdInf(ProdInfDto prodInfDto) throws BizServiceException {
        if (prodInfDto != null) {
            ProdInfPo prodInfPo = ConverterService.convert(prodInfDto, ProdInfPo.class);
            prodInfDao.save(prodInfPo);
        }
    }

    @Transactional
    @Override
    public void updateProdInf(ProdInfDto prodInfDto) throws BizServiceException {
        if (prodInfDto != null) {
            ProdInfPo prodInfPo = prodInfDao.findOne(prodInfDto.getProdId());
            if (prodInfPo != null) {
                prodInfPo.setName(prodInfDto.getName());
                prodInfPo.setType(prodInfDto.getType());
                prodInfPo.setDetailText(prodInfDto.getDetailText());
                prodInfDao.save(prodInfPo);
            }
        }
    }

}
