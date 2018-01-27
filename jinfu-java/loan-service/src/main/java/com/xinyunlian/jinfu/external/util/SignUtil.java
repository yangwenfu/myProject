package com.xinyunlian.jinfu.external.util;



import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/*****
 * 雁阵签名工具类
 * @author Chenweiwei
 *
 */
public class SignUtil {
    /**
	 * 获取RSA
	 * @param method：消息方法名
	 * @param ver： 版本
	 * @param channelId： 渠道ID
	 * @param privateKey： 用于RSA签名的私钥
	 * @param param：消息体对象(可以为一个对象或者HashMap容器，但不能为JSON字符串)
	 * @return
	 */
	public static String generateRSASign(String method, String ver, String channelId, String privateKey, Object param){
		HashMap<String, Object> msg = new HashMap<String, Object>();
		try {
			msg.put("method", method);
			msg.put("ver", ver);
			msg.put("channelId", channelId);
			msg.put("signType", "RSA");
			msg.put("params", param);
			JSONObject jsonObject = GsonUtil.getJsonObject(GsonUtil.getGson().toJson(msg));
			String genSignData = genSignData(jsonObject);
			System.out.println("RSA sign Data:"+genSignData);
			return RSAUtil.sign(privateKey, genSignData);
		} catch (Exception e) {
			return null;
		}
	}

	 /**
	 * 获取MD5签名
	 * @param method：消息方法名
	 * @param ver： 版本
	 * @param channelId： 渠道ID
	 * @param md5Key： MD5签名秘钥
	 * @param param：消息体对象(可以为一个对象或者HashMap容器，但不能为JSON字符串)
	 * @return
	 */
	public static String generateMD5Sign(String method, String ver, String channelId, String md5Key, Object param){
		HashMap<String, Object> msg = new HashMap<String, Object>();
		try {
			msg.put("method", method);
			msg.put("ver", ver);
			msg.put("channelId", channelId);
			msg.put("signType", "MD5");
			msg.put("params", param);

			JSONObject jsonObject = GsonUtil.getJsonObject(GsonUtil.getGson().toJson(msg));
			String genSignData = genSignData(jsonObject);
			genSignData += "&key="+ md5Key;
			System.out.println("MD5 sign Data:"+genSignData);
			return Md5Util.getInstance().md5Digest(genSignData.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}


	/****
	 *  sign签名校验
	 * @param request：完整请求消息的String字符串
	 * @param key：参与sign校验的key (RSA为public key)
	 * @return：签名结果 true or false
	 */
	public static boolean verifySign(String request, String key){
		boolean checkResult = false;
		try {
		//	JSONObject jsonObject = GsonUtil.getJsonObject(GsonUtil.getGson().toJson(request));
			JSONObject jsonObject= new JSONObject().fromObject(request);
			String genSignData = genSignData(jsonObject);
			String sign =  jsonObject.getString("sign");
			sign = sign.replace(" ", "+");
			String signType =  jsonObject.getString("signType");
			if ("MD5".equals(signType)) {
				genSignData += "&key="+key;
				System.out.println("genMD5SignData:"+genSignData);
				String md5Digest = Md5Util.getInstance().md5Digest(genSignData.getBytes("utf-8"));
				System.out.println("generated MD5:"+md5Digest);
				if(sign != null && sign.equals(md5Digest)){
					checkResult = true;
				}else{
					checkResult = false;
				}
			} else if ("RSA".equals(signType)) {
				System.out.println("genRSASignData:"+genSignData);
				checkResult = RSAUtil.checksign(key, genSignData, sign);
			}
		} catch (Exception e) {
			System.out.println("verify sign failed:"+e.getMessage());
			checkResult = false;
		}

		return checkResult;
	}

	/****
	 * 生成排序好的待签名字符串
	 * @param jsonObject：完整请求消息的JSON对象
	 * @return 参与sign签名的排序好的待签名字符串
	 */
    public static String genSignData(JSONObject jsonObject)
	{
		StringBuffer content = new StringBuffer();

		//按照key做首字母升序排列
		List<String> keys = new ArrayList<String>(jsonObject.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);

		//按照顺序拼接待签名字符串
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);

			if ("sign".equals(key) ||
				"statusCode".equals(key) ||
				"errMsg".equals(key)){//去除不需参与签名的内容
				continue;
			}

			String value = (String) jsonObject.getString(key);
			if (null == value) {// 空串不参与签名
				continue;
			}

			content.append((i == 0 ? "" : "&") + key + "=" + value);
		}

		String signSrc = content.toString();
		if (signSrc.startsWith("&")) {
			signSrc = signSrc.replaceFirst("&", "");
		}

		return signSrc;
	}


	/****
	 * test例子
	 * @param args
	 */
	public static void main(String[] args) {

		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJiZpfSH7DU0CBNdbn0uZohSGFmK6NGMyNPd4pn6oGtZd8PEvcetZdqDWIBUf8iyQC776n2jsl0TOIgqFL9U56RQxXu880EhODPSC8K2NGNfG8czFaJzpIIacStQA/vJ3BVOFXnM14IzgRnTuDcIJvw0UdMvlP10PtC+p50/VKQdAgMBAAECgYBZhuMfKFNDD2ihe9IjrQJIfel9NwrKTc9QxT95WNmewVmLSDSTHj7ASQi2GVzysOkI55C170DobCthb1bGvZg8HZiWgy3v7hP0RFp+ceqpOBaY09U+giJZ0IG/rXtxIk5Tf7nlC7+QO7CO2bCFYBcOdA/kAIRSeva3zR9Wyns3IQJBAMjck+qn3yQFgdWZkZ2xRFFFYoHgtvZihSlacmpTvJ/km61tJUJrKbDUlLY6a5bs9ZCOcDAgKYgSxFsGRAZphtUCQQDCfYdMrOCsiPnn1zKT6f04CcZl45aKtU0e0epdqvuQsUrp2tcz+eRUkUVqMpw2OcoOZDv/jdez1gGk2k9L09wpAkEAxFps9t14UzxW+boQEXmy8UfEznYgJaeVySEz7CFDqYLPdK/X1p/vt394iNN/TaEDRXcY0NMABpdiACGV6jbKqQJAcep4fw6bIjOwvHytYTmiWVpQXIlrOZ9rpmupbGejpWJS0JqfhhAODwJvt/4gxRogIUHQaqS3/NuSZu5/l5hl0QJAYLquPJZffJyGfRZAvbgfnKRSSWA03gwNeZrxuZ60YCwjW09MBGoZUTk31k8A9G7Bpi0JaZXnx3suLJ+hoNkLsA==";
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYmaX0h+w1NAgTXW59LmaIUhhZiujRjMjT3eKZ+qBrWXfDxL3HrWXag1iAVH/IskAu++p9o7JdEziIKhS/VOekUMV7vPNBITgz0gvCtjRjXxvHMxWic6SCGnErUAP7ydwVThV5zNeCM4EZ07g3CCb8NFHTL5T9dD7QvqedP1SkHQIDAQAB";

		//注：如果数值存在类似60.0的则需要变成60再进行加密，verifySign时不做要求
		//建议：param类型使用Map或者是po,不使用json,因为对象生成json的时候可能吧60变成60.0导致签名校验失败
		String param = "{\"balance\":2080.53,\"commissions\":60,\"loanAmount\":2000,\"refunds\":[{\"periodNumber\":1,\"dueDate\":\"20170108\",\"dueAmount\":693.51,\"status\":3},{\"periodNumber\":2,\"dueDate\":\"20170207\",\"dueAmount\":693.51,\"status\":3},{\"periodNumber\":3,\"dueDate\":\"20170309\",\"dueAmount\":693.51,\"status\":3}],\"loanDate\":\"20161209\"}";
		String ver = "2.0";
		String method = "getLoanDetailInfo";
		String channelId = "2";

		String md5Sign = generateMD5Sign(method, ver, channelId, "1234", param);
		System.out.println("MD5 sign:"+md5Sign);

		String rsaSign = generateRSASign(method, ver, channelId, privateKey, param);
		System.out.println("RSA sign:"+rsaSign);

		String request = "{\"method\":\"getLoanDetailInfo\",\"ver\":\"2.0\",\"channelId\":\"2\",\"params\":{\"balance\":2080.53,\"commissions\":60,\"loanAmount\":2000,\"refunds\":[{\"periodNumber\":1,\"dueDate\":\"20170108\",\"dueAmount\":693.51,\"status\":3},{\"periodNumber\":2,\"dueDate\":\"20170207\",\"dueAmount\":693.51,\"status\":3},{\"periodNumber\":3,\"dueDate\":\"20170309\",\"dueAmount\":693.51,\"status\":3}],\"loanDate\":\"20161209\"},\"signType\":\"RSA\",\"sign\":\""+rsaSign+"\",\"statusCode\":200}";

		boolean checksign = verifySign(request, publicKey);//RSA
		//boolean checksign = verifySign(request, "1234");//MD5

		System.out.println("sign verify result:"+checksign);


	}
}
