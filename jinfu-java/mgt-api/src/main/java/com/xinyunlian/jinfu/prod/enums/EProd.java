package com.xinyunlian.jinfu.prod.enums;

/**
 * Created by DongFC on 2016-11-07.
 */
public enum EProd {

    S01001("S01001", "店铺保"), L01001("L01001", "云速贷"),S01002("S01002", "保骉车险"), L01002("L01002", "云随贷"), F01001("F01001", "中融基金"), L01003("L01003", "云小钱"), P01001("P01001", "云联云码")
    , L01004("L01004", "云联车闪贷");

    private String code;
    private String text;

    EProd(String code, String text) {
        this.code = code;
        this.text = text;
    }

    EProd() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
