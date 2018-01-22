package com.xinyunlian.jinfu.customer.dto;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.UserLabelDto;
import com.xinyunlian.jinfu.user.dto.UserLinkmanDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KimLL on 2016/8/18.
 */

public class CustomerDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserInfoDto userDto;

    private boolean hasOpenId;

    private boolean hasRisk;

    private CustomerDealerDto customerDealerDto;

    private List<CustomerStoreDto> customerStoreDtos = new ArrayList<>();

    private List<BankCardDto> bankCardDtos = new ArrayList<>();

    private List<UserLinkmanDto> userLinkmanDtos = new ArrayList<>();

    private List<UserLabelDto> userLabelDtos = new ArrayList<>();

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

    public List<CustomerStoreDto> getCustomerStoreDtos() {
        return customerStoreDtos;
    }

    public void setCustomerStoreDtos(List<CustomerStoreDto> customerStoreDtos) {
        this.customerStoreDtos = customerStoreDtos;
    }

    public CustomerDealerDto getCustomerDealerDto() {
        return customerDealerDto;
    }

    public void setCustomerDealerDto(CustomerDealerDto customerDealerDto) {
        this.customerDealerDto = customerDealerDto;
    }

    public List<BankCardDto> getBankCardDtos() {
        return bankCardDtos;
    }

    public void setBankCardDtos(List<BankCardDto> bankCardDtos) {
        this.bankCardDtos = bankCardDtos;
    }

    public List<UserLinkmanDto> getUserLinkmanDtos() {
        return userLinkmanDtos;
    }

    public void setUserLinkmanDtos(List<UserLinkmanDto> userLinkmanDtos) {
        this.userLinkmanDtos = userLinkmanDtos;
    }

    public List<UserLabelDto> getUserLabelDtos() {
        return userLabelDtos;
    }

    public void setUserLabelDtos(List<UserLabelDto> userLabelDtos) {
        this.userLabelDtos = userLabelDtos;
    }
}
