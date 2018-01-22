import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMMemberSearchDto;
import com.xinyunlian.jinfu.yunma.dto.YmMemberBizDto;
import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jinfu.spring.xml"})
@TransactionConfiguration(defaultRollback = false)
public class YMMemberServiceImplTest {
    @Autowired
    private YMMemberService ymMemberService;
    @Autowired
    private YMUserInfoService ymUserInfoService;

    /**
     * Method: addMember(YMMemberDto dto)
     */
    @Test
    public void testAddMember() {
        YMMemberDto dto = new YMMemberDto();
        dto.setMemberNo("ceshi123");
        dto.setUserId("123");
        dto.setStoreId(1l);
        dto.setQrcodeNo("111");
        ymMemberService.addMember(dto);
    }

    /**
     * Method: get(Long id)
     */
    @Test
    public void testGet() {
        YMMemberDto dto = ymMemberService.get(265L);
        System.out.println(dto.getMemberNo());
    }

    /**
     * Method: alterStatus(Long id, EMemberStatus status)
     */
    @Test
    public void testAlterStatus() {
        ymMemberService.alterStatus(265L, EMemberStatus.ENABLE);
    }

    /**
     * Method: alterBankCard(Long id, Long bankCardId)
     */
    @Test
    public void testAlterBankCard() {
        ymMemberService.alterBankCard(265L,1l);
    }

    /**
     * Method: unbind(Long id)
     */
    @Test
    public void testUnbind() {
        ymMemberService.unbind(265L);
    }

    /**
     * Method: getMemberPage(YMMemberSearchDto searchDto)
     */
    @Test
    public void testGetMemberPage() {
        YMMemberSearchDto searchDto = new YMMemberSearchDto();
        searchDto = ymMemberService.getMemberPage(searchDto);
        System.out.println(searchDto.getList().size());
    }

    /**
     * Method: updateMemberBiz(List<YmMemberBizDto> memberBizDtos)
     */
    @Test
    public void testUpdateMemberBiz() {
        List<YmMemberBizDto> ymMemberBizDtos = new ArrayList<>();
        YmMemberBizDto dto = new YmMemberBizDto();
        dto.setMemberNo("P00122218152096664");
        dto.setBizCode(EBizCode.WECHAT);
        dto.setRate(BigDecimal.valueOf(0.35));
        ymMemberBizDtos.add(dto);
        ymMemberService.updateMemberBiz(ymMemberBizDtos);
    }

    /**
     * Method: getMemberByStoreId(Long storeId)
     */
    @Test
    public void testGetMemberByStoreId() {
        YMMemberDto ymMemberDto = ymMemberService.getMemberByStoreId(2104L);
        System.out.println(ymMemberDto.getMemberNo());
    }

    /**
     * Method: getMemberListByUserId(String userId)
     */
    @Test
    public void testGetMemberListByUserId() {
       List<YMMemberDto> list = ymMemberService.getMemberListByUserId("UC0000000404");
        System.out.println(list.size());
    }

    @Test
    public void testDeleteByUserId() {
        ymUserInfoService.deleteByUserId("UC2000000126");
    }


} 
