package com.xinyunlian.jinfu.push.dto;

import java.io.Serializable;

/**
 * Created by apple on 2017/1/10.
 */
public class PushImageDto implements Serializable {

    private String imagePath;

    private String fileAddr;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFileAddr() {
        return fileAddr;
    }

    public void setFileAddr(String fileAddr) {
        this.fileAddr = fileAddr;
    }
}
