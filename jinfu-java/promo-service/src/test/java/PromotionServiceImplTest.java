import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.promo.dto.*;
import com.xinyunlian.jinfu.promo.enums.EPlatform;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;
import com.xinyunlian.jinfu.promo.enums.EPromoInfType;
import com.xinyunlian.jinfu.promo.enums.ERecordType;
import com.xinyunlian.jinfu.promo.service.PromotionService;
import com.xinyunlian.jinfu.rule.dto.RuleFirstDiscountDto;
import com.xinyunlian.jinfu.rule.dto.RuleFirstGiftDto;
import com.xinyunlian.jinfu.rule.dto.RuleFullOffDto;
import com.xinyunlian.jinfu.rule.dto.RuleFullOffGradDto;
import com.xinyunlian.jinfu.rule.enums.EDiscountType;
import com.xinyunlian.jinfu.rule.enums.EOffType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by bright on 2016/11/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class PromotionServiceImplTest {
    @Autowired
    private PromotionService promotionService;

//    @Test
    public void savePromotionForFirstOrderGift() throws Exception {
        PromoInfDto promoInfDto = new PromoInfDto();
        promoInfDto.setProdTypeId(3L);
        promoInfDto.setProdId("L01001");
        promoInfDto.setStatus(EPromoInfStatus.INACTIVE);
        promoInfDto.setAlias("欢欢喜喜过大年");
        promoInfDto.setName("首单有礼");
        promoInfDto.setDescribe("测试用");
        promoInfDto.setEndDate(DateHelper.getDate("2016-12-31 00:00:00", "yyyy-MM-dd hh:mm:ss"));
        promoInfDto.setMinimum(BigDecimal.valueOf(1000));
        promoInfDto.setPerLimit(100);
        List<EPlatform> platforms = new ArrayList<>();
        platforms.add(EPlatform.DEALER);
        platforms.add(EPlatform.LOAN);
        promoInfDto.setPlatform(platforms);
        promoInfDto.setStartDate(DateHelper.getDate("2016-12-01 00:00:00", "yyyy-MM-dd hh:mm:ss"));
        promoInfDto.setTotalLimit(10000);
        promoInfDto.setType(EPromoInfType.DISCOUNT_ON_FIRST_ORDER);
        List<PromoAreaDto> areas = new ArrayList<>();
        PromoAreaDto promoAreaDto1 = new PromoAreaDto();
        promoAreaDto1.setAreaId(44050L);
        promoAreaDto1.setAreaTreePath(",3321,44050,");
        areas.add(promoAreaDto1);
        List<WhiteBlackUserDto> users = new ArrayList<>();
        WhiteBlackUserDto whiteBlackUserDto1 = new WhiteBlackUserDto();
        whiteBlackUserDto1.setMobile("18888888888");
        whiteBlackUserDto1.setRecordType(ERecordType.WHITE_RECORD);
        whiteBlackUserDto1.setTobaccoCertificateNo("78123781273");
        whiteBlackUserDto1.setIdCardNo("330511111111111111");
        whiteBlackUserDto1.setUserName("样本");
        users.add(whiteBlackUserDto1);
        List<CompanyCostDto> costsPlan = new ArrayList<>();
        CompanyCostDto companyCostDto1 = new CompanyCostDto();
        companyCostDto1.setCompanyName("金主1");
        companyCostDto1.setScale(BigDecimal.valueOf(100));
        costsPlan.add(companyCostDto1);
        List<RuleFirstGiftDto> gifts = new ArrayList<>();
        RuleFirstGiftDto ruleFirstGiftDto1 = new RuleFirstGiftDto();
        ruleFirstGiftDto1.setGiftName("红包");
        ruleFirstGiftDto1.setDescribe("100块钱红包");
        ruleFirstGiftDto1.setPerNum(1);
        ruleFirstGiftDto1.setPrice(BigDecimal.valueOf(100));
        ruleFirstGiftDto1.setTotal(100);
        gifts.add(ruleFirstGiftDto1);

        PromotionDto promotion = new PromotionDto();
        promotion.setPromoInfDto(promoInfDto);
        promotion.setPromoAreaDto(areas);
        promotion.setCompanyCostDto(costsPlan);
        promotion.setRuleFirstGiftDto(gifts);

        promotionService.savePromotion(promotion);
    }

//    @Test
    public void savePromotionForFirstOrderDis() throws Exception {
        PromoInfDto promoInfDto = new PromoInfDto();
        promoInfDto.setProdTypeId(3L);
        promoInfDto.setProdId("L01001");
        promoInfDto.setStatus(EPromoInfStatus.INACTIVE);
        promoInfDto.setAlias("开开心心过大年");
        promoInfDto.setName("首单折扣");
        promoInfDto.setDescribe("测试用");
        promoInfDto.setEndDate(DateHelper.getDate("2016-12-31 00:00:00", "yyyy-MM-dd hh:mm:ss"));
        promoInfDto.setMinimum(BigDecimal.valueOf(1000));
        promoInfDto.setPerLimit(100);
        List<EPlatform> platforms = new ArrayList<>();
        platforms.add(EPlatform.DEALER);
        platforms.add(EPlatform.LOAN);
        promoInfDto.setPlatform(platforms);
        promoInfDto.setStartDate(DateHelper.getDate("2016-12-01 00:00:00", "yyyy-MM-dd hh:mm:ss"));
        promoInfDto.setTotalLimit(10000);
        promoInfDto.setType(EPromoInfType.DISCOUNT_ON_FIRST_ORDER);
        List<PromoAreaDto> areas = new ArrayList<>();
        PromoAreaDto promoAreaDto1 = new PromoAreaDto();
        promoAreaDto1.setAreaId(44050L);
        promoAreaDto1.setAreaTreePath(",3321,44050,");
        areas.add(promoAreaDto1);
        List<WhiteBlackUserDto> users = new ArrayList<>();
        WhiteBlackUserDto whiteBlackUserDto1 = new WhiteBlackUserDto();
        whiteBlackUserDto1.setMobile("18888888888");
        whiteBlackUserDto1.setRecordType(ERecordType.WHITE_RECORD);
        whiteBlackUserDto1.setTobaccoCertificateNo("78123781273");
        whiteBlackUserDto1.setIdCardNo("330511111111111111");
        whiteBlackUserDto1.setUserName("样本");
        users.add(whiteBlackUserDto1);
        List<CompanyCostDto> costsPlan = new ArrayList<>();
        CompanyCostDto companyCostDto1 = new CompanyCostDto();
        companyCostDto1.setCompanyName("金主2");
        companyCostDto1.setScale(BigDecimal.valueOf(100));
        costsPlan.add(companyCostDto1);

        RuleFirstDiscountDto ruleFirstDiscountDto = new RuleFirstDiscountDto();
        ruleFirstDiscountDto.setDiscount(BigDecimal.valueOf(10));
        ruleFirstDiscountDto.setDiscountType(EDiscountType.INTR_PER_DIEM);
        ruleFirstDiscountDto.setTerm(30);

        PromotionDto promotion = new PromotionDto();
        promotion.setPromoInfDto(promoInfDto);
        promotion.setPromoAreaDto(areas);
        promotion.setCompanyCostDto(costsPlan);
        promotion.setRuleFirstDiscountDto(ruleFirstDiscountDto);

        promotionService.savePromotion(promotion);
    }

//    @Test
    public void savePromotionForTotalAmt() throws Exception {
        PromoInfDto promoInfDto = new PromoInfDto();
        promoInfDto.setProdTypeId(3L);
        promoInfDto.setProdId("L01001");
        promoInfDto.setStatus(EPromoInfStatus.INACTIVE);
        promoInfDto.setAlias("白白胖胖过大年");
        promoInfDto.setName("满减促销");
        promoInfDto.setDescribe("测试用");
        promoInfDto.setEndDate(DateHelper.getDate("2016-12-31 00:00:00", "yyyy-MM-dd hh:mm:ss"));
        promoInfDto.setMinimum(BigDecimal.valueOf(1000));
        promoInfDto.setPerLimit(100);
        List<EPlatform> platforms = new ArrayList<>();
        platforms.add(EPlatform.DEALER);
        platforms.add(EPlatform.LOAN);
        promoInfDto.setPlatform(platforms);
        promoInfDto.setStartDate(DateHelper.getDate("2016-12-01 00:00:00", "yyyy-MM-dd hh:mm:ss"));
        promoInfDto.setTotalLimit(10000);
        promoInfDto.setType(EPromoInfType.DISCOUNT_ON_FIRST_ORDER);
        List<PromoAreaDto> areas = new ArrayList<>();
        PromoAreaDto promoAreaDto1 = new PromoAreaDto();
        promoAreaDto1.setAreaId(44050L);
        promoAreaDto1.setAreaTreePath(",3321,44050,");
        areas.add(promoAreaDto1);
        List<WhiteBlackUserDto> users = new ArrayList<>();
        WhiteBlackUserDto whiteBlackUserDto1 = new WhiteBlackUserDto();
        whiteBlackUserDto1.setMobile("18888888888");
        whiteBlackUserDto1.setRecordType(ERecordType.WHITE_RECORD);
        whiteBlackUserDto1.setTobaccoCertificateNo("78123781273");
        whiteBlackUserDto1.setIdCardNo("330511111111111111");
        whiteBlackUserDto1.setUserName("样本");
        users.add(whiteBlackUserDto1);
        List<CompanyCostDto> costsPlan = new ArrayList<>();
        CompanyCostDto companyCostDto1 = new CompanyCostDto();
        companyCostDto1.setCompanyName("金主2");
        companyCostDto1.setScale(BigDecimal.valueOf(100));
        costsPlan.add(companyCostDto1);

        RuleFullOffDto ruleFirstDiscountDto = new RuleFullOffDto();
        ruleFirstDiscountDto.setOffType(EOffType.MONEY);
        ruleFirstDiscountDto.setTerm(10);
        ruleFirstDiscountDto.setCap(Boolean.FALSE);

        List<RuleFullOffGradDto> ruleFullOffGradDtos = new ArrayList<>();
        RuleFullOffGradDto ruleFullOffGradDto1 = new RuleFullOffGradDto();
        ruleFullOffGradDto1.setDiscount(BigDecimal.valueOf(10));
        ruleFullOffGradDto1.setAmount(BigDecimal.valueOf(10));
        ruleFullOffGradDtos.add(ruleFullOffGradDto1);
        ruleFirstDiscountDto.setRuleFullOffGradDtos(ruleFullOffGradDtos);

        PromotionDto promotion = new PromotionDto();
        promotion.setPromoInfDto(promoInfDto);
        promotion.setPromoAreaDto(areas);
        promotion.setCompanyCostDto(costsPlan);
        promotion.setRuleFullOffDto(ruleFirstDiscountDto);

        promotionService.savePromotion(promotion);
    }

    @Test
    public void search() throws Exception {
        PromoInfSearchDto searchDto = new PromoInfSearchDto();
        searchDto.setName("满减");
        searchDto.setCurrentPage(1);
        searchDto = promotionService.search(searchDto);
        assertEquals(searchDto.getList().size(), 1);
        System.out.println(JsonUtil.toJson(searchDto));
    }

    @Test
    public void getByPromotionId() throws Exception {
        PromotionDto promotionDto = promotionService.getByPromotionId(2L);
        assertEquals(promotionDto.getPromoInfDto().getPromoId(), Long.valueOf(2));
        System.out.println(JsonUtil.toJson(promotionDto));
    }

    @Test
    public void activePromotion(){
        promotionService.activePromotion(2L);
        PromotionDto promotionDto = promotionService.getByPromotionId(2L);
        assertEquals(promotionDto.getPromoInfDto().getStatus(), EPromoInfStatus.ACTIVE);
    }

    @Test
    public void invalidPromotion(){
        promotionService.invalidPromotion(2L);
        PromotionDto promotionDto = promotionService.getByPromotionId(2L);
        assertEquals(promotionDto.getPromoInfDto().getStatus(), EPromoInfStatus.INVALID);
    }

    @Test
    public void finishPromotion(){
        promotionService.finishPromotion(2L);
        PromotionDto promotionDto = promotionService.getByPromotionId(2L);
        assertEquals(promotionDto.getPromoInfDto().getStatus(), EPromoInfStatus.FINISHED);
    }

    @Test
    public void deletePromotion(){
        promotionService.deletePromotion(2L);
        PromotionDto promotionDto = promotionService.getByPromotionId(2L);
        assertEquals(promotionDto.getPromoInfDto().getStatus(), EPromoInfStatus.DELETED);
    }

    @Test
    public void getWhiteRecords() throws Exception {
        List<WhiteBlackUserDto> whiteList = promotionService.getWhiteBlackRecords(2L, ERecordType.WHITE_RECORD);
        assertEquals(whiteList.size(), 1);
        System.out.println(JsonUtil.toJson(whiteList));
    }

    @Test
    public void getBlackRecords() throws Exception {
        List<WhiteBlackUserDto> blackList = promotionService.getWhiteBlackRecords(2L, ERecordType.BLACK_RECORD);
        assertEquals(blackList.size(), 0);
        System.out.println(JsonUtil.toJson(blackList));

    }

    @Test
    public void testGetAndSave(){
        Long promoId = 10L;
        PromotionDto dto = promotionService.getByPromotionId(promoId);
        System.out.println(JsonUtil.toJson(dto.getPromoAreaDto()));
        dto.setPromoAreaDto(new ArrayList<>());
        System.out.println("================");
        promotionService.savePromotion(dto);
        System.out.println("================");
        dto = promotionService.getByPromotionId(promoId);
        System.out.println(JsonUtil.toJson(dto.getPromoAreaDto()));
    }

    @Test
    public void testFinishPromotion(){
        promotionService.finishJob();
    }
}