import org.springframework.util.ResourceUtils;

import com.xinyunlian.jinfu.store.entity.StoreInfPo;

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;

/**
 * @author Sephy
 * @since: 2017-06-25
 */
public class GroovyScriptTest {

	public static void main(String[] args) throws Exception {
		StoreInfPo po = new StoreInfPo();
		po.setAddress("上店街内4号");
		GroovyScriptEngine engine = new GroovyScriptEngine("");
		Class klass = engine
				.loadScriptByName(ResourceUtils.getURL("classpath:groovy/GroovyStoreScoreStrategy.groovy").toString());
		GroovyObject groovyObject = (GroovyObject) klass.newInstance();
		Object[] params = new Object[] { po };
		Object result = groovyObject.invokeMethod("computeScore", params);
		System.out.println(result);
	}
}