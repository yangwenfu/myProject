import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.dto.ProductSearchDto;
import com.xinyunlian.jinfu.prod.enums.EProd;
import com.xinyunlian.jinfu.prod.enums.EProdAppDetailCfg;
import com.xinyunlian.jinfu.prod.enums.EShelfPlatform;
import com.xinyunlian.jinfu.prod.service.ProdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by dongfangchao on 2016/11/17.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations="classpath:standard-code-dao-test.xml") // 加载配置
public class ProdServiceTest {

    @Autowired
    private ProdService prodService;

    @Test
    public void getProdList(){
        ProductSearchDto dto = new ProductSearchDto();
        dto.setProdTypePath(",1,");

        List<ProductDto> list =  prodService.getProdList(dto);
        System.out.println(JsonUtil.toJson(list));
    }

    @Test
    public void getProdListByCondition(){
        List<ProductDto> list = prodService.getProdListByIdsAndPlatform(null, EShelfPlatform.JINFU_MALL);
        System.out.println(JsonUtil.toJson(list));
    }

    @Test
    public void getProdListByPlatformAndCfg(){
        List<ProductDto> list = prodService.getProdListByPlatformAndCfg(EShelfPlatform.JINFU_MALL, EProdAppDetailCfg.RCMD,2);
        System.out.println(JsonUtil.toJson(list));
    }

    @Test
    public void getProdListByPlatformCfgInd(){
        List<ProductDto> list = prodService.getProdListByPlatformCfgInd(EShelfPlatform.JINFU_MALL, EProdAppDetailCfg.RCMD, "5227", 2);
        System.out.println(JsonUtil.toJson(list));
    }

    @Test
    public void checkProdArea(){
        Boolean ret = prodService.checkProdArea(EProd.S01001.getCode(), 1l, "5227");
        System.out.println("ret = " + ret);
    }

    @Test
    public void getProdById(){
        ProductDto l01002 = prodService.getProdById("L01002");
        System.out.println(JsonUtil.toJson(l01002));
    }

}
