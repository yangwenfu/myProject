package com.xinyunlian.jinfu.app.dto;

import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by dongfangchao on 2017/7/6/0006.
 */
public class AppVerCtrlLogSearchDto extends PagingDto<AppVersionControlLogDto> {
    private static final long serialVersionUID = -7644408359525740648L;

    private EAppSource appSource;

    private EOperatingSystem operatingSystem;

    public EAppSource getAppSource() {
        return appSource;
    }

    public void setAppSource(EAppSource appSource) {
        this.appSource = appSource;
    }

    public EOperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(EOperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}
