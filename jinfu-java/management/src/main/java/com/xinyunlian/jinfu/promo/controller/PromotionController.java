package com.xinyunlian.jinfu.promo.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.FileUtils;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.prod.dto.AreaDetailLvlDto;
import com.xinyunlian.jinfu.prod.dto.ProdTypeInfDto;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.prod.service.ProdTypeInfService;
import com.xinyunlian.jinfu.promo.dto.*;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;
import com.xinyunlian.jinfu.promo.enums.ERecordType;
import com.xinyunlian.jinfu.promo.service.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by bright on 2016/11/21.
 */
@RestController
@RequestMapping("promotion")
@RequiresPermissions({"PROMO_MGT"})
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private ProdService prodService;

    @Autowired
    private ProdTypeInfService prodTypeService;

    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResultDto<PromoInfSearchDto> findPromoInf(PromoInfSearchDto searchDto){
        searchDto = promotionService.search(searchDto);
        List<PromoInfDto> promoInfDtos = (List<PromoInfDto>)searchDto.getList();
        promoInfDtos.forEach(promoInfDto -> {
            String prodId = promoInfDto.getProdId();
            Long prodTypeId = promoInfDto.getProdTypeId();
            ProductDto prod = prodService.getProdById(prodId);
            ProdTypeInfDto prodTypeInf = prodTypeService.getProdTypeById(prodTypeId);
            promoInfDto.setProdName(prod.getProdName());
            promoInfDto.setProdTypeName(prodTypeInf.getProdTypeName());
        });
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), searchDto);
    }

    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public ResultDto<String> upload(@RequestParam MultipartFile file){
        File tempFile = null;
        try {
            tempFile = FileUtils.createTempFile(".xls");
            file.transferTo(tempFile);
        } catch (IOException e) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("common.operate.fail"),"上传失败！");
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), tempFile.getAbsolutePath());
    }

    @RequestMapping(value = "whiteblacktemplate", method = RequestMethod.GET)
    public ModelAndView getTemplate(){
        Map<String, Object> model = new HashedMap();
        model.put("data", Collections.EMPTY_LIST);
        model.put("fileName","促销用户模板.xls");
        model.put("tempPath","/templates/促销用户模板.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

    @RequestMapping(value = "whiteblackusers", method = RequestMethod.GET)
    public ModelAndView getUsers(@RequestParam Long promotionId, @RequestParam ERecordType recordType){
        List<WhiteBlackUserDto> whiteBlackUserDtos = promotionService.getWhiteBlackRecords(promotionId, recordType);
        List<WhiteBlackRecordDto> records = new ArrayList<>();
        whiteBlackUserDtos.forEach(whiteBlackUserDto -> {
            WhiteBlackRecordDto record = ConverterService.convert(whiteBlackUserDto, WhiteBlackRecordDto.class);
            records.add(record);
        });
        Map<String, Object> model = new HashedMap();
        model.put("data", records);
        model.put("fileName","促销用户.xls");
        model.put("tempPath","/templates/促销用户模板.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResultDto save(@RequestBody PromotionSaveDto promotion){
        PromotionDto promotionDto = ConverterService.convert(promotion, PromotionDto.class);
        promotionDto.setRuleFirstGiftDto(promotion.getRuleFirstGiftDto());

        List<PromotionAreaDto> areas = promotion.getPromoAreaDto();
        if(null == areas || 0 == areas.size()){
            String productId = promotionDto.getPromoInfDto().getProdId();
            List<AreaDetailLvlDto> prodAreas = prodService.getAreaByProd(productId);
            List<PromotionAreaDto> filledAreas = new ArrayList<>();
            prodAreas.forEach(areaDetailLvlDto -> {
                PromotionAreaDto areaDto = ConverterService.convert(areaDetailLvlDto, PromotionAreaDto.class);
                filledAreas.add(areaDto);
            });
            areas = filledAreas;
        }

        List<PromoAreaDto> promoAreaDtos = new ArrayList<>();
        areas.forEach(area -> {
            PromoAreaDto areaDto = new PromoAreaDto();
            StringBuilder stringBuilder = new StringBuilder();
            if (area.getProvinceId() != null){
                stringBuilder.append(",").append(area.getProvinceId());
                if (area.getCityId() != null){
                    stringBuilder.append(",").append(area.getCityId());
                    if (area.getCountyId() != null){
                        stringBuilder.append(",").append(area.getCountyId());
                    }
                }
            }

            stringBuilder.append(",");

            areaDto.setAreaTreePath(stringBuilder.toString());
            String[] areaIds = stringBuilder.toString().split(",");
            areaDto.setAreaId(Long.parseLong(areaIds[areaIds.length - 1]));
            areaDto.setId(area.getId());
            promoAreaDtos.add(areaDto);
        });

        promotionDto.setPromoAreaDto(promoAreaDtos);
        promotionDto.setCompanyCostDto(promotion.getCompanyCostDto());

        String whiteListFilePath = promotion.getWhiteListFilePath();
        String blackListFilePath = promotion.getBlackListFilePath();
        if(!StringUtils.isEmpty(whiteListFilePath)){
            List<WhiteBlackUserDto> whiteList = parseExcel(whiteListFilePath, ERecordType.WHITE_RECORD);
            promotionDto.getWhiteList().addAll(whiteList);
        }

        if(!StringUtils.isEmpty(blackListFilePath)) {
            List<WhiteBlackUserDto> blackList = parseExcel(blackListFilePath, ERecordType.BLACK_RECORD);
            promotionDto.getBlackList().addAll(blackList);
        }

        promotionService.savePromotion(promotionDto);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    };

    private List<WhiteBlackUserDto> parseExcel(String fileName, ERecordType recordType)  {
        List<WhiteBlackUserDto> records = new ArrayList<>();
        HSSFWorkbook workbook = null;
        try{
            workbook = new HSSFWorkbook(new FileInputStream(fileName));
            HSSFSheet hssfSheet = workbook.getSheetAt(0);
            if (hssfSheet != null) {
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow == null) {
                        continue;
                    }
                    HSSFCell nameCell = hssfRow.getCell(0);
                    String name = "";
                    String phone = "";
                    String idCard = "";
                    String tobaccoLicense = "";
                    if(nameCell != null) {
                        nameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        name = nameCell.getStringCellValue();
                    }
                    HSSFCell phoneCell = hssfRow.getCell(1);
                    if(phoneCell != null) {
                        phoneCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        phone = phoneCell.getStringCellValue();
                    }
                    HSSFCell idCardCell = hssfRow.getCell(2);
                    if(idCardCell != null){
                        idCardCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        idCard = idCardCell.getStringCellValue();
                    }
                    HSSFCell tobaccoLicenseCell = hssfRow.getCell(3);
                    if(tobaccoLicenseCell != null) {
                        tobaccoLicenseCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        tobaccoLicense = tobaccoLicenseCell.getStringCellValue();
                    }
                    WhiteBlackUserDto record = new WhiteBlackUserDto();
                    record.setUserName(name);
                    record.setMobile(phone);
                    record.setIdCardNo(idCard);
                    record.setTobaccoCertificateNo(tobaccoLicense);
                    record.setRecordType(recordType);
                    records.add(record);
                }
            }
        }catch (IOException e){
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "解析Excel失败", e);
        } finally {
            if(workbook != null){
                try{
                    workbook.close();
                }catch (Exception e){
                    // Ignore
                }
            }
        }
        return records;
    }

    @ResponseBody
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResultDto<PromotionSaveDto> getPromotion(@RequestParam Long promotionId){
        PromotionDto promotionDto = promotionService.getByPromotionId(promotionId);
        PromotionSaveDto promotion = ConverterService.convert(promotionDto, PromotionSaveDto.class);
        List<PromoAreaDto> areaDtos = promotionDto.getPromoAreaDto();
        List<PromotionAreaDto> areas = new ArrayList<>();
        areaDtos.forEach(areaDto -> {
            PromotionAreaDto area = new PromotionAreaDto();
            area.setId(areaDto.getId());
            String[] pathArray = areaDto.getAreaTreePath().split(",");
            if (pathArray != null && pathArray.length > 0){
                try {
                    area.setProvinceId(Long.parseLong(pathArray[1]));
                    area.setCityId(Long.parseLong(pathArray[2]));
                    area.setCountyId(Long.parseLong(pathArray[3]));
                } catch (Exception e) {
                    // Ignore
                }
            }
            areas.add(area);
        });
        promotion.setPromoAreaDto(areas);
        promotion.setCompanyCostDto(promotionDto.getCompanyCostDto());
        promotion.setRuleFirstGiftDto(promotionDto.getRuleFirstGiftDto());

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), promotion);
    }


    @ResponseBody
    @RequestMapping(value = "invalid", method = RequestMethod.POST)
    public ResultDto deactivePromotion(@RequestParam Long promotionId){
        promotionService.invalidPromotion(promotionId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @ResponseBody
    @RequestMapping(value = "active", method = RequestMethod.POST)
    public ResultDto activePromotion(@RequestParam Long promotionId){
        promotionService.activePromotion(promotionId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResultDto deletePromotion(@RequestParam Long promotionId){
        promotionService.deletePromotion(promotionId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }
}
