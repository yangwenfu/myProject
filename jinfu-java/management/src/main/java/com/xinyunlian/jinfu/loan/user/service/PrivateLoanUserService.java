package com.xinyunlian.jinfu.loan.user.service;

import com.xinyunlian.jinfu.car.dto.UserCarDto;
import com.xinyunlian.jinfu.car.service.UserCarService;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.house.dto.UserHouseDto;
import com.xinyunlian.jinfu.house.service.UserHouseService;
import com.xinyunlian.jinfu.loan.audit.service.PrivateLoanAuditService;
import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanApplUserService;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.risk.dto.resp.UserCreditInfoDto;
import com.xinyunlian.jinfu.spider.dto.resp.RiskUserInfoDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserBankAcctDto;
import com.xinyunlian.jinfu.user.dto.UserBankAcctTrdDto;
import com.xinyunlian.jinfu.user.dto.ext.*;
import com.xinyunlian.jinfu.user.service.UserBankAcctService;
import com.xinyunlian.jinfu.user.service.UserBankAcctTrdService;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserLinkmanService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Willwang on 2017/3/4.
 */

@Service
public class PrivateLoanUserService {

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private LoanApplUserService loanApplUserService;

    @Autowired
    private UserLinkmanService userLinkmanService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserHouseService userHouseService;

    @Autowired
    private UserCarService userCarService;

    @Autowired
    private UserBankAcctService userBankAcctService;

    @Autowired
    private UserBankAcctTrdService userBankAcctTrdService;

    @Autowired
    private PrivateLoanAuditService privateLoanAuditService;
    @Autowired
    private PictureService pictureService;

    /**
     * 获得用户基础信息
     *
     * @param userId
     * @param applId
     * @return
     */
    public Object getBase(String userId, String applId) {
        if (this.isFromBackup(applId)) {
            return this.getApplUser(applId, "base");
        }
        return this.getUserExt(userId, "base");
    }

    /**
     * 获得用户家庭信息
     *
     * @param userId
     * @param applId
     * @return
     */
    public Object getFamily(String userId, String applId) {
        if (this.isFromBackup(applId)) {
            return this.getApplUser(applId, "family");
        }
        return this.getUserExt(userId, "family");
    }

    /**
     * 获取用户财务信息
     *
     * @param userId
     * @param applId
     * @return
     */
    public Object getFin(String userId, String applId) {
        if (this.isFromBackup(applId)) {
            return this.getApplUser(applId, "fin");
        }
        return this.getUserExt(userId, "fin");
    }

    /**
     * 获取用户社保公积金信息
     *
     * @param userId
     * @param applId
     * @return
     */
    public Object getFund(String userId, String applId) {
        if (this.isFromBackup(applId)) {
            return this.getApplUser(applId, "fund");
        }
        return this.getUserExt(userId, "fund");
    }

    /**
     * 获取用户联系人信息
     * @param userId
     * @param applId
     * @return
     */
    public Object getLinkman(String userId, String applId){
        return userLinkmanService.findByUserId(userId);
    }

    /**
     * 获取用户店铺信息
     * @param userId
     * @param applId
     * @return
     */
    public Object getStore(String userId, String applId){
        if (this.isFromBackup(applId)) {
            return this.getApplUser(applId, "store");
        }
        return storeService.findByUserId(userId);
    }

    /**
     * 获取房屋信息
     * @param userId
     * @param applId
     */
    public Object getHouse(String userId, String applId){
        if (this.isFromBackup(applId)) {
            return this.getApplUser(applId, "house");
        }

        List<UserHouseDto> userHouseDtos = userHouseService.list(userId);
        if(!CollectionUtils.isEmpty(userHouseDtos)){
            userHouseDtos.forEach(userHouseDto -> {
                userHouseDto.setPictureDtos(pictureService
                        .list(userHouseDto.getId().toString(),EPictureType.HOUSE_PROPERTY));
            });
        }
        return userHouseDtos;
    }

    /**
     * 获取用户车辆信息
     * @param userId
     * @param applId
     */
    public Object getCar(String userId, String applId){
        if (this.isFromBackup(applId)) {
            return this.getApplUser(applId, "car");
        }
        List<UserCarDto> userCarDtos =  userCarService.list(userId);
        if(!CollectionUtils.isEmpty(userCarDtos)){
            userCarDtos.forEach(userCarDto -> {
                List<PictureDto> pictureDtos = pictureService.list(userCarDto.getId().toString());
                pictureDtos.forEach(pictureDto -> {
                    if(pictureDto.getPictureType() == EPictureType.CAR_DRIVING_LICENSE ||
                            pictureDto.getPictureType() == EPictureType.CAR_REGISTER_CERTIFICATE){
                        userCarDto.getPictureDtos().add(pictureDto);
                    }
                });

            });
        }
        return userCarDtos;
    }

    /**
     * 获取用户银行卡信息
     * @param userId
     * @param applId
     * @return
     */
    public Collection<UserBankAcctDto> getBank(String userId, String applId){
        if(this.isFromBackup(applId)){
            String json = this.getApplUser(applId, "bank");
            if(StringUtils.isEmpty(json)){
                return null;
            }
            List<UserBankAcctDto> userBanks = JsonUtil.toObject(Object.class, json);
            return userBanks;
        }
        List<UserBankAcctDto> userBankAcctDtos = userBankAcctService.list(userId);
        if(!CollectionUtils.isEmpty(userBankAcctDtos)){
            userBankAcctDtos.forEach(userBankAcctDto -> {
                userBankAcctDto.setPictureDtos(pictureService
                        .list(userBankAcctDto.getBankAccountId().toString(), EPictureType.BANK_TRADE));
            });
        }
        return userBankAcctDtos;
    }

    /**
     * 获取银行流水信息
     * @param bankAcctId
     * @param applId
     * @return
     */
    public List<UserBankAcctTrdDto> getBankTrd(Long bankAcctId, String applId){
        if(this.isFromBackup(applId)){
            String json = this.getApplUser(applId, "bank");
            if(StringUtils.isEmpty(json)){
                return null;
            }
            Map<UserBankAcctDto, List<UserBankAcctTrdDto>> userBanks = JsonUtil.toObject(HashMap.class, json);
            Iterator<Map.Entry<UserBankAcctDto, List<UserBankAcctTrdDto>>> it = userBanks.entrySet().iterator();

            while(it.hasNext()){
                Map.Entry<UserBankAcctDto, List<UserBankAcctTrdDto>> entry = it.next();
                UserBankAcctDto bank = entry.getKey();
                if(bankAcctId.equals(bank.getBankAccountId())){
                    return entry.getValue();
                }
            }
            return null;
        }
        return userBankAcctTrdService.list(bankAcctId);
    }

    /**
     * 获取烟草爬虫数据
     * @param userId
     * @param applId
     * @return
     */
    public RiskUserInfoDto getTobaccoSpider(String userId, String applId){
//        if(this.isFromBackup(applId)){
//            String json = this.getApplUser(applId, "tobacco");
//            if(StringUtils.isEmpty(json)){
//                return null;
//            }
//            return JsonUtil.toObject(RiskUserInfoDto.class, json);
//        }
        return privateLoanAuditService.getTobaccoSpider(userId);
    }

    /**
     * 获取征信数据
     * @param userId
     * @param applId
     * @param force
     * @return
     */
    public UserCreditInfoDto getCreditSpider(String userId, String applId, boolean force){
//        if(this.isFromBackup(applId)){
//            LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(applId);
//            if (loanApplUserDto == null) {
//                return null;
//            }
//            UserCreditInfoDto userCredit = new UserCreditInfoDto();
//            userCredit.setPhoneInfo(loanApplUserDto.getCreditPhone());
//            userCredit.setOverDueInfo(loanApplUserDto.getCreditOverdue());
//            userCredit.setLoanInfo(loanApplUserDto.getCreditLoan());
//            userCredit.setBlacklistInfo(loanApplUserDto.getCreditBlacklist());
//            userCredit.setLogStatitics(loanApplUserDto.getCreditStatistics());
//            userCredit.setQueryDate(DateHelper.getWorkDate());
//            return userCredit;
//        }

        return privateLoanAuditService.getCreditSpider(userId, force);
    }

    /**
     * 是否从拷贝数据中进行取值
     *
     * @param applId
     * @return
     */
    private boolean isFromBackup(String applId) {
        return true;
    }

    /**
     * 获取贷款用户信息备份中中的数据
     *
     * @param applId
     * @return
     */
    private String getApplUser(String applId, String which) {
        String result = "";
        LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(applId);
        if (loanApplUserDto == null) {
            return null;
        }

        String prefix = "";
        if(AppConfigUtil.isProdEnv()){
            prefix = "https://www.ylfin.com/upload/jinfu";
        }else{
            prefix = "https://yltest.xylpay.com/upload/iou";
        }

        switch (which) {
            case "base":
            case "family":
            case "fin":
            case "fund":
                result = loanApplUserDto.getUserBase();
                result = this.replaceAuthKey(result, prefix);
                break;
            case "linkman":
                result = loanApplUserDto.getUserLinkman();
                break;
            case "house":
                result = loanApplUserDto.getHouseBase();
                result = this.replaceAuthKey(result, prefix);
                break;
            case "store":
                result = loanApplUserDto.getStoreBase();
                result = this.replaceAuthKey(result, prefix);
                break;
            case "car":
                result = loanApplUserDto.getCarBase();
                result = this.replaceAuthKey(result, prefix);
                break;
            case "bank":
                result = loanApplUserDto.getBankBase();
                result = this.replaceAuthKey(result, prefix);
                break;
            case "tobacco":
                result = loanApplUserDto.getUserTobacco();
                break;
            default:
                break;
        }

        return result;
    }

    private String replaceAuthKey(String json, String prefix){
        String regex = "(http|https)://(.*?)\",";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(json);

        while(m.find()){
            String part = m.group(1) + "://" + m.group(2);
            json = json.replace(part, this.replaceOneAuthKey(part, prefix));
        }

        return json;
    }

    private String replaceOneAuthKey(String url, String prefix){
        if(StringUtils.isEmpty(url)){
            return url;
        }
        url = url.trim();

        //非http地址直接返回
        if(!url.contains("http://") && !url.contains("https://")){
            return url;
        }

        int index = url.indexOf("?auth_key=");

        //去掉?号以后的东西以及前缀
        url =  index > 0 ? url.substring(0, index) : url;
        url = url.replace(prefix, "");

        StringBuilder sb = new StringBuilder();
        return sb.append(prefix).append(StaticResourceSecurity.getSecurityURI(url)).toString();
    }

    private UserExtIdDto getUserExt(String userId, String which) {
        UserExtIdDto userExtIdDto;
        switch (which) {
            case "base":
                userExtIdDto = new UserExtBaseDto();
                break;
            case "family":
                userExtIdDto = new UserExtFamilyDto();
                break;
            case "fin":
                userExtIdDto = new UserExtFinDto();
                break;
            case "fund":
                userExtIdDto = new UserExtFundDto();
                break;
            default:
                userExtIdDto = new UserExtBaseDto();
                break;
        }
        userExtIdDto.setUserId(userId);
        userExtIdDto = userExtService.getUserExtPart(userExtIdDto);
        return userExtIdDto;
    }

}
