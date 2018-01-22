package com.xinyunlian.jinfu.crm.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.crm.dao.CrmCallDao;
import com.xinyunlian.jinfu.crm.dao.CrmCallNoteDao;
import com.xinyunlian.jinfu.crm.dao.CrmCallTypeDao;
import com.xinyunlian.jinfu.crm.dto.CallSearchDto;
import com.xinyunlian.jinfu.crm.dto.CrmCallDto;
import com.xinyunlian.jinfu.crm.dto.CrmCallNoteDto;
import com.xinyunlian.jinfu.crm.entity.CrmCallNotePo;
import com.xinyunlian.jinfu.crm.entity.CrmCallPo;
import com.xinyunlian.jinfu.crm.entity.CrmCallTypePo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户通话记录ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class CrmCallServiceImpl implements CrmCallService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrmCallServiceImpl.class);

	@Autowired
	private CrmCallDao crmCallDao;
	@Autowired
	private CrmCallNoteDao callNoteDao;
	@Autowired
	private CrmCallTypeDao crmCallTypeDao;

	@Override
	@Transactional
	public CrmCallDto save(CrmCallDto crmCallDto){
		CrmCallPo crmCallPo = new CrmCallPo();
		if(crmCallDto.getCallId() != null){
			crmCallPo = crmCallDao.findOne(crmCallDto.getCallId());
		}
		ConverterService.convert(crmCallDto,crmCallPo);
		crmCallDao.save(crmCallPo);
		if(!CollectionUtils.isEmpty(crmCallDto.getCallNoteDtos())){
			for (CrmCallNoteDto callNoteDto: crmCallDto.getCallNoteDtos()){
				if(null == callNoteDto.getCallNoteId()) {
					callNoteDto.setCallId(crmCallPo.getCallId());
					CrmCallNotePo callNotePo = ConverterService.convert(callNoteDto, CrmCallNotePo.class);
					callNoteDao.save(callNotePo);
				}
			}
		}
		crmCallDto.setCallId(crmCallPo.getCallId());
		return crmCallDto;
	}

	@Override
	public CrmCallDto findByCallId(Long callId){
		CrmCallPo crmCallPo = crmCallDao.findOne(callId);
		CrmCallDto crmCallDto = ConverterService.convert(crmCallPo,CrmCallDto.class);
		return crmCallDto;
	}

	@Override
	@Transactional(readOnly = true)
	public CallSearchDto getCallPage(CallSearchDto callSearchDto) {
		Specification<CrmCallPo> spec = (Root<CrmCallPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			Predicate predicate = cb.conjunction();
			List<Expression<Boolean>> expressions = predicate.getExpressions();
			if (null != callSearchDto) {
				if (!StringUtils.isEmpty(callSearchDto.getUserName())) {
					expressions.add(cb.like(root.get("userName"), BizUtil.filterString(callSearchDto.getUserName())));
				}
				if (!StringUtils.isEmpty(callSearchDto.getMobile())) {
					expressions.add(cb.like(root.get("mobile"), BizUtil.filterStringRight(callSearchDto.getMobile())));
				}
				if (!StringUtils.isEmpty(callSearchDto.getArea())) {
					expressions.add(cb.equal(root.get("area"), callSearchDto.getArea()));
				}
				if (!StringUtils.isEmpty(callSearchDto.getCity())) {
					expressions.add(cb.equal(root.get("city"), callSearchDto.getCity()));
				}
				if (!StringUtils.isEmpty(callSearchDto.getProvince())) {
					expressions.add(cb.equal(root.get("province"), callSearchDto.getProvince()));
				}
				if (!StringUtils.isEmpty(callSearchDto.getTobaccoCertificateNo())) {
					expressions.add(cb.like(root.get("tobaccoCertificateNo"), BizUtil.filterString(callSearchDto.getTobaccoCertificateNo())));
				}
				if (!StringUtils.isEmpty(callSearchDto.getCreateStartDate())) {
					expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(callSearchDto.getCreateStartDate())));
				}
				if (!StringUtils.isEmpty(callSearchDto.getCreateEndDate())) {
					expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(callSearchDto.getCreateEndDate())));
				}
				if (null != callSearchDto.getCallTypeId()) {
					expressions.add(cb.equal(root.get("callTypeId"), callSearchDto.getCallTypeId()));
				}
				if (null != callSearchDto.getCallTypeFirstId()) {
					expressions.add(cb.equal(root.get("callTypeFirstId"), callSearchDto.getCallTypeFirstId()));
				}
				if (null != callSearchDto.getCallTypeSecondId()) {
					expressions.add(cb.equal(root.get("callTypeSecondId"), callSearchDto.getCallTypeSecondId()));
				}
				if (null != callSearchDto.getStatus()) {
					expressions.add(cb.equal(root.get("status"), callSearchDto.getStatus()));
				}
				if (null != callSearchDto.getQaStatus()) {
					expressions.add(cb.equal(root.get("qaStatus"), callSearchDto.getQaStatus()));
				}
				if (!StringUtils.isEmpty(callSearchDto.getDealPerson())) {
					expressions.add(cb.equal(root.get("dealPerson"), callSearchDto.getDealPerson()));
				}

			}
			return predicate;
		};

		Pageable pageable = new PageRequest(callSearchDto.getCurrentPage() - 1,
				callSearchDto.getPageSize(), Direction.DESC, "callId");
		Page<CrmCallPo> page = crmCallDao.findAll(spec, pageable);

		List<CrmCallDto> data = new ArrayList<>();
		for (CrmCallPo po : page.getContent()) {
			CrmCallDto crmCallDto = ConverterService.convert(po, CrmCallDto.class);
			CrmCallTypePo callTypePo = crmCallTypeDao.findOne(crmCallDto.getCallTypeId());
			if(null != callTypePo) {
				crmCallDto.setCallTypeName(callTypePo.getCallTypeName());
			}
			data.add(crmCallDto);
		}
		callSearchDto.setList(data);
		callSearchDto.setTotalPages(page.getTotalPages());
		callSearchDto.setTotalRecord(page.getTotalElements());
		return callSearchDto;
	}

	@Override
	public void addNote(CrmCallNoteDto callNoteDto) {
		CrmCallNotePo po = ConverterService.convert(callNoteDto, CrmCallNotePo.class);
		callNoteDao.save(po);
	}

	@Override
	public List<CrmCallNoteDto> listNotes(Long callId){
		List<CrmCallNotePo> callNotePos = callNoteDao.findByCallId(callId);
		List<CrmCallNoteDto> callNoteDtos = new ArrayList<>();
		if(!CollectionUtils.isEmpty(callNotePos)){
			callNotePos.forEach(crmCallNotePo -> {
				CrmCallNoteDto callNoteDto = ConverterService.convert(crmCallNotePo,CrmCallNoteDto.class);
				callNoteDtos.add(callNoteDto);
			});
		}
		return callNoteDtos;
	}

	@Override
	public  Long countByCallTypeIdIn(List<Long> callTypeIds) {
		return crmCallDao.countByCallTypeIdIn(callTypeIds);
	}
	
}
