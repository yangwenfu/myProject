package com.xinyunlian.jinfu.qrcode.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.qrcode.enums.EProdCodeStatus;

/**
 * Created by menglei on 2017年03月14日.
 */
public class ProdCodeSearchDto extends PagingDto<ProdCodeDto> {

    private static final long serialVersionUID = 1L;

    private String qrCodeNo;

    private String startCreateTs;

    private String endCreateTs;

    private String startBindTs;

    private String endBindTs;

    private EProdCodeStatus status;

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public String getStartCreateTs() {
        return startCreateTs;
    }

    public void setStartCreateTs(String startCreateTs) {
        this.startCreateTs = startCreateTs;
    }

    public String getEndCreateTs() {
        return endCreateTs;
    }

    public void setEndCreateTs(String endCreateTs) {
        this.endCreateTs = endCreateTs;
    }

    public String getStartBindTs() {
        return startBindTs;
    }

    public void setStartBindTs(String startBindTs) {
        this.startBindTs = startBindTs;
    }

    public String getEndBindTs() {
        return endBindTs;
    }

    public void setEndBindTs(String endBindTs) {
        this.endBindTs = endBindTs;
    }

    public EProdCodeStatus getStatus() {
        return status;
    }

    public void setStatus(EProdCodeStatus status) {
        this.status = status;
    }
}
