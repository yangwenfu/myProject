package com.xinyunlian.jinfu.finaccbankcard.enums;

/**
 * Created by dongfangchao on 2016/11/28/0028.
 */
public enum ECertificateType {

    ID_CARD_NO("ID_CARD_NO","0"), PASSPORT("PASSPORT","1"), CERTIFICATE_OF_OFFICERS("CERTIFICATE_OF_OFFICERS","2"),
    CERTIFICATE_OF_SOLDIER("CERTIFICATE_OF_SOLDIER","3"), HK_MACAO_CERTIFICATE("HK_MACAO_CERTIFICATE", "4"),
    RESIDENCE_BOOKLET("RESIDENCE_BOOKLET", "5"), FOREIGN_PASSPORT("FOREIGN_PASSPORT", "6"), OTHERS_CERTIFICATE("OTHERS_CERTIFICATE", "7"),
    CIVILIAN_CERTIFICATE("CIVILIAN_CERTIFICATE","8"), POLICE_OFFICER_CERTIFICATE("POLICE_OFFICER_CERTIFICATE","9"),
    MTP("MTP","A");

    private String code;
    private String text;

    ECertificateType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    ECertificateType() {
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
