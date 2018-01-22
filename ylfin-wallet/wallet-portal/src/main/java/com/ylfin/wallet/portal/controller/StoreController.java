package com.ylfin.wallet.portal.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.enums.ERelationship;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.ylfin.core.auth.AuthenticationAdapter;
import com.ylfin.core.beanmapper.BeanMapperOperations;
import com.ylfin.core.exception.ServiceException;
import com.ylfin.wallet.portal.CurrentUser;
import com.ylfin.wallet.portal.controller.req.StoreExtInfoReq;
import com.ylfin.wallet.portal.controller.req.StoreInfoReq;
import com.ylfin.wallet.portal.controller.vo.StoreInfo;
import com.ylfin.wallet.portal.controller.vo.StoreSimpleInfo;
import com.ylfin.wallet.portal.service.ImageInfo;
import com.ylfin.wallet.portal.service.ImageService;
import com.ylfin.wallet.portal.util.EnumConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@Api("店铺接口")
@RestController
@RequestMapping("/api/store")
public class StoreController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private AuthenticationAdapter<CurrentUser, String> authenticationAdapter;
    @Autowired
    private BeanMapperOperations beanMapperOperations;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ApiBaiduService apiBaiduService;

    @ApiOperation("获取当前用户店铺列表")
    @GetMapping("/getStores")
    public Page<StoreSimpleInfo> list() {
        String currentUserId = authenticationAdapter.getCurrentUserId();
        List<StoreInfDto> storeInfDtos = storeService.findByUserId(currentUserId);
        if (storeInfDtos == null || storeInfDtos.size() <= 0) {
            return null;
        }
        List<StoreSimpleInfo> list = new ArrayList<>();
        StringBuffer s = new StringBuffer();
        for (StoreInfDto storeInfDto : storeInfDtos) {
            StoreSimpleInfo storeSimpleInfo = new StoreSimpleInfo();
            storeSimpleInfo.setStoreId(storeInfDto.getStoreId());
            //省市地区不会为null可以为""
            String fullAddress = s.append(storeInfDto.getProvince()).append(storeInfDto.getCity())
                    .append(storeInfDto.getStreet()).append(storeInfDto.getAddress()).toString();
            storeSimpleInfo.setFullAddress(fullAddress);
            storeSimpleInfo.setStoreName(storeInfDto.getStoreName());
            storeSimpleInfo.setCreateTs(storeInfDto.getCreateTs());
            list.add(storeSimpleInfo);
            s.delete(0, s.length());
        }
        return new PageImpl<>(list);
    }

    @ApiOperation("添加店铺信息")
    @PostMapping
    public StoreInfo addStore(@RequestBody @Valid StoreInfoReq storeInfoReq) {
        StoreInfDto storeInfDto = beanMapperOperations.map(storeInfoReq, StoreInfDto.class);
        storeInfDto.setUserId(authenticationAdapter.getCurrentUserId());

        if (StringUtils.isNotBlank(storeInfoReq.getTobaccoCertificateNo())){
            //烟草证号判断
            if (!storeInfoReq.getTobaccoCertificateNo().matches("[0-9]{12}")) {
                throw new ServiceException("请输入正确的烟草证号");
            }
        }
        storeInfDto.setSource(ESource.REGISTER);
        StoreInfDto storeInf = storeService.save(storeInfDto);
        apiBaiduService.updatePoint(storeInf);
        if (StringUtils.isNotBlank(storeInfoReq.getTobaccoCertPic())) {//烟草许可证号图片
            imageService.saveTobaccoLicenseImg(storeInfoReq.getTobaccoCertPic(), storeInf.getStoreId().toString());
        }
        if (StringUtils.isNotBlank(storeInfoReq.getBizLicencePic())) {//许可证图片
            imageService.saveStoreLicencPic(storeInfoReq.getBizLicencePic(),storeInf.getStoreId().toString());
        }
        StoreInfo storeInfo = beanMapperOperations.map(storeInf, StoreInfo.class);
        return storeInfo;
    }

    @ApiOperation("修改店铺信息")
    @PostMapping("/{storeId}")
    public void updateStore(@PathVariable("storeId") Long storeId, @Valid @RequestBody StoreInfoReq storeInfoReq) {
        if (storeId == null) {
            throw new ServiceException("店鋪ID不能为空!");
        }
        StoreInfDto infDto = storeService.findByStoreId(storeId);
        String currentUserId = authenticationAdapter.getCurrentUserId();
        if (StringUtils.isBlank(currentUserId)){
            throw new ServiceException("用户未登录");
        }
        if (infDto == null || !currentUserId.equals(infDto.getUserId())) {
            throw new ServiceException("店铺不存在");
        }
        StoreInfDto storeInfo = beanMapperOperations.map(storeInfoReq, StoreInfDto.class);
        storeInfo.setStoreId(storeId);
        storeService.updateStore(storeInfo);
        apiBaiduService.updatePoint(storeInfo);
    }

    @ApiOperation("修改店铺补充信息")
    @PostMapping("/{storeId}/ext")
    public void updateStoreExtraInfo(@PathVariable("storeId") Long storeId, @RequestBody StoreExtInfoReq storeExtInfoReq) {
        if (storeId ==null){
            throw new ServiceException("店鋪ID不能为空");
        }
        String currentUserId = authenticationAdapter.getCurrentUserId();
        if (StringUtils.isBlank(currentUserId)){
            throw new ServiceException("用户未登录");
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(storeId);
        if (storeInfDto == null || !currentUserId.equals(storeInfDto.getUserId())){
            throw new ServiceException("该店铺不存在");
        }
        if (StringUtils.isNotBlank(storeExtInfoReq.getRelationship())){
            ERelationship eRelationship = EnumConverter.getERelationship(storeExtInfoReq.getRelationship());
            storeInfDto.setRelationship(eRelationship);
        }
        storeInfDto.setBusinessArea(storeExtInfoReq.getBusinessArea()==null? BigDecimal.ZERO:storeExtInfoReq.getBusinessArea());
        storeInfDto.setEmployeeNum(storeExtInfoReq.getEmployeeNum()==null?0:storeExtInfoReq.getEmployeeNum());
        if (StringUtils.isNotBlank(storeExtInfoReq.getHouseholdRegisterMainPic())){//户口本主页
            imageService.saveHouseholdRegisterMainImg(storeExtInfoReq.getHouseholdRegisterMainPic(),storeInfDto.getUserId());
        }
        if (StringUtils.isNotBlank(storeExtInfoReq.getHouseholdRegisterOwnerPic())){//户口本本人
            ImageInfo imageInfo = imageService.saveHouseholdRegisterOwnerPic(storeExtInfoReq.getHouseholdRegisterOwnerPic(), storeInfDto.getUserId());
            storeInfDto.setResidenceBookletPic(imageInfo.getImgPath());
            storeInfDto.setResidenceBookletPicId(imageInfo.getPicId());
        }
        storeService.saveAndUpdateStore(storeInfDto);
    }

}
