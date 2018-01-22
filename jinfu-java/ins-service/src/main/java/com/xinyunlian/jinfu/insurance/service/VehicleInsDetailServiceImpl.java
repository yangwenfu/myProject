package com.xinyunlian.jinfu.insurance.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.insurance.dao.VehicleInsDetailDao;
import com.xinyunlian.jinfu.insurance.entity.VehicleInsDetailPo;
import com.xinyunlian.jinfu.zhongan.dto.VehicleInsDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dongfangchao on 2016/12/5/0005.
 */
@Service
public class VehicleInsDetailServiceImpl implements VehicleInsDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleInsDetailServiceImpl.class);

    @Autowired
    private VehicleInsDetailDao vehicleInsDetailDao;

    @Override
    public VehicleInsDetailDto getVehicleInsDetail(Long id) throws BizServiceException {
        VehicleInsDetailPo po = vehicleInsDetailDao.findOne(id);
        VehicleInsDetailDto dto = ConverterService.convert(po, VehicleInsDetailDto.class);
        return dto;
    }

    @Override
    @Transactional
    public VehicleInsDetailDto addVehicleInsDetail(VehicleInsDetailDto detailDto) throws BizServiceException {
        VehicleInsDetailPo po = ConverterService.convert(detailDto, VehicleInsDetailPo.class);
        po = vehicleInsDetailDao.save(po);
        VehicleInsDetailDto retDto = ConverterService.convert(po, VehicleInsDetailDto.class);
        return retDto;
    }

    @Override
    public VehicleInsDetailDto getVehicleInsDetailByOrderId(String orderId) throws BizServiceException {
        VehicleInsDetailPo po = vehicleInsDetailDao.findByPerInsuranceOrderNo(orderId);
        VehicleInsDetailDto dto = ConverterService.convert(po, VehicleInsDetailDto.class);
        return dto;
    }

    @Override
    @Transactional
    public void updateVehicleInsDetailById(VehicleInsDetailDto detailDto) throws BizServiceException {
        VehicleInsDetailPo po = vehicleInsDetailDao.findOne(detailDto.getId());
        po.setPerInsuranceOrderNo(detailDto.getPerInsuranceOrderNo());
        po.setSrcBizPremiumInterval(detailDto.getSrcBizPremiumInterval());
        po.setSrcCompelPremiumInterval(detailDto.getSrcCompelPremiumInterval());
        po.setSrcTaxPreimum(detailDto.getSrcTaxPreimum());
        po.setBusinessPolicyNo(detailDto.getBusinessPolicyNo());
        po.setBusinessPremiumInterval(detailDto.getBusinessPremiumInterval());
        po.setVehiclePolicyEffectiveDate(detailDto.getVehiclePolicyEffectiveDate());
        po.setVehiclePolicyExpiryDate(detailDto.getVehiclePolicyExpiryDate());
        po.setCompelPolicyNo(detailDto.getCompelPolicyNo());
        po.setCompelPremiumInterval(detailDto.getCompelPremiumInterval());
        po.setCompelEffectiveDate(detailDto.getCompelEffectiveDate());
        po.setCompelExpiryDate(detailDto.getCompelExpiryDate());
        po.setTaxPreimum(detailDto.getTaxPreimum());
        po.setVehicleOwnerName(detailDto.getVehicleOwnerName());
        po.setPolicyHolderName(detailDto.getPolicyHolderName());
        po.setInsurantName(detailDto.getInsurantName());
        po.setPolicyHolderPhoneNo(detailDto.getPolicyHolderPhoneNo());
        po.setVehicleLicencePlateNo(detailDto.getVehicleLicencePlateNo());
        po.setVinNo(detailDto.getVinNo());
        po.setVehicleEngineNo(detailDto.getVehicleEngineNo());
        po.setReceiverName(detailDto.getReceiverName());
        po.setReceiverPhoneNo(detailDto.getReceiverPhoneNo());
        po.setReceiverAddress(detailDto.getReceiverAddress());
        po.setCorrectVehicleEndoNo(detailDto.getCorrectVehicleEndoNo());
        po.setCorrectVehicleDeltaPremium(detailDto.getCorrectVehicleDeltaPremium());
        po.setCorrectVehicleApplyDate(detailDto.getCorrectVehicleApplyDate());
        po.setCorrectVehicleEffectiveDate(detailDto.getCorrectVehicleEffectiveDate());
        po.setCorrectCompelEndoNo(detailDto.getCorrectCompelEndoNo());
        po.setCorrectCompelDeltaPremium(detailDto.getCorrectCompelDeltaPremium());
        po.setCorrectCompelApplyDate(detailDto.getCorrectCompelApplyDate());
        po.setCorrectCompelEffectiveDate(detailDto.getCorrectCompelEffectiveDate());
    }

}
