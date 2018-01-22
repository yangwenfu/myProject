package com.ylfin.wallet.portal.controller.vo;

import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;
import lombok.Data;

import java.util.Date;

/**
 * Create by yangwenfu on 2018/1/16
 */
@Data
public class DealerUserInfo {

    private String userId;

    private String dealerId;

    private String mobile;

    private String name;

    private String linePic;


}
