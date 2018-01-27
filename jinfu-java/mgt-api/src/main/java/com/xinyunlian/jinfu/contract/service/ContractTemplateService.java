package com.xinyunlian.jinfu.contract.service;

import com.xinyunlian.jinfu.contract.dto.ContractTemplateDto;

/**
 * Created by JL on 2016/9/20.
 */
public interface ContractTemplateService {


    /**
     * 根据模版编号获得合同模版
     *
     * @param tmpltId
     * @return
     */
    ContractTemplateDto getTemplate(Long tmpltId);

    /**
     * 保存合同模版
     *
     * @param contractTemplateDto
     */
    void save(ContractTemplateDto contractTemplateDto);


}
