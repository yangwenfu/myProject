package com.xinyunlian.jinfu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.external.dto.LoanRefundsDto;
import com.xinyunlian.jinfu.external.dto.RefundDto;
import com.xinyunlian.jinfu.external.dto.RefundsAdvanceDto;
import com.xinyunlian.jinfu.external.dto.RequsetInfo;
import com.xinyunlian.jinfu.external.dto.req.LoanApplyReq;
import com.xinyunlian.jinfu.external.dto.req.RegisterReq;
import com.xinyunlian.jinfu.external.dto.resp.LoanApplyNotify;
import com.xinyunlian.jinfu.external.dto.resp.LoanRefundsNotify;
import com.xinyunlian.jinfu.external.enums.ConfirmationType;
import com.xinyunlian.jinfu.external.enums.LoanTermType;
import com.xinyunlian.jinfu.external.enums.PaymentOptionType;
import com.xinyunlian.jinfu.external.service.ATZApiService;
import com.xinyunlian.jinfu.external.service.ATZApiServiceImpl;
import com.xinyunlian.jinfu.external.util.*;
import com.xinyunlian.jinfu.repay.ERefundFlag;
import com.xinyunlian.jinfu.repay.dto.ExternalRepayCallbackDto;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by godslhand on 2017/6/19.
 */
public class SimpleATZApiServiceImplTest {

    private String idNo ="330683199303285015"; //e99464be-3e6d-427f-a3f8-9a6e81abb1e4
    private String tel ="1223";




    @Test
    public void testRegister() throws IOException {
        register();
    }

    @Test
    public void testLoanApply() throws IOException {
//        register();
        apply();//0fcdc962-96e9-4fe4-9754-faefbcc85e6d,330881198712082119 //9a076f8b-f9c3-4332-9c84-b1ada16e65cb,330881198712082111
         //634b1739-0c8e-4ee8-b05e-e3b415f9a1a2
//   43a1eae8-ed18-4ab1-b26f-e9b6e6edd3a5  ,330881198712082112   /15688ba6-76c9-49bb-97ab-302c65ebd3c4,330881198712082113
    }
    @Test
    public void testConfirmApply(){
        getService().loanContractConfirm("cea5c9bc-efd6-49cc-8882-8292c2f8f130", ConfirmationType.agree);
    }

    @Test
    public void testGetOverDueLoanDetailInfo(){
        getService().getOverDueLoanDetailInfo("56ecf007-55ff-4fae-ba8a-30fb92f2fbb8");
    }

    @Test
    public  void testGetLoanPaymentPlan(){

      List<RefundDto> refundDto = getService().getLoanPaymentPlan("0fcdc962-96e9-4fe4-9754-faefbcc85e6d");
      Assert.assertNotNull(refundDto);
    }


    @Test
    public void testLoanRefundInAdvance(){//loanRefuandInAdvanceId：5730a721-7413-4920-9ca5-3a36a609c0ea，cea5c9bc-efd6-49cc-8882-8292c2f8f130
      RefundsAdvanceDto refundsAdvanceDto = getService().loanRefundInAdvance("cea5c9bc-efd6-49cc-8882-8292c2f8f130");//9fbc330e-90ad-473e-990b-d813b7f61b8a
      Assert.assertNotNull(refundsAdvanceDto);
    }

    @Test
    public void  testLoanRefuandInAdvanceConfirm(){
      Boolean res =  getService().loanRefuandInAdvanceConfirm("e43d9aba-0714-46b9-b06c-dc0325cc02c9",
                "d4b5cf55-4be1-4757-8da4-d9e80986a240",ConfirmationType.agree);
      Assert.assertTrue(res);
    }




    private void register() throws IOException {
        RegisterReq registerReq = new RegisterReq() ;
        registerReq.setIdNo(idNo);
        registerReq.setUserName("钱城");
        registerReq.setContactTel(tel);
        registerReq.setUserIdResource("11");
        registerReq.setRegisterName("烟草小店");
        registerReq.setLegalRepId("330683199303285017");
        registerReq.setLegalRepMobile("119");
        registerReq.setLegalRepAdd("宁波江北");
        registerReq.setActualAdd("宁波江北");
        String bas64 =Base64Util.byteToBase64Encoding(Base64Util.getFileByte(new File("D:/timg.jpg")));
        System.out.println(bas64);
        registerReq.setBussinesLicencePhoto(bas64);
        registerReq.setTaxRegistrationPhoto(bas64);
        registerReq.setOrganizationCodePhoto(bas64);
        registerReq.setIdPhotoFront(bas64);
        registerReq.setOutsidePhoto(bas64);
        registerReq.setInsidePhoto(bas64);
        getService().register(registerReq);
    }
    private  void  apply(){
        LoanApplyReq loanApply=  new LoanApplyReq();
        loanApply.setIdNo(idNo);
        loanApply.setAmount(new Double(10000));
        loanApply.setLoanTerm(Integer.valueOf(LoanTermType._90.getCode()));
        loanApply.setPaymentOption(Integer.valueOf(PaymentOptionType.thirty_period.getCode()));
        loanApply.setProductId("c911c61e-9d67-4c66-9e63-a56d3d9f7073");
        loanApply.setLoanPurpose("测试23");

        String applyId =getService().loanApply(loanApply);
        System.out.println(applyId);
    }


    private ATZApiService getService(){
        return  new ATZApiServiceImpl();
    }

    @Test
    public void testJSON(){
        DemoEnitty d= new DemoEnitty(1,new Date(),100D);
        d.setTotal(new BigDecimal("100"));
        System.out.println(JSON.toJSONString(d));
    }

    @Test
    public void testFefounds(){
        getService().loanRefundInAdvance("1d5e6a72-e9b8-453e-80d6-940586f038a5");
    }

    @Test
    public void testFile() throws IOException {
        File f=  ATZImageUtil.download("http://yltest.xylpay.com/upload/iou/DEALER_INFO_IMG/201704/c21bc292efd940228d91c05c215fc5d0.jpg?auth_key=1498385808120-355e8a639a5a966c56dc9445854286bb");

//        new File(UUID.randomUUID()+".jpg");
        System.out.println(f.getAbsolutePath());
        String filestr = Base64Util.byteToBase64Encoding(Base64Util.getFileByte(f));
        f.delete();
        System.out.println(filestr);
    }


    @Test
    public void testBeanCopy(){
        LoanRefundsNotify loanRefundsNotify = new LoanRefundsNotify() ;
        loanRefundsNotify.setLoanId("11111");
        loanRefundsNotify.setDate("20170702");
        loanRefundsNotify.setPeriodNumber(11);
        loanRefundsNotify.setResult(1);
        loanRefundsNotify.setRefundFlag(1);
        loanRefundsNotify.setRefundCapital(100.00);
        loanRefundsNotify.setAmount(500.00);
        loanRefundsNotify.setRefundDefaultInterest(500.00);
        loanRefundsNotify.setPeriodNumber(11);
        ExternalRepayCallbackDto loanRefundsDto = convert2ReplyDto(loanRefundsNotify);
        loanRefundsDto.setApplId("1111");
        System.out.println(loanRefundsDto.toString());
    }


    private ExternalRepayCallbackDto convert2ReplyDto(LoanRefundsNotify notify) {
        ExternalRepayCallbackDto callbackDto = new ExternalRepayCallbackDto();
        callbackDto.setResult(notify.getResult()==notify.RESULT_SUCCCESS?true:false);
        callbackDto.setReason(notify.getReason());
        callbackDto.setPeriod(notify.getPeriodNumber());
        callbackDto.setDate(notify.getDate());
        if(notify.getAmount()!=null)
            callbackDto.setAmount(BigDecimal.valueOf(notify.getAmount()));
        if(notify.getRefundCapital()!=null)
         callbackDto.setCapital(BigDecimal.valueOf(notify.getRefundCapital()));
        if(notify.getRefundInterest()!=null)
            callbackDto.setInterest(BigDecimal.valueOf(notify.getRefundInterest()));
        if(notify.getRefundCommission()!=null)
         callbackDto.setFee(BigDecimal.valueOf(notify.getRefundCommission()));
        if(notify.getRefundDefaultInterest()!=null)
         callbackDto.setFine(BigDecimal.valueOf(notify.getRefundDefaultInterest()));
        callbackDto.setRefundFlag(EnumHelper.translate(ERefundFlag.class,String.valueOf(notify.getRefundFlag())));
        callbackDto.setRefundType(notify.getRefundType());
        return callbackDto;
    }

    @Test
    public void testSingCheck(){

        System.out.println(Boolean.valueOf(null));
//        for (int i=0;i<100;i++){
//            System.out.println(new Random().nextInt(9)+6);
//        }

//       SignUtil.generateMD5Sign("loanApplyResultNotification","2");

//        System.out.println(UUID.randomUUID());
//        System.out.println(PaymentOptionType.valueOf("once").getText());


//        String request="{\"method\":\"loanRefundResultNotification\",\"ver\":\"2.0\",\"channelId\":\"34\",\"params\":{\"amount\":4000.41,\"result\":1,\"refundFlag\":1,\"refundCapital\":3283.83,\"refundDefaultInterest\":566.58,\"refundInterest\":150.0,\"periodNumber\":1,\"date\":\"20170622\",\"refundCommission\":0.0,\"refundType\":3,\"loanId\":\"56ecf007-55ff-4fae-ba8a-30fb92f2fbb8\"},\"signType\":\"RSA\",\"sign\":\"RFJebS8vLFDcTh2KhLC8SwbLOu/9vL9AEUPw3NBFpj/kL34VC4L8bJ8ESV1fyBiYtVsaFu3hDGgxzu68nP6NP0bDEkP1ymYSNSDbtkWqXSsbhXNfbufKRqoqsE8wnG DvqlARAIpUe92wCm9f9PItJps9GscxivLf2MFJdRkSvM\\u003d\"}";
//        String key="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDRT81CpnhWDnLutAsOfEtWF5EJrpddPsRcqxzBtbEtybmpcRyRpKL+Xx2ivha3k+kVOoWezVIldNwDtJbqm1Ns1USn/j+V8d/BikxybgDvthoQGMRt/LRgikKyzv+97vn6n+Fl5hwBh4iT85afuZCkRBopGGK/wMD6LDpNuO7OpQIDAQAB";
//        Assert.assertTrue(SignUtil.verifySign(request,key)); ;
    }

    class DemoEnitty{
        private int id;
        private Date date ;
        private Double amount ;
        private BigDecimal total ;

        public BigDecimal getTotal() {
            return total;
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }

        public DemoEnitty(int id, Date date, Double amount) {
            this.id = id;
            this.date = date;
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }
}
