package com.ylfin.wallet.portal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.xinyunlian.jinfu.bank.dto.BankCardBinDto;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.BankInfDto;
import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.finaccbankcard.service.FinAccBankCardService;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.CertifyInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.ylfin.core.auth.AuthenticationAdapter;
import com.ylfin.core.beanmapper.BeanMapperOperations;
import com.ylfin.core.exception.ServiceException;
import com.ylfin.core.sms.SmsOperations;
import com.ylfin.core.tool.VerifyCodeService;
import com.ylfin.wallet.portal.CurrentUser;
import com.ylfin.wallet.portal.controller.req.BankcardBindingReq;
import com.ylfin.wallet.portal.controller.vo.BankcardBin;
import com.ylfin.wallet.portal.controller.vo.BankcardInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("银行卡接口")
@RestController
@RequestMapping("/api/bankcard")
public class BankcardController {

	@Autowired
	private BankService bankService;

	@Autowired
	private UserService userService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private AuthenticationAdapter<CurrentUser, String> authenticationAdapter;

	@Autowired
	private YMMemberService ymMemberService;

	@Autowired
	private FinAccBankCardService finAccBankCardService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private BeanMapperOperations beanMapperOperations;

	@Autowired
	private SmsOperations smsOperations;

	@Autowired
	private VerifyCodeService verifyCodeService;

	private static final String VERIFY_CODE_PREFIX = "verifyCode:bankcard:";

	@Value("${sms.tplKey.bankcard}")
	private String tplKey;

	@ApiOperation("识别银行卡卡bin")
	@GetMapping("/card-bin")
	public BankcardBin cardBin(@RequestParam("bankcardNo") String bankcardNo) {
		if (StringUtils.isBlank(bankcardNo)){
			throw new ServiceException("银行卡号不能为空");
		}
		BankCardBinDto bankCardBinDto = bankService.findByNumLengthAndBin(bankcardNo);
		BankcardBin bankcardBin = beanMapperOperations.map(bankCardBinDto, BankcardBin.class);
		if (bankCardBinDto != null) {
			if (null == bankCardBinDto.getBankId()) {
				throw new ServiceException("对不起，暂不支持该银行卡，请更换银行卡");
			}
			if (!StringUtils.equals("借记卡", bankCardBinDto.getCardType())) {
				throw new ServiceException("对不起，暂仅支持借记卡，请更换银行卡");
			}
			BankInfDto bankInfDto = bankService.getBank(bankCardBinDto.getBankId());
			bankcardBin.setCardType(ECardType.DEBIT);
			bankcardBin.setBankCnapsCode(bankInfDto.getBankCnapsCode());
			bankcardBin.setBankLogo(bankInfDto.getBankLogo());
			return bankcardBin;
		} else {
			throw new ServiceException("卡号输入错误，请核对卡号");
		}
	}

	@ApiOperation("绑定银行卡时发送验证码")
	@GetMapping("/verify-code")
	public void sendVerifyCode(@RequestParam("mobile") String mobile) {
		UserInfoDto userInfoDto = userService.findUserByUserId(authenticationAdapter.getCurrentUserId());

		if (StringUtils.isBlank(userInfoDto.getMobile())) {
			throw new ServiceException("手机号码不能为空");
		}
		String key = VERIFY_CODE_PREFIX + mobile;
		String verifyCode = verifyCodeService.generateVerifyCode(key);
		Map<String, String> params = new HashMap<>();
		params.put("code", verifyCode);
		smsOperations.send(tplKey, params, mobile);

	}

	@ApiOperation("绑定银行卡(四要素认证)")
	@PostMapping
	public void binding(@RequestBody  @Valid BankcardBindingReq bankcardBindingReq) {
		String key = VERIFY_CODE_PREFIX + bankcardBindingReq.getMobileNo();
		boolean flag = verifyCodeService.verify(key, bankcardBindingReq.getVerifyCode());
		if (!flag) {
			throw new ServiceException("验证码错误");
		}
		String currentUserId = authenticationAdapter.getCurrentUserId();
		UserInfoDto userInfoDto = userService.findUserByUserId(currentUserId);
		if (userInfoDto == null){
			throw new ServiceException("当前用户不存在");
		}
		if (StringUtils.isBlank(userInfoDto.getUserName()) || !userInfoDto.getUserName().equals(bankcardBindingReq.getBankCardName())){
			throw new ServiceException("开户人和实名用户不匹配");
		}
		CertifyInfoDto certifyInfoDto = new CertifyInfoDto();
		certifyInfoDto.setBankCardNo(bankcardBindingReq.getBankCardNo());
		certifyInfoDto.setUserId(currentUserId);
		certifyInfoDto.setUserName(bankcardBindingReq.getBankCardName());
		//绑定银行卡之前首先要实名认证,用户的userName一定要和银行卡的开户名字一致
		userService.certify(certifyInfoDto);//如果没有抛出异常代表认证成功

		BankCardDto bankCardDto = beanMapperOperations.map(bankcardBindingReq,BankCardDto.class);
		bankCardDto.setUserId(currentUserId);
		bankCardDto.setCreateTs(new Date());
		bankService.saveBankCard(bankCardDto);
		//生成用户支付合同
		UserContractDto userContractDto = new UserContractDto();
		userContractDto.setTemplateType(ECntrctTmpltType.ZFXY);
		contractService.saveContract(userContractDto, userInfoDto);
	}

	@ApiOperation("解绑银行卡")
	@PostMapping("/{bankcardId}")
	public void unbinding(@PathVariable("bankcardId") Long bankcardId) {
		BankCardDto bankCardDto = bankService.getBankCard(bankcardId);
		//检查理财是否绑定银行卡
		if (finAccBankCardService.checkBankCardExist(bankCardDto.getBankCardNo())) {
			throw new ServiceException("您购买了理财产品，无法删除银行卡");
		}
		//检查云码是否绑定银行卡
		if (ymMemberService.findByBankCardId(bankcardId).size() > 0) {
			throw new ServiceException("您绑定了云码，无法删除银行卡");
		}
		//检查小贷是否绑定银行卡
		List<LoanDtlDto> loanDtlDtos = loanService.findByBankCardId(bankcardId);
		if (!CollectionUtils.isEmpty(loanDtlDtos)) {
			for (LoanDtlDto loanDtlDto : loanDtlDtos) {
				if (loanDtlDto.getLoanStat() != ELoanStat.PAID) {
					throw new ServiceException("您购买了贷款产品，无法删除银行卡");
				}
			}
		}
		bankService.deleteBankCard(bankcardId);

	}

	@ApiOperation("获取银行卡列表信息")
	@GetMapping
	public Page<BankcardInfo> list() {
		String currentUserId = authenticationAdapter.getCurrentUserId();
		List<BankCardDto> bankCardDtos = bankService.findByUserId(currentUserId);
		if (CollectionUtils.isEmpty(bankCardDtos)){
			return null;
		}
		List<BankcardInfo> list = new ArrayList<>();
		for (BankCardDto bankCardDto : bankCardDtos) {
			BankcardInfo bankcardInfo = beanMapperOperations.map(bankCardDto, BankcardInfo.class);
			list.add(bankcardInfo);
		}
		Page<BankcardInfo> bankcardInfoPage = new PageImpl(list);
		return bankcardInfoPage;
	}
}
