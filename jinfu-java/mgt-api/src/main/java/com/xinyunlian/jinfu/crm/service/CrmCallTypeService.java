package com.xinyunlian.jinfu.crm.service;

import com.xinyunlian.jinfu.crm.dto.CrmCallTypeDto;

import java.util.List;

/**
 * 客户通话类型Service
 * @author jll
 * @version 
 */

public interface CrmCallTypeService {

    List<CrmCallTypeDto> getCallTypeList(CrmCallTypeDto callTypeDto);

    void save(CrmCallTypeDto crmCallTypeDto);

    void update(List<CrmCallTypeDto> callTypeDtos);

    void delete(Long callTypeId);

    CrmCallTypeDto findByCallTypeId(Long callTypeId);

}
