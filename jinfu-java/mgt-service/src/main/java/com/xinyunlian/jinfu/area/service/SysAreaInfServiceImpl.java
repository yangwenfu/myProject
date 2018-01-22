package com.xinyunlian.jinfu.area.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.xinyunlian.jinfu.area.dao.SysAreaInfDao;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.dto.SysAreaInfSearchDto;
import com.xinyunlian.jinfu.area.entity.SysAreaInfPo;
import com.xinyunlian.jinfu.area.enums.ESysAreaLevel;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.prod.dao.ProdAreaDao;

import static com.xinyunlian.jinfu.prod.enums.EShelfPlatform.AIO;

/**
 * Created by DongFC on 2016-08-22.
 */
@Service
public class SysAreaInfServiceImpl implements SysAreaInfService {

    @Autowired
    private SysAreaInfDao sysAreaInfDao;

    @Autowired
    private ProdAreaDao prodAreaDao;

    @Override
    public List<SysAreaInfDto> getSysAreaInfList(SysAreaInfSearchDto sysAreaInfSearchDto) {

        Specification<SysAreaInfPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != sysAreaInfSearchDto) {
                if (sysAreaInfSearchDto.getParent()!=null) {
                    expressions.add(cb.equal(root.get("parent"), sysAreaInfSearchDto.getParent()));
                }else {
                    expressions.add(cb.isNull(root.get("parent")));
                }
                if (!StringUtils.isEmpty(sysAreaInfSearchDto.getName())) {
                    expressions.add(cb.like(root.get("name"), BizUtil.filterString(sysAreaInfSearchDto.getName())));
                }
            }
            return predicate;
        };

        List<SysAreaInfPo> list = sysAreaInfDao.findAll(spec);
        List<SysAreaInfDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                SysAreaInfDto dto = ConverterService.convert(po, SysAreaInfDto.class);
                result.add(dto);
            });
        }

        return result;
    }

    @Transactional
    @Override
    public void createSysAreaInf(SysAreaInfDto sysAreaInfDto) {
        if (sysAreaInfDto != null){
            SysAreaInfPo po = ConverterService.convert(sysAreaInfDto, SysAreaInfPo.class);
            if (po.getParent() != null) {//create normal area
                SysAreaInfPo parentArea = sysAreaInfDao.findOne(po.getParent());
                String treePath = parentArea.getTreePath() + po.getParent() + ",";
                po.setTreePath(treePath);
            }else {// create province
                po.setTreePath(",");
            }
            sysAreaInfDao.save(po);
        }

    }

    @Transactional
    @Override
    public void updateSysAreaInf(SysAreaInfDto sysAreaInfDto) {
        if (sysAreaInfDto != null && !StringUtils.isEmpty(sysAreaInfDto.getName())){
            SysAreaInfPo po = sysAreaInfDao.findOne(sysAreaInfDto.getId());
            po.setName(sysAreaInfDto.getName());
        }
    }

    @Transactional
    @Override
    public void deleteSysAreaInf(Long areaId) {
        if (areaId != null){
            sysAreaInfDao.deleteById(areaId);
            prodAreaDao.deleteByAreaId(areaId);
        }
    }

    @Override
    public SysAreaInfDto getSysAreaInfById(Long areaId) {
        SysAreaInfDto dto = null;
        if (areaId != null){
            SysAreaInfPo po = sysAreaInfDao.findOne(areaId);
            dto = ConverterService.convert(po, SysAreaInfDto.class);
        }

        return dto;
    }

    @Override
    public List<SysAreaInfDto> getSpecificSysArea(Long regionId, ESysAreaLevel sysAreaLevel) {

        List<SysAreaInfDto> result = new ArrayList<>();
        List<SysAreaInfPo> list = sysAreaInfDao.findSpecificSysAreaInf(regionId, sysAreaLevel.getLevel());
        list.forEach(po -> {
            SysAreaInfDto dto = ConverterService.convert(po, SysAreaInfDto.class);
            result.add(dto);
        });
        return result;
    }

    @Override
    public List<SysAreaInfDto> getSysAreaInfByLevel(ESysAreaLevel sysAreaLevel) {
        List<SysAreaInfDto> result = new ArrayList<>();
        List<SysAreaInfPo> list = sysAreaInfDao.findByLevel(sysAreaLevel.getLevel());
        list.forEach(po -> {
            SysAreaInfDto dto = ConverterService.convert(po, SysAreaInfDto.class);
            result.add(dto);
        });
        return result;
    }

    @Override
    public List<SysAreaInfDto>  getSysAreaInfByIds(List<Long> areaIds) {
        List<SysAreaInfDto> result = new ArrayList<>();
        List<SysAreaInfPo> list = sysAreaInfDao.findByIdInOrderByGbCodeAsc(areaIds);
        list.forEach(po -> {
            SysAreaInfDto dto = ConverterService.convert(po, SysAreaInfDto.class);
            result.add(dto);
        });
        return result;
    }

    @Override
    public SysAreaInfDto getSysAreaByGbCode(String gbCode) {
        SysAreaInfPo po = sysAreaInfDao.findByGbCode(gbCode);
        if (po != null){
            return ConverterService.convert(po, SysAreaInfDto.class);
        }

        return null;
    }

    @Override
    public List<SysAreaInfDto> getSysAreaByName(String name) {
        List<SysAreaInfPo> sysAreaInfPos = sysAreaInfDao.findByName(name);
        if(sysAreaInfPos == null){
            return null;
        }
        List<SysAreaInfDto> sysAreaInfDtos = new ArrayList<>();
            sysAreaInfPos.forEach(sysAreaInfPo -> {
                SysAreaInfDto sysAreaInfDto = ConverterService.convert(sysAreaInfPo, SysAreaInfDto.class);
                sysAreaInfDtos.add(sysAreaInfDto);
            });

        return sysAreaInfDtos;
    }

    @Override
    public List<SysAreaInfDto> getSysAreaUnderGbCode(String gbCode) {
        SysAreaInfPo po = sysAreaInfDao.findByGbCode(gbCode);
        if (po != null) {
            List<SysAreaInfPo> poList = sysAreaInfDao.findByParentOrderByGbCodeAsc(po.getId());
            return ConverterService.convertToList(poList, SysAreaInfDto.class);
        }
        return Collections.emptyList();
    }

    public List<SysAreaInfDto>  getSysAreaInfByFullName(String fullName) {
        List<SysAreaInfDto> result = new ArrayList<>();
        List<SysAreaInfPo> list = sysAreaInfDao.findByFullName(fullName);
        list.forEach(po -> {
            SysAreaInfDto dto = ConverterService.convert(po, SysAreaInfDto.class);
            result.add(dto);
        });
        return result;
    }

    @Override
    public List<SysAreaInfDto> getSysAreaByLvlAndName(ESysAreaLevel sysAreaLevel, String regionName, Long parent) {
        List<SysAreaInfDto> result = new ArrayList<>();
        List<SysAreaInfPo> list;
        if (parent == null){
            list = sysAreaInfDao.findByLvlAndName(sysAreaLevel.getLevel(), regionName);
        }else {
            list = sysAreaInfDao.findByLvlAndNameAndParent(sysAreaLevel.getLevel(), regionName, parent);
        }

        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                SysAreaInfDto dto = ConverterService.convert(po, SysAreaInfDto.class);
                result.add(dto);
            });
        }

        return result;
    }
}
