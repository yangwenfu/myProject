package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.enums.ESignedMark;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.loan.dto.bestsign.LoanBestSignDto;
import com.xinyunlian.jinfu.loan.dto.contract.LoanContractDto;
import com.xinyunlian.jinfu.loan.service.PrivateAitouziContractService;
import com.xinyunlian.jinfu.loan.service.PrivateXYDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jll
 */
@RestController
@RequestMapping(value = "loan/xyd")
public class LoanXYDController {

    @Autowired
    private PrivateAitouziContractService privateAitouziService;
    @Autowired
    private PrivateXYDService privateXYDService;
    @Autowired
    private ContractService contractService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanXYDController.class);

    /**
     * 小烟贷合同列表
     * @param applId
     * @return
     */
    @RequestMapping(value = "contract/list", method = RequestMethod.GET)
    public ResultDto list(@RequestParam String applId){
        String userId = SecurityContext.getCurrentUserId();
        List<LoanContractDto> rs = new ArrayList<>();
        Map<String,UserContractDto> contractDtoMap = new HashMap<>();
        List<UserContractDto> userContractDtos = contractService.getUserContractByBizId(userId, applId);
        if(!CollectionUtils.isEmpty(userContractDtos)){
            userContractDtos.forEach(userContractDto -> {
                contractDtoMap.put(userContractDto.getTemplateType().getCode(),userContractDto);
            });
        }

        //贷款协议
        LoanContractDto t1 = new LoanContractDto();
        ECntrctTmpltType contractType = ECntrctTmpltType.YDL01006;
        UserContractDto userContractDto = contractDtoMap.get(contractType.getCode());
        t1.setName(contractType.getText());
        t1.setType(contractType.getCode());
        t1.setSigned(userContractDto != null && userContractDto.getSignedMark() != null && userContractDto.getSignedMark() == ESignedMark.FIRST_SIGN);
        rs.add(t1);

        //咨询协议
        LoanContractDto t2 = new LoanContractDto();
        contractType = ECntrctTmpltType.ZXL01006;
        userContractDto = contractDtoMap.get(contractType.getCode());
        t2.setName(contractType.getText());
        t2.setType(contractType.getCode());
        t2.setSigned(userContractDto != null && userContractDto.getSignedMark() != null && userContractDto.getSignedMark() == ESignedMark.FIRST_SIGN);
        rs.add(t2);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    /**
     * 获取合同签署的地址
     * @param applId
     * @param type
     * @return
     */
    @RequestMapping(value = "contract/get", method = RequestMethod.GET)
    public ResultDto get(@RequestParam String applId, @RequestParam ECntrctTmpltType type){
        privateXYDService.setReturnUrl("/jinfu/web/loan/xyd/contract/return");
        LoanBestSignDto loanBestSignDto = privateXYDService.get(applId, type);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanBestSignDto);
    }

    /**
     * 合同同步回调
     * @return
     */
    @RequestMapping(value = "contract/return", method = RequestMethod.GET)
    public ResultDto returnBack(@RequestParam String code, @RequestParam String signID, @RequestParam String applId){
        privateXYDService.signed(code, signID, applId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

}
