package com.xinyunlian.jinfu.api.service;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.HttpUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 云码合作方推送通知
 * Created by menglei on 2017年06月02日.
 */
@Service
public class ApiThirdServiceImpl implements ApiThirdService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApiThirdService.class);

    /**
     * 云码推送通知第三方
     *
     * @param params
     * @return
     * @throws BizServiceException
     */
    @Override
    public void thirdNotify(Map<String, String> params, String notifyUrl) throws BizServiceException {
        LOGGER.info("start notify third yunma,params:{},notifyUrl:{}", params, notifyUrl);
        try {
            String result = HttpUtil.doPost(notifyUrl, "application/json", JsonUtil.toJson(params));
            LOGGER.info("end notify third yunma result,result:{}", result);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
    }

}
