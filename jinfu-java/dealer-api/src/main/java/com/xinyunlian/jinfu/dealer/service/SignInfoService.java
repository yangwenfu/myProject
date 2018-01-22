package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.dealer.dto.SignInfoDto;
import com.xinyunlian.jinfu.dealer.dto.SignInfoViewDto;
import com.xinyunlian.jinfu.dealer.dto.SignInfoViewSearchDto;

import java.util.List;

/**
 * Created by menglei on 2017年05月03日.
 */
public interface SignInfoService {

    SignInfoViewSearchDto getSignInfoPage(SignInfoViewSearchDto signInfoViewSearchDto);

    List<SignInfoViewDto> getSignInfoReport(SignInfoViewSearchDto signInfoViewSearchDto);

    void createSignIn(SignInfoDto signInfoDto) throws BizServiceException;

    void updateSignOut(SignInfoDto signInfoDto) throws BizServiceException;

    SignInfoDto getByUserIdAndStoreId(String userId, Long storeId);

    SignInfoDto getById(Long id);

    List<SignInfoViewDto> getSignInfoList(SignInfoViewSearchDto signInfoViewSearchDto);

}
