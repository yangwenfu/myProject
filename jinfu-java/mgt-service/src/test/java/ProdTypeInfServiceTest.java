import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.prod.dto.ProdTypeInfDto;
import com.xinyunlian.jinfu.prod.dto.ProdTypeInfSerachDto;
import com.xinyunlian.jinfu.prod.service.ProdTypeInfService;
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
@ContextConfiguration(locations="classpath:jinfu.spring.xml") // 加载配置
public class ProdTypeInfServiceTest {

    @Autowired
    private ProdTypeInfService prodTypeInfService;

    @Test
    public void getProdTypeList(){
        ProdTypeInfSerachDto dto = new ProdTypeInfSerachDto();
        dto.setParent(null);

        List<ProdTypeInfDto> list =  prodTypeInfService.getProdTypeList(dto);
        System.out.println(JsonUtil.toJson(list));
    }

}
