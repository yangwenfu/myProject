package com.xinyunlian.jinfu.bank.service;

import com.xinyunlian.jinfu.bank.dao.BankCardBinDao;
import com.xinyunlian.jinfu.bank.dao.BankCardDao;
import com.xinyunlian.jinfu.bank.dao.BankInfDao;
import com.xinyunlian.jinfu.bank.dto.BankCardBinDto;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.BankInfDto;
import com.xinyunlian.jinfu.bank.entity.BankCardBinPo;
import com.xinyunlian.jinfu.bank.entity.BankCardPo;
import com.xinyunlian.jinfu.bank.entity.BankInfPo;
import com.xinyunlian.jinfu.bank.enums.EBankCardStatus;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.user.dao.UserDao;
import com.xinyunlian.jinfu.user.entity.UserInfoPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行ServiceImpl
 * @author KimLL
 * @version
 */

@Service
public class BankServiceImpl implements BankService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankServiceImpl.class);
	@Autowired
	private BankInfDao bankInfDao;
	@Autowired
	private BankCardDao bankCardDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private BankCardBinDao bankCardBinDao;
	@Value("${file.addr}")
	private String fileAddr;

	@Override
	@Transactional
	public BankCardDto saveBankCard(BankCardDto bankCardDto) throws BizServiceException{
		//用户信息不存在
		UserInfoPo userInfoPo = userDao.findOne(bankCardDto.getUserId());
		if(userInfoPo == null){
			throw new BizServiceException(EErrorCode.USER_NOT_EXIST,"用户不存在");
		}

		//银行卡号不能重复
		if(!StringUtils.isEmpty(bankCardDto.getBankCardNo())) {
			BankCardPo bank = bankCardDao.findByUserIdAndBankCardNoAndStatus(userInfoPo.getUserId(),bankCardDto.getBankCardNo(), EBankCardStatus.NORMAL);
			if (bank != null) {
				throw new BizServiceException(EErrorCode.BANK_CARD_NO_IS_EXIST,"银行卡号已存在");
			}
		}

		//银行信息不存在
		BankInfPo bankInfPo = bankInfDao.findOne(bankCardDto.getBankId());
		if(bankInfPo == null){
			throw new BizServiceException(EErrorCode.BANK_NOT_EXIST,"该银行不存在");
		}

		//绑卡银行卡姓名和用户名不一致
		if(!StringUtils.equals(userInfoPo.getUserName(),bankCardDto.getBankCardName())){
			throw new BizServiceException(EErrorCode.USER_NAME_AND_BANK_USER_NAME_NOT_SAME,"用户名和银行卡账户名不一致");
		}

		if(!StringUtils.equals(userInfoPo.getIdCardNo(),bankCardDto.getIdCardNo())){
			throw new BizServiceException(EErrorCode.USER_ID_CARD_AND_BANK_ID_CARD_NOT_SAME,"银行卡身份证号与身份证号不一致");
		}

		//赋银行信息
		BankCardPo bankCardPo = ConverterService.convert(bankCardDto, BankCardPo.class);
		bankCardPo.setStatus(EBankCardStatus.NORMAL);
		bankCardPo.setBankName(bankInfPo.getBankName());
		bankCardPo.setBankCnapsCode(bankInfPo.getBankCnapsCode());
		bankCardPo.setBankCode(bankInfPo.getBankCode());
		userInfoPo.setBankAuth(true);

		bankCardDao.save(bankCardPo);
		userDao.save(userInfoPo);
		bankCardDto.setBankCardId(bankCardPo.getBankCardId());
		bankCardDto.setBankName(bankCardPo.getBankName());
		return bankCardDto;
	}

	@Override
	@Transactional
	public BankCardDto saveWithUserName(BankCardDto bankCardDto) throws BizServiceException{
		//用户信息不存在
		UserInfoPo userInfoPo = userDao.findOne(bankCardDto.getUserId());
		if(userInfoPo == null){
			throw new BizServiceException(EErrorCode.USER_NOT_EXIST,"用户不存在");
		}

		//银行卡号不能重复
		if(!StringUtils.isEmpty(bankCardDto.getBankCardNo())) {
			BankCardPo bank = bankCardDao.findByUserIdAndBankCardNoAndStatus(userInfoPo.getUserId(),bankCardDto.getBankCardNo(), EBankCardStatus.NORMAL);
			if (bank != null) {
				throw new BizServiceException(EErrorCode.BANK_CARD_NO_IS_EXIST,"银行卡号已存在");
			}
		}

		//银行信息不存在
		BankInfPo bankInfPo = bankInfDao.findOne(bankCardDto.getBankId());
		if(bankInfPo == null){
			throw new BizServiceException(EErrorCode.BANK_NOT_EXIST,"该银行不存在");
		}

		if(!StringUtils.isEmpty(userInfoPo.getUserName()) && !StringUtils.isEmpty(userInfoPo.getIdCardNo())) {
			//绑卡银行卡姓名和用户名不一致
			if (!StringUtils.equals(userInfoPo.getUserName(), bankCardDto.getBankCardName())) {
				throw new BizServiceException(EErrorCode.USER_NAME_AND_BANK_USER_NAME_NOT_SAME, "用户名和银行卡账户名不一致");
			}

			if (!StringUtils.equals(userInfoPo.getIdCardNo(), bankCardDto.getIdCardNo())) {
				throw new BizServiceException(EErrorCode.USER_ID_CARD_AND_BANK_ID_CARD_NOT_SAME, "银行卡身份证号与身份证号不一致");
			}
		}

		//赋银行信息
		BankCardPo bankCardPo = ConverterService.convert(bankCardDto, BankCardPo.class);
		bankCardPo.setStatus(EBankCardStatus.NORMAL);
		bankCardPo.setBankName(bankInfPo.getBankName());
		bankCardPo.setBankCnapsCode(bankInfPo.getBankCnapsCode());
		bankCardPo.setBankCode(bankInfPo.getBankCode());

		userInfoPo.setBankAuth(true);
		userInfoPo.setUserName(bankCardDto.getBankCardName());
		userInfoPo.setIdCardNo(bankCardDto.getIdCardNo());

		bankCardDao.save(bankCardPo);
		userDao.save(userInfoPo);
		bankCardDto.setBankCardId(bankCardPo.getBankCardId());
		bankCardDto.setBankName(bankCardPo.getBankName());
		return bankCardDto;
	}

	@Override
	@Transactional
	public BankCardDto save(BankCardDto bankCardDto) throws BizServiceException{
		//用户信息不存在
		UserInfoPo userInfoPo = userDao.findOne(bankCardDto.getUserId());
		if(userInfoPo == null){
			throw new BizServiceException(EErrorCode.USER_NOT_EXIST,"用户不存在");
		}
		userInfoPo.setBankAuth(true);
		userDao.save(userInfoPo);

		//赋银行信息
		BankCardPo bankCardPo = ConverterService.convert(bankCardDto, BankCardPo.class);
		bankCardPo.setStatus(EBankCardStatus.NORMAL);
		BankCardPo savedPo = bankCardDao.save(bankCardPo);
		return ConverterService.convert(savedPo, BankCardDto.class);
	}

	@Override
	public BankCardDto getBankCard(Long bankCardId) {
		BankCardPo bankCardPo = bankCardDao.findOne(bankCardId);
		BankCardDto bankCardDto = ConverterService.convert(bankCardPo, BankCardDto.class);
		if(bankCardDto != null) {
			List<BankInfPo>  bankInfPos = bankInfDao.findByBankCode(bankCardDto.getBankCode().toUpperCase());
			if(!CollectionUtils.isEmpty(bankInfPos)){
				bankCardDto.setBankLogo(fileAddr + bankInfPos.get(0).getBankLogo());
			}
		}
		return bankCardDto;
	}

	@Override
	public void deleteBankCard(Long bankCardId) {
		BankCardPo bankCardPo = bankCardDao.findOne(bankCardId);
		bankCardPo.setStatus(EBankCardStatus.DELETE);
		bankCardDao.save(bankCardPo);
	}

	@Override
	public List<BankCardDto> findByUserId(String userId) {
		List<BankCardDto> bankCardDtoList = null;
		List<BankCardPo> bankCardPoList = bankCardDao.findByUserIdAndStatus(userId,EBankCardStatus.NORMAL);

		List<BankInfPo> bankInfList = bankInfDao.findAll(new Sort(Sort.Direction.DESC, "bankId"));
		Map<String, BankInfPo> bankMap = new HashMap<>();
		if (!CollectionUtils.isEmpty(bankInfList)){
			for (BankInfPo bankInfPo : bankInfList){
				bankMap.put(bankInfPo.getBankCode().toUpperCase(), bankInfPo);
			}
		}

		if(bankCardPoList != null){
			bankCardDtoList = new ArrayList<>();
			for(BankCardPo bankCardPo : bankCardPoList){
				BankCardDto bankCardDto = ConverterService.convert(bankCardPo, BankCardDto.class);
				BankInfPo bankInfPo = bankMap.get(bankCardDto.getBankCode().toUpperCase());
				if (bankInfPo != null){
					bankCardDto.setBankLogo(fileAddr + StaticResourceSecurity.getSecurityURI(bankInfPo.getBankLogo()));
				}
				bankCardDtoList.add(bankCardDto);
			}
		}
		return bankCardDtoList;
	}


	@Override
	public Long countByUserId(String userId){
		return bankCardDao.countByUserIdAndStatus(userId,EBankCardStatus.NORMAL);
	}

	@Override
	public Long countByUserIdSupport(String userId){
		return bankCardDao.countByUserIdSupport(userId);
	}

	@Override
	public List<BankInfDto> findBankInfAll() {
		List<BankInfDto> bankInfDtoList = null;
		List<BankInfPo> bankInfPoList = bankInfDao.findAll();
		if(bankInfPoList != null){
			bankInfDtoList = new ArrayList<>();
			for(BankInfPo bankInfPo : bankInfPoList){
				BankInfDto bankInfDto = ConverterService.convert(bankInfPo, BankInfDto.class);
				bankInfDtoList.add(bankInfDto);
			}
		}
		return bankInfDtoList;
	}

	@Override
	public List<BankInfDto> findBySupport() {
		List<BankInfDto> bankInfDtoList = null;
		List<BankInfPo> bankInfPoList = bankInfDao.findBySupport(true);
		if(bankInfPoList != null){
			bankInfDtoList = new ArrayList<>();
			for(BankInfPo bankInfPo : bankInfPoList){
				BankInfDto bankInfDto = ConverterService.convert(bankInfPo, BankInfDto.class);
				bankInfDtoList.add(bankInfDto);
			}
		}
		return bankInfDtoList;
	}

	@Override
	@Transactional
	public void save(List<BankCardBinDto> bankCardBinDtos){
		if(!CollectionUtils.isEmpty(bankCardBinDtos)) {
			bankCardBinDtos.forEach(bankCardBinDto -> {
				BankCardBinPo bankCardBinPo = ConverterService.convert(bankCardBinDto, BankCardBinPo.class);
				bankCardBinDao.save(bankCardBinPo);
			});
		}
	}

	@Override
	public BankCardBinDto findByNumLengthAndBin(String cardNo){
		List<BankCardBinPo> bankCardBinPos = bankCardBinDao.findByCardNo(cardNo);
		if(bankCardBinPos == null ){
			return null;
		}
		if(bankCardBinPos.size()>0){
			BankCardBinDto bankCardBinDto = ConverterService.convert(bankCardBinPos.get(0),BankCardBinDto.class);
			return bankCardBinDto;
		}
		return null;
	}

	@Override
	public List<Object[]> findByStoreIds(List<Long> storeIds){
		List<Object[]> list = bankCardDao.findByStoreId(storeIds);
		return list;
	}

	@Override
	public List<BankInfDto> findByBankCode(String bankCode) {
		List<BankInfDto> ret = new ArrayList<>();

		List<BankInfPo> bankInfoList = bankInfDao.findByBankCode(bankCode);
		if (!CollectionUtils.isEmpty(bankInfoList)){
			for (BankInfPo po:bankInfoList) {
				BankInfDto dto = ConverterService.convert(po, BankInfDto.class);
				ret.add(dto);
			}
		}

		return ret;
	}

	@Override
	public BankInfDto getBank(Long bankId) {
		BankInfPo bankInfPo = bankInfDao.findOne(bankId);
		LOGGER.debug("==收银台添加银行卡调试 start==");
		LOGGER.debug(bankId.toString());
		LOGGER.debug(bankInfPo==null?"银行信息获取不到":"获取到了");
		LOGGER.debug("==收银台添加银行卡调试 start==");
		BankInfDto bankInfDto = ConverterService.convert(bankInfPo, BankInfDto.class);
		if (bankInfDto != null){
			bankInfDto.setBankLogo(fileAddr + StaticResourceSecurity.getSecurityURI(bankInfPo.getBankLogo()));
		}
		return bankInfDto;
	}

	@Override
	public BankCardDto getBankCard(String userId, String bankCardNo) {
		BankCardPo po = bankCardDao.findByUserIdAndBankCardNoAndStatus(userId, bankCardNo, EBankCardStatus.NORMAL);
		return ConverterService.convert(po, BankCardDto.class);
	}

}
