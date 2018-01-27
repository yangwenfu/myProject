package com.xinyunlian.jinfu.crm.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.crm.dao.CrmCallTypeDao;
import com.xinyunlian.jinfu.crm.dto.CrmCallTypeDto;
import com.xinyunlian.jinfu.crm.entity.CrmCallTypePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户通话类型ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class CrmCallTypeServiceImpl implements CrmCallTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrmCallTypeServiceImpl.class);

	@Autowired
	private CrmCallTypeDao crmCallTypeDao;

	@Override
	public List<CrmCallTypeDto> getCallTypeList(CrmCallTypeDto callTypeDto) {
		Specification<CrmCallTypePo> spec = (root, query, cb) -> {
			Predicate predicate = cb.conjunction();
			List<Expression<Boolean>> expressions = predicate.getExpressions();
			if (null != callTypeDto) {
				if (null != callTypeDto.getParent()) {
					expressions.add(cb.equal(root.get("parent"), callTypeDto.getParent()));
				}else if(null != callTypeDto.getParents()){
					expressions.add(cb.in(root.get("parent")).value(callTypeDto.getParents()));
				}else {
					expressions.add(cb.isNull(root.get("parent")));
				}

				if(null != callTypeDto.getDisplay()){
					expressions.add(cb.equal(root.get("display"),callTypeDto.getDisplay()));
				}
			}

			return predicate;
		};

		List<CrmCallTypePo> list = crmCallTypeDao.findAll(spec,new Sort(Sort.Direction.ASC, "orders"));
		List<CrmCallTypeDto> result = new ArrayList<>();
		if (!CollectionUtils.isEmpty(list)){
			list.forEach(po -> {
				CrmCallTypeDto dto = ConverterService.convert(po, CrmCallTypeDto.class);
				result.add(dto);
			});
		}

		return result;
	}

	@Transactional
	@Override
	public void save(CrmCallTypeDto crmCallTypeDto) {
		if (crmCallTypeDto != null){
			CrmCallTypePo po = ConverterService.convert(crmCallTypeDto, CrmCallTypePo.class);
			if (po.getParent() != null) {
				CrmCallTypePo parent = crmCallTypeDao.findOne(po.getParent());
				String treePath = parent.getCallTypePath() + po.getParent() + ",";
				po.setCallTypePath(treePath);
			}else {// create province
				po.setCallTypePath(",");
			}
			crmCallTypeDao.save(po);
		}
	}

	@Transactional
	@Override
	public void update(List<CrmCallTypeDto> callTypeDtos) {
		if (!CollectionUtils.isEmpty(callTypeDtos)){
			callTypeDtos.forEach(dto -> {
				CrmCallTypePo po = crmCallTypeDao.findOne(dto.getCallTypeId());
				po.setCallTypeName(dto.getCallTypeName());
				po.setOrders(dto.getOrders());
				po.setDisplay(dto.getDisplay());
			});
		}
	}

	@Override
	@Transactional
	public void delete(Long callTypeId) {
		if (callTypeId != null){
			crmCallTypeDao.delete(callTypeId);
			crmCallTypeDao.deleteByParent(callTypeId);
		}
	}

	@Override
	public CrmCallTypeDto findByCallTypeId(Long callTypeId){
		if(null == callTypeId){
			return null;
		}
		CrmCallTypePo crmCallTypePo = crmCallTypeDao.findOne(callTypeId);
		CrmCallTypeDto callTypeDto = ConverterService.convert(crmCallTypePo,CrmCallTypeDto.class);
		return callTypeDto;
	}
	
}
