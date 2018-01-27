package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.dealer.dto.DealerUserSubscribeDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserSubscribeType;
import com.xinyunlian.jinfu.dealer.service.DealerUserSubscribeService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2017年08月02日.
 */
@Controller
@RequestMapping("open-api/wechat/checkInfo")
public class ApiWeChatController {

    @Autowired
    private DealerUserSubscribeService dealerUserSubscribeService;
    @Autowired
    private DealerUserService dealerUserService;

    public static String TOKEN = "yunlianjinfu";

    @RequestMapping(method = RequestMethod.GET)
    public void get(HttpServletRequest request, HttpServletResponse response) {
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败
            if (checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
            out = null;
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        String eventType = map.get("Event");
        if ("subscribe".equals(eventType)) {//关注事件
            String eventKey = map.get("EventKey");
            if (StringUtils.isEmpty(eventKey)) {
                return;
            }
            DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(eventKey.replaceAll("qrscene_", StringUtils.EMPTY));
            if (dealerUserDto == null) {
                return;
            }
            DealerUserSubscribeDto dealerUserSubscribeDto = new DealerUserSubscribeDto();
            dealerUserSubscribeDto.setDealerId(dealerUserDto.getDealerId());
            dealerUserSubscribeDto.setUserId(dealerUserDto.getUserId());
            dealerUserSubscribeDto.setOpenId(map.get("FromUserName"));
            dealerUserSubscribeDto.setWechatType(EDealerUserSubscribeType.YLJF);
            dealerUserSubscribeService.createDealerUserSubscribe(dealerUserSubscribeDto);
        } else if ("unsubscribe".equalsIgnoreCase(eventType)) {//取消关注

        } else if ("click".equals(eventType)) {//click：点击推事件

        } else if ("view".equals(eventType)) {//view：跳转URL

        } else if ("view".equals(eventType)) {//view：跳转URL

        } else if ("scancode_push".equals(eventType)) {//scancode_waitmsg：扫码推事件

        } else if ("scancode_waitmsg".equals(eventType)) {//scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框

        } else if ("pic_sysphoto".equals(eventType)) {//pic_sysphoto：弹出系统拍照发图

        } else if ("pic_photo_or_album".equals(eventType)) {//pic_photo_or_album：弹出拍照或者相册发图

        } else if ("pic_weixin".equals(eventType)) {//pic_weixin：弹出微信相册发图器

        } else if ("location_select".equals(eventType)) {//location_select：弹出地理位置选择器

        } else if ("media_id".equals(eventType)) {//media_id：下发消息（除文本消息）

        } else if ("view_limited".equals(eventType)) {//view_limited：跳转图文消息URL

        } else if ("card_pass_check".equalsIgnoreCase(eventType)) {//卡券审核通过

        } else if ("card_not_pass_check".equalsIgnoreCase(eventType)) {//卡券审核不通过

        } else if ("user_get_card".equalsIgnoreCase(eventType)) {//卡券领取事件

        } else if ("user_del_card".equalsIgnoreCase(eventType)) {//卡券删除

        } else if ("user_consume_card".equalsIgnoreCase(eventType)) {// 卡券核销

        } else {//普通文本消息
            //未知
        }
        //暂时空着，在这里可处理用户请求
    }

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)) {
            return false;
        }
        String[] arr = new String[]{TOKEN, timestamp, nonce};
        // 将token、timestamp、nonce三个参数进行字典排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 将sha1加密后的字符串可与signature对比
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

}
