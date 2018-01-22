import com.xinyunlian.jinfu.common.util.SortUtils;
import com.xinyunlian.jinfu.common.util.UrlUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dongfangchao on 2016/11/18.
 */
public class StringUtilTest {

    @Test
    public void sortAscii(){
        Map<String, Object> map = new HashMap<>();
        map.put("attach", "at");
        map.put("desc", 11);
        map.put("partner", "10000");
        map.put("totalFee", "");

        TestObject to = new TestObject();
        to.setAge(12);
        to.setName("tom");
        map.put("to", to);

        Map<String, String> lmap = SortUtils.sortFieldObject(map, true, true);
        String params = UrlUtils.generateUrlParam(lmap);
        System.out.println(params);
    }

}
