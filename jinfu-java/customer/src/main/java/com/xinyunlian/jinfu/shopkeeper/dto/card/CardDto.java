package com.xinyunlian.jinfu.shopkeeper.dto.card;

import com.xinyunlian.jinfu.spider.dto.resp.RiskUserInfoDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2017/2/14.
 */
public class CardDto {
    private IdAuthDto idAuthDto;

    private BaseInfoDto baseInfoDto;

    private RiskUserInfoDto riskUserInfoDto;

    private List<StoreBaseDto> storeBaseDtos = new ArrayList<>();

    private List<StoreExtDto> storeExtDtos = new ArrayList<>();

    private List<LinkmanDto> linkmanDtos = new ArrayList<>();

    private long bankCardCount;

    private long bankCardLoanCount;

    public IdAuthDto getIdAuthDto() {
        return idAuthDto;
    }

    public void setIdAuthDto(IdAuthDto idAuthDto) {
        this.idAuthDto = idAuthDto;
    }

    public BaseInfoDto getBaseInfoDto() {
        return baseInfoDto;
    }

    public void setBaseInfoDto(BaseInfoDto baseInfoDto) {
        this.baseInfoDto = baseInfoDto;
    }

    public RiskUserInfoDto getRiskUserInfoDto() {
        return riskUserInfoDto;
    }

    public void setRiskUserInfoDto(RiskUserInfoDto riskUserInfoDto) {
        this.riskUserInfoDto = riskUserInfoDto;
    }

    public List<StoreBaseDto> getStoreBaseDtos() {
        return storeBaseDtos;
    }

    public void setStoreBaseDtos(List<StoreBaseDto> storeBaseDtos) {
        this.storeBaseDtos = storeBaseDtos;
    }

    public List<StoreExtDto> getStoreExtDtos() {
        return storeExtDtos;
    }

    public void setStoreExtDtos(List<StoreExtDto> storeExtDtos) {
        this.storeExtDtos = storeExtDtos;
    }

    public List<LinkmanDto> getLinkmanDtos() {
        return linkmanDtos;
    }

    public void setLinkmanDtos(List<LinkmanDto> linkmanDtos) {
        this.linkmanDtos = linkmanDtos;
    }

    public long getBankCardCount() {
        return bankCardCount;
    }

    public void setBankCardCount(long bankCardCount) {
        this.bankCardCount = bankCardCount;
    }

    public long getBankCardLoanCount() {
        return bankCardLoanCount;
    }

    public void setBankCardLoanCount(long bankCardLoanCount) {
        this.bankCardLoanCount = bankCardLoanCount;
    }
}
