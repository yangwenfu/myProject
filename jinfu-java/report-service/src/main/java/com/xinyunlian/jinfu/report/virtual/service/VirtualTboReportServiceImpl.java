package com.xinyunlian.jinfu.report.virtual.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.report.virtual.dao.VirtualTobaccoDao;
import com.xinyunlian.jinfu.report.virtual.dto.VirtualTboSearchDto;
import com.xinyunlian.jinfu.report.virtual.entity.VirtualTboSearchPo;
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

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 虚拟烟草证ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class VirtualTboReportServiceImpl implements VirtualTboReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VirtualTboReportServiceImpl.class);

	@Autowired
	private VirtualTobaccoDao virtualTobaccoDao;

	@Override
	public VirtualTboSearchDto getPage(VirtualTboSearchDto searchDto) {
		Specification<VirtualTboSearchPo> spec = (Root<VirtualTboSearchPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			Predicate predicate = cb.conjunction();
			List<Expression<Boolean>> expressions = predicate.getExpressions();

			if (null != searchDto) {
				if (!StringUtils.isEmpty(searchDto.getUserName())) {
					expressions.add(cb.like(root.get("userName"), BizUtil.filterString(searchDto.getUserName())));
				}
				if (!StringUtils.isEmpty(searchDto.getMobile())) {
					expressions.add(cb.like(root.get("mobile"), BizUtil.filterString(searchDto.getMobile())));
				}
				if (!StringUtils.isEmpty(searchDto.getAssignPerson())) {
					expressions.add(cb.like(root.get("assignPerson"), BizUtil.filterString(searchDto.getAssignPerson())));
				}
				if (!StringUtils.isEmpty(searchDto.getArea())) {
					expressions.add(cb.equal(root.get("area"), searchDto.getArea()));
				}
				if (!StringUtils.isEmpty(searchDto.getCity())) {
					expressions.add(cb.equal(root.get("city"), searchDto.getCity()));
				}
				if (!StringUtils.isEmpty(searchDto.getProvince())) {
					expressions.add(cb.equal(root.get("province"), searchDto.getProvince()));
				}
				if (!StringUtils.isEmpty(searchDto.getStreet())) {
					expressions.add(cb.equal(root.get("street"), searchDto.getStreet()));
				}

				if (!StringUtils.isEmpty(searchDto.getTobaccoCertificateNo())) {
					expressions.add(cb.like(root.get("tobaccoCertificateNo"), BizUtil.filterString(searchDto.getTobaccoCertificateNo())));
				}

				if (!StringUtils.isEmpty(searchDto.getTakeStartTime())) {
					expressions.add(cb.greaterThanOrEqualTo(root.get("takeTime"), DateHelper.getStartDate(searchDto.getTakeStartTime())));
				}
				if (!StringUtils.isEmpty(searchDto.getTakeEndTime())) {
					expressions.add(cb.lessThanOrEqualTo(root.get("takeTime"), DateHelper.getEndDate(searchDto.getTakeEndTime())));
				}

			}
			return predicate;
		};

		Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1,
				searchDto.getPageSize(),Direction.DESC,"takeTime");
		Page<VirtualTboSearchPo> page = virtualTobaccoDao.findAll(spec, pageable);

		List<VirtualTboSearchDto> data = new ArrayList<>();
		for (VirtualTboSearchPo po : page.getContent()) {
			VirtualTboSearchDto virtualTboSearchDto = ConverterService.convert(po, VirtualTboSearchDto.class);
			data.add(virtualTboSearchDto);
		}
		searchDto.setList(data);
		searchDto.setTotalPages(page.getTotalPages());
		searchDto.setTotalRecord(page.getTotalElements());
		return searchDto;
	}

}
