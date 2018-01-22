package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.user.dao.MgtDeptDao;
import com.xinyunlian.jinfu.user.dto.MgtDeptDto;
import com.xinyunlian.jinfu.user.entity.MgtDeptPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongfangchao on 2016/12/6/0006.
 */
@Service
public class MgtDeptServiceImpl implements MgtDeptService {

    @Autowired
    private MgtDeptDao mgtDeptDao;

    @Override
    public List<MgtDeptDto> getDeptList() throws BizServiceException {

        List<MgtDeptPo> list = mgtDeptDao.findAll();

        List<MgtDeptDto> retData = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach( po -> {
                MgtDeptDto dto = ConverterService.convert(po, MgtDeptDto.class);
                retData.add(dto);
            });
        }

        return retData;
    }

    @Override
    public MgtDeptDto getDeptTree() throws BizServiceException{

        List<MgtDeptDto> dtoList = getDeptList();
        if (!CollectionUtils.isEmpty(dtoList)){
            MgtDeptDto root = new MgtDeptDto();
            for (MgtDeptDto dto:dtoList) {
                if (dto.getDeptParent() == null){
                    root = dto;
                    dtoList.remove(dto);
                    break;
                }
            }
            root.getDeptTree(dtoList);
            return root;
        }

        return null;
    }

    @Override
    public MgtDeptDto getDeptById(Long deptId) throws BizServiceException{
        MgtDeptPo po = mgtDeptDao.findOne(deptId);
        MgtDeptDto dto = ConverterService.convert(po, MgtDeptDto.class);
        return dto;
    }

    @Override
    @Transactional
    public MgtDeptDto addDept(MgtDeptDto dept) throws BizServiceException{
        MgtDeptPo parentDept = mgtDeptDao.findOne(dept.getDeptParent());
        MgtDeptPo po = ConverterService.convert(dept, MgtDeptPo.class);
        mgtDeptDao.save(po);
        po.setDeptTreePath(parentDept.getDeptTreePath() + po.getDeptId() + ",");
        MgtDeptDto dto = ConverterService.convert(po, MgtDeptDto.class);
        return dto;
    }

    @Override
    @Transactional
    public void deleteDirectDeptById(Long deptId) throws BizServiceException{
        mgtDeptDao.delete(deptId);
    }

    @Override
    @Transactional
    public void deleteDeptById(Long deptId) throws BizServiceException {
        MgtDeptPo po = mgtDeptDao.findOne(deptId);
        if (po != null){
            mgtDeptDao.deleteLikePath(po.getDeptTreePath());
        }
    }

    @Override
    @Transactional
    public void updateDeptName(MgtDeptDto deptDto) throws BizServiceException {
        MgtDeptPo po = mgtDeptDao.findOne(deptDto.getDeptId());
        if (!StringUtils.isEmpty(deptDto.getDeptName())){
            po.setDeptName(deptDto.getDeptName());
        }
    }

}
