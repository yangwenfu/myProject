package com.xinyunlian.jinfu.yunma.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.yunma.enums.EDepotReceiveStatus;
import com.xinyunlian.jinfu.yunma.enums.EDepotStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * 云码库Entity
 *
 * @author menglei
 */

public class YmDepotSearchDto extends PagingDto<YmDepotViewDto> {

    private String qrCodeNo;

    private EDepotStatus status;

    private EDepotReceiveStatus receiveStatus;

    private String mobile;

    private Integer bindCount = 0;

    private List<String> qrCodeNos = new ArrayList<>();

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public EDepotStatus getStatus() {
        return status;
    }

    public void setStatus(EDepotStatus status) {
        this.status = status;
    }

    public EDepotReceiveStatus getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(EDepotReceiveStatus receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<String> getQrCodeNos() {
        return qrCodeNos;
    }

    public void setQrCodeNos(List<String> qrCodeNos) {
        this.qrCodeNos = qrCodeNos;
    }

    public Integer getBindCount() {
        return bindCount;
    }

    public void setBindCount(Integer bindCount) {
        this.bindCount = bindCount;
    }
}


