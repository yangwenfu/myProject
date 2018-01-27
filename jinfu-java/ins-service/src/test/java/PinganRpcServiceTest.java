import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealSource;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;
import com.xinyunlian.jinfu.insurance.service.InsuranceOrderService;
import com.xinyunlian.jinfu.pingan.dto.*;
import com.xinyunlian.jinfu.pingan.service.PinganRpcService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:standard-code-dao-test.xml"})
public class PinganRpcServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PinganRpcServiceTest.class);

    @Autowired
    private PinganRpcService pinganRpcService;

    @Autowired
    private InsuranceOrderService insuranceOrderService;

    @Test
    public void getAllInsuredGrade() {
        List<PinganInsuredGradeDto> allInsuredGrade = pinganRpcService.getAllInsuredGrade();
        System.out.println(JsonUtil.toJson(allInsuredGrade));
    }

    @Test
    public void isTyphoonControlRegion() {
        Boolean typhoonControlRegion = pinganRpcService.isTyphoonControlRegion(197l);
        System.out.println(typhoonControlRegion);
    }

    @Test
    public void getPinganRegion() {
        PinganRegionDto pinganRegion = pinganRpcService.getPinganRegion("421181", 3);
        System.out.println(JsonUtil.toJson(pinganRegion));
    }
    @Test
    public void getPinganRegionList() {
        List<PinganRegionDto> pinganRegionList = pinganRpcService.getPinganRegionList(100l);
        System.out.println(JsonUtil.toJson(pinganRegionList));
    }
    @Test
    public void getPinganQuotePrice() {
        PinganQuotePriceRetDto pinganQuotePrice = pinganRpcService.getPinganQuotePrice("005", 1933l);
        System.out.println(JsonUtil.toJson(pinganQuotePrice));
    }
    @Test
    public void getPreOrderUserInfo(){
        PreOrderUserInfoDto preOrderUserInfo = pinganRpcService.getPreOrderUserInfo("123");
        System.out.println(JsonUtil.toJson(preOrderUserInfo));
    }
    @Test
    public void getPinganOauthToken(){
        String pinganOauthToken = pinganRpcService.getPinganOauthToken(false);
        System.out.println(pinganOauthToken);
    }
    @Test
    public void convertJson(){
        String json = "{\"ret\":\"0\",\"msg\":\"\",\"requestId\":\"1499848612679\",\"data\":{\"responseCode\":\"999999\",\"result\":{\"mobileTelephone\":\"13808848228\",\"insurantName\":\"网新新云联金融信息服务（浙江）有限公司\",\"totalActualPremium\":\"370.00\",\"certificateNo\":\"440725197608134563\",\"totalInsuredAmount\":\"400000.00\",\"amountCurrencyCode\":\"01\",\"noticeNo\":\"26110006000136067134\",\"address\":\"中马街道汇豪天下\",\"policyNo\":\"12693033900151472855\",\"productName\":\"财产综合险\",\"applyPolicyNo\":\"52693033900210483907\",\"applicantName\":\"云联金服公司名称\",\"insuranceEndDate\":\"2018-07-12 23:59:59\",\"premiumCurrencyCode\":\"01\",\"premiumRate\":\"0.000925\",\"insuranceBeginDate\":\"2017-07-13 08:00:00\"},\"responseMsg\":\"成功\"}}";
        QunarApplyRespBase respBase = JsonUtil.toObject(QunarApplyRespBase.class, json);
        System.out.println(1);
    }
    @Test
    public void qunarApply(){

        PreOrderUserInfoReqTest req = new PreOrderUserInfoReqTest();
        req.setTobaccoCertificateNo("1573548464994641");
        req.setStoreId(5l);
        req.setStoreName("东东烟杂店");
        req.setUserName("刘郁东");
        req.setLinkmanName("晓刘");
        req.setMobile("13808848228");
        req.setProvinceGbCode("330000");
        req.setProvinceId(11l);
        req.setProvinceName("浙江省");
        req.setCityGbCode("330200");
        req.setCityId(289l);
        req.setCityName("宁波市");
        req.setCountyGbCode("330205");
        req.setCountyId(1933l);
        req.setCountyName("江北区");
        req.setDetailAddress("中马街道汇豪天下");
        req.setEmail("bigmeal@sina.cn");
        req.setInsuranceBeginDate(DateHelper.add(Calendar.getInstance().getTime(), Calendar.DATE, 1));
        req.setTotalInsuredAmount(new BigDecimal("300000"));
        req.setTotalActualPreium(new BigDecimal("260"));
        req.setDealType(EPerInsDealType.JINFUSERVICE);
        req.setDealSource(EPerInsDealSource.INNERMGT);
        req.setProductCode("MP04000001");
        req.setPlanCodeList(Arrays.asList("PL0400010", "PL0400201", "PL0400022", "PL0400204"));

        //金服地区
        String provinceId = "926";
        String cityId = "941";
        String areaId = "944";
        String streetId = "14314";
        //用户身份证号
        String idCardNo = "440725197608134563";

        try {
            //插入订单表
            PerInsuranceInfoDto perInsuranceInfoDto = new PerInsuranceInfoDto();
            perInsuranceInfoDto.setDealType(req.getDealType());
            perInsuranceInfoDto.setDealSource(req.getDealSource());
            perInsuranceInfoDto.setOperatorName("董方超");
            perInsuranceInfoDto.setStoreId(req.getStoreId());
            perInsuranceInfoDto.setTobaccoPermiNo(req.getTobaccoCertificateNo());
            perInsuranceInfoDto.setStoreName(req.getStoreName());
            perInsuranceInfoDto.setPhoneNo(req.getMobile());
            perInsuranceInfoDto.setStoreAddress(req.getDetailAddress());
            perInsuranceInfoDto.setOrderStatus(EPerInsOrderStatus.INPROCESS);
            perInsuranceInfoDto.setProductId("S01001");
            perInsuranceInfoDto.setProductName("店铺保");

            StringBuilder storeAreaTreePath = new StringBuilder();
            if (!StringUtils.isEmpty(provinceId)){
                storeAreaTreePath.append(",").append(provinceId).append(",");
            }
            if (!StringUtils.isEmpty(storeAreaTreePath) && !StringUtils.isEmpty(cityId)){
                storeAreaTreePath.append(cityId).append(",");
            }
            if (!StringUtils.isEmpty(storeAreaTreePath) && !StringUtils.isEmpty(areaId)){
                storeAreaTreePath.append(areaId).append(",");
            }
            if (!StringUtils.isEmpty(storeAreaTreePath) && !StringUtils.isEmpty(streetId)){
                storeAreaTreePath.append(streetId).append(",");
            }
            perInsuranceInfoDto.setStoreAreaTreePath(storeAreaTreePath.toString());

            String perOrderNo = insuranceOrderService.addInsOrderInfo(perInsuranceInfoDto);

            //插入订单用户信息表，关联保险订单
            PreOrderUserInfoDto preOrderUserInfoDto = ConverterService.convert(req, PreOrderUserInfoDto.class);
            preOrderUserInfoDto.setPreInsuranceOrderNo(perOrderNo);
            preOrderUserInfoDto.setIdCardNo(idCardNo);

            if (!CollectionUtils.isEmpty(req.getPlanCodeList())){
                StringBuilder planCodeBuilder = new StringBuilder();
                for (int i = 0; i < req.getPlanCodeList().size(); i++) {
                    planCodeBuilder.append(req.getPlanCodeList().get(i));
                    if (i != req.getPlanCodeList().size() - 1){
                        planCodeBuilder.append(",");
                    }
                }
                preOrderUserInfoDto.setPlanCode(planCodeBuilder.toString());
            }

            PreOrderUserInfoDto dbPreOrderUserInfoDto = pinganRpcService.addPreOrderUserInfo(preOrderUserInfoDto);

            //去平安投保
            QunarApplyResultDto response = pinganRpcService.qunarApply(dbPreOrderUserInfoDto);

            //更新保单信息
            PerInsuranceInfoDto dbInsOrderDto = insuranceOrderService.getInsOrderByOrderId(perOrderNo);

            if (PinganOpenApiRetCode.SUCCESS.equals(response.getResponseCode())){
                dbInsOrderDto.setInsuredPerson(response.getInsurantName());
                Long insuranceAmt = 0l;
                BigDecimal oneThousand = new BigDecimal("1000");
                if (response.getTotalInsuredAmount() != null){
                    insuranceAmt = response.getTotalInsuredAmount().multiply(oneThousand).longValue();
                }
                dbInsOrderDto.setInsuranceAmt(insuranceAmt);
                Long insuranceFee = 0l;
                if (response.getTotalActualPremium() != null){
                    insuranceFee = response.getTotalActualPremium().multiply(oneThousand).longValue();
                }
                dbInsOrderDto.setInsuranceFee(insuranceFee);
                dbInsOrderDto.setPolicyHolder(response.getApplicantName());
                dbInsOrderDto.setOrderStartTime(response.getInsuranceBeginDate());
                dbInsOrderDto.setOrderStopTime(response.getInsuranceEndDate());
                dbInsOrderDto.setOrderStatus(EPerInsOrderStatus.SUCCESS);
                insuranceOrderService.updateInsOrder(dbInsOrderDto);

                try {
                    pinganRpcService.pasElectronicPolicy(perOrderNo);
                } catch (Exception e) {
                    LOGGER.error("获取电子保单失败", e);
                }
                LOGGER.debug("投保处理完成");
                System.out.println("投保成功");
            }else {
                dbInsOrderDto.setOrderStatus(EPerInsOrderStatus.FAILURE);
                dbInsOrderDto.setOrderStatusCause(response.getResponseMsg());
                insuranceOrderService.updateInsOrder(dbInsOrderDto);
                System.out.println("投保失败，" + response.getResponseMsg());
            }
        } catch (BizServiceException e) {
            LOGGER.error("投保失败", e);
        }
    }

    @Test
    public void pasElectronicPolicy(){
        pinganRpcService.pasElectronicPolicy("102071158953786008");
    }

}
