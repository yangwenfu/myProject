package com.xinyunlian.jinfu.qrcode.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.order.dao.ThirdOrderProdDao;
import com.xinyunlian.jinfu.order.entity.ThirdOrderProdPo;
import com.xinyunlian.jinfu.qrcode.dao.ProdCodeDao;
import com.xinyunlian.jinfu.qrcode.dao.ProdCodeViewDao;
import com.xinyunlian.jinfu.qrcode.dto.*;
import com.xinyunlian.jinfu.qrcode.entity.ProdCodePo;
import com.xinyunlian.jinfu.qrcode.entity.ProdCodeViewPo;
import com.xinyunlian.jinfu.qrcode.enums.EProdCodeStatus;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by menglei on 2017年03月08日.
 */
@Service
public class ProdCodeServiceImpl implements ProdCodeService {

    @Autowired
    private ProdCodeDao prodCodeDao;
    @Autowired
    private ProdCodeViewDao prodCodeViewDao;
    @Autowired
    private ThirdOrderProdDao thirdOrderProdDao;

    @Override
    @Transactional(readOnly = true)
    public ProdCodeSearchDto getProdCodePage(ProdCodeSearchDto prodCodeSearchDto) {

        Specification<ProdCodePo> spec = (Root<ProdCodePo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != prodCodeSearchDto) {
                if (StringUtils.isNotEmpty(prodCodeSearchDto.getQrCodeNo())) {
                    expressions.add(cb.like(root.get("qrCodeNo"), "%" + prodCodeSearchDto.getQrCodeNo() + "%"));
                }
                if (StringUtils.isNotEmpty(prodCodeSearchDto.getStartBindTs())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("bindTime"), DateHelper.getStartDate(prodCodeSearchDto.getStartBindTs())));
                }
                if (StringUtils.isNotEmpty(prodCodeSearchDto.getEndBindTs())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("bindTime"), DateHelper.getEndDate(prodCodeSearchDto.getEndBindTs())));
                }
                if (StringUtils.isNotEmpty(prodCodeSearchDto.getStartCreateTs())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(prodCodeSearchDto.getStartCreateTs())));
                }
                if (StringUtils.isNotEmpty(prodCodeSearchDto.getEndCreateTs())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(prodCodeSearchDto.getEndCreateTs())));
                }
                if (prodCodeSearchDto.getStatus() != null) {
                    expressions.add(root.get("status").in(prodCodeSearchDto.getStatus()));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(prodCodeSearchDto.getCurrentPage() - 1, prodCodeSearchDto.getPageSize(), Sort.Direction.DESC, "prodCodeId");
        Page<ProdCodePo> page = prodCodeDao.findAll(spec, pageable);

        List<ProdCodeDto> data = new ArrayList<>();
        for (ProdCodePo po : page.getContent()) {
            ProdCodeDto prodCodeDto = ConverterService.convert(po, ProdCodeDto.class);
            data.add(prodCodeDto);
        }

        prodCodeSearchDto.setList(data);
        prodCodeSearchDto.setTotalPages(page.getTotalPages());
        prodCodeSearchDto.setTotalRecord(page.getTotalElements());

        return prodCodeSearchDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ProdCodeViewSearchDto getProdCodeViewPage(ProdCodeViewSearchDto searchDto) {
        Specification<ProdCodeViewPo> spec = (Root<ProdCodeViewPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {
                if (StringUtils.isNotEmpty(searchDto.getQrCodeNo())) {
                    expressions.add(cb.like(root.get("qrCodeNo"), BizUtil.filterString(searchDto.getQrCodeNo())));
                }
                if (StringUtils.isNotEmpty(searchDto.getProdName())) {
                    expressions.add(cb.like(root.get("prodName"), BizUtil.filterString(searchDto.getProdName())));
                }
                if (StringUtils.isNotEmpty(searchDto.getStoreName())) {
                    expressions.add(cb.like(root.get("storeName"), BizUtil.filterString(searchDto.getStoreName())));
                }
                if (null != searchDto.getProvinceId()) {
                    expressions.add(cb.equal(root.get("provinceId"), searchDto.getProvinceId()));
                }
                if (null != searchDto.getCityId()) {
                    expressions.add(cb.equal(root.get("cityId"), searchDto.getCityId()));
                }
                if (null != searchDto.getAreaId()) {
                    expressions.add(cb.equal(root.get("areaId"), searchDto.getAreaId()));
                }
                if (searchDto.getFrozen() != null) {
                    expressions.add(root.get("frozen").in(searchDto.getFrozen()));
                }
                if (StringUtils.isNotEmpty(searchDto.getStartBindTs())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("bindTime"), DateHelper.getStartDate(searchDto.getStartBindTs())));
                }
                if (StringUtils.isNotEmpty(searchDto.getEndBindTs())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("bindTime"), DateHelper.getEndDate(searchDto.getEndBindTs())));
                }
                if (StringUtils.isNotEmpty(searchDto.getStartSellTs())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("sellTime"), DateHelper.getStartDate(searchDto.getStartSellTs())));
                }
                if (StringUtils.isNotEmpty(searchDto.getEndSellTs())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("sellTime"), DateHelper.getEndDate(searchDto.getEndSellTs())));
                }
                expressions.add(cb.notEqual(root.get("status"), EProdCodeStatus.UNUSED));
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1,
                searchDto.getPageSize(), Sort.Direction.DESC, "bindTime");
        Page<ProdCodeViewPo> page = prodCodeViewDao.findAll(spec, pageable);

        List<ProdCodeViewDto> data = new ArrayList<>();
        for (ProdCodeViewPo po : page.getContent()) {
            ProdCodeViewDto dto = ConverterService.convert(po, ProdCodeViewDto.class);
            data.add(dto);
        }
        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdCodeDto> getProdCodeExportList(ProdCodeSearchDto prodCodeSearchDto) {

        Specification<ProdCodePo> spec = (Root<ProdCodePo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != prodCodeSearchDto) {
                if (StringUtils.isNotEmpty(prodCodeSearchDto.getQrCodeNo())) {
                    expressions.add(cb.like(root.get("qrCodeNo"), "%" + prodCodeSearchDto.getQrCodeNo() + "%"));
                }
                if (StringUtils.isNotEmpty(prodCodeSearchDto.getStartBindTs())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("bindTime"), DateHelper.getStartDate(prodCodeSearchDto.getStartBindTs())));
                }
                if (StringUtils.isNotEmpty(prodCodeSearchDto.getEndBindTs())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("bindTime"), DateHelper.getEndDate(prodCodeSearchDto.getEndBindTs())));
                }
                if (StringUtils.isNotEmpty(prodCodeSearchDto.getStartCreateTs())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(prodCodeSearchDto.getStartCreateTs())));
                }
                if (StringUtils.isNotEmpty(prodCodeSearchDto.getEndCreateTs())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(prodCodeSearchDto.getEndCreateTs())));
                }
                if (prodCodeSearchDto.getStatus() != null) {
                    expressions.add(root.get("status").in(prodCodeSearchDto.getStatus()));
                }
            }
            return predicate;
        };

        List<ProdCodePo> list = prodCodeDao.findAll(spec, new Sort(Sort.Direction.DESC, "prodCodeId"));

        List<ProdCodeDto> data = new ArrayList<>();
        for (ProdCodePo po : list) {
            ProdCodeDto prodCodeDto = ConverterService.convert(po, ProdCodeDto.class);
            data.add(prodCodeDto);
        }

        return data;
    }

    @Transactional
    @Override
    public Boolean addBathProdCode(Integer count) throws BizServiceException {
        List<ProdCodePo> prodCodeList = buildProdCodeList(count);
        if (CollectionUtils.isEmpty(prodCodeList)) {//超过每日最大生成数
            return false;
        }
        prodCodeDao.save(prodCodeList);
        return true;
    }

    private List<ProdCodePo> buildProdCodeList(Integer count) throws BizServiceException {
        List<ProdCodePo> prodCodeList = new ArrayList<>();
        String date = DateHelper.formatDate(new Date(), "yyMMdd");
        ProdCodePo prodCode = prodCodeDao.findLastByDateTime(DateHelper.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
        Integer num = 0;
        if (prodCode != null) {
            if (StringUtils.isEmpty(prodCode.getQrCodeNo()) || prodCode.getQrCodeNo().length() < 7) {
                return null;
            }
            num = Integer.valueOf(prodCode.getQrCodeNo().substring(6, prodCode.getQrCodeNo().length()));
        }
        ProdCodePo prodCodePo;
        String batchNo = String.valueOf(System.currentTimeMillis());
        for (int i = 0; i < count; i++) {
            prodCodePo = new ProdCodePo();
            num = num + 1;
            String qrCodeNo = date + String.format("%06d", num);
            prodCodePo.setQrCodeNo(qrCodeNo);
            String qrCodeUrl = null;
            try {
                qrCodeUrl = AppConfigUtil.getConfig("qrcode.url") + "/product/" + qrCodeNo + "/" + EncryptUtil.encryptMd5("product" + qrCodeNo).substring(0, 3);
            } catch (NoSuchAlgorithmException e) {
                throw new BizServiceException(EErrorCode.USER_PASSWORD_MD5_ERROR, "MD5加密失败", e);
            } catch (UnsupportedEncodingException e) {
                throw new BizServiceException(EErrorCode.USER_PASSWORD_MD5_ERROR, "MD5加密失败", e);
            }
            prodCodePo.setQrCodeUrl(qrCodeUrl);
            prodCodePo.setFrozen(false);
            prodCodePo.setStatus(EProdCodeStatus.UNUSED);
            prodCodePo.setBatchNo(batchNo);
            prodCodeList.add(prodCodePo);
        }
        return prodCodeList;
    }

    @Transactional
    @Override
    public ProdCodeDto getOne(Long prodCodeId) {
        ProdCodePo po = prodCodeDao.findOne(prodCodeId);
        if (po == null) {
            return null;
        }
        ProdCodeDto dto = ConverterService.convert(po, ProdCodeDto.class);
        return dto;
    }

    @Transactional
    @Override
    public ProdCodeDto getByQrCodeUrl(String qrCodeUrl) {
        ProdCodePo po = prodCodeDao.findByQrCodeUrl(qrCodeUrl);
        if (po == null) {
            return null;
        }
        ProdCodeDto dto = ConverterService.convert(po, ProdCodeDto.class);
        return dto;
    }

    @Transactional
    @Override
    public List<ProdCodeDto> getListByOrderId(Long orderId) {
        List<ProdCodePo> poList = prodCodeDao.findByOrderId(orderId);
        List<ProdCodeDto> list = new ArrayList<>();
        for (ProdCodePo po : poList) {
            ProdCodeDto dto = ConverterService.convert(po, ProdCodeDto.class);
            list.add(dto);
        }
        return list;
    }

    @Transactional
    @Override
    public ProdCodeViewDto getViewOne(Long prodCodeId) {
        ProdCodeViewPo po = prodCodeViewDao.findOne(prodCodeId);
        if (po == null) {
            return null;
        }
        ProdCodeViewDto dto = ConverterService.convert(po, ProdCodeViewDto.class);
        return dto;
    }

    @Transactional
    @Override
    public void deleteProdCode(Long prodCodeId) throws BizServiceException {
        ProdCodePo prodCodePo = prodCodeDao.findOne(prodCodeId);
        if (prodCodePo != null && prodCodePo.getStatus().equals(EProdCodeStatus.UNUSED)) {
            prodCodeDao.delete(prodCodeId);
        }
    }

    @Transactional
    @Override
    public void frozenProdCode(Long prodCodeId) throws BizServiceException {
        ProdCodePo prodCodePo = prodCodeDao.findOne(prodCodeId);
        if (prodCodePo != null && !prodCodePo.getStatus().equals(EProdCodeStatus.UNUSED)) {
            if (prodCodePo.getFrozen()) {
                prodCodePo.setFrozen(false);
            } else {
                prodCodePo.setFrozen(true);
            }
            prodCodeDao.save(prodCodePo);
        }
    }

    @Transactional
    @Override
    public void bindProdCode(ProdCodeDto prodCodeDto) throws BizServiceException {
        ThirdOrderProdPo thirdOrderPro = thirdOrderProdDao.findOne(prodCodeDto.getOrderProdId());
        if (thirdOrderPro == null) {
            throw new BizServiceException(EErrorCode.UPLUS_THIRD_ORDER_PROD_NOT_FOUND);
        }
        ProdCodePo prodCodePo = prodCodeDao.findOne(prodCodeDto.getProdCodeId());
        if (prodCodePo == null) {
            throw new BizServiceException(EErrorCode.UPLUS_PROD_CODE_NOT_FOUND);
        }
        prodCodePo.setOrderProdId(prodCodeDto.getOrderProdId());
        prodCodePo.setProdId(prodCodeDto.getProdId());
        prodCodePo.setStoreId(prodCodeDto.getStoreId());
        prodCodePo.setBindTime(new Date());
        prodCodePo.setFrozen(false);
        prodCodePo.setStatus(EProdCodeStatus.SALE);
        prodCodePo.setOrderId(prodCodeDto.getOrderId());
        prodCodeDao.save(prodCodePo);
        thirdOrderPro.setBindCount(thirdOrderPro.getBindCount() - 1);
        thirdOrderProdDao.save(thirdOrderPro);
    }

    @Transactional
    @Override
    public void updateStatusSold(Long prodCodeId, String orderNo) throws BizServiceException {
        ProdCodePo prodCodePo = prodCodeDao.findOne(prodCodeId);
        if (prodCodePo != null && prodCodePo.getStatus().equals(EProdCodeStatus.SALE) && !prodCodePo.getFrozen()) {
            prodCodePo.setStatus(EProdCodeStatus.SOLD);
            prodCodePo.setSellTime(new Date());
            prodCodePo.setProdOrderNo(orderNo);
            prodCodeDao.save(prodCodePo);
        }
    }

    /**
     * 根据订单列表查订单已绑码数
     *
     * @param orderIds
     * @return
     */
    @Override
    public List<ProdCodeCountDto> getCountByOrderId(List<Long> orderIds) {
        List<Object[]> list = prodCodeDao.findCountByOrderId(orderIds);
        List<ProdCodeCountDto> dtoList = new ArrayList<>();
        ProdCodeCountDto prodCodeCountDto;
        for (Object[] object : list) {
            prodCodeCountDto = new ProdCodeCountDto();
            prodCodeCountDto.setOrderId(Long.valueOf(object[0].toString()));
            prodCodeCountDto.setBindCount(Integer.valueOf(object[1].toString()));
            dtoList.add(prodCodeCountDto);
        }
        return dtoList;
    }

}
