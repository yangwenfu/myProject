package com.xinyunlian.jinfu.user.dto.resp;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by KimLL on 2016/8/18.
 */

public class UserDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserInfoDto userDto;

    private UserExtDto userExtDto;

    private boolean hasOpenId;

    private boolean hasRisk;

    private List<StoreInfDto> storeInfPoList;

    private List<BankCardDto> bankCardPoList;

    public UserInfoDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserInfoDto userDto) {
        this.userDto = userDto;
    }

    public boolean isHasOpenId() {
        return hasOpenId;
    }

    public void setHasOpenId(boolean hasOpenId) {
        this.hasOpenId = hasOpenId;
    }

    public boolean isHasRisk() {
        return hasRisk;
    }

    public void setHasRisk(boolean hasRisk) {
        this.hasRisk = hasRisk;
    }

    public List<StoreInfDto> getStoreInfPoList() {
        return storeInfPoList;
    }

    public void setStoreInfPoList(List<StoreInfDto> storeInfPoList) {
        this.storeInfPoList = storeInfPoList;
    }

    public List<BankCardDto> getBankCardPoList() {
        return bankCardPoList;
    }

    public void setBankCardPoList(List<BankCardDto> bankCardPoList) {
        this.bankCardPoList = bankCardPoList;
    }

    public UserExtDto getUserExtDto() {
        return userExtDto;
    }

    public void setUserExtDto(UserExtDto userExtDto) {
        this.userExtDto = userExtDto;
    }



    @Override
    public String toString() {
        return "UserDetailDto{" +
                "userDto=" + userDto +
                ", storeInfPoList=" + storeInfPoList +
                ", bankCardPoList=" + bankCardPoList +
                '}';
    }
}
