package com.xinyunlian.jinfu;

import com.xinyunlian.jinfu.common.entity.id.IdUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.NBCBOrderDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.NBCBOrderSearchDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.entity.NBCBOrderPo;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBApprStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBCreditStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBLoanStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBReceiveStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.service.NBCBOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by bright on 2017/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class NBCBOrderServiceTest {

    @Autowired
    private NBCBOrderService nbcbOrderService;

    @Test
    public void createOrder(){
        String userId = "UC0000000000";
        NBCBOrderDto nbcbOrderDto = new NBCBOrderDto();
        nbcbOrderDto.setUserId(userId);
        nbcbOrderDto.setOrderNo((String)IdUtil.produce(NBCBOrderPo.class));
        nbcbOrderService.createOrder(nbcbOrderDto);
    }

    @Test
    public void updateOrderReceiveStatus(){
        String userId = "UC0000000000";
        NBCBOrderDto nbcbOrderDto = new NBCBOrderDto();
        nbcbOrderDto.setUserId(userId);
        nbcbOrderDto.setOrderNo("NBCB2051601230069003");
        nbcbOrderDto.setReceiveStatus(ENBCBReceiveStatus.SUCCESS);
        nbcbOrderDto.setModifyTs(new Date());
        nbcbOrderService.updateOrder(nbcbOrderDto);
    }

    @Test
    public void updateApprovalInf(){
        String userId = "UC0000000000";
        String orderNo = "NBCB2051601230069003";
        NBCBOrderDto nbcbOrderDto = nbcbOrderService.findByOrderNo(orderNo);
        nbcbOrderDto.setApprStatus(ENBCBApprStatus.APPROVED);
        nbcbOrderDto.setCredit(BigDecimal.valueOf(100000));
        nbcbOrderDto.setCreditDeadLine(DateHelper.getDate("2017-06-16"));
        nbcbOrderDto.setCreditStatus(ENBCBCreditStatus.NORMAL);
        nbcbOrderDto.setModifyTs(new Date());
        nbcbOrderService.updateOrder(nbcbOrderDto);
    }

    @Test
    public void updateLoanInf(){
        String userId = "UC0000000000";
        String orderNo = "NBCB2051601230069003";
        NBCBOrderDto nbcbOrderDto = nbcbOrderService.findByOrderNo(orderNo);
        nbcbOrderDto.setLoanRemaining(BigDecimal.valueOf(100000));
        nbcbOrderDto.setLoanRemainingAvg(BigDecimal.valueOf(100000).divide(BigDecimal.valueOf(365), BigDecimal.ROUND_HALF_UP));
        nbcbOrderDto.setLoanStatus(ENBCBLoanStatus.NOMRAL);
        nbcbOrderDto.setModifyTs(new Date());
        nbcbOrderService.updateOrder(nbcbOrderDto);
    }

    @Test
    public void canApply(){
        String userId = "UC0000000000";
        System.out.println(nbcbOrderService.canApply(userId));;
    }

    @Test
    public void hasEntryPermission(){
        String userId = "UC0000000000";
        System.out.println(nbcbOrderService.hasEntryPermission(userId, null));
    }

    @Test
    public void getAllUserIds(){
        System.out.println(JsonUtil.toJson(nbcbOrderService.getAllAppliedUserId()));
    }

    @Test
    public void getPage(){
        NBCBOrderSearchDto searchDto = new NBCBOrderSearchDto();
        searchDto.setUserId("UC0000002485");
        NBCBOrderSearchDto page = nbcbOrderService.getPage(searchDto);
        System.out.println(JsonUtil.toJson(page));
    }
}
