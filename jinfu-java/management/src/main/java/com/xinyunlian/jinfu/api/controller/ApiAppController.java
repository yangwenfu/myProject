package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.dto.ApiAppForceUpdateDto;
import com.xinyunlian.jinfu.app.dto.AppVersionControlDto;
import com.xinyunlian.jinfu.app.service.AppVersionControlService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongfangchao on 2017/6/20/0020.
 */
@RestController
@RequestMapping("/open-api/app")
public class ApiAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiAppController.class);

    @Autowired
    private AppVersionControlService appVersionControlService;

    /**
     * 检查APP版本信息
     * @param requestBody
     * @return
     */
    @PostMapping(value = "/checkAppVersion")
    public ResultDto checkAppVersion(@RequestBody ApiAppForceUpdateDto requestBody){
        try {
            String sign = EncryptUtil.encryptMd5(requestBody.getAppName(), requestBody.getSourceType().substring(1, 3));
            if (!sign.equals(requestBody.getSign())){
                return ResultDtoFactory.toNack("验签失败");
            }

            AppVersionControlDto appVersionControlDto = appVersionControlService.checkAppVersion(requestBody.getAppName(), requestBody.getSourceType(), requestBody.getVersion());
            if (appVersionControlDto != null){
                return ResultDtoFactory.toCustom("APP_FORCE_UPDATE", "发现新版本，请重新下载更新", appVersionControlDto);
            }else {
                return ResultDtoFactory.toNack("已经是最新的APP了");
            }
        } catch (Exception e) {
            LOGGER.error("检查app版本异常", e);
            return ResultDtoFactory.toBizError(e.getMessage(), e);
        }
    }

}
