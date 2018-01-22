package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * @author Willwang
 */
public class OCRDto implements Serializable{

    /**
     * OCR传递过来的BASE64的图片信息
     */
    private String idCardFrontPicBase64;

    public String getIdCardFrontPicBase64() {
        return idCardFrontPicBase64;
    }

    public void setIdCardFrontPicBase64(String idCardFrontPicBase64) {
        this.idCardFrontPicBase64 = idCardFrontPicBase64;
    }
}
