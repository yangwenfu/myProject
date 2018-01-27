package com.xinyunlian.jinfu.risk.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.risk.dao.UserCreditInfoDao;
import com.xinyunlian.jinfu.risk.domain.UserCreditQueryResultDto;
import com.xinyunlian.jinfu.risk.dto.req.*;
import com.xinyunlian.jinfu.risk.dto.resp.UserCreditInfoDto;
import com.xinyunlian.jinfu.risk.entity.UserCreditInfoPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.List;

/**
 * Created by bright on 2016/11/16.
 */
@Service
public class UserCreditServiceImpl implements UserCreditService {
    @Autowired
    private UserCreditInfoDao userCreditInfoDao;

    @Override
    public UserCreditInfoDto getUserCreditInfo(String userId) throws BizServiceException{
        Specification<UserCreditInfoPo> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            List<Expression<Boolean>> expressions = predicate.getExpressions();

            expressions.add(cb.equal(root.get("userId"), userId));

            return predicate;
        };

        // Fixme: 目前使用分页获取最新的一条记录
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "createTs"));
        Page<UserCreditInfoPo> page = userCreditInfoDao.findAll(specification, pageable);
        UserCreditInfoDto userCreditInfoDto = null;
        if(page.hasContent()){
            UserCreditInfoPo userCreditInfoPo = page.getContent().get(0);
            userCreditInfoDto = ConverterService.convert(userCreditInfoPo, UserCreditInfoDto.class);
        }
        return userCreditInfoDto;
    }

    @Override
    public UserCreditInfoDto retrieveUserCreditInfo(UserCreditInfoReqDto userCreditInfoReqDto) throws BizServiceException{
        CreditPhoneInfoReqDto creditPhoneInfoReqDto =
                ConverterService.convert(userCreditInfoReqDto, CreditPhoneInfoReqDto.class);
        creditPhoneInfoReqDto.setContactMain(userCreditInfoReqDto.getPhone());
        StringBuilder sb = new StringBuilder();
        for(String contact: userCreditInfoReqDto.getLinkedContacts()){
            sb.append(contact);
            sb.append(",,,");
        }
        if (sb.length()>0){
            sb.deleteCharAt(sb.length()-1);
        }
        creditPhoneInfoReqDto.setContacts(sb.toString());

        CreditOverdueInfoReqDto creditOverdueInfoReqDto =
                ConverterService.convert(userCreditInfoReqDto, CreditOverdueInfoReqDto.class);

        CreditLoanInfoReqDto creditLoanInfoReqDto =
                ConverterService.convert(userCreditInfoReqDto, CreditLoanInfoReqDto.class);

        CreditBlacklistInfoReqDto creditBlacklistInfoReqDto =
                ConverterService.convert(userCreditInfoReqDto, CreditBlacklistInfoReqDto.class);

        CreditLogStatisticsReqDto creditLogStatisticsReqDto =
                ConverterService.convert(userCreditInfoReqDto, CreditLogStatisticsReqDto.class);

        CreditIdCheckReqDto creditIdCheckReqDto =
                ConverterService.convert(userCreditInfoReqDto, CreditIdCheckReqDto.class);

        CreditBankCardAuthReqDto creditBankCardAuthReqDto =
                ConverterService.convert(userCreditInfoReqDto, CreditBankCardAuthReqDto.class);

        String phoneInfo = getInfo(AppConfigUtil.getConfig("credit.pingan.domain")+"/b/phoneinfo", creditPhoneInfoReqDto);
        String overdueInfo = getInfo(AppConfigUtil.getConfig("credit.pingan.domain")+"/b/overdueClassify", creditOverdueInfoReqDto);
        String loanInfo = getInfo(AppConfigUtil.getConfig("credit.pingan.domain")+"/b/loanClassify", creditLoanInfoReqDto);
        String blacklistInfo = getInfo(AppConfigUtil.getConfig("credit.pingan.domain")+"/b/blacklist", creditLogStatisticsReqDto);
        String logStatistics = getInfo(AppConfigUtil.getConfig("credit.pingan.domain")+"/b/logStatistics", creditLogStatisticsReqDto);
        // TODO 身份证和银行卡验证暂时不上
//        String idCheck = getInfo(AppConfigUtil.getConfig("credit.pingan.domain")+"/idCheck", creditIdCheckReqDto);
//        String bankCardAuth = getInfo(AppConfigUtil.getConfig("credit.pingan.domain")+"/bankcardauth", creditBankCardAuthReqDto);

        UserCreditInfoDto userCreditInfoDto = new UserCreditInfoDto();
        userCreditInfoDto.setUserId(userCreditInfoReqDto.getUserId());
        userCreditInfoDto.setQueryDate(userCreditInfoReqDto.getQueryDate());
        userCreditInfoDto.setPhoneInfo(phoneInfo);
        userCreditInfoDto.setOverDueInfo(overdueInfo);
        userCreditInfoDto.setLoanInfo(loanInfo);
        userCreditInfoDto.setBlacklistInfo(blacklistInfo);
        userCreditInfoDto.setLogStatitics(logStatistics);
//        userCreditInfoDto.setIdCheck(idCheck);
//        userCreditInfoDto.setBankCardAuth(bankCardAuth);
        return userCreditInfoDto;
    }

    @Override
    public void saveUserCreditInfo(UserCreditInfoDto userCreditInfoDto) {
        UserCreditInfoPo userCreditInfoPo = ConverterService.convert(userCreditInfoDto, UserCreditInfoPo.class);
        userCreditInfoDao.save(userCreditInfoPo);
    }

    private void fillAuthHeader(CreditReqBaseDto creditReqBaseDto) throws BizServiceException{
        try {
            String pname = AppConfigUtil.getConfig("credit.pingan.pname");
            String pkey = AppConfigUtil.getConfig("credit.pingan.pkey");
            String ptime = String.valueOf(System.currentTimeMillis());
            String vkey = EncryptUtil.encryptMd5(pkey + "_" + ptime + "_" + pkey);
            creditReqBaseDto.setPname(pname);
            creditReqBaseDto.setPtime(ptime);
            creditReqBaseDto.setVkey(vkey);
        }catch (Exception e){
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }
    }

    private String getInfo(String url, CreditReqBaseDto request){
        fillAuthHeader(request);
        String result = null;
        try {
            result = OkHttpUtil.postForm(url,JsonUtil.toMap(request), true);
        } catch (IOException e) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "接口请求失败", e);
        }
        return result;
    }
}
