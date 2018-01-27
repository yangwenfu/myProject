import com.xinyunlian.jinfu.virtual.dto.VirtualTobaccoDto;
import com.xinyunlian.jinfu.virtual.enums.ETakeStatus;
import com.xinyunlian.jinfu.virtual.service.VirtualTobaccoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jinfu.spring.xml"})
@TransactionConfiguration(defaultRollback = false)
public class VirtualTobaccoServiceImplTest {
    @Autowired
    private VirtualTobaccoService virtualTobaccoService;


    @Test
    public void testCountByTakeStatus() throws Exception {
        Long count = virtualTobaccoService.countByTakeStatus(ETakeStatus.TAKED);
        System.out.println(count);
    }

    @Test
    public void testCountUsed() throws Exception {
        Long count = virtualTobaccoService.countUsed();
        System.out.println(count);
    }

    /**
     * Method: take(VirtualTobaccoDto virtualTobaccoDto, int takeNum)
     */
    @Test
    public void testTake() throws Exception {
        VirtualTobaccoDto virtualTobaccoDto = new VirtualTobaccoDto();
        virtualTobaccoDto.setProvinceId(3321L);
        virtualTobaccoDto.setProvince("陕西省");
        virtualTobaccoDto.setCityId(44049L);
        virtualTobaccoDto.setCity("西安市");
        virtualTobaccoDto.setAreaId(44069L);
        virtualTobaccoDto.setArea("高陵区");
        virtualTobaccoDto.setStreetId(44287L);
        virtualTobaccoDto.setStreet("泾渭街道办事处");
        virtualTobaccoDto.setAreaCode("610117");
        virtualTobaccoDto.setRemark("我是备注");
        List<VirtualTobaccoDto> virtualTobaccoDtos = virtualTobaccoService.take(virtualTobaccoDto,20);
        virtualTobaccoDtos.forEach(virtualTobaccoDto1 -> {
            System.out.println(virtualTobaccoDto1.getTobaccoCertificateNo());
        });
    }




} 
