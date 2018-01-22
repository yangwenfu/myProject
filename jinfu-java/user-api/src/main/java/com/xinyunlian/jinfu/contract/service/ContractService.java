package com.xinyunlian.jinfu.contract.service;

import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.enums.ESignedMark;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;

import java.util.List;

/**
 * Created by JL on 2016/9/20.
 */
public interface ContractService {
    UserContractDto getContract(UserContractDto userContractDto);
    UserContractDto saveContract(UserContractDto userContractDto, UserInfoDto userInfoDto);

    /**
     * 同步保存合同
     * @param userContractDto
     * @param userInfoDto
     */
    UserContractDto saveContractSync(UserContractDto userContractDto, UserInfoDto userInfoDto);

    /**
     * 更新原类型合同
     * @param userContractDto
     * @param userInfoDto
     */
    void updateContract(UserContractDto userContractDto, UserInfoDto userInfoDto);

    /**
     * 更新上上签签约状态
     * @param signId
     * @param signedMark
     */
    void updateSignedMark(String signId, ESignedMark signedMark);

    UserContractDto getUserContract(String userId, ECntrctTmpltType cntrctTmpltType);
    UserContractDto getUserContractByBizId(String userId, ECntrctTmpltType cntrctTmpltType, String bizId);
    List<UserContractDto> getUserContractByBizId(String userId, String bizId);
    UserContractDto getUserContractByCntrctId(String cntrctId);
    UserContractDto updateBsContractInf(UserContractDto userContractDto, String contractId);
    UserContractDto saveContract2(UserContractDto userContractDto, UserInfoDto userInfoDto);

    /**
     * 根据上上签签名id获取合同信息
     * @param bsSignId
     * @return
     */
    UserContractDto getUserContractBySignId(String bsSignId);

    /**
     * 根据主键删除合同
     * @param contId
     */
    void deleteUserContractByContId(String contId);
}