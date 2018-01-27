package com.ylfin.wallet.portal.config;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.finaccbankcard.service.FinAccBankCardService;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.service.*;

import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfiguration {

	@Bean
	public ReferenceBean<UserService> userServiceReferenceBean() {
		ReferenceBean<UserService> bean = new ReferenceBean<>();
		bean.setInterface(UserService.class);
		bean.setId("userService");
		return bean;
	}

	@Bean
	public ReferenceBean<PictureService> pictureServiceReferenceBean() {
		ReferenceBean<PictureService> bean = new ReferenceBean<>();
		bean.setInterface(PictureService.class);
		bean.setId("pictureService");
		return bean;
	}
    @Bean
    public ReferenceBean<BankService> bankServiceReferenceBean() {
        ReferenceBean<BankService> bean = new ReferenceBean<>();
        bean.setInterface(BankService.class);
        bean.setId("bankService");
        return bean;
    }
    @Bean
    public ReferenceBean<SmsService> smsServiceReferenceBean() {
        ReferenceBean<SmsService> bean = new ReferenceBean<>();
        bean.setInterface(SmsService.class);
        bean.setId("smsService");
        return bean;
    }

    @Bean
    public ReferenceBean<ContractService> contractServiceReferenceBean() {
        ReferenceBean<ContractService> bean = new ReferenceBean<>();
        bean.setInterface(ContractService.class);
        bean.setId("contractService");
        return bean;
    }

    @Bean
    public ReferenceBean<UserLinkmanService> userLinkmanServiceReferenceBean() {
        ReferenceBean<UserLinkmanService> bean = new ReferenceBean<>();
        bean.setInterface(UserLinkmanService.class);
        bean.setId("userLinkmanService");
        return bean;
    }

    @Bean
    public ReferenceBean<UserExtService> userExtServiceReferenceBean() {
        ReferenceBean<UserExtService> bean = new ReferenceBean<>();
        bean.setInterface(UserExtService.class);
        bean.setId("userExtService");
        return bean;
    }

    @Bean
    public ReferenceBean<StoreService> storeServiceReferenceBean() {
        ReferenceBean<StoreService> bean = new ReferenceBean<>();
        bean.setInterface(StoreService.class);
        bean.setId("storeService");
        return bean;
    }


    @Bean
    public ReferenceBean<YMMemberService> yMMemberServiceReferenceBean() {
        ReferenceBean<YMMemberService> bean = new ReferenceBean<>();
        bean.setInterface(YMMemberService.class);
        bean.setId("yMMemberService");
        return bean;
    }

    @Bean
    public ReferenceBean<FinAccBankCardService> finAccBankCardServiceReferenceBean() {
        ReferenceBean<FinAccBankCardService> bean = new ReferenceBean<>();
        bean.setInterface(FinAccBankCardService.class);
        bean.setId("finAccBankCardService");
        return bean;
    }

    @Bean
    public ReferenceBean<LoanService> loanServiceReferenceBean() {
        ReferenceBean<LoanService> bean = new ReferenceBean<>();
        bean.setInterface(LoanService.class);
        bean.setId("loanService");
        return bean;
    }

    @Bean
    public ReferenceBean<RiskUserInfoService> riskUserInfoServiceReferenceBean() {
        ReferenceBean<RiskUserInfoService> bean = new ReferenceBean<>();
        bean.setInterface(RiskUserInfoService.class);
        bean.setId("riskUserInfoService");
        return bean;
    }

    @Bean
    public ReferenceBean<DealerUserService> dealerUserServiceReferenceBean() {
        ReferenceBean<DealerUserService> bean = new ReferenceBean<>();
        bean.setInterface(DealerUserService.class);
        bean.setId("dealerUserService");
        return bean;
    }

    @Bean
    public ReferenceBean<ApiBaiduService> apiBaiduServiceReferenceBean() {
        ReferenceBean<ApiBaiduService> bean = new ReferenceBean<>();
        bean.setInterface(ApiBaiduService.class);
        bean.setId("apiBaiduService");
        return bean;
    }

}
