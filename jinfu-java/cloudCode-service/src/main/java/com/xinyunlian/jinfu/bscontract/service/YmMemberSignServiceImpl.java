package com.xinyunlian.jinfu.bscontract.service;

import com.xinyunlian.jinfu.bscontract.dao.YmMemberSignDao;
import com.xinyunlian.jinfu.bscontract.dto.YmMemberSignDto;
import com.xinyunlian.jinfu.bscontract.entity.YmMemberSignPo;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dongfangchao on 2017/2/22/0022.
 */
@Service
public class YmMemberSignServiceImpl implements YmMemberSignService{

    @Autowired
    private YmMemberSignDao ymMemberSignDao;

    @Override
    public YmMemberSignDto getYmMemberSignById(Long id) {
        YmMemberSignPo po = ymMemberSignDao.findOne(id);
        YmMemberSignDto dto = ConverterService.convert(po, YmMemberSignDto.class);
        return dto;
    }

    @Override
    public YmMemberSignDto getYmMemberSignByStoreId(Long storeId) {
        YmMemberSignPo po = ymMemberSignDao.findByStoreId(storeId);
        YmMemberSignDto dto = ConverterService.convert(po, YmMemberSignDto.class);
        return dto;
    }

    @Override
    public YmMemberSignDto getYmMemberSignByQrcodeNo(String qrcodeNo) {
        YmMemberSignPo po = ymMemberSignDao.findByQrcodeNo(qrcodeNo);
        YmMemberSignDto dto = ConverterService.convert(po, YmMemberSignDto.class);
        return dto;
    }

    @Override
    @Transactional
    public YmMemberSignDto saveYmMemberSign(YmMemberSignDto ymMemberSignDto) throws BizServiceException {
        YmMemberSignPo po = ConverterService.convert(ymMemberSignDto, YmMemberSignPo.class);
        YmMemberSignPo retPo = ymMemberSignDao.save(po);
        YmMemberSignDto retDto = ConverterService.convert(retPo, YmMemberSignDto.class);
        return retDto;
    }

    @Override
    @Transactional
    public void updateYmMemberSignStatus(String qrcodeNo) throws BizServiceException {
        YmMemberSignPo po = ymMemberSignDao.findByQrcodeNo(qrcodeNo);
        if (po != null){
            po.setSigned(true);
        }
    }

    @Override
    @Transactional
    public void updateYmMemberSignFilePath(Long id, String firstPageFilePath, String lastPageFilePath) throws BizServiceException {
        YmMemberSignPo po = ymMemberSignDao.findOne(id);
        if (po != null){
            po.setFirstPageFilePath(firstPageFilePath);
            po.setLastPageFilePath(lastPageFilePath);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws BizServiceException {
        ymMemberSignDao.delete(id);
    }

    @Override
    @Transactional
    public void deleteByStoreId(Long storeId) throws BizServiceException {
        ymMemberSignDao.deleteByStoreId(storeId);
    }

    @Override
    @Transactional
    public void updateYmMemberSignStatusById(Long id) throws BizServiceException {
        YmMemberSignPo po = ymMemberSignDao.findOne(id);
        if (po != null){
            po.setSigned(true);
        }
    }
}
