import com.xinyunlian.jinfu.ad.dao.AdInfDao;
import com.xinyunlian.jinfu.ad.dao.AdPicDao;
import com.xinyunlian.jinfu.ad.dao.AdPositionDao;
import com.xinyunlian.jinfu.ad.entity.AdInfPo;
import com.xinyunlian.jinfu.ad.entity.AdPicPo;
import com.xinyunlian.jinfu.ad.entity.AdPositionPo;
import com.xinyunlian.jinfu.ad.enums.EAdStatus;
import com.xinyunlian.jinfu.ad.service.AdService;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2017/1/5/0005.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations="classpath:jinfu.spring.xml") // 加载配置
public class AdDaoTest {

    @Autowired
    private AdInfDao adInfDao;
    @Autowired
    private AdPositionDao adPositionDao;
    @Autowired
    private AdService adService;
    @Autowired
    private AdPicDao adPicDao;

    @Test
    @Transactional(readOnly = true)
    public void findByAdStatusAndAdPosId(){
        /*AdPositionPo po = new AdPositionPo();
        po.setPosId(1l);

        List<AdInfPo> adList = adInfDao.findByAdStatusAndAdPositionPo(EAdStatus.NORMAL, po);
        System.out.println(JsonUtil.toJson(adList));*/

        /*Specification<AdInfPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            query.where(predicate);

            List<Expression<Boolean>> expressions = predicate.getExpressions();

            Subquery<Long> subQuery = query.subquery(Long.class);
            Root<AdPositionPo> adPositionPoRoot = subQuery.from(AdPositionPo.class);
            subQuery.select(adPositionPoRoot.get("posId"));
            Predicate subPredicate = cb.conjunction();
            List<Expression<Boolean>> subExpressions = subPredicate.getExpressions();

            subExpressions.add(cb.equal(root.get("adPositionPo"), adPositionPoRoot.get("posId")));

            AdPositionPo pos = new AdPositionPo();
            pos.setPosId(1l);
            expressions.add(cb.equal(root.get("adPositionPo"),pos));
            expressions.add(cb.notEqual(root.get("adStatus"), EAdStatus.DELETE));
            return predicate;
        };

        List<AdInfPo> list = adInfDao.findAll(spec);
        System.out.println(JsonUtil.toJson(list));*/

    }

    @Test
    public void findAd(){
//        AdInfPo po = adInfDao.findOne(2l);
        List<AdInfPo> po = adInfDao.findByAdStatusAndAdPosId(EAdStatus.DELETE, 1l);
        System.out.println(JsonUtil.toJson(po));
    }

    @Test
    public void findAdPos(){
        AdPositionPo po = adPositionDao.findOne(1l);
        System.out.println(JsonUtil.toJson(po));
    }

    @Test
    public void findByPosId(){
        Date now = DateHelper.getStartDate(new Date());
        List<AdPicPo> list = adPicDao.findByAdPosSizeId(8l, now, now);
        System.out.println(JsonUtil.toJson(list));
    }

}
