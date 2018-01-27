package com.xinyunlian.jinfu.pingan.dto;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class PinganOpenApiRetCode {

    //成功
    public static final String SUCCESS = "999999";

    //接口未定义
    public static final String INTERFACE_UNDEFINE = "000404";

    //失败，详细原因在错误信息中体现
    public static  final String FAIL = "000500";

    //系统错误
    public static final String SYSTEM_ERROR = "000000";

    //重复承保
    public static final String DUPLICATE_INSURED = "888888";

    //核保未通过，转人工复核
    public static final String AUDIT_FAILED = "777777";

    //调用正常
    public static final String RET_SUCCESS = "0";

    //非法的access_token 请重新获取token
    public static final String ILLEGAL_TOKEN = "13002";

    //已失效的access_token 请重新获取token
    public static final String INVALID_TOKEN = "13012";

}
