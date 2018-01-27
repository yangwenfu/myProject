import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.SortUtils;
import com.xinyunlian.jinfu.common.util.UrlUtils;
import com.xinyunlian.jinfu.zrfundstx.dto.SuperCashQueryShareResp;
import com.xinyunlian.jinfu.zrfundstx.dto.SuperCashQueryShareSubResp;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dongfangchao on 2016/12/25/0025.
 */
public class ZrFundsHttpServiceMainTest {

    /*public static void main(String[] args) {
        try {
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "\n" +
                    "<Message> \n" +
                    "  <Responsebody> \n" +
                    "    <Response> \n" +
                    "      <Version>1.0.0</Version>  \n" +
                    "      <MerchantId>stdid</MerchantId>  \n" +
                    "      <DistributorCode>stdcode</DistributorCode>  \n" +
                    "      <BusinType>transResultQueryOrder</BusinType>  \n" +
                    "      <ApplicationNo>1221321321</ApplicationNo>  \n" +
                    "      <TotalRecord>1</TotalRecord>  \n" +
                    "      <ReturnCode>0000</ReturnCode>  \n" +
                    "      <ReturnMsg>成功</ReturnMsg>  \n" +
                    "      <Extension/>  \n" +
                    "      <assetList> \n" +
                    "        <asset> \n" +
                    "          <FundCode>1010</FundCode>  \n" +
                    "          <TotalFundVol>2000</TotalFundVol>  \n" +
                    "          <FundDayIncome>50</FundDayIncome>  \n" +
                    "          <AvailableVol>100</AvailableVol> \n" +
                    "        </asset>  \n" +
                    "        <asset> \n" +
                    "          <FundCode>1020</FundCode>  \n" +
                    "          <TotalFundVol>2500</TotalFundVol>  \n" +
                    "          <FundDayIncome>150</FundDayIncome>  \n" +
                    "          <AvailableVol>120</AvailableVol> \n" +
                    "        </asset> \n" +
                    "      </assetList> \n" +
                    "    </Response> \n" +
                    "  </Responsebody>  \n" +
                    "  <Signature>93EBA24619073A9D385549B9B55B58EC</Signature> \n" +
                    "</Message>\n";
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            StreamSource streamSource = new StreamSource(is);
            Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
            unmarshaller.setClassesToBeBound(SuperCashQueryShareRespMsg.class);
            SuperCashQueryShareRespMsg msg = (SuperCashQueryShareRespMsg)unmarshaller.unmarshal(streamSource);
            System.out.println(JsonUtil.toJson(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static void main(String[] args) throws Exception{
        /*String zrFundsSign = "4BADA51CB51F48C365E7F459005BB89A";

        BankSignApplyResp response = new BankSignApplyResp();
        response.setReturnCode("9999");
        response.setReturnMsg("无效请求");

        Map<String, String> responseMap = JsonUtil.toMap(response);
        Map<String, String> sortedMap = SortUtils.sortFieldString(responseMap, true, true);

        Map<String, String> tmpSortedMap = new LinkedHashMap<>();
        Iterator<String> it = sortedMap.keySet().iterator();
        while (it.hasNext()){
            String key = it.next();
            String value = sortedMap.get(key);
            key = StringUtils.capitalize(key);
            tmpSortedMap.put(key, value);
        }

        String signSrc = UrlUtils.generateUrlParam(tmpSortedMap);
        String jinfuSign = EncryptUtil.encryptMd5(signSrc + "&key=8db4a013a8b515349c307f1e448ce836");
        if (!zrFundsSign.equalsIgnoreCase(jinfuSign)){
            System.out.println("验签失败！中融的签名：" + zrFundsSign + ";金服的签名：" + jinfuSign);
        }else {
            System.out.println("验签成功！");
        }*/
        /*BankSignApplyReq req = new BankSignApplyReq();
        req.setAccoreqSerial("1231324");
        req.setSignBizType("2");
        Map<String, String> requestBody = JsonUtil.toMap(req);
        System.out.println("1");*/

        /*Map<String, String> req = new HashMap<>();
        req.put("Add","123");
        req.put("Bdd", "1243453");

        System.out.println(JsonUtil.toJson(req));*/
        SuperCashQueryShareResp response = new SuperCashQueryShareResp();
        response.setApplicationNo("12143243245");
        response.setVersion("1.0.0");
        response.setTotalRecord(12l);
        response.setDistributorCode("0000028");

        List<SuperCashQueryShareSubResp> subResponses = new ArrayList<>();
        SuperCashQueryShareSubResp subResp1 = new SuperCashQueryShareSubResp();
        subResp1.setFundCode("0110");
        subResp1.setAvailableVol(new BigDecimal("1222.98"));
        SuperCashQueryShareSubResp subResp2 = new SuperCashQueryShareSubResp();
        subResp2.setFundCode("0120");
        subResp2.setAvailableVol(new BigDecimal("300.76"));

        response.setList(null);

        Map<String, String> responseMap = JsonUtil.toMap(response);
        Map<String, String> sortedMap = SortUtils.sortFieldString(responseMap, true, true);

        Map<String, String> tmpSortedMap = new LinkedHashMap<>();
        Iterator<String> it = sortedMap.keySet().iterator();
        while (it.hasNext()){
            String key = it.next();
            String value = sortedMap.get(key);
            key = StringUtils.capitalize(key);
            tmpSortedMap.put(key, value);
        }

        String signSrc = UrlUtils.generateUrlParam(tmpSortedMap);

        System.out.println(signSrc);
    }

}
