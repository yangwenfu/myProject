import com.xinyunlian.jinfu.cms.dto.ArticleSearchDto;
import com.xinyunlian.jinfu.cms.dto.CmsArticleDto;
import com.xinyunlian.jinfu.cms.dto.CmsArticleTypeDto;
import com.xinyunlian.jinfu.cms.enums.EArcPlatform;
import com.xinyunlian.jinfu.cms.service.CmsArticleService;
import com.xinyunlian.jinfu.cms.service.CmsArticleTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations="classpath:jinfu.spring.xml") // 加载配置
public class CmsArticleServiceImplTest {
    @Autowired
    private CmsArticleService cmsArticleService;
    @Autowired
    private CmsArticleTypeService cmsArticleTypeService;

    /**
     * Method: save(CmsArticleDto cmsArticleDto)
     */
    @Test
    public void testSave() throws Exception {
        CmsArticleDto cmsArticleDto = new CmsArticleDto();

        cmsArticleDto.getArcPlatform().add(EArcPlatform.BUDDY);
        cmsArticleDto.setArticleTypeId(3L);
        cmsArticleDto.setArticleTypeTree(",1,2,3");
        cmsArticleDto.setBigPic("/123/333");
        cmsArticleDto.setContent("内容");
        cmsArticleDto.setFilePath("/1/3/4");
        cmsArticleDto.setRecommend(true);
        cmsArticleDto.setTop(true);
        cmsArticleDto.setSeoDescribe("1231");
        cmsArticleDto.setSeoTitle("asd");
        cmsArticleDto.setTitle("标题");
        cmsArticleService.save(cmsArticleDto);
    }

    /**
     * Method: delete(Long id)
     */
    @Test
    public void testDelete() throws Exception {
        cmsArticleService.delete(1l);
    }

    /**
     * Method: get(Long id)
     */
    @Test
    public void testGet() throws Exception {
        CmsArticleDto cmsArticleDto = cmsArticleService.get(2l);
        System.out.println(cmsArticleDto.getTitle());
    }

    /**
     * Method: getPage(ArticleSearchDto searchDto)
     */
    @Test
    public void testGetPage() throws Exception {
        ArticleSearchDto searchDto = new ArticleSearchDto();
        cmsArticleService.getPage(searchDto);
    }

    @Test
    public void testTypeSave() throws Exception {
        CmsArticleTypeDto cmsArticleTypeDto = new CmsArticleTypeDto();
        cmsArticleTypeDto.setParent(3);
        cmsArticleTypeDto.setName("2.2云易贷");
        cmsArticleTypeService.save(cmsArticleTypeDto);
    }

    @Test
    public void testTypeDelete() throws Exception {
        cmsArticleTypeService.delete(12l);
    }

    @Test
    public void testTypeGetList() throws Exception {
        CmsArticleTypeDto cmsArticleTypeDto = new CmsArticleTypeDto();
        cmsArticleTypeDto.setParent(1);
        List<CmsArticleTypeDto> list = cmsArticleTypeService.getList(cmsArticleTypeDto);
        System.out.println(list.size());
    }
} 
