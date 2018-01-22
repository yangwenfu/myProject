package com.xinyunlian.jinfu.loan.dto.user;

import java.io.Serializable;

/**
 * @author Willwang
 */
public class OCRDto implements Serializable{

    /**
     * OCR传递过来的BASE64的图片信息
     */
    private String imageContent;

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }
}
