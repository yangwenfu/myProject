package com.xinyunlian.jinfu.paycode.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.sms.SmsUtil;
import com.xinyunlian.jinfu.payCode.dto.PayCodeBalanceLogSearchDto;
import com.xinyunlian.jinfu.payCode.dto.PayCodeDto;
import com.xinyunlian.jinfu.payCode.dto.PayCodeSearchDto;
import com.xinyunlian.jinfu.payCode.enums.PayCodeBalanceType;
import com.xinyunlian.jinfu.payCode.enums.PayCodeStatus;
import com.xinyunlian.jinfu.payCode.service.PayCodeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by menglei on 2017/8/29.
 */
@Controller
@RequestMapping(value = "paycode")
public class YmPayCodeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YmPayCodeController.class);

    @Autowired
    private PayCodeService payCodeService;

    /**
     * 支付码列表
     *
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("支付码列表")
    public ResultDto<PayCodeSearchDto> getList(PayCodeSearchDto searchDto) {
        searchDto = payCodeService.list(searchDto);
        return ResultDtoFactory.toAck("获取成功", searchDto);
    }

    /**
     * 详情
     * @param payCodeNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation("支付码详情")
    public ResultDto<PayCodeDto> getDetail(@RequestParam String payCodeNo) {
        PayCodeDto payCodeDto = payCodeService.get(payCodeNo);
        return ResultDtoFactory.toAck("获取成功", payCodeDto);
    }

    /**
     * 流水明细 mobile和paycode
     * @param searchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logList", method = RequestMethod.GET)
    @ApiOperation("支付码流水明细 mobile和paycode必传 type按类型传")
    public ResultDto<PayCodeBalanceLogSearchDto> getLogList(PayCodeBalanceLogSearchDto searchDto) {
        PayCodeBalanceLogSearchDto list = payCodeService.logList(searchDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 支付码冻结
     *
     * @param payCodeNo
     * @return
     */
    @RequestMapping(value = "frozen", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("支付码冻结")
    public ResultDto<Object> frozen(@RequestParam String payCodeNo) {
        PayCodeDto payCodeDto = payCodeService.get(payCodeNo);
        if (payCodeDto == null) {
            return ResultDtoFactory.toNack("操作失败");
        }
        PayCodeStatus payCodeStatus;
        if (PayCodeStatus.FROZEN.equals(payCodeDto.getStatus())) {
            payCodeStatus = PayCodeStatus.ENABLE;
        } else if (PayCodeStatus.ENABLE.equals(payCodeDto.getStatus())) {
            payCodeStatus = PayCodeStatus.FROZEN;
        } else {
            return ResultDtoFactory.toNack("操作失败");
        }
        PayCodeDto dto = payCodeService.updateStatus(payCodeDto.getPayCodeNo(), payCodeStatus);

        if (PayCodeStatus.FROZEN.equals(dto.getStatus())) {
            Map<String, String> params = new HashMap<>();
            SmsUtil.send("153", params, payCodeDto.getMobile());
        }
        return ResultDtoFactory.toAck("操作成功");
    }

    /**
     * 支付码退卡
     *
     * @param payCodeNo
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("支付码退卡")
    public ResultDto<Object> delete(@RequestParam String payCodeNo) {
        PayCodeDto payCodeDto = payCodeService.get(payCodeNo);
        if (payCodeDto == null) {
            return ResultDtoFactory.toNack("操作失败");
        }
        payCodeService.updateStatus(payCodeDto.getPayCodeNo(), PayCodeStatus.DISABLE);

        Map<String, String> params = new HashMap<>();
        SmsUtil.send("154", params, payCodeDto.getMobile());
        return ResultDtoFactory.toAck("操作成功");
    }

    /**
     * 支付码绑定
     *
     * @param payCodeDto
     * @return
     */
    @RequestMapping(value = "bind", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("支付码绑定")
    public ResultDto<Object> bind(@RequestBody PayCodeDto payCodeDto) {
        PayCodeDto dto = null;

        try {
            dto = payCodeService.save(payCodeDto);
        } catch (Exception e) {
            return ResultDtoFactory.toNack(e.getMessage());
        }
        if (dto == null) {
            return ResultDtoFactory.toNack("操作失败");
        }
        return ResultDtoFactory.toAck("操作成功");
    }

    /**
     * 支付码充值
     *
     * @param payCodeDto
     * @return
     */
    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("支付码充值")
    public ResultDto<Object> recharge(@RequestBody PayCodeDto payCodeDto) {
        PayCodeDto dto = payCodeService.get(payCodeDto.getPayCodeNo());
        if (dto == null) {
            return ResultDtoFactory.toNack("支付码不存在");
        }
        if (payCodeDto.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            return ResultDtoFactory.toNack("充值金额必须大于0");
        }
        PayCodeDto payCode = null;
        try {
            payCode = payCodeService.balance(dto.getPayCodeNo(), payCodeDto.getBalance(), PayCodeBalanceType.RECHARGE, null);
        } catch (Exception e) {
            return ResultDtoFactory.toNack(e.getMessage());
        }
        if (payCode == null) {
            return ResultDtoFactory.toNack("充值失败");
        }

        Map<String, String> params = new HashMap<>();
        params.put("recharge", String.valueOf(payCodeDto.getBalance()));
        params.put("accountBalance", String.valueOf(payCode.getBalance()));
        SmsUtil.send("151", params, dto.getMobile());
        return ResultDtoFactory.toAck("操作成功");
    }

}
