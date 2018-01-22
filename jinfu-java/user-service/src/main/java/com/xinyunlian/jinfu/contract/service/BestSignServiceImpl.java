package com.xinyunlian.jinfu.contract.service;

import cn.bestsign.sdk.BestSignSDK;
import cn.bestsign.sdk.domain.vo.params.ReceiveUser;
import cn.bestsign.sdk.domain.vo.params.SendUser;
import cn.bestsign.sdk.integration.Constants;
import cn.bestsign.sdk.integration.Logger;
import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.contract.dao.ContractBestCompanyDao;
import com.xinyunlian.jinfu.contract.dao.ContractBestSignCfgDao;
import com.xinyunlian.jinfu.contract.dao.UserBestSignDao;
import com.xinyunlian.jinfu.contract.dto.*;
import com.xinyunlian.jinfu.contract.entity.ContractBestCompanyPo;
import com.xinyunlian.jinfu.contract.entity.ContractBestSignCfgPo;
import com.xinyunlian.jinfu.contract.entity.UserBestSignPo;
import com.xinyunlian.jinfu.contract.enums.EBsSignType;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by dongfangchao on 2017/2/7/0007.
 */
@Service
public class BestSignServiceImpl implements BestSignService{
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BestSignServiceImpl.class);
   /* private String bestSignUrl = "https://www.bestsign.cn";
    private String mid = "7ba6edc898b94af8806934f7bb6d0db1";
    private String yljfPrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMl7r9Ja/362IDwuLt+qGSIJ3PCCjoE35HbL6tdw6G3KscLFQTJHnwbuR1F0Vx6ZUeGlTpL+rsWKy39wxF7Kciy37YH3HrokDvsYWoQPvJYgG06PokRNd5LfvaR9VZqKnUtTJj+U8nOkyuMm/1aPS3rW4sRcGrqrsA/hDgbEbiOPAgMBAAECgYEAxRMVZBUcCW3NU+/8a0uur6ukDyA0kVIBRu2odDh9MD7t5swYPDzv4NCas6KvQD/JZoPRulggzRo4eBvuB6XPTA5rbrgbSaQM5z5hpRCX8c45eqHs99arJ9HWd5yfsJNVYwxPiItBedjaZ3dThowtH6149Pkq3PqMZPmqV7SvCMkCQQD3OouzCB6G48Oudp+4CDHQ0MFfiCLj0qZwdokGs35OXs6IYuDaEkgx2RfhFFVmaO7WoqEgEwIR4H8P83NxGhxLAkEA0KGoQuf74bRPhpOUVZulZG6bJtzPZesSngRz55sxUDVG2iDL191r9r9UeTAAgovEYkQpT+1CVFtqHm0UywRDTQJBAIn9KGoF1xwM1nRzC1SpZjyZt8S9dFPaiEFvN64A0zek3PvszHmLCLah6B0dv0jIur4byXjggyA9QE5D4KQ5IZ8CQFtavgv1+uRdCQmbq7NEwUpV6Fg/Dg3JvlA1EO+UlCZT/d9bxwaR7UmRhBLAwyhmvSpuQEzATnATy0bhat5u0m0CQQCjZQXwxMmLpylMNtHXBKdNcFbPMr+T8wCie6QJY3ZT50BpabF1yPkTYlIUd9W8v/llgKIeQaZt2VPbiI8NNhiH";
    private String midEmail = "jiayongchun@xinyunlian.com";
    private String midName = "宁波云联小额贷款有限公司";
    private String midMobile = "15669920009";*/
    @Value("${best.sign.url}")
    private String bestSignUrl;
    @Value("${best.sign.mid}")
    private String mid;
    @Value("${yljf.private.key}")
    private String yljfPrivateKey;
    @Value("${mid.email}")
    private String midEmail;
    @Value("${mid.name}")
    private String midName;
    @Value("${mid.mobile}")
    private String midMobile;
    private static BestSignSDK sdk = null;
    @Autowired
    private UserBestSignDao userBestSignDao;

    @Autowired
    private ContractBestSignCfgDao contractBestSignCfgDao;
    @Autowired
    private ContractBestCompanyDao contractBestCompanyDao;

    private synchronized BestSignSDK getSdk(){
        if (sdk == null){
            sdk = BestSignSDK.getInstance(mid, yljfPrivateKey, bestSignUrl);
            sdk.setLogDir(System.getProperty("user.dir"));
            sdk.setDebugLevel(Logger.DEBUG_LEVEL.DEBUG);
        }
        return sdk;
    }
    @Override
    public String regUser(BsRegUserReq req) throws Exception{
        JSONObject json = getSdk().regUser(Constants.USER_TYPE.PERSONAL, req.getEmail(), req.getMobile(), req.getName());
        LOGGER.debug("上上签用户注册返回json：");
        LOGGER.debug(json.toJSONString());
        return json.toJSONString();
    }
    @Override
    public String certificateApply(BsCertApplReq req) throws Exception{
        JSONObject json = getSdk().certificateApply(Constants.CA_TYPE.CFCA, req.getName(), req.getPassword(), req.getMobile(),
                req.getEmial(), req.getAddress(), req.getProvince(), req.getCity(), req.getIdentity());
        LOGGER.debug("上上签申请个人CA证书返回json：");
        LOGGER.debug(json.toJSONString());
        return json.toJSONString();
    }
    @Override
    public String sjdsendcontractdocUpload(BsSjdSendReq req) throws Exception{
        BsSendUser bsSendUser = req.getSendUser();
        SendUser sendUser;
        if (req.getSendUser() != null){
            sendUser = new SendUser(bsSendUser.getEmail(), bsSendUser.getName(), bsSendUser.getMobile(),
                    30, req.getSelfSign(), Constants.USER_TYPE.ENTERPRISE, false, req.getFilename(), "FYI");
        }else {
            sendUser = new SendUser(midEmail, midName, midMobile,
                    30, req.getSelfSign(), Constants.USER_TYPE.ENTERPRISE, false, req.getFilename(), "FYI");
        }
        //收件人
        ReceiveUser[] bsReceiveUserList = null;
        if (!CollectionUtils.isEmpty(req.getReceiveUserList())){
            bsReceiveUserList = new ReceiveUser[req.getReceiveUserList().size()];
            for (int i = 0; i < req.getReceiveUserList().size(); i++){
                BsReceiveUser item = req.getReceiveUserList().get(i);
                Constants.USER_TYPE userType = item.getPersonal()?Constants.USER_TYPE.PERSONAL:Constants.USER_TYPE.ENTERPRISE;
                ReceiveUser bsReceiveUser = new ReceiveUser(StringUtils.isEmpty(item.getEmail())?"":item.getEmail(), item.getName(), item.getMobile(),
                        userType, Constants.CONTRACT_NEEDVIDEO.NONE, false);
                bsReceiveUserList[i] = bsReceiveUser;
            }
        }
        JSONObject json = null;
        try {
            json = getSdk().sjdsendcontractdocUpload(bsReceiveUserList, sendUser, req.getFileData(), req.getFilename());
        } catch (Exception e) {
            LOGGER.error("上上签合同发送异常", e);
            Map<String, Object> info = new HashMap<>();
            info.put("code","100001");
            Map<String, Object> info2 = new HashMap<>();
            info2.put("info", info);
            Map<String, Object> response = new HashMap<>();
            response.put("response",info2);
            return JsonUtil.toJson(response);
        }
        LOGGER.debug("上上签合同发送返回json：");
        LOGGER.debug(json.toJSONString());
        return json.toJSONString();
    }
    @Override
    public String getSignPageSignimagePc(ManualSignReq req) throws Exception{
        Constants.DEVICE_TYPE device_type = Constants.DEVICE_TYPE.MOBILE;
        if (req.getPc() != null && req.getPc()){
            device_type = Constants.DEVICE_TYPE.PC;
        }
        String coordinateString = getCoordinateString(req.getBestSignCfgList());
        String json = getSdk().getSignPageSignimagePc(req.getSignId(), req.getEmail(), coordinateString, req.getReturnUrl(), device_type);
        LOGGER.debug("上上签多处手动签名返回：");
        LOGGER.debug(json);
        return json;
    }
    @Override
    public String autoSignFopp(BsAutoSignFoppReq req) throws Exception {
        String coordinateString = getCoordinateString(req.getBestSignCfgList());
        System.out.println("==多处自动签名请求==");

        String email = midEmail, sealName = "", noticeUrls = "";

        if(!StringUtils.isEmpty(req.getEmail())){
            email = req.getEmail();
        }

        if(!StringUtils.isEmpty(req.getSealname())){
            sealName = req.getSealname();
        }

        if(!StringUtils.isEmpty(req.getNoticeUrls())){
            noticeUrls = req.getNoticeUrls();
        }

        JSONObject json = getSdk().AutoSignFopp(email, req.getSignid(), coordinateString, "1", sealName, noticeUrls);
        return json.toJSONString();
    }
    @Override
    public String viewContract(BsViewContractReq req) throws Exception {
        String json = getSdk().ViewContract(req.getFsdid(), req.getDocid());
        LOGGER.debug("上上签合同文档预览返回：");
        LOGGER.debug(json);
        return json;
    }
    @Override
    public UserBestSignDto getUserBestSignByUserId(String userId) throws BizServiceException {
        UserBestSignPo po = userBestSignDao.findByUserId(userId);
        UserBestSignDto dto = ConverterService.convert(po, UserBestSignDto.class);
        return dto;
    }
    @Override
    @Transactional
    public UserBestSignDto addUserBestSign(UserBestSignDto dto) throws BizServiceException {
        UserBestSignPo po = ConverterService.convert(dto, UserBestSignPo.class);
        UserBestSignPo dbPo = userBestSignDao.save(po);
        UserBestSignDto retDto = ConverterService.convert(dbPo, UserBestSignDto.class);
        return retDto;
    }
    @Override
    @Transactional
    public void updateUserBestSign(UserBestSignDto dto) throws BizServiceException {
        UserBestSignPo po = userBestSignDao.findByUserId(dto.getUserId());
        po.setBestSignUid(dto.getBestSignUid());
        po.setBestSignCa(dto.getBestSignCa());
    }

    @Override
    public List<ContractBestSignCfgDto> getCntrctBsCfg(ECntrctTmpltType cntrctTmpltType, EBsSignType signType) throws BizServiceException {
        List<ContractBestSignCfgDto> retList = new ArrayList<>();

        List<ContractBestSignCfgPo> poList = contractBestSignCfgDao.findByCntrctTmpltTypeAndSignType(cntrctTmpltType, signType);
        if (!CollectionUtils.isEmpty(poList)){
            poList.forEach(po -> {
                ContractBestSignCfgDto dto = ConverterService.convert(po, ContractBestSignCfgDto.class);
                retList.add(dto);
            });
        }

        return retList;
    }

    @Override
    public List<ContractBestCompanyDto> getBestCompany(ECntrctTmpltType cntrctTmpltType, EBsSignType signType) throws BizServiceException {
        List<ContractBestCompanyPo> poList = contractBestCompanyDao.findByCntrctTmpltTypeAndSignType(cntrctTmpltType, signType);
        List<ContractBestCompanyDto> retList = ConverterService.convertToList(poList,ContractBestCompanyDto.class);
        return retList;
    }

    @Override
    public byte[] contractDownloadMobile(String signId) throws Exception {
        byte[] fis = getSdk().contractDownloadMobile(signId);
        return fis;
    }

    @Override
    public String contractInfo(String signId) throws Exception {
        JSONObject json = getSdk().contractInfo(signId);
        LOGGER.debug("上上签查询合同信息返回：");
        LOGGER.debug(json.toJSONString());
        return json.toJSONString();
    }

    private String getCoordinateString(List<ContractBestSignCfgDto> bestSignCfgList){
        StringBuilder coordinateBuilder = new StringBuilder();
        coordinateBuilder.append("[");
        if (!CollectionUtils.isEmpty(bestSignCfgList)){
            for (int i=0;i<bestSignCfgList.size();i++){
                ContractBestSignCfgDto cfg = bestSignCfgList.get(i);
                Map<String, Object> data = new HashMap<>();
                data.put("pagenum", cfg.getPagenum());
                data.put("signx", cfg.getSignx());
                data.put("signy", cfg.getSigny());
                coordinateBuilder.append(JsonUtil.toJson(data));
                if (i != bestSignCfgList.size() - 1){
                    coordinateBuilder.append(",");
                }
            }
        }
        coordinateBuilder.append("]");

        return coordinateBuilder.toString();
    }

    @Override
    public String uploaduserimage(BsUploadUserImageReq req) throws Exception{
        JSONObject json = getSdk().uploaduserimage(req.getUseracount(),req.getMobile(),req.getImgType(),
                Base64.decodeBase64(req.getImage()),req.getImgName(),req.getSealName(),req.getName(),Constants.USER_TYPE.ENTERPRISE);
        LOGGER.debug("上上签用户注册返回json：");
        LOGGER.debug(json.toJSONString());
        return json.toJSONString();
    }

    @Override
    public String certificateCompanyApply(BsCertApplReq req) throws Exception{
        JSONObject json = getSdk().certificateApply(Constants.CA_TYPE.ZJCA,req.getName(), req.getPassword(),
                req.getLinkMan(), req.getMobile(), req.getEmial(), req.getAddress(), req.getProvince(), req.getCity(),
                req.getLinkIdCode(),req.getOrgCode(), req.getOrgCode(), req.getOrgCode());
        LOGGER.debug("上上签申请企业CA证书返回json：");
        LOGGER.debug(json.toJSONString());
        return json.toJSONString();
    }
}