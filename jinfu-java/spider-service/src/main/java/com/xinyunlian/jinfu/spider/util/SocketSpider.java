package com.xinyunlian.jinfu.spider.util;

import com.xinyunlian.jinfu.spider.dto.OrderInfo;
import com.xinyunlian.jinfu.spider.dto.SocketConfigDto;
import com.xinyunlian.jinfu.spider.dto.UserInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.io.*;
import java.net.Socket;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by carrot on 2017/8/7.
 */
public class SocketSpider {

    public static final Logger LOGGER = LoggerFactory.getLogger(SocketSpider.class);

    private SocketConfigDto config;

    private static final String CHAOJIYING_CDOE_TYPE = "4005";

    private static final String CHAOJIYING_MIN_LENGTH = "5";

    private static final String RESP_SUCC_CODE = "7427";

    private static final Integer USER_INFO_SUCC_CODE = 10112;

    private static final String RESP_IMG_CODE_ERR = "D1E9D6A4C2EBB4EDCEF3A3A100";

    private static final String XML_SPLIT_CODE = "802709000000";

    private static final String USER_TYPE = "1";

    //private static final String CLIENT_ID = "10077";

    public SocketSpider(SocketConfigDto config) {
        this.config = config;
    }

    public String spider(String loginName, String loginPassword, Boolean... args) {
        Socket socket = null;
        File pngFile = null;
        InputStream in = null;
        OutputStream os = null;
        Integer yzmId = null;
        Integer loginCode;
        String custName;
        JSONObject result = new JSONObject();
        boolean isLogin = false;
        Boolean onlyLogin = args != null && args.length > 0 && args[0] == Boolean.TRUE ? true : false;
        try {
            StringBuffer sb = new StringBuffer();

            //初始化登录socket客户端
            socket = new Socket(config.getAuthIp(), config.getAuthPort());
            in = socket.getInputStream();
            os = socket.getOutputStream();
            if(config.isAuthCode()){
                sb.append(config.getLoginReq());
                SocketUtil.writeGB2312StringZ(sb,config.getClientId());
                SocketUtil.writeGB2312StringZ(sb,loginPassword);
                sb = SocketUtil.send(os,in,sb,"sendLoginByUrl");
                //解析登录响应结果
                SocketUtil.readUnsignedInt(sb);
                loginCode = SocketUtil.readUnsignedInt(sb);
                if (config.getLoginSuccCode() == loginCode) {
                    isLogin = true;
                }
            } else {
                int codeImgReqTime = 0;
                boolean retryLogin = true;
                //验证码错误的情况下 重新发起登录请求
                while (codeImgReqTime < 3 && retryLogin) {
                    sb = login(in, os, loginName, loginPassword, yzmId,
                            codeImgReqTime == 0 ? config.getCodeImgReq() : config.getCodeImgRefreshReq());

                    //解析登录响应结果
                    SocketUtil.readUnsignedInt(sb);
                    loginCode = SocketUtil.readUnsignedInt(sb);
                    retryLogin = false;

                    if (config.getLoginSuccCode() != loginCode) {
                        isLogin = false;
                        //验证码解析失败的情况下发起重试
                        if (sb.toString().contains(RESP_IMG_CODE_ERR)) {
                            codeImgReqTime++;
                            retryLogin = true;
                        }
                    } else
                        isLogin = true;
                }
            }

            if (!isLogin)
                throw new Exception("login fail,loginResponse:" + sb);

            SocketUtil.readUnsignedInt(sb);
            SocketUtil.readGB2312StringZ(sb);
            SocketUtil.readUnsignedShort(sb);
            String ip = SocketUtil.readGB2312StringZ(sb);
            int port = SocketUtil.readUnsignedInt(sb);
            String acc_name = SocketUtil.readGB2312StringZ(sb);
            String login_str = SocketUtil.readGB2312StringZ(sb);
            SocketUtil.readGB2312StringZ(sb);
            custName = SocketUtil.readGB2312StringZ(sb).trim();
            SocketUtil.readGB2312StringZ(sb).trim();
            result.put("custName", custName);

            os.close();
            socket.close();

            if (!onlyLogin) {
                socket = new Socket(ip, port);
                in = socket.getInputStream();
                os = socket.getOutputStream();

                sb = new StringBuffer();
                sb.append(config.getBindReq());
                SocketUtil.writeGB2312StringZ(sb, acc_name);
                SocketUtil.writeGB2312StringZ(sb, login_str);

                //发送绑定请求
                sb = SocketUtil.send(os, in, sb, "BIND");
                if (!sb.toString().contains(RESP_SUCC_CODE))
                    throw new Exception("user bind failed");


                loadMapReady(in, os, config);

                sb = new StringBuffer();
                sb.append(config.getToHomeReq());

                //发送toHome请求
                sb = SocketUtil.send(os, in, sb, "TO_HOME");
                if (!sb.toString().contains(RESP_SUCC_CODE))
                    throw new Exception("user toHome failed");

                loadMapReady(in, os, config);

                sb = new StringBuffer();
                sb.append(config.getUserInfoReq());
                SocketUtil.writeGB2312StringZ(sb, loginName);

                //发送查询个人信息请求
                sb = SocketUtil.send(os, in, sb, "USER_INFO");
                SocketUtil.readUnsignedInt(sb);
                int userInfoCode = SocketUtil.readUnsignedInt(sb);
                LOGGER.debug("userInfoCode:" + userInfoCode);
                if (userInfoCode != USER_INFO_SUCC_CODE)
                    throw new Exception("userInfo request failed");

                LOGGER.debug(SocketUtil.readUnsignedInt(sb) + "");
                LOGGER.debug(SocketUtil.readUnsignedInt(sb) + "");
                LOGGER.debug(SocketUtil.readUnsignedInt(sb) + "");
                String userInfoXml = SocketUtil.readUTF8StringZ(sb).trim();
                LOGGER.debug(userInfoXml);

                XMLSerializer xmlSerializer = new XMLSerializer();
                JSONObject jsonObject = (JSONObject) xmlSerializer.read(userInfoXml);
                LOGGER.debug("userInfo:" + jsonObject.toString());

                //解析组装个人信息
                UserInfo riskUserInfoDto = new UserInfo();
                riskUserInfoDto.setCustName(custName);
                loadUserInfo(jsonObject, riskUserInfoDto);
                result.put("userInfo", riskUserInfoDto);


                sb = new StringBuffer();
                sb.append(config.getToOrderReq());

                //发送toOrder请求
                SocketUtil.send(os, in, sb, "TO_ORDER");

                loadMapReady(in, os, config);

                sb = new StringBuffer();
                sb.append(config.getOrderListReq());
                Calendar cal = Calendar.getInstance();
                Date now = cal.getTime();
                cal.add(Calendar.YEAR, -1);
                Date lastYear = cal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-M-d");
                SocketUtil.writeGB2312StringZ(sb, sdf.format(lastYear));
                SocketUtil.writeGB2312StringZ(sb, sdf.format(now));
                StringBuffer request = new StringBuffer();
                SocketUtil.writeUnsignedShort(request, (short) ((sdf.format(lastYear) + sdf.format(now)).getBytes().length + 8));
                sb = request.append(sb);

                //发送查询历史订单请求
                sb = SocketUtil.send(os, in, sb, "QUERY_HISTORY_ORDER");
                JSONArray array = parseOrderList(sb);
                result.put("orderInfo", loadUserOrder(array));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            result.put("login", isLogin);

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (pngFile != null) {
                pngFile.deleteOnExit();
            }
        }
        return result.toString();
    }

    private StringBuffer login(InputStream in, OutputStream os, String loginName, String loginPassword,
                               Integer yzmId, String codeImgReq) throws IOException, InterruptedException {
        String yzmCode = null;

        //发送拉去验证码图片请求
        os.write(SocketUtil.hexStringToByteArray(codeImgReq));
        os.flush();

        //解析请求返回的图片
        Thread.sleep(2000L);
        ByteArrayOutputStream baos = SocketUtil.acceptByteArray(in);
        byte[] bytes = baos.toByteArray();
        List<String> yzmIds = new ArrayList<>();
        List<String> imgSizes = new ArrayList<>();
        ByteArrayOutputStream img = new ByteArrayOutputStream();
        Integer size;
        int i = 0;
        for (byte b : bytes) {
            if (i >= 8 && i < 12)
                yzmIds.add(String.format("%02X", b));

            if (i >= 12 && i < 14)
                imgSizes.add(String.format("%02X", b));

            if (i == 14) {
                Collections.reverse(imgSizes);
                Collections.reverse(yzmIds);
                yzmId = Integer.parseInt(StringUtils.join(yzmIds.toArray()), 16);
                size = Integer.parseInt(StringUtils.join(imgSizes.toArray()), 16);
                img.write(bytes, 14, size);
                LOGGER.debug("yzmid : " + yzmId + " ImgSize:" + size);
                break;
            }
            i++;
        }


        //调用第三方验证码解析接口
        String yzm_err_no = null;
        int yzm_post_time = 0;
        while (!"0".equals(yzm_err_no) && yzm_post_time < 3) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start("超级鹰验证码接口");
            String res = ChaoJiYing.PostPic(CHAOJIYING_CDOE_TYPE, CHAOJIYING_MIN_LENGTH, img.toByteArray());
            stopWatch.stop();
            LOGGER.debug(stopWatch.prettyPrint());

            LOGGER.debug("yzm res : " + res);
            com.alibaba.fastjson.JSONObject codeResult = com.alibaba.fastjson.JSONObject.parseObject(res);
            yzm_err_no = codeResult.getString("err_no");
            yzmCode = codeResult.getString("pic_str");
            yzm_post_time++;
        }


        img.flush();
        img.close();

        StringBuffer sb = new StringBuffer(config.getLoginReq());
        SocketUtil.writeGB2312StringZ(sb, loginName);
        if (config.isEncodePwd())
            SocketUtil.writeGB2312StringZ(sb, URLEncoder.encode(SocketUtil.getBase64(loginPassword), "utf-8"));
        else
            SocketUtil.writeGB2312StringZ(sb, loginPassword);

        SocketUtil.writeGB2312StringZ(sb, USER_TYPE);

        SocketUtil.writeUnsignedInt(sb, yzmId);
        SocketUtil.writeGB2312StringZ(sb, yzmCode);

        //发送登录请求
        return SocketUtil.send(os, in, sb, "LOGIN");
    }

    private void loadUserInfo(JSONObject data, UserInfo riskUserInfoDto) {
        JSONObject item = data.getJSONObject("item");
        if (item != null) {
            riskUserInfoDto.setNickname(item.getString("@custName"));
            riskUserInfoDto.setLicenseCode(item.getString("@custLicenceCode"));
            riskUserInfoDto.setAddress(item.getString("@custAddress"));
            riskUserInfoDto.setPhone(item.getString("@custPhoneNumber"));
            riskUserInfoDto.setManager(item.getString("@custMgrCode"));
            riskUserInfoDto.setCustSize(item.getString("@operationScaleName"));
            riskUserInfoDto.setRetailType(item.getString("@operationClassName"));
            riskUserInfoDto.setPeriods(item.getString("@orderFreqName"));
            riskUserInfoDto.setMarketType(item.getString("@businessEnviName"));
            riskUserInfoDto.setCustType(item.getString("@serviceClassName"));
        }
    }

    private List<OrderInfo> loadUserOrder(JSONArray array) {
        List<OrderInfo> orderDtoList = new ArrayList<>();
        for (Iterator it = array.iterator(); it.hasNext(); ) {
            JSONObject item = (JSONObject) it.next();
            OrderInfo order = new OrderInfo();
            order.setOrderNo(item.getString("@id"));
            order.setAmount(item.getString("@demandQuant"));
            order.setTotalPrice(item.getString("@money"));
            order.setOrderTime(item.getString("@date").split(" ")[0]);
            orderDtoList.add(order);
        }
        return orderDtoList;
    }

    private JSONArray parseOrderList(StringBuffer data) throws UnsupportedEncodingException {
        int index = data.lastIndexOf(XML_SPLIT_CODE);
        String split = data.substring(index - 6, index + 16);
        String xmlData = data.toString().replaceAll(split, "");
        List<String> readStr = new ArrayList<>();
        for (int i = 0; i < xmlData.length() / 2; i++)
            readStr.add(xmlData.substring(i * 2, i * 2 + 2));
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlData = SocketUtil.hexStringListToString(readStr, "utf-8");
        System.out.println(xmlData);
        xmlData = xmlData.split("<orders>")[1].split("</orders>")[0];
        xmlData = "<orders>" + xmlData + "</orders>";
        return (JSONArray) xmlSerializer.read(xmlData);
    }

    private static void loadMapReady(InputStream in, OutputStream os, SocketConfigDto config) throws IOException, InterruptedException {
        StringBuffer sb = new StringBuffer();
        sb.append(config.getLoadMapReadyReq());
        SocketUtil.send(os, in, sb, "LOAD_MAP_READY");
    }


}
