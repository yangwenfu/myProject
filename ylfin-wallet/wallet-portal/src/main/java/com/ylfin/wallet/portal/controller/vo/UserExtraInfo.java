package com.ylfin.wallet.portal.controller.vo;

import com.xinyunlian.jinfu.user.enums.EHouseProperty;
import com.xinyunlian.jinfu.user.enums.EMarryStatus;
import lombok.Data;

@Data
public class UserExtraInfo {

    private String city = "";

    private String area = "";

    private String street = "";

    private String address = "";

    private String marryStatus;
    //住宅性质
    private String houseProperty;
    /**
     * 结婚证照片
     */
    private String marryCertificatePic = "";
    /**
     * 户口本户主页照片
     */
    private String householderBookletPic;
    /**
     * 户口本照片
     */
    private String residenceBookletPic = "";
}
