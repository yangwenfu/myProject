package com.xinyunlian.jinfu.contract.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.IdUtil;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.FileUtils;
import com.xinyunlian.jinfu.common.util.ToPDFUtils;
import com.xinyunlian.jinfu.common.util.VelocityUtils;
import com.xinyunlian.jinfu.contract.dao.UserContractDao;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.entity.UserContractPo;
import com.xinyunlian.jinfu.contract.entity.id.producer.ContractIdProducer;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.enums.ESignedMark;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * Created by JL on 2016/9/20.
 */
@Service
public class ContractServiceImpl implements ContractService {
    private static Logger LOGGER = LoggerFactory.getLogger(ContractServiceImpl.class);
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private UserContractDao userContractDao;
    @Autowired
    private ContractIdProducer contractNoProducer;
    @Value("${file.addr}")
    private String fileAddr;
    /**
     * 获得合同内容
     *
     * @param userContractDto
     * @return
     */
    @Override
    public UserContractDto getContract(UserContractDto userContractDto) {
        VelocityContext vc = new VelocityContext();
        Map<String, String> map = userContractDto.getModel();
        for (String key : map.keySet()) {
            vc.put(key, map.get(key) == null ? "" : map.get(key));
        }
        userContractDto.setContent(VelocityUtils.generateStr(vc, userContractDto.getTemplateType().getPath()));
        return userContractDto;
    }
    /**
     * 生成合同文件并保存上传
     *
     * @param userContractDto
     */
    @Override
    @Transactional
    public UserContractDto saveContract(UserContractDto userContractDto, UserInfoDto userInfoDto) {
        UserContractPo userContractPo = ConverterService.convert(userContractDto, UserContractPo.class);
        String[] contractArgs = this.generateContract(userContractDto);
        userContractPo.setUserId(userInfoDto.getUserId());
        userContractPo.setSignName(userInfoDto.getUserName());
        userContractPo.setContent(contractArgs[0]);
        userContractPo.setFilePath(contractArgs[1]);
        userContractPo.setSignDate(new Date());
        userContractPo.setContractName(userContractDto.getTemplateType().getText());
        userContractPo.setContractId(contractArgs[2]);
        userContractPo = userContractDao.save(userContractPo);

        return ConverterService.convert(userContractPo, UserContractDto.class);
    }

    @Override
    public UserContractDto saveContractSync(UserContractDto userContractDto, UserInfoDto userInfoDto) {
        return this.saveContract(userContractDto, userInfoDto);
    }

    @Override
    @Transactional
    public void updateContract(UserContractDto userContractDto, UserInfoDto userInfoDto) {
        UserContractPo userContractPo = userContractDao.findByUserIdAndTemplateType(userContractDto.getUserId(), userContractDto.getTemplateType());
        String[] contractArgs = this.generateContract(userContractDto);
        userContractPo.setUserId(userInfoDto.getUserId());
        userContractPo.setSignName(userInfoDto.getUserName());
        userContractPo.setContent(contractArgs[0]);
        userContractPo.setFilePath(contractArgs[1]);
        userContractPo.setSignDate(new Date());
        userContractPo.setContractName(userContractDto.getTemplateType().getText());
        userContractDao.save(userContractPo);
    }

    @Override
    @Transactional
    public void updateSignedMark(String signId, ESignedMark signedMark) {
        UserContractPo userContractPo = userContractDao.findByBsSignid(signId);
        userContractPo.setSignedMark(signedMark);
        userContractDao.save(userContractPo);
    }

    private String[] generateContract(UserContractDto userContractDto){
        VelocityContext vc = new VelocityContext();
        Map<String, String> map = userContractDto.getModel();
        for (String key : map.keySet()) {
            vc.put(key, map.get(key));
        }
        String content = VelocityUtils.generateStr(vc, userContractDto.getTemplateType().getPath());
        String filePath;
        Context context = new Context(userContractDto.getTemplateType().getCode());
        String contractId = IdUtil.produce(contractNoProducer, context);
        try {
            File tmpFile = FileUtils.createHTML(content);
            filePath = fileStoreService.upload(EFileType.USER_CONTRACT, ToPDFUtils.generatePDF(tmpFile.getAbsolutePath()), contractId + ".pdf");
        } catch (IOException e) {
            LOGGER.error("合同文件上传失败", e);
            throw new BizServiceException();
        }

        return new String[]{content, filePath, contractId};
    }

    @Override
    public UserContractDto getUserContract(String userId, ECntrctTmpltType cntrctTmpltType) {
        UserContractPo userContractPo = userContractDao.findByUserIdAndTemplateType(userId, cntrctTmpltType);
        if (userContractPo != null) {
            return ConverterService.convert(userContractPo, UserContractDto.class);
        } else {
            return null;
        }
    }
    @Override
    public UserContractDto getUserContractByBizId(String userId, ECntrctTmpltType cntrctTmpltType, String bizId) {
        UserContractPo userContractPo = userContractDao.findByUserIdAndTemplateTypeAndBizId(userId, cntrctTmpltType, bizId);
        if (userContractPo != null) {
            return ConverterService.convert(userContractPo, UserContractDto.class);
        } else {
            return null;
        }
    }

    @Override
    public List<UserContractDto> getUserContractByBizId(String userId, String bizId) {
        List<UserContractPo> userContractPos = userContractDao.findByUserIdAndBizId(userId, bizId);
        return ConverterService.convertToList(userContractPos,UserContractDto.class);
    }

    @Override
    public UserContractDto getUserContractByCntrctId(String cntrctId) {
        UserContractPo po = userContractDao.findOne(cntrctId);
        if (po != null) {
            UserContractDto dto = ConverterService.convert(po, UserContractDto.class);
            if (!StringUtils.isEmpty(dto.getFilePath())){
                dto.setFilePath(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getFilePath()));
            }
            return dto;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public UserContractDto updateBsContractInf(UserContractDto userContractDto, String contractId) {
        UserContractPo po = userContractDao.findOne(contractId);
        po.setBsDocid(userContractDto.getBsDocid());
        po.setBsSignid(userContractDto.getBsSignid());
        po.setBsVatecode(userContractDto.getBsVatecode());
        po = userContractDao.save(po);
        return ConverterService.convert(po, UserContractDto.class);
    }

    @Override
    public UserContractDto saveContract2(UserContractDto userContractDto, UserInfoDto userInfoDto) {
        VelocityContext vc = new VelocityContext();
        Map<String, String> map = userContractDto.getModel();
        for (String key : map.keySet()) {
            vc.put(key, map.get(key));
        }
        String content = VelocityUtils.generateStr(vc, userContractDto.getTemplateType().getPath());
        String filePath;
        Context context = new Context(userContractDto.getTemplateType().getCode());
        String contractId = IdUtil.produce(contractNoProducer, context);
        try {
            File tmpFile = FileUtils.createHTML(content);
            filePath = fileStoreService.upload(EFileType.USER_CONTRACT, ToPDFUtils.generatePDF(tmpFile.getAbsolutePath()), contractId + ".pdf");
        } catch (IOException e) {
            LOGGER.error("合同文件上传失败", e);
            throw new BizServiceException();
        }
        UserContractPo userContractPo = ConverterService.convert(userContractDto, UserContractPo.class);
        userContractPo.setUserId(userInfoDto.getUserId());
        userContractPo.setSignName(userInfoDto.getUserName());
        userContractPo.setContent(content);
        userContractPo.setFilePath(filePath);
        userContractPo.setSignDate(new Date());
        userContractPo.setContractName(userContractDto.getTemplateType().getText());
        userContractPo.setContractId(contractId);
        UserContractPo retPo = userContractDao.save(userContractPo);
        UserContractDto retDto = ConverterService.convert(retPo, UserContractDto.class);
        return retDto;
    }

    @Override
    public UserContractDto getUserContractBySignId(String bsSignId) {
        UserContractPo po = userContractDao.findByBsSignid(bsSignId);
        UserContractDto dto = ConverterService.convert(po, UserContractDto.class);
        return dto;
    }

    @Override
    @Transactional
    public void deleteUserContractByContId(String contId) {
        userContractDao.deleteById(contId);
    }
}