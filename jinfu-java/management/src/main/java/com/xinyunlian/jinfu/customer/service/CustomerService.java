package com.xinyunlian.jinfu.customer.service;

import com.xinyunlian.jinfu.customer.dto.UserBusHasDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsInfoSearchDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;
import com.xinyunlian.jinfu.insurance.service.InsuranceOrderService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by King on 2017/1/13.
 */
@Service
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private InsuranceOrderService insuranceOrderService;
    @Autowired
    private YMMemberService ymMemberService;

    /**
     * 检查店铺有哪些业务
     * @param storeId
     * @return
     */
    public UserBusHasDto CheckStoreOrderHas(Long storeId){
        UserBusHasDto userBusHasDto = new UserBusHasDto();
        //判断是否有云码业务
        YMMemberDto memberDto = ymMemberService.getMemberByStoreId(storeId);
        if(null != memberDto){
            userBusHasDto.setHasYuMa(true);
        }

        //判断是否有店铺保业务 由于需求调整暂时没使用 20170113
        PerInsInfoSearchDto perInsInfoSearchDto = new PerInsInfoSearchDto();
        perInsInfoSearchDto.setStoreId(storeId);
        perInsInfoSearchDto.setPerInsOrderStatus(EPerInsOrderStatus.SUCCESS);
        List<PerInsuranceInfoDto> perInsuranceInfoDtos = insuranceOrderService.getInsOrder(perInsInfoSearchDto);
        if(!CollectionUtils.isEmpty(perInsuranceInfoDtos) && perInsuranceInfoDtos .size() >0){
            userBusHasDto.setHasStoreIns(true);
        }
        return userBusHasDto;
    }

    /**
     * 检查用户已办理业务
     * @param userId
     * @return
     */
    public UserBusHasDto CheckUserOrderHas(String userId){
        UserBusHasDto userBusHasDto = new UserBusHasDto();
        //判断是否有云码业务
        List<YMMemberDto> ymMemberDtos = ymMemberService.getMemberListByUserId(userId);
        if(!CollectionUtils.isEmpty(ymMemberDtos) && ymMemberDtos.size() > 0){
            userBusHasDto.setHasYuMa(true);
        }

        return userBusHasDto;
    }
}
