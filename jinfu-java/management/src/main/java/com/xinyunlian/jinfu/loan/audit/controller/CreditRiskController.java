package com.xinyunlian.jinfu.loan.audit.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.ylfin.risk.dto.ZhimaCreditResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.loan.audit.dto.TdPreLoanReportResponse;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.UserLinkmanDto;
import com.xinyunlian.jinfu.user.dto.resp.UserDetailDto;
import com.xinyunlian.jinfu.user.service.UserLinkmanService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.ylfin.risk.dto.BfdCreditScoreDto;
import com.ylfin.risk.dto.ZcCreditScoreDto;
import com.ylfin.risk.dto.tongdun.RiskItemDto;
import com.ylfin.risk.dto.tongdun.TdPreLoanReportDto;
import com.ylfin.risk.job.RiskJob;
import com.ylfin.risk.service.RiskCtrlService;
import com.ylfin.rm.loan.dto.LoanRiskItemDto;
import com.ylfin.rm.loan.service.LoanRiskManagementService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Sephy
 * @since: 2017-03-10
 */
@RestController
@RequestMapping("loan/creditRisk")
public class CreditRiskController {

	@Autowired
	private RiskJob riskJob;

	@Autowired
	private RiskCtrlService riskCtrlService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserLinkmanService userLinkmanService;

	@Autowired
	private LoanRiskManagementService loanRiskManagementService;

	@ApiOperation(value = "获取宜信致诚查询报告")
	@RequestMapping(value = "zcScore", method = RequestMethod.GET)
	public ResultDto<ZcCreditScoreDto> getZcCreditScore(@RequestParam String userId) {
		UserDetailDto userDetailDto = userService.findUserDetailByUserId(userId);
		String idCardNo = null;
		if (!Objects.isNull(userDetailDto)) {
            UserInfoDto userDto = userDetailDto.getUserDto();
            if (!Objects.isNull(userDto)) {
				idCardNo = userDto.getIdCardNo();
				if (StringUtils.isNotBlank(idCardNo)) {
					ZcCreditScoreDto zcCreditScoreDto = riskCtrlService.getZcCreditScore(idCardNo);
					return ResultDtoFactory.toAckData(zcCreditScoreDto);
				}
			}
		}
		return ResultDtoFactory.toNack(String.format("用户信息不全, 身份证号码: %s, 无法获取致诚征信信息", idCardNo));
	}

	@ApiOperation(value = "刷新宜信致诚查询报告")
	@RequestMapping(value = "fetchZcScore", method = RequestMethod.GET)
	public ResultDto<ZcCreditScoreDto> fetchZcCreditScore(@RequestParam String userId) {
		UserDetailDto userDetailDto = userService.findUserDetailByUserId(userId);
		String idCardNo = null;
		String username = null;
		if (!Objects.isNull(userDetailDto)) {
			UserInfoDto userDto = userDetailDto.getUserDto();
			if (!Objects.isNull(userDto)) {
				idCardNo = userDto.getIdCardNo();
				username = userDto.getUserName();
				if (StringUtils.isBlank(idCardNo) || StringUtils.length(idCardNo) != 18) {
					return ResultDtoFactory.toNack(String.format("身份 %s 证格式验证失败", idCardNo));
				}
				if (StringUtils.isBlank(username)) {
					return ResultDtoFactory.toNack(String.format("姓名 %s 证格式验证失败", username));
				}
				ZcCreditScoreDto zcCreditScoreDto = riskJob.fetchAndSaveZcCreditScore(idCardNo, username);
				return ResultDtoFactory.toAckData(zcCreditScoreDto);
			}
		}
		return ResultDtoFactory.toNack(String.format("用户信息不全, 身份证号码: %s, 姓名: %s, 无法获取致诚征信信息", idCardNo, username));
	}

	@ApiOperation(value = "获取百融查询报告")
	@RequestMapping(value = "bfdScore", method = RequestMethod.GET)
	public ResultDto<BfdCreditScoreDto> getBfdCreditScore(@RequestParam String userId) {
        UserDetailDto userDetailDto = userService.findUserDetailByUserId(userId);
		String idCardNo = null;
		if (!Objects.isNull(userDetailDto)) {
            UserInfoDto userDto = userDetailDto.getUserDto();
			if (!Objects.isNull(userDto)) {
				idCardNo = userDto.getIdCardNo();
				if (StringUtils.isNotBlank(idCardNo)) {
					BfdCreditScoreDto bfdCreditScoreDto = riskCtrlService.getBfdCreditScore(idCardNo);
					return ResultDtoFactory.toAckData(bfdCreditScoreDto);
				}
			}
		}
		return ResultDtoFactory.toNack(String.format("用户信息不全, 身份证号码: %s, 无法获取百融征信信息", idCardNo));
	}

	@ApiOperation(value = "刷新百融查询报告")
	@RequestMapping(value = "fetchBfdScore", method = RequestMethod.GET)
	public ResultDto<BfdCreditScoreDto> fetchBfdCreditScore(@RequestParam String userId) {
		UserDetailDto userDetailDto = userService.findUserDetailByUserId(userId);
		String idCardNo = null;
		String username = null;
		String mobile = null;
		if (!Objects.isNull(userDetailDto)) {
			UserInfoDto userDto = userDetailDto.getUserDto();
			if (!Objects.isNull(userDto)) {
				idCardNo = userDto.getIdCardNo();
				username = userDto.getUserName();
				mobile = userDto.getMobile();
				if (StringUtils.isBlank(idCardNo) || StringUtils.length(idCardNo) != 18) {
					return ResultDtoFactory.toNack(String.format("身份 %s 证格式验证失败", idCardNo));
				}
				if (StringUtils.isBlank(mobile) || StringUtils.length(mobile) != 11) {
					return ResultDtoFactory.toNack(String.format("手机号码 %s 证格式验证失败", mobile));

				}
				if (StringUtils.isBlank(username)) {
					return ResultDtoFactory.toNack(String.format("姓名 %s 证格式验证失败", username));
				}
				List<UserLinkmanDto> linkmanDtos = userLinkmanService.findByUserId(userId);
				List<String> linkmanMobiles = new ArrayList<>(3);
				int count = 0;
				for (UserLinkmanDto linkmanDto : linkmanDtos) {
					String linkmanMobile = linkmanDto.getMobile();
					if (StringUtils.isNotBlank(linkmanMobile) && StringUtils.length(linkmanMobile) == 11) {
						linkmanMobiles.add(linkmanMobile);
						count++;
						if (count == 3) {
							break;
						}
					}
				}
				BfdCreditScoreDto bfdCreditScoreDto = riskJob.fetchAndSaveBfdCreditScore(idCardNo, mobile, username,
						linkmanMobiles);
				return ResultDtoFactory.toAckData(bfdCreditScoreDto);
			}
		}
		return ResultDtoFactory
				.toNack(String.format("用户信息不全, 身份证号码: %s, 姓名: %s, 手机号码: %s, 无法获取百融征信信息", idCardNo, username, mobile));
	}

	@ApiOperation(value = "风控指标数据")
	@RequestMapping(value = "loanRisk", method = RequestMethod.GET)
	public ResultDto<List<LoanRiskItemDto>> getLoanRisk(@RequestParam String loanId){
		List<LoanRiskItemDto> loanRiskItems = loanRiskManagementService.getLoanRiskItems(loanId);
		return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanRiskItems);
	}

	@ApiOperation(value = "获取同盾报告")
	@RequestMapping(value = "tdRreLoanReport", method = RequestMethod.GET)
	public ResultDto<TdPreLoanReportResponse> getTdRreLoanReport(@RequestParam String loapApplyId) {
		TdPreLoanReportResponse response = null;
		TdPreLoanReportDto tdPreLoanReportDto = riskCtrlService.getTdPreLoanReport(loapApplyId);
		if (tdPreLoanReportDto != null) {
			response = ConverterService.convert(tdPreLoanReportDto, TdPreLoanReportResponse.class);
			response.setContacts(tdPreLoanReportDto.getContacts());
			List<RiskItemDto> riskItems = tdPreLoanReportDto.getRiskItems();
			response.setRiskItemMap(groupRiskItems(riskItems));
		}
		return ResultDtoFactory.toAckData(response);
	}

	@ApiOperation(value = "刷新同盾报告")
	@RequestMapping(value = "fetchTdRreLoanReport", method = RequestMethod.GET)
	public ResultDto<TdPreLoanReportResponse> fetchTdRreLoanReport(@RequestParam String loapApplyId) {
		TdPreLoanReportResponse response = null;
		TdPreLoanReportDto tdPreLoanReportDto = riskJob.fetchAndSavePreLoanReport(loapApplyId);
		if (tdPreLoanReportDto != null) {
			response = ConverterService.convert(tdPreLoanReportDto, TdPreLoanReportResponse.class);
			response.setContacts(tdPreLoanReportDto.getContacts());
			List<RiskItemDto> riskItems = tdPreLoanReportDto.getRiskItems();
			response.setRiskItemMap(groupRiskItems(riskItems));
		}
		return ResultDtoFactory.toAckData(response);
	}

	@ApiOperation(value = "获取芝麻信用")
	@RequestMapping(value = "zhimaCredit", method = RequestMethod.GET)
	public ResultDto<ZhimaCreditResultDto> getZhimaCreditReport(@RequestParam String loapApplyId) {
		ZhimaCreditResultDto result = riskCtrlService.getZhimaCreditResult(loapApplyId);
		return ResultDtoFactory.toAckData(result);
	}

	@ApiOperation(value = "刷新芝麻信用")
	@RequestMapping(value = "fetchZhimaCredit", method = RequestMethod.GET)
	public ResultDto<ZhimaCreditResultDto> fetchZhimaCreditReport(@RequestParam String loapApplyId) {
		ZhimaCreditResultDto result = riskJob.fetchAndSaveZhimaCreditReport(loapApplyId);
		return ResultDtoFactory.toAckData(result);
	}


	private static Map<String, List<RiskItemDto>> groupRiskItems(List<RiskItemDto> riskItems) {
		if (CollectionUtils.isNotEmpty(riskItems)) {
			Map<String, List<RiskItemDto>> riskItemMap = riskItems.stream().collect(Collectors.groupingBy(RiskItemDto::getGroup));
			return riskItemMap;
		}
		return null;
	}
}
