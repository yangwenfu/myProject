package com.xinyunlian.jinfu.customer.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardBinDto;
import com.xinyunlian.jinfu.bank.dto.BankInfDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.BeanValidators;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.excel.ImportExcel;
import com.xinyunlian.jinfu.customer.dto.CardBinDto;
import com.xinyunlian.jinfu.prod.controller.ProdController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KimLL on 2016/8/26.
 */
@Controller
@RequestMapping(value = "bank")
public class BankController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdController.class);
    @Autowired
    private BankService bankService;

    /**
     * 获取银行卡信息
     * @return
     */
    @RequestMapping(value = "/getBank", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<BankInfDto>> getBank() {
        List<BankInfDto> bankInfDtoList = bankService.findBankInfAll();
        return ResultDtoFactory.toAck("获取成功", bankInfDtoList);
    }

    /**
     * 通过银行卡获取银行卡所属银行
     * @return
     */
    @RequestMapping(value = "/getCardBin", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getCardBin(@RequestParam String cardNo) {
        BankCardBinDto bankCardBinDto = bankService.findByNumLengthAndBin(cardNo);
        if(bankCardBinDto == null){
            return  ResultDtoFactory.toNack("暂不支持该银行卡，请更换银行卡");
        }
        return  ResultDtoFactory.toAck("获取成功",bankCardBinDto);
    }

    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public ResultDto<String> upload(@RequestParam MultipartFile file,@RequestParam Long bankId){
        try {
            ImportExcel ei = new ImportExcel(file, 0, 0);
            List<CardBinDto> cardBinDtos = ei.getDataList(CardBinDto.class);

            if(!CollectionUtils.isEmpty(cardBinDtos)){
                for (CardBinDto cardBinDto : cardBinDtos) {
                    try {
                        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                        Validator validator = factory.getValidator();
                        BeanValidators.validateWithException(validator, cardBinDto);
                    } catch (ConstraintViolationException ex) {
                        StringBuilder failureMsg = new StringBuilder();
                        List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                        for (String message : messageList) {
                            failureMsg.append(message + "; ");
                        }
                        return ResultDtoFactory.toNack(MessageUtil.getMessage("common.operate.fail"), failureMsg.toString());
                    }
                }

                List<BankCardBinDto> bankCardBinDtos = new ArrayList<>();
                cardBinDtos.forEach(cardBinDto -> {
                    BankCardBinDto bankCardBinDto = ConverterService
                            .convert(cardBinDto,BankCardBinDto.class);
                    bankCardBinDto.setBinLength(cardBinDto.getBin().length());
                    if(cardBinDto.getCardType().equals("1")){
                        bankCardBinDto.setCardType("借记卡");
                    }else if(cardBinDto.getCardType().equals("3")){
                        bankCardBinDto.setCardType("信用卡");
                    }else if(cardBinDto.getCardType().equals("4")){
                        bankCardBinDto.setCardType("信用卡");
                    }else if(cardBinDto.getCardType().equals("7")){
                        bankCardBinDto.setCardType("预付卡");
                    }
                    bankCardBinDto.setBankId(bankId);
                    bankCardBinDtos.add(bankCardBinDto);
                });
                bankService.save(bankCardBinDtos);
            }

        } catch (Exception e) {
            LOGGER.debug("上传失败卡bin",e);
            return ResultDtoFactory.toNack(MessageUtil.getMessage("common.operate.fail"),"上传失败！");
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

}
