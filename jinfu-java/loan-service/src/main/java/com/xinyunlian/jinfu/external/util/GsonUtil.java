package com.xinyunlian.jinfu.external.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;

public class GsonUtil {

	/**
	 * Gson 只转换添加注解的字段
	 */
	public static Gson getExcludeGson(){
		return GsonFactory.getExcludeGson();
	}

	/**
	 * 获取普通Gson对象
	 */
	public static Gson getGson(){
		return GsonFactory.getGson();
	}

	/**
	 * 根据key获取json
	 */
	public static Object getJsonByKey(String json, String key){
		try {
			return JSONObject.fromObject(json).get(key);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @param jsonStr json字符串
	 * @return 使用json库解析json
	 */
	public static JSONObject getJsonObject(String jsonStr){
		return JSONObject.fromObject(jsonStr);
	}

	private static class GsonFactory{
		public final static String DATE_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
		public static Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT1).create();
		public static GsonBuilder builder = new GsonBuilder();

		private static Gson getGson(){
			return gson;
		}

		private static Gson getExcludeGson(){
			builder.excludeFieldsWithoutExposeAnnotation();
			return builder.create();
		}

	}
}
