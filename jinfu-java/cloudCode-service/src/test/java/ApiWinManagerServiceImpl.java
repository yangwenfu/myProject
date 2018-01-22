import com.xinyunlian.jinfu.api.service.ApiWinManagerService;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import com.xinyunlian.jinfu.trade.dto.YmBizDto;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.service.YmBizService;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2017年03月09日.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jinfu.spring.xml"})
@TransactionConfiguration(defaultRollback = false)
public class ApiWinManagerServiceImpl {

    @Autowired
    private ApiWinManagerService apiWinManagerService;
    @Autowired
    private YmBizService ymBizService;


    /**
     * Method: getTradePage(YmTradeSearchDto searchDto)
     */
    @Test
    public void testRegister() {
        //向赢掌柜请求注册店铺
        Map<String, String> memberParams = new HashMap<>();
        memberParams.put("merchantName", "磊的小店");//商户名称
        memberParams.put("address", "洪塘街道");//商家地址
        memberParams.put("mobile", "13967899785");//申请人手机号
        memberParams.put("applicantName", "孟磊");//持卡人姓名;错误会导致结算不成功
        memberParams.put("applicantIdno", "330227199002197512");//申请人身份证号
        memberParams.put("area", "330205");//区编号
        memberParams.put("prov", "330000");//省编号
        memberParams.put("city", "330200");//市编号
        memberParams.put("licenseno", "11112364896");//营业执照号
        memberParams.put("licensedate", "2017-03-09");//营业执照日期
        String memberNo = apiWinManagerService.getmemberNo(memberParams);

        //银行卡添加
        Map<String, String> bankCardParams = new HashMap<>();
        bankCardParams.put("name", "磊的小店");//商户名称
        bankCardParams.put("memberCode", memberNo);//商家编码
        bankCardParams.put("mobile", "13967899785");//预留手机
        bankCardParams.put("branchName", "中国工商银行");//支行名
        bankCardParams.put("branchNo", "102100099996");//支行号
        bankCardParams.put("cardNo", "6222023901003337009");//卡号
        bankCardParams.put("bankName", "中国工商银行");//银行名
        apiWinManagerService.saveBankCard(bankCardParams);
        //费率设置
        List<YmBizDto> bizList = ymBizService.findAll();
        Map<String, String> rateParams = new HashMap<>();
        rateParams.put("memberCode", "P0213967899785");
        rateParams.put("rate", bizList.get(0).getRate() + StringUtils.EMPTY);
        apiWinManagerService.saveRate(rateParams);
        //System.out.printf("MemberNo====" + "P0213967899785");
    }

    @Test
    public void testBathRegister() {
//        Map<String, String> cardbranchNoParams = new HashMap<>();
//        cardbranchNoParams.put("cardNo", "6222023901003337009");//银行卡号
//        cardbranchNoParams.put("city", "宁波市");//城市名称
//        cardbranchNoParams.put("keyWord", "工商");//关键词
//        Map<String, String> cardMap = apiWinManagerService.getCardbranchNo(cardbranchNoParams);
        //向赢掌柜请求注册店铺
        Map<String, String> memberParams = new HashMap<>();
        memberParams.put("merchantName", "磊的小店");//商户名称
        memberParams.put("mobile", "13967899785");//申请人手机号
        memberParams.put("cardholder", "孟磊");//持卡人姓名;错误会导致结算不成功
        memberParams.put("cardNo", "6222023901003337009");//银行卡号
        memberParams.put("branchNo", "102100099996");//支行号
        apiWinManagerService.getBathMemberNo(memberParams);
        //System.out.printf("MemberNo====" + memberNo);
    }

    @Test
    public void testJsPay() {
        String orderNo = "8" + String.valueOf(System.currentTimeMillis()).substring(0, 10) + RandomUtil.getNumberStr(3);//生成订单号
        Map<String, String> jsPayParams = new HashMap<>();
        jsPayParams.put("memberCode", "P0113967899785");//商家编号
        jsPayParams.put("notifyUrl", "https://yltest.xylpay.com");//交易结果通知接口Url
        jsPayParams.put("discountAmt", "0");//优惠金额
        jsPayParams.put("transAmt", "1");//实收金额
        jsPayParams.put("userid", "2088202449681691");//用户支付ID
        jsPayParams.put("orderNo", orderNo);//外部订单号
        jsPayParams.put("subject", "磊的小店");//交易对象
        jsPayParams.put("returnUrl", "https://yltest.xylpay.com");//跳转页面
        apiWinManagerService.jsPay(jsPayParams, EBizCode.ALLIPAY);
        //System.out.printf(payMap.get("respCode") + "===" + payMap.get("respDesc") + "===" + payMap.get("orderNo") + "===" + payMap.get("url"));
    }

}
