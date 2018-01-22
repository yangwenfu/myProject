package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.dealer.dao.DealerGroupDao;
import com.xinyunlian.jinfu.dealer.dto.DealerGroupDto;
import com.xinyunlian.jinfu.dealer.dto.DealerGroupSearchDto;
import com.xinyunlian.jinfu.dealer.entity.DealerGroupPo;
import org.apache.commons.lang.StringUtils;
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
 * Created by menglei on 2016年08月26日.
 */
@Service
public class DealerGroupServiceImpl implements DealerGroupService {

    @Autowired
    private DealerGroupDao dealerGroupDao;

    @Override
    public List<DealerGroupDto> getGroupList(DealerGroupSearchDto dealerGroupSearchDto) {
        Specification<DealerGroupPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerGroupSearchDto) {
                if (StringUtils.isNotEmpty(dealerGroupSearchDto.getParent())) {
                    expressions.add(cb.equal(root.get("parent"), dealerGroupSearchDto.getParent()));
                } else {
                    expressions.add(cb.isNull(root.get("parent")));
                }
                if (StringUtils.isNotEmpty(dealerGroupSearchDto.getName())) {
                    expressions.add(cb.like(root.get("name"), BizUtil.filterString(dealerGroupSearchDto.getName())));
                }
                if (StringUtils.isNotEmpty(dealerGroupSearchDto.getDealerId())) {
                    expressions.add(cb.equal(root.get("dealerId"), dealerGroupSearchDto.getDealerId()));
                }
            }
            return predicate;
        };
        List<DealerGroupPo> list = dealerGroupDao.findAll(spec);
        List<DealerGroupDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(po -> {
                DealerGroupDto dto = ConverterService.convert(po, DealerGroupDto.class);
                result.add(dto);
            });
        }
        return result;
    }

    @Transactional
    @Override
    public void createDealerGroup(DealerGroupDto dealerGroupDto) {
        if (dealerGroupDto != null) {
            DealerGroupPo po = ConverterService.convert(dealerGroupDto, DealerGroupPo.class);
            if (StringUtils.isNotEmpty(po.getParent())) {
                DealerGroupPo parent = dealerGroupDao.findOne(po.getParent());
                String treePath = parent.getTreePath() + po.getParent() + ",";
                po.setTreePath(treePath);
            } else {
                po.setTreePath(",");
            }
            dealerGroupDao.save(po);
        }
    }

    @Transactional
    @Override
    public void updateDealerGroup(DealerGroupDto dealerGroupDto) {
        if (dealerGroupDto != null) {
            DealerGroupPo dealerGroupPo = dealerGroupDao.findOne(dealerGroupDto.getGroupId() + StringUtils.EMPTY);
            if (dealerGroupPo != null) {
                dealerGroupPo.setName(dealerGroupDto.getName());
                dealerGroupDao.save(dealerGroupPo);
            }
        }
    }

    @Transactional
    @Override
    public void deleteDealerGroup(String groupId) {
        DealerGroupPo dealerGroupPo = dealerGroupDao.findOne(groupId);
        if (dealerGroupPo != null) {
            dealerGroupDao.delete(groupId);
        }
    }


}
