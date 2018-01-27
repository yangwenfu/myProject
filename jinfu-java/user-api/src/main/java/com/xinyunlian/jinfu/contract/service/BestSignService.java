package com.xinyunlian.jinfu.contract.service;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.contract.dto.*;
import com.xinyunlian.jinfu.contract.enums.EBsSignType;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;

import java.util.List;

/**
 * Created by dongfangchao on 2017/2/7/0007.
 * 上上签服务接口
 */
public interface BestSignService {
    /**
     * 用户注册
     * @param req
     * @return
     * @throws Exception
     */
    String regUser(BsRegUserReq req) throws Exception;
    /**
     * 申请个人CA证书
     * @param req
     * @return
     * @throws Exception
     */
    String certificateApply(BsCertApplReq req) throws Exception;
    /**
     * 合同发送
     * @param req
     * @return
     * @throws Exception
     */
    String sjdsendcontractdocUpload(BsSjdSendReq req) throws Exception;
    /**
     * 多处手动签名
     * @param req
     * @return
     * @throws Exception
     */
    String getSignPageSignimagePc(ManualSignReq req) throws Exception;
    /**
     * 多处自动签名
     * @param req
     * @return
     * @throws Exception
     */
    String autoSignFopp(BsAutoSignFoppReq req) throws Exception;
    /**
     * 合同文档预览
     * @param req
     * @return
     * @throws Exception
     */
    String viewContract(BsViewContractReq req) throws Exception;
    /**
     * 获取用户上上签注册信息
     * @param userId
     * @return
     * @throws BizServiceException
     */
    UserBestSignDto getUserBestSignByUserId(String userId) throws BizServiceException;
    /**
     * 新增上上签签约记录
     * @param dto
     * @return
     * @throws BizServiceException
     */
    UserBestSignDto addUserBestSign(UserBestSignDto dto) throws BizServiceException;
    /**
     * 更新上上签签约记录
     * @param dto
     * @throws BizServiceException
     */
    void updateUserBestSign(UserBestSignDto dto) throws BizServiceException;

    /**
     * 获取上上签签名配置信息
     * @param cntrctTmpltType
     * @param signType
     * @return
     * @throws BizServiceException
     */
    List<ContractBestSignCfgDto> getCntrctBsCfg(ECntrctTmpltType cntrctTmpltType, EBsSignType signType) throws BizServiceException;

    /**
     * 获取合同签署的公司
     * @param cntrctTmpltType
     * @param signType
     * @return
     * @throws BizServiceException
     */
    List<ContractBestCompanyDto> getBestCompany(ECntrctTmpltType cntrctTmpltType, EBsSignType signType) throws BizServiceException;

    /**
     * 下载上上签合同
     * @param signId
     * @return
     * @throws Exception
     */
    byte[] contractDownloadMobile(String signId) throws Exception;

    /**
     * 查询合同信息
     * @param signId
     * @return
     * @throws Exception
     */
    String contractInfo(String signId) throws Exception;

    /**
     * 上传公章
     * @param req
     * @return
     * @throws Exception
     */
    String uploaduserimage(BsUploadUserImageReq req) throws Exception;

    /**
     * 申请企业CA
     * @param req
     * @return
     * @throws Exception
     */
    String certificateCompanyApply(BsCertApplReq req) throws Exception;

}