package com.xinyunlian.jinfu.insurance.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.insurance.dao.PerInsuranceInfoDao;
import com.xinyunlian.jinfu.insurance.dto.InsureCallBackRespDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsInfoSearchDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoPageDto;
import com.xinyunlian.jinfu.insurance.entity.PerInsuranceInfoPo;
import com.xinyunlian.jinfu.insurance.enums.EInsureResult;
import com.xinyunlian.jinfu.insurance.enums.EInvokerType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;
import com.xinyunlian.jinfu.pingan.dto.PolicyDto;
import com.xinyunlian.jinfu.pingan.service.PinganService;
import com.xinyunlian.jinfu.zhongan.dto.*;
import com.xinyunlian.jinfu.zhongan.service.ZhonganOpenPlatformService;
import com.zhongan.scorpoin.common.ZhongAnNotifyClient;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * Created by DongFC on 2016-09-21.
 */
@Service
public class InsuranceOrderServiceImpl implements InsuranceOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceOrderServiceImpl.class);

    @Value("${pingan.ins.web.url.prefix}")
    private String pignanWebUrlPrefix;

    @Value("${pingan.ins.app.url.prefix}")
    private String pignanAppUrlPrefix;

    @Autowired
    private PerInsuranceInfoDao perInsuranceInfoDao;

    @Autowired
    private PinganService pinganService;

    @Value(value = "${insure.pdf.path}")
    private String insurePdfPath;

    @Value("${zhongan.h5.url}")
    private String zhognanH5Url;

    @Value("${yljf.zhongan.private.key}")
    private String PRK;

    @Value("${zhongan.env}")
    private String zhonganEnv;

    @Autowired
    private ZhonganOpenPlatformService zhonganOpenPlatformService;

    @Value("${zhongan.rc4.key}")
    private String zhonganRC4Key;

    @Override
    public String getPinganUrl(PolicyDto policyDto, EInvokerType invokerType) throws BizServiceException {
        String encryptString = pinganService.aesEncrypt(policyDto);
        String prefix = pignanWebUrlPrefix;
        if (EInvokerType.APP == invokerType){
            prefix = pignanAppUrlPrefix;
        }

        return prefix + encryptString;
    }

    @Override
    public String addInsOrderInfo(PerInsuranceInfoDto perInsuranceInfoDto) throws BizServiceException {

        PerInsuranceInfoPo po = ConverterService.convert(perInsuranceInfoDto, PerInsuranceInfoPo.class);
        perInsuranceInfoDao.save(po);

        return po.getPerInsuranceOrderNo();
    }

    @Override
    public List<PerInsuranceInfoDto> getInsOrder(PerInsInfoSearchDto perInsInfoSearchDto) throws BizServiceException {

        Specification<PerInsuranceInfoPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != perInsInfoSearchDto) {
                if (perInsInfoSearchDto.getPerInsOrderStatus() != null) {
                    expressions.add(cb.equal(root.get("orderStatus"), perInsInfoSearchDto.getPerInsOrderStatus()));
                }
                if (perInsInfoSearchDto.getStoreId() != null) {
                    expressions.add(cb.equal(root.get("storeId"), perInsInfoSearchDto.getStoreId()));
                }
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getProductId())){
                    expressions.add(cb.equal(root.get("productId"), perInsInfoSearchDto.getProductId()));
                }
            }
            return predicate;
        };

        List<PerInsuranceInfoPo> list = perInsuranceInfoDao.findAll(spec, new Sort(Sort.Direction.DESC, "orderDate"));
        List<PerInsuranceInfoDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                PerInsuranceInfoDto dto = ConverterService.convert(po, PerInsuranceInfoDto.class);
                result.add(dto);
            });
        }

        return result;
    }

    @Override
    @Transactional
    public InsureCallBackRespDto updateInsOrderByOrderId(PerInsuranceInfoDto dto) throws BizServiceException {
        InsureCallBackRespDto respDto = new InsureCallBackRespDto();

        PerInsuranceInfoPo po = perInsuranceInfoDao.findOne(dto.getPerInsuranceOrderNo());
        if (po == null){
            respDto.setResult(EInsureResult.FAIL.getCode());
            respDto.setMsg("该保单号不存在");
            return respDto;
        }

        po.setTobaccoPermiNo(dto.getTobaccoPermiNo());
        po.setStoreName(dto.getStoreName());
        po.setStoreAddress(dto.getStoreAddress());
        po.setPhoneNo(dto.getPhoneNo());
        po.setPolicyHolder(dto.getPolicyHolder());
        po.setInsuredPerson(dto.getInsuredPerson());
        po.setInsuranceAmt(dto.getInsuranceAmt());
        po.setInsuranceFee(dto.getInsuranceFee());
        po.setOrderStartTime(dto.getOrderStartTime());
        po.setOrderStopTime(dto.getOrderStopTime());
        po.setOrderStatus(EPerInsOrderStatus.SUCCESS);

        respDto.setResult(EInsureResult.SUCCESS.getCode());
        respDto.setMsg(EInsureResult.SUCCESS.getDescription());

        return respDto;
    }

    @Override
    public PerInsuranceInfoPageDto getInsOrderPage(PerInsInfoSearchDto perInsInfoSearchDto) throws BizServiceException {

        Specification<PerInsuranceInfoPo> spec = (root,  query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != perInsInfoSearchDto) {
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getPerInsuranceOrderNo())) {
                    expressions.add(cb.like(root.get("perInsuranceOrderNo"), BizUtil.filterString(perInsInfoSearchDto.getPerInsuranceOrderNo())));
                }
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getStoreName())) {
                    expressions.add(cb.like(root.get("storeName"), BizUtil.filterString(perInsInfoSearchDto.getStoreName())));
                }
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getStoreAreaTreePath())) {
                    expressions.add(cb.like(root.get("storeAreaTreePath"), BizUtil.filterStringRight(perInsInfoSearchDto.getStoreAreaTreePath())));
                }
                if (perInsInfoSearchDto.getOrderDateFrom() != null){
                    Date orderDateFrom = DateHelper.getStartDate(perInsInfoSearchDto.getOrderDateFrom());
                    expressions.add(cb.greaterThanOrEqualTo(root.get("orderDate"), orderDateFrom));
                }
                if (perInsInfoSearchDto.getOrderDateTo() != null){
                    Date orderDateTo = DateHelper.getEndDate(perInsInfoSearchDto.getOrderDateTo());
                    expressions.add(cb.lessThanOrEqualTo(root.get("orderDate"), orderDateTo));
                }
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getOperatorName())){
                    expressions.add(cb.like(root.get("operatorName"), BizUtil.filterString(perInsInfoSearchDto.getOperatorName())));
                }
                if (!CollectionUtils.isEmpty(perInsInfoSearchDto.getPerInsuranceOrderNoList())){
                    expressions.add(cb.in(root.get("perInsuranceOrderNo")).value(perInsInfoSearchDto.getPerInsuranceOrderNoList()));
                }
                if (!CollectionUtils.isEmpty(perInsInfoSearchDto.getTobaccoPermiNoList())){
                    expressions.add(cb.in(root.get("tobaccoPermiNo")).value(perInsInfoSearchDto.getTobaccoPermiNoList()));
                }
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getTobaccoPermiNo())){
                    expressions.add(cb.like(root.get("tobaccoPermiNo"), BizUtil.filterString(perInsInfoSearchDto.getTobaccoPermiNo())));
                }
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getPhoneNo())){
                    expressions.add(cb.like(root.get("phoneNo"), BizUtil.filterString(perInsInfoSearchDto.getPhoneNo())));
                }
                if (perInsInfoSearchDto.getPerInsDealSource() != null){
                    expressions.add(cb.equal(root.get("dealSource"), perInsInfoSearchDto.getPerInsDealSource()));
                }
                if (perInsInfoSearchDto.getPerInsOrderStatus() != null){
                    expressions.add(cb.equal(root.get("orderStatus"), perInsInfoSearchDto.getPerInsOrderStatus()));
                }
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getProductId())){
                    expressions.add(cb.equal(root.get("productId"), perInsInfoSearchDto.getProductId()));
                }
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getInsuredPerson())){
                    expressions.add(cb.like(root.get("insuredPerson"), perInsInfoSearchDto.getInsuredPerson()));
                }
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getPolicyHolder())){
                    expressions.add(cb.like(root.get("policyHolder"), perInsInfoSearchDto.getPolicyHolder()));
                }
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getDealerPerson())){
                    expressions.add(cb.like(root.get("operatorName"), BizUtil.filterString(perInsInfoSearchDto.getDealerPerson())));
                    expressions.add(cb.equal(root.get("dealType"), EPerInsDealType.MANAGERSERVICE));
                }
                if (!StringUtils.isEmpty(perInsInfoSearchDto.getRemark())){
                    expressions.add(cb.like(root.get("remark"), BizUtil.filterString(perInsInfoSearchDto.getRemark())));
                }
                if (!CollectionUtils.isEmpty(perInsInfoSearchDto.getStoreIdList())){
                    expressions.add(cb.in(root.get("storeId")).value(perInsInfoSearchDto.getStoreIdList()));
                }
                if (perInsInfoSearchDto.getDealType() != null){
                    expressions.add(cb.equal(root.get("dealType"), perInsInfoSearchDto.getDealType()));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(perInsInfoSearchDto.getCurrentPage() - 1, perInsInfoSearchDto.getPageSize(), Sort.Direction.DESC, "orderDate");
        Page<PerInsuranceInfoPo> page = perInsuranceInfoDao.findAll(spec, pageable);

        List<PerInsuranceInfoDto> data = new ArrayList<>();
        if (!CollectionUtils.isEmpty(page.getContent())){
            page.getContent().forEach(po -> {
                PerInsuranceInfoDto perInsuranceInfoDto = ConverterService.convert(po, PerInsuranceInfoDto.class);
                if (perInsuranceInfoDto.getDealType() != null){
                    perInsuranceInfoDto.setDealTypeName(perInsuranceInfoDto.getDealType().getText());
                }
                if (perInsuranceInfoDto.getOrderStatus() != null){
                    perInsuranceInfoDto.setOrderStatusName(perInsuranceInfoDto.getOrderStatus().getText());
                }
                data.add(perInsuranceInfoDto);
            });
        }

        PerInsuranceInfoPageDto retDto = new PerInsuranceInfoPageDto();
        retDto.setList(data);
        retDto.setTotalPages(page.getTotalPages());
        retDto.setTotalRecord(page.getTotalElements());
        retDto.setPageSize(perInsInfoSearchDto.getPageSize());
        retDto.setCurrentPage(perInsInfoSearchDto.getCurrentPage());

        return retDto;
    }

    @Override
    public PerInsuranceInfoDto getInsOrderByOrderId(String orderId) throws BizServiceException {

        PerInsuranceInfoPo po = perInsuranceInfoDao.findOne(orderId);
        PerInsuranceInfoDto dto = ConverterService.convert(po, PerInsuranceInfoDto.class);
        if (dto != null){
            String filePath = StaticResourceSecurity.getSecurityURI("/INSURANCE/zhongyan_" + orderId + ".pdf");
            if (AppConfigUtil.isProdEnv()){
                filePath = StaticResourceSecurity.getSecurityURI("/zhongyan_" + orderId + ".pdf");
            }
            dto.setOrderUrl(insurePdfPath + filePath);
        }

        return dto;
    }

    @Override
    @Transactional
    public void updateExpiryInsOrder() throws BizServiceException {
        perInsuranceInfoDao.updateExpiryInsOrder();
    }

    @Override
    public String getZhonganUrl(ZhongAnRequestDto req, EInvokerType invokerType) {
        try {
            String encryptStr = zhonganOpenPlatformService.rc4Encrypt(req);
            if (StringUtils.isEmpty(encryptStr)){
                return null;
            }
            String url = zhognanH5Url + "&bizContent=" + encryptStr;
            return url;
        } catch (Exception e) {
            LOGGER.error("获取跳转众安链接失败", e);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean updateVehInsOrderByOrderId(PerInsuranceInfoDto dto) throws BizServiceException {

        PerInsuranceInfoPo po = perInsuranceInfoDao.findOne(dto.getPerInsuranceOrderNo());
        if (po == null){
            return false;
        }

        po.setPhoneNo(dto.getPhoneNo());
        po.setPolicyHolder(dto.getPolicyHolder());
        po.setInsuredPerson(dto.getInsuredPerson());
        po.setInsuranceAmt(dto.getInsuranceAmt());
        po.setInsuranceFee(dto.getInsuranceFee());
        po.setOrderStartTime(dto.getOrderStartTime());
        po.setOrderStopTime(dto.getOrderStopTime());
        po.setOrderStatus(EPerInsOrderStatus.SUCCESS);
        po.setRemark(dto.getRemark());

        return true;
    }

    @Override
    public VehInsNotifyDto getZhonganData(Map<String, String[]> map) throws BizServiceException {
        try {
            ZhongAnNotifyClient notify = new ZhongAnNotifyClient(zhonganEnv, PRK);
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()){
                String key = it.next();
                String[] array = map.get(key);
                for (int i = 0; i < array.length; i++){
                    String unescapeHtml = StringEscapeUtils.unescapeHtml(array[i]);
                    array[i] = unescapeHtml;
                }
                map.put(key, array);
            }
            String json = notify.parseNotifyRequest(map);

            VehicleInsNotifyStringDto vehicleInsNotifyStringDto = JsonUtil.toObject(VehicleInsNotifyStringDto.class, json);
            VehInsNotifyDto retDto = null;
            if (vehicleInsNotifyStringDto != null){
                retDto = ConverterService.convert(vehicleInsNotifyStringDto, VehInsNotifyDto.class);
                String bizCnt = vehicleInsNotifyStringDto.getBizContent();
                if (!StringUtils.isEmpty(bizCnt)){
                    String vehDetail = RC4Util.decryRC4(bizCnt, zhonganRC4Key);
                    LOGGER.debug("==解码后的车险详情==");
                    LOGGER.debug(vehDetail);
                    if (!StringUtils.isEmpty(vehDetail)){
                        VehicleInsDetailNotifyStringDto vehicleInsDetailNotifyStringDto = JsonUtil.toObject(VehicleInsDetailNotifyStringDto.class, vehDetail);
                        if (vehicleInsDetailNotifyStringDto != null){
                            VehInsDetailNotifyDto vehInsDetailNotifyDto = ConverterService.convert(vehicleInsDetailNotifyStringDto, VehInsDetailNotifyDto.class);
                            retDto.setVehInsDetailNotifyDto(vehInsDetailNotifyDto);
                        }
                    }
                }
            }
            return retDto;
        } catch (Exception e1) {
            LOGGER.error("解析失败", e1);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }
    }

    @Override
    @Transactional
    public PerInsuranceInfoDto updateInsOrder(PerInsuranceInfoDto dto) throws BizServiceException {
        PerInsuranceInfoPo po = perInsuranceInfoDao.findOne(dto.getPerInsuranceOrderNo());
        if (po == null){
            throw new BizServiceException(EErrorCode.INS_ORDER_NOT_EXIST);
        }
        ConverterService.convert(dto, po);
        PerInsuranceInfoPo dbOne = perInsuranceInfoDao.save(po);
        return ConverterService.convert(dbOne, PerInsuranceInfoDto.class);
    }

    @Override
    @Transactional
    public PerInsuranceInfoDto addAndReturnInsOrderInfo(PerInsuranceInfoDto perInsuranceInfoDto) throws BizServiceException {
        PerInsuranceInfoPo po = ConverterService.convert(perInsuranceInfoDto, PerInsuranceInfoPo.class);
        PerInsuranceInfoPo dbOrder = perInsuranceInfoDao.save(po);
        return ConverterService.convert(dbOrder, PerInsuranceInfoDto.class);
    }

}
