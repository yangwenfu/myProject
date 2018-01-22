package groovy

import com.xinyunlian.jinfu.store.entity.StoreInfPo
import com.xinyunlian.jinfu.store.service.StoreScoreStrategy
import groovy.util.logging.Slf4j

import java.util.regex.Pattern

@Slf4j
class GroovyStoreScoreStrategy implements StoreScoreStrategy {

    final static int RULE_BONUS = 1250

    final static Pattern DIGIGAL_PATTERN = Pattern.compile('.*\\d+.*')

    @Override
    int computeScore(StoreInfPo storeInfPo) {
        if (storeInfPo == null) {
            log.warn("传入店铺上下文为空")
            return 0
        }
        String address = storeInfPo.address
        if (address == null) {
            log.warn("地址字段为空")
            return 0
        }
        final RULE_BONUS = RULE_BONUS
        int score = 0

        if (address.indexOf('路') > 0) {
            if (log.isDebugEnabled()) {
                log.debug('{} 匹配到"路"', address)
            }
            score += RULE_BONUS
        }
        if (address.indexOf('街') > 0) {
            log.debug('{} 匹配到"街"', address)
            score += RULE_BONUS
        }
        if (address.indexOf('号') > 0) {
            log.debug('{} 匹配到"号"', address)
            score += RULE_BONUS
        }
        if (DIGIGAL_PATTERN.matcher(address).matches()) {
            log.debug('{} 匹配到数字', address)
            score += RULE_BONUS
        }
        return score
    }
}