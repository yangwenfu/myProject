/*
 * Project Name: standard-code-base
 * File Name: ValidateInterceptor.java
 * Class Name: ValidateInterceptor
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * 
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xinyunlian.jinfu.api.validate;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.yunma.dto.YmThirdConfigDto;
import com.xinyunlian.jinfu.yunma.service.YmThirdConfigService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class OpenApiInterceptor {

    @Autowired
    private YmThirdConfigService ymThirdConfigService;

    @Before("@annotation(com.xinyunlian.jinfu.api.validate.OpenApi)")
    public void validate1(final JoinPoint jp) {
        final Object[] args = jp.getArgs();
        for (Object arg : args
                ) {
            if (arg instanceof OpenApiBaseDto) {
                OpenApiBaseDto validatedObj = (OpenApiBaseDto) arg;
                YmThirdConfigDto ymThirdConfigDto = ymThirdConfigService.findByAppId(validatedObj.getAppId());
                String key = null;
                if (ymThirdConfigDto != null) {
                    key = ymThirdConfigDto.getKey();
                }
                if (StringUtils.isEmpty(key)) {
                    throw new BizServiceException(EErrorCode.OPEN_API_SIGN_VALIDATE_FAIL, "签名认证失败");
                }
                if (!validatedObj.validate(key)) {
                    throw new BizServiceException(EErrorCode.OPEN_API_SIGN_VALIDATE_FAIL, "签名认证失败");
                }
            }
        }
    }


}
