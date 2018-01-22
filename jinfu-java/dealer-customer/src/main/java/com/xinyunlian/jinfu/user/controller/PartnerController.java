package com.xinyunlian.jinfu.user.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.user.dto.DealerUserQRInfo;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by King on 2017/3/14.
 */
@Controller
@RequestMapping(value = "partner")
public class PartnerController {
    @Autowired
    private DealerUserService dealerUserService;

    /**
     * 扫一扫获取分销员信息
     * @return
     */
    @RequestMapping(value = "/dealerUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> scan(@RequestParam String qrCode, @RequestParam String qrCodeKey,
                                  @RequestParam String createTime) {
        DealerUserQRInfo qrInfo = new DealerUserQRInfo();
        qrInfo.setQrCode(qrCode);
        qrInfo.setQrType("partner");
        qrInfo.setQrKey(qrCodeKey);
        qrInfo.setCreateTime(createTime);
        qrInfo = dealerUserService.checkQrInfo(qrInfo);
        if(!qrInfo.isQrStatus()){
            return ResultDtoFactory.toNack("该二维码已失效请重新扫码！");
        }

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),qrInfo);

    }


}
