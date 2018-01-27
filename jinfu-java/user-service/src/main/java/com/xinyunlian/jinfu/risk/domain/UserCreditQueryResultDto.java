package com.xinyunlian.jinfu.risk.domain;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/17.
 */
public class UserCreditQueryResultDto  implements Serializable {
    /* 返回查询结果编码 */
    private String result;

    /* result不为0时的错误信息描述。 */
    private String message;

    /* 结果数据，将会直接以文本形式保存 */
    private JsonNode data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }
}
