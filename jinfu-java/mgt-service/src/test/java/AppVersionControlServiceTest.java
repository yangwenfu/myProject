import com.xinyunlian.jinfu.app.dto.AppOpLogDto;
import com.xinyunlian.jinfu.app.dto.AppVersionControlDto;
import com.xinyunlian.jinfu.app.dto.AppVersionControlLogDto;
import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import com.xinyunlian.jinfu.app.service.AppVersionControlService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by dongfangchao on 2017/6/20/0020.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations = "classpath:standard-code-dao-test.xml") // 加载配置
public class AppVersionControlServiceTest {

    @Autowired
    private AppVersionControlService appVersionControlService;

    @Test
    public void checkAppVersion(){
        AppVersionControlDto appVersionControlDto = appVersionControlService.checkAppVersion("YLZG", "IOS", "1.3.3");
        System.out.println(JsonUtil.toJson(appVersionControlDto));
    }

    @Test
    public void normalRelease(){

        AppVersionControlReqTest req = new AppVersionControlReqTest();
        req.setAppForceUpdate(true);
        req.setAppPath("http://www.baidu.com");
        req.setAppSource(EAppSource.YLZG);
        req.setOperatingSystem(EOperatingSystem.IOS);
        req.setUpdateTip("云联掌柜需要更新嘞");
        req.setVersionCode(3l);
        req.setVersionName("2.1.0");

        AppVersionControlDto currentRelease = appVersionControlService.getApp(req.getAppSource().getCode(), req.getOperatingSystem().getCode());
        if (currentRelease == null){
            System.out.println("APP不存在");
            return;
        }

        AppVersionControlDto nextRelease = ConverterService.convert(req, AppVersionControlDto.class);
        nextRelease.setId(currentRelease.getId());
        nextRelease.setNormalRelease(true);

        //版本号校验
        Integer compareResult =
                appVersionControlService.compareAppVersionName(nextRelease.getAppSource().getCode(),
                        nextRelease.getOperatingSystem().getCode(), nextRelease.getVersionName(), true);
        if (compareResult != 1){
            System.out.println("版本号不能低于或等于上一个正式版本号！");
            return;
        }

        AppVersionControlLogDto appVersionControlLogDto = ConverterService.convert(currentRelease, AppVersionControlLogDto.class);

        try {
            appVersionControlService.normalReleaseUpdate(nextRelease, appVersionControlLogDto);
            String userId = SecurityContext.getCurrentUserId();
            concatAppOpLog(userId, nextRelease, currentRelease, false);

            //更新缓存
            appVersionControlService.updateCache(req.getAppSource().getCode(), req.getOperatingSystem().getCode(), false);
            System.out.println("修改成功");
            return;
        } catch (BizServiceException e) {
            System.out.println(e.getError());
            return;
        }
    }

    private void concatAppOpLog(String userId, AppVersionControlDto newDto, AppVersionControlDto oldDto, Boolean add){
//        MgtUserDto userDto = mgtUserService.getMgtUserInf(userId);

        StringBuilder opLog = new StringBuilder();
        if (add){
            String appForceUpdate = newDto.getAppForceUpdate()?"是":"否";

            opLog.append("设置版本名为“")
                    .append(newDto.getVersionName())
                    .append("”, 版本码为“")
                    .append(newDto.getVersionCode())
                    .append("”, 强制更新为“")
                    .append(appForceUpdate)
                    .append("”");
        }else {
            String oldAppForceUpdate = oldDto.getAppForceUpdate()?"是":"否";
            String newAppForceUpdate = newDto.getAppForceUpdate()?"是":"否";

            opLog.append("修改版本名“")
                    .append(oldDto.getVersionName())
                    .append("”为“")
                    .append(newDto.getVersionName())
                    .append("”, 版本码“")
                    .append(oldDto.getVersionCode())
                    .append("”为“")
                    .append(newDto.getVersionCode())
                    .append("”, 强制更新“")
                    .append(oldAppForceUpdate)
                    .append("”为“")
                    .append(newAppForceUpdate)
                    .append("”");
        }

        AppOpLogDto logDto = new AppOpLogDto();
        logDto.setAppId(oldDto.getId());
        logDto.setOperator("调试者");
        logDto.setUpdateTime(new Date());
        logDto.setOpLog(opLog.toString());
        logDto.setVersionName(newDto.getVersionName());
        logDto.setVersionCode(newDto.getVersionCode());
        logDto.setAppForceUpdate(newDto.getAppForceUpdate());
        appVersionControlService.addAppOpLog(logDto);
    }

}
