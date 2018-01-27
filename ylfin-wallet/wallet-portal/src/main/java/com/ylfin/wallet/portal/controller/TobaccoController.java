package com.ylfin.wallet.portal.controller;

import java.util.List;

import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.spider.dto.req.AuthLoginDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import com.ylfin.core.auth.AuthenticationAdapter;
import com.ylfin.core.beanmapper.BeanMapperOperations;
import com.ylfin.core.exception.ServiceException;
import com.ylfin.wallet.portal.CurrentUser;
import com.ylfin.wallet.portal.controller.vo.CheckResult;
import com.ylfin.wallet.portal.controller.vo.TobaccoTradeAccount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("烟草证号接口")
@RestController
@RequestMapping("/api/tobacco")
public class TobaccoController {

	@Autowired
	private AuthenticationAdapter<CurrentUser, String> authenticationAdapter;

	@Autowired
	private StoreService storeService;

	@Autowired
	private BeanMapperOperations beanMapperOperations;

	@Autowired
	private RiskUserInfoService riskUserInfoService;

	@Autowired
	private UserService userService;

	@Autowired
	private ContractService contractService;

	@ApiOperation("当前用户是否授权订烟系统")
	@GetMapping("/trade-account/check")
	public CheckResult exists() {
		String currentUserId = authenticationAdapter.getCurrentUserId();
		Boolean b = riskUserInfoService.isAuthed(currentUserId);
		CheckResult checkResult = new CheckResult();
		checkResult.setResult(b);
		return checkResult;
	}

    @ApiOperation("当前用户更新订烟系统帐号")
    @PostMapping("/trade-account")
    public void updateAccount(@RequestBody @Valid TobaccoTradeAccount account) {
        String currentUserId = authenticationAdapter.getCurrentUserId();
        List<StoreInfDto> storeList = storeService.findByUserId(currentUserId);
        if(CollectionUtils.isEmpty(storeList)){
            throw new ServiceException("请先添加店铺");
        }
        StoreInfDto store = storeList.get(0);
        for(StoreInfDto storeInfDto : storeList) {
            if(account.getUsername().equals(storeInfDto.getTobaccoCertificateNo())){
                store = storeInfDto;
                break;
            }
        }
        AuthLoginDto authLoginDto = beanMapperOperations.map(account,AuthLoginDto.class);
        authLoginDto.setUserId(currentUserId);
        if(StringUtils.isNotEmpty(store.getProvinceId())){
            authLoginDto.setProvinceId(Long.parseLong(store.getProvinceId()));
        }
        if(StringUtils.isNotEmpty(store.getCityId())) {
            authLoginDto.setCityId(Long.parseLong(store.getCityId()));
        }
        if(StringUtils.isNotEmpty(store.getAreaId())) {
            authLoginDto.setAreaId(Long.parseLong(store.getAreaId()));
        }
        boolean b = riskUserInfoService.authLogin(authLoginDto);
        if (!b){
            throw new ServiceException("授权失败");
        }
        //合同签订
        this.saveContractPCSQ(currentUserId);
    }

	private void saveContractPCSQ(String userId) {
		UserContractDto userContractDto = new UserContractDto();
		userContractDto.setUserId(userId);
		userContractDto.setTemplateType(ECntrctTmpltType.XSMSQ);
		UserInfoDto userInfoDto = userService.findUserByUserId(authenticationAdapter.getCurrentUserId());
		UserContractDto existsContract = contractService.getUserContract(userId, ECntrctTmpltType.XSMSQ);

		if (existsContract == null) {
			contractService.saveContract(userContractDto, userInfoDto);
		} else {
			contractService.updateContract(userContractDto, userInfoDto);
		}
	}
}
