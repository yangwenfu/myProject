package com.xinyunlian.jinfu.picture.enums;

/**
 * Created by KimLL on 2016/8/31.
 */
public enum EPictureType {
    CARD_FRONT("USER_FRONT","用户身份证正面"),
    CARD_BACK("USER_BACK","用户身份证背面"),
    HELD_ID_CARD("HELD_ID_CARD","手持身份证"),
    HOUSE_CERTIFICATE("HOUSE_CERTIFICATE","房产证"),
    MARRY_CERTIFICATE("MARRY_CERTIFICATE","结婚证"),
    HOUSEHOLDER_BOOKLET("HOUSEHOLDER_BOOKLET","户口本户主页"),
    RESIDENCE_BOOKLET("RESIDENCE_BOOKLET","户口本本人页"),
    STORE_LICENCE("STORE_LICENCE","营业执照"),
    STORE_TOBACCO("STORE_TOBACCO","烟草证"),
    STORE_HEADER("STORE_HEADER","店铺门头照"),
    STORE_INNER("STORE_INNER","店铺内部照片"),
    STORE_OUTSIDE("STORE_OUTSIDE","店铺周边照片"),
    STORE_HOUSE_CERTIFICATE("STORE_HOUSE_CERTIFICATE","店铺房产证"),
    STORE_NO_TOBACCO_LICENCE("STORE_NO_TOBACCO_LICENCE","非烟许可证号"),

    BANK_CARD_FRONT("BANK_CARD_FRONT","银行卡正面"),
    ACCOUNT_LICENCE("ACCOUNT_LICENCE","开户许可证"),

    CAR_REGISTER_CERTIFICATE("CAR_REGISTER_CERTIFICATE","机动车登记证书"),
    CAR_DRIVING_LICENSE("CAR_DRIVING_LICENSE","机动车行驶证"),
    HOUSE_PROPERTY ("HOUSE_PROPERTY","房产证明"),
    BANK_TRADE("BANK_TRADE","银行流水"),

    //小贷-网纹照片、活体照片
    LINE("LINE", "网纹照片"),
    LIVE("LIVE", "活体照片")
    ;

    private String code;

    private String text;

    EPictureType(String code, String text) {
        this.code = code;
        this.text = text;
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
