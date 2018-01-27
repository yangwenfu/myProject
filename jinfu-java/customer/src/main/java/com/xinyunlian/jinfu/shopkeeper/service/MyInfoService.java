package com.xinyunlian.jinfu.shopkeeper.service;

import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.BeanValidators;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.point.dto.UserScoreboardDto;
import com.xinyunlian.jinfu.point.service.UserScoreService;
import com.xinyunlian.jinfu.shopkeeper.dto.card.*;
import com.xinyunlian.jinfu.shopkeeper.dto.my.StoreEachDto;
import com.xinyunlian.jinfu.shopkeeper.dto.my.UserDto;
import com.xinyunlian.jinfu.sign.service.UserSignInLogService;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.UserLinkmanDto;
import com.xinyunlian.jinfu.user.enums.ELabelType;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserLinkmanService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.user.enums.ERelationship;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.xinyunlian.jinfu.common.util.BeanValidators.isValid;

/**
 * Created by King on 2017/2/16.
 */
@Service
public class MyInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyInfoService.class);
    @Autowired
    private UserExtService userExtService;
    @Autowired
    private UserLinkmanService userLinkmanService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private RiskUserInfoService riskUserInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private BankService bankService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private YMMemberService ymMemberService;
    @Autowired
    private UserSignInLogService userSignInLogService;
    @Autowired
    private UserScoreService userScoreService;

    public UserDto getUser(String userId){
        UserInfoDto userInfoDto = userService.findUserByUserId(userId);

        CardDto cardDto = this.getCard(userId);
        CardCheckDto cardCheck = this.getCardCheck(cardDto);
        UserDto userDto = ConverterService.convert(userInfoDto,UserDto.class);

        long cardCount = 0;

        if(cardCheck.isIdAuthIsFull()){
            userDto.setIdAuthIsFull(true);
            cardCount++;
        }
        if(cardCheck.isBaseIsFull()){
            cardCount++;
        }
        if(cardCheck.isStoreIsFull()){
            cardCount++;
        }
        if(!CollectionUtils.isEmpty(cardDto.getStoreBaseDtos())){
            String mmc = cardDto.getStoreBaseDtos().get(0).getIndustryMcc();
            if(!StringUtils.isEmpty(mmc)) {
                userDto.setAuthInfo(ELabelType.valueOf("MCC" + mmc).getText());
            }
        }
        if(cardCheck.isLinkmanIsFull()){
            cardCount++;
        }

        Boolean signIn = userSignInLogService.checkSignIn(userId, Calendar.getInstance().getTime());

        UserScoreboardDto userScoreboardDto = userScoreService.getUserScoreByUserId(userId);
        List<YMMemberDto> list = ymMemberService.getMemberListByUserId(userId);
        if (userDto != null){
            userDto.setSignIn(signIn);
            Long userScore = userScoreboardDto!=null?userScoreboardDto.getTotalScore():0l;
            userDto.setUserScore(userScore);
            if (!CollectionUtils.isEmpty(list)){
                userDto.setYmMember(true);
            }
        }

        userDto.setBankCardCount(cardDto.getBankCardCount());
        userDto.setCardCount(cardCount);
        return userDto;
    }

    /**
     * 获取卡片完整度状态
     * @param cardDto
     * @return
     */
    public CardCheckDto getCardCheck(CardDto cardDto){
        CardCheckDto cardCheck = new CardCheckDto();
        cardCheck.setIdAuthIsFull(isValid(cardDto.getIdAuthDto()));
        cardCheck.setBaseIsFull(isValidBaseInfo(cardDto.getBaseInfoDto()));
        cardCheck.setStoreIsFull(isValidStore(cardDto.getStoreBaseDtos()));
        cardCheck.setLinkmanIsFull(isValidLinkman(cardDto.getLinkmanDtos()));
        cardCheck.setRiskAuthIsFull(riskUserInfoService.isAuthed(SecurityContext.getCurrentUserId()));
        cardCheck.setBankAuthIsFull(cardDto.getBankCardCount() >0);
        cardCheck.setStoreExtIsFull(isValidStoreExt(cardDto.getStoreExtDtos()));
        return cardCheck;
    }

    /**
     * 获取用户的个人信息并转为卡片信息
     *
     * @param userId
     * @return
     */
    public CardDto getCard(String userId) {
        UserExtDto userExtDto = userExtService.findUserByUserId(userId);
        List<UserLinkmanDto> linkmanDtos = userLinkmanService.findByUserId(userId);
        List<StoreInfDto> storeInfDtos = storeService.findByUserId(userId);
        Long bankCardCount = bankService.countByUserId(userId);
        Long bankCardLoanCount = bankService.countByUserIdSupport(userId);

        IdAuthDto idAuthDto = ConverterService.convert(userExtDto, IdAuthDto.class);

        BaseInfoDto baseInfoDto = ConverterService.convert(userExtDto, BaseInfoDto.class);

        List<LinkmanDto> linkmans = new ArrayList<>();
        if(!CollectionUtils.isEmpty(linkmanDtos)) {
            for (UserLinkmanDto linkmanDto : linkmanDtos) {
                LinkmanDto linkman = ConverterService.convert(linkmanDto, LinkmanDto.class);
                linkmans.add(linkman);
            }
        }

        List<StoreBaseDto> stores = new ArrayList<>();
        List<StoreExtDto> storeExtDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(storeInfDtos)) {
            for (StoreInfDto storeInfDto : storeInfDtos) {
                StoreBaseDto store = ConverterService.convert(storeInfDto, StoreBaseDto.class);
                StoreExtDto storeExtDto = ConverterService.convert(storeInfDto, StoreExtDto.class);

                IndustryDto industryDto = industryService.getByMcc(storeInfDto.getIndustryMcc());
                store.setIndustryMcc(industryDto.getMcc());
                store.setLicenceName(industryDto.getLicenceName());
                store.setStoreLicence(industryDto.getStoreLicence());
                store.setIndName(industryDto.getIndName());

                stores.add(store);
                storeExtDtos.add(storeExtDto);
            }
        }

        CardDto cardDto = new CardDto();
        cardDto.setIdAuthDto(idAuthDto);
        cardDto.setBaseInfoDto(baseInfoDto);
        cardDto.setStoreBaseDtos(stores);
        cardDto.setLinkmanDtos(linkmans);
        cardDto.setBankCardCount(bankCardCount);
        cardDto.setBankCardLoanCount(bankCardLoanCount);
        cardDto.setStoreExtDtos(storeExtDtos);

        return cardDto;
    }

    public StoreExtCheckDto chechStoreExt(String userId){
        StoreExtCheckDto storeExtCheckDto = new StoreExtCheckDto();
        List<StoreEachDto> storeEachDtos = new ArrayList<>();

        List<StoreInfDto> storeInfDtos = storeService.findByUserId(userId);

        if(!CollectionUtils.isEmpty(storeInfDtos)) {
            for (StoreInfDto storeInfDto : storeInfDtos) {
                StoreExtDto storeExtDto = ConverterService.convert(storeInfDto, StoreExtDto.class);
                boolean valid = BeanValidators.isValid(storeExtDto);
                if(valid){
                    storeExtCheckDto.setStoreExtIsFull(true);
                    break;
                }
                StoreEachDto storeEachDto = ConverterService.convert(storeInfDto, StoreEachDto.class);
                storeEachDtos.add(storeEachDto);
            }
        }

        if(!storeExtCheckDto.isStoreExtIsFull()){
            storeExtCheckDto.setStoreEachDtos(storeEachDtos);
        }

        return storeExtCheckDto;
    }

    private boolean isValidBaseInfo(BaseInfoDto baseInfoDto) {
        if (!isValid(baseInfoDto)) {
            return false;
        }
        return true;
    }

    private boolean isValidLinkman(List<LinkmanDto> linkmans) {
        if (linkmans.size() < 1) {
            return false;
        }

        for (LinkmanDto linkman : linkmans) {
            if (isValid(linkman)) {
                return true;
            }
        }

        return false;
    }

    private boolean isValidStore(List<StoreBaseDto>  stores) {
        if (stores.size() == 0) {
            return false;
        }

        for (StoreBaseDto store : stores) {
            if (isValid(store)) {
                if(store.getStoreLicence() && !StringUtils.isEmpty(store.getLicence())
                        && !StringUtils.isEmpty(store.getLicencePic())) {
                    return true;
                }else if(!store.getStoreLicence()){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isValidStoreExt(List<StoreExtDto>  storeExtDtos) {
        if (storeExtDtos.size() == 0) {
            return false;
        }

        for (StoreExtDto storeExtDto : storeExtDtos) {
            if (isValid(storeExtDto)) {
                if(storeExtDto.getRelationship() == ERelationship.COUPLE){
                    if(!StringUtils.isEmpty(storeExtDto.getMarryCertificatePic())){
                        return true;
                    }
                }else {
                    return true;
                }
            }
        }

        return false;
    }
}
