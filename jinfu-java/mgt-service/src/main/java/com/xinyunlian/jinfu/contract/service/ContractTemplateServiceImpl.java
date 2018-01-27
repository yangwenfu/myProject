package com.xinyunlian.jinfu.contract.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.contract.dao.ContractTemplateDao;
import com.xinyunlian.jinfu.contract.dto.ContractTemplateDto;
import com.xinyunlian.jinfu.contract.entity.ContractTemplatePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by JL on 2016/9/20.
 */
@Service
public class ContractTemplateServiceImpl implements ContractTemplateService {

    @Autowired
    private ContractTemplateDao contractTemplateDao;

    @Override
    public ContractTemplateDto getTemplate(Long tmpltId) {
        ContractTemplatePo contractTemplatePo = contractTemplateDao.findOne(tmpltId);
        return ConverterService.convert(contractTemplatePo, ContractTemplateDto.class);
    }

    @Override
    @Transactional
    public void save(ContractTemplateDto contractTemplateDto) {
        if (contractTemplateDto.getTemplateId() == null) {
            ContractTemplatePo contractTemplatePo = ConverterService.convert(contractTemplateDto, ContractTemplatePo.class);
            contractTemplateDao.save(contractTemplatePo);
        } else {
            ContractTemplatePo contractTemplatePo = contractTemplateDao.findOne(contractTemplateDto.getTemplateId());
            ConverterService.convert(contractTemplateDto, contractTemplatePo);
            contractTemplateDao.save(contractTemplatePo);
        }

    }
}
