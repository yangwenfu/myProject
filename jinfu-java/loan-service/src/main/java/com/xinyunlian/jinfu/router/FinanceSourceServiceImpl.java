package com.xinyunlian.jinfu.router;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.external.dto.LoanRecordMqDto;
import com.xinyunlian.jinfu.loan.dao.LoanApplDao;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.router.dto.FinanceSourceConfigDto;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.dto.FinanceSourceLoanDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.ylfin.fundRouter.dto.AreaDto;
import com.ylfin.fundRouter.dto.LoanDto;
import com.ylfin.fundRouter.dto.SourceFundConfigDto;
import com.ylfin.fundRouter.dto.SourceFundDto;
import com.ylfin.fundRouter.service.SourceFundConfigService;
import com.ylfin.fundRouter.service.SourceFundService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资金路由方法的封装，由于原资金路由是小贷先实现的，后期切换到SourceFundService中
 * @author willwang
 */
@Service
public class FinanceSourceServiceImpl implements FinanceSourceService{

    @Autowired
    private SourceFundService sourceFundService;

    @Autowired
    private SourceFundConfigService sourceFundConfigService;

    @Autowired
    private LoanApplDao loanApplDao;

    @Autowired
    private QueueSender queueSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceSourceService.class);

    @Override
    public FinanceSourceDto getActive(FinanceSourceLoanDto financeSourceLoanDto) throws BizServiceException{

        SourceFundDto sourceFundDto = sourceFundService.getRightSourceFund(this.getLoanDto(financeSourceLoanDto));

        return this.getFinanceSourceDto(sourceFundDto);
    }

    @Override
    public FinanceSourceDto getOwn(FinanceSourceLoanDto financeSourceLoanDto) throws BizServiceException{
        LoanDto loanDto = new LoanDto();
        AreaDto areaDto = new AreaDto();

        areaDto.setProvinceId(financeSourceLoanDto.getProvinceId());
        areaDto.setCityId(financeSourceLoanDto.getCityId());

        loanDto.setLoanNumber(financeSourceLoanDto.getApplId());
        loanDto.setProdId(financeSourceLoanDto.getProdId());
        loanDto.setOneLoanMoney(financeSourceLoanDto.getLoanAmt());
        loanDto.setAreaDto(areaDto);

        LOGGER.debug("getOwn,{}", financeSourceLoanDto);

        SourceFundDto sourceFundDto = sourceFundService.getOwnSourceFund(this.getLoanDto(financeSourceLoanDto));

        return this.getFinanceSourceDto(sourceFundDto);
    }

    @Override
    public FinanceSourceDto findById(Integer id) throws BizServiceException{

        if(id == null){
            //默认当成自有
            FinanceSourceDto financeSourceDto = new FinanceSourceDto();
            financeSourceDto.setId(0);
            financeSourceDto.setType(EFinanceSourceType.OWN);
            return financeSourceDto;
        }

        LOGGER.debug("findById,{}", id);

        SourceFundDto sourceFundDto = sourceFundService.getById(Long.parseLong(Integer.toString(id)));

        return this.getFinanceSourceDto(sourceFundDto);
    }

    @Override
    public List<FinanceSourceDto> getAll() throws BizServiceException{
        List<FinanceSourceDto> financeSourceDtos = new ArrayList<>();

        List<SourceFundDto> sourceFundDtos = sourceFundService.getAllList();

        if(CollectionUtils.isNotEmpty(sourceFundDtos)){

            for (SourceFundDto sourceFundDto : sourceFundDtos) {
                financeSourceDtos.add(this.getFinanceSourceDto(sourceFundDto));
            }

        }

        return financeSourceDtos;
    }

    public Map<Integer, FinanceSourceDto> getAllMap() throws BizServiceException {

        List<FinanceSourceDto> financeSourceDtos = this.getAll();
        Map<Integer, FinanceSourceDto> map = new HashMap<>();
        if (financeSourceDtos.size() > 0) {
            financeSourceDtos.forEach(financeSourceDto -> map.put(financeSourceDto.getId(), financeSourceDto));
        }
        return map;
    }

    @Override
    public FinanceSourceConfigDto getConfig(Integer id) throws BizServiceException {
        SourceFundConfigDto sourceFundConfigDto = sourceFundConfigService.getBySourceFundId(Long.parseLong(Integer.toString(id)));

        if(sourceFundConfigDto == null){
            return null;
        }

        FinanceSourceConfigDto financeSourceConfigDto = ConverterService.convert(sourceFundConfigDto, FinanceSourceConfigDto.class);

        LOGGER.debug("getConfig,{}", financeSourceConfigDto);

        financeSourceConfigDto.setDepository(false);

        if(financeSourceConfigDto.getConnectionTube() != null && financeSourceConfigDto.getConnectionTube()){
            financeSourceConfigDto.setDepository(true);
        }

        return financeSourceConfigDto;
    }

    @Override
    public void revertAmt(String applId) throws BizServiceException {

        LoanApplPo loanApplPo = loanApplDao.findOne(applId);

        FinanceSourceDto financeSourceDto = this.findById(loanApplPo.getFinanceSourceId());

        if(financeSourceDto != null){
            //给资金路由发送撤销消息，认为额度申请已经失效
            LoanRecordMqDto data = new LoanRecordMqDto(applId, BigDecimal.ZERO, financeSourceDto.getType().getCode());
            queueSender.send(DestinationDefine.LOAN_APPLY_OUT_RESULT, JsonUtil.toJson(data));
        }

    }

    /**
     * 小贷端资金路由传参转换成资金路由方传参
     * @param financeSourceLoanDto
     * @return
     */
    private LoanDto getLoanDto(FinanceSourceLoanDto financeSourceLoanDto){

        LoanDto loanDto = new LoanDto();
        AreaDto areaDto = new AreaDto();

        areaDto.setProvinceId(financeSourceLoanDto.getProvinceId());
        areaDto.setCityId(financeSourceLoanDto.getCityId());

        loanDto.setLoanNumber(financeSourceLoanDto.getApplId());
        loanDto.setProdId(financeSourceLoanDto.getProdId());
        loanDto.setOneLoanMoney(financeSourceLoanDto.getLoanAmt());
        loanDto.setAreaDto(areaDto);

        return loanDto;
    }

    /**
     * 资金路由DTO转小贷内部DTO
     * @param sourceFundDto
     * @return
     */
    private FinanceSourceDto getFinanceSourceDto(SourceFundDto sourceFundDto){
        if(sourceFundDto == null){
            return null;
        }

        LOGGER.debug("getFinanceSourceDto,id:{},type:{}", sourceFundDto.getSourceFundId(), sourceFundDto.getType());

        FinanceSourceDto rs = new FinanceSourceDto();

        rs.setId(Integer.parseInt(Long.toString(sourceFundDto.getSourceFundId())));

        switch(sourceFundDto.getType()){
            case "OWN":
                rs.setType(EFinanceSourceType.OWN);
                break;
            case "AITOUZI":
                rs.setType(EFinanceSourceType.AITOUZI);
                break;
            default:LOGGER.error("小贷系统内未找到对应的资金端类型 sourceFundDto.type:{}", sourceFundDto.getType());
                break;
        }

        return rs;
    }
}
