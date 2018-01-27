package com.xinyunlian.jinfu.loan.audit.dto;

import com.ylfin.risk.dto.tongdun.AddressDetectDto;
import com.ylfin.risk.dto.tongdun.ContactDto;
import com.ylfin.risk.dto.tongdun.GeoDto;
import com.ylfin.risk.dto.tongdun.RiskItemDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Sephy
 * @since: 2017-04-18
 */
@ApiModel
public class TdPreLoanReportResponse implements Serializable {
	private static final long serialVersionUID = 7843851359064132041L;

	@ApiModelProperty("同盾报告编号")
	private String reportId;
    @ApiModelProperty("同盾风险分数")
	private Integer finalScore;
    @ApiModelProperty("同盾风险风险结果, Accept:建议通过, Review:建议审核, Reject:建议拒绝")
	private String finalDecision;
	private String deviceType;

	private Date applyTime;
	private Date reportTime;
	private Date loanApplyTime;
	private GeoDto geoIp;
	private GeoDto geoTrueIp;
    @ApiModelProperty("同盾扫描出来的风险项")
    private List<RiskItemDto> riskItems;
    @ApiModelProperty("归属地解析")
	private AddressDetectDto addressDetect;
    @ApiModelProperty("申请编号")
	private String applicationId;
	@ApiModelProperty("信用分")
	private Integer creditScore;
    @ApiModelProperty("近7天内申请贷款申请数")
    private Integer d7LoanApplyNum;
    @ApiModelProperty("近7天内申请贷款申情况")
    private String d7LoanApplyStatus;
    @ApiModelProperty("近1月内申请贷款申请数")
    private Integer m1LoanApplyNum;
    @ApiModelProperty("近1月内申请贷款申情况")
    private String m1LoanApplyStatus;
    @ApiModelProperty("近3个月内申请贷款申请数")
    private Integer m3LoanApplyNum;
    @ApiModelProperty("近3月内申请贷款申情况")
    private String m3LoanApplyStatus;
    @ApiModelProperty("身份证是否命中同盾网贷黑名单")
    private Boolean idNoHitBlacklist;
    @ApiModelProperty("手机号是否命中同盾网贷黑名单")
    private Boolean mobileHitBlacklist;
    @ApiModelProperty("命中法院名单类型")
    private String hitCourtExecList;
    @ApiModelProperty("是否命中法院名单")
	private Boolean hitCourtExec;
    @ApiModelProperty("同盾扫描出来的风险项, 根据检查项分组")
	private Map<String, List<RiskItemDto>> riskItemMap;

    private String username;

    private String idNo;

    private String mobile;

    private Boolean hasInfo;

    private List<ContactDto> contacts;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Integer getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Integer finalScore) {
        this.finalScore = finalScore;
    }

    public String getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Date getLoanApplyTime() {
        return loanApplyTime;
    }

    public void setLoanApplyTime(Date loanApplyTime) {
        this.loanApplyTime = loanApplyTime;
    }

    public GeoDto getGeoIp() {
        return geoIp;
    }

    public void setGeoIp(GeoDto geoIp) {
        this.geoIp = geoIp;
    }

    public GeoDto getGeoTrueIp() {
        return geoTrueIp;
    }

    public void setGeoTrueIp(GeoDto geoTrueIp) {
        this.geoTrueIp = geoTrueIp;
    }

    public List<RiskItemDto> getRiskItems() {
        return riskItems;
    }

    public void setRiskItems(List<RiskItemDto> riskItems) {
        this.riskItems = riskItems;
    }

    public AddressDetectDto getAddressDetect() {
        return addressDetect;
    }

    public void setAddressDetect(AddressDetectDto addressDetect) {
        this.addressDetect = addressDetect;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Integer getD7LoanApplyNum() {
        return d7LoanApplyNum;
    }

    public void setD7LoanApplyNum(Integer d7LoanApplyNum) {
        this.d7LoanApplyNum = d7LoanApplyNum;
    }

    public String getD7LoanApplyStatus() {
        return d7LoanApplyStatus;
    }

    public void setD7LoanApplyStatus(String d7LoanApplyStatus) {
        this.d7LoanApplyStatus = d7LoanApplyStatus;
    }

    public Integer getM1LoanApplyNum() {
        return m1LoanApplyNum;
    }

    public void setM1LoanApplyNum(Integer m1LoanApplyNum) {
        this.m1LoanApplyNum = m1LoanApplyNum;
    }

    public String getM1LoanApplyStatus() {
        return m1LoanApplyStatus;
    }

    public void setM1LoanApplyStatus(String m1LoanApplyStatus) {
        this.m1LoanApplyStatus = m1LoanApplyStatus;
    }

    public Integer getM3LoanApplyNum() {
        return m3LoanApplyNum;
    }

    public void setM3LoanApplyNum(Integer m3LoanApplyNum) {
        this.m3LoanApplyNum = m3LoanApplyNum;
    }

    public String getM3LoanApplyStatus() {
        return m3LoanApplyStatus;
    }

    public void setM3LoanApplyStatus(String m3LoanApplyStatus) {
        this.m3LoanApplyStatus = m3LoanApplyStatus;
    }

    public Boolean getIdNoHitBlacklist() {
        return idNoHitBlacklist;
    }

    public void setIdNoHitBlacklist(Boolean idNoHitBlacklist) {
        this.idNoHitBlacklist = idNoHitBlacklist;
    }

    public Boolean getMobileHitBlacklist() {
        return mobileHitBlacklist;
    }

    public void setMobileHitBlacklist(Boolean mobileHitBlacklist) {
        this.mobileHitBlacklist = mobileHitBlacklist;
    }

    public String getHitCourtExecList() {
        return hitCourtExecList;
    }

    public void setHitCourtExecList(String hitCourtExecList) {
        this.hitCourtExecList = hitCourtExecList;
    }

    public Boolean getHitCourtExec() {
        return hitCourtExec;
    }

    public void setHitCourtExec(Boolean hitCourtExec) {
        this.hitCourtExec = hitCourtExec;
    }

    public Map<String, List<RiskItemDto>> getRiskItemMap() {
        return riskItemMap;
    }

    public void setRiskItemMap(Map<String, List<RiskItemDto>> riskItemMap) {
        this.riskItemMap = riskItemMap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getHasInfo() {
        return hasInfo;
    }

    public void setHasInfo(Boolean hasInfo) {
        this.hasInfo = hasInfo;
    }

    public List<ContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDto> contacts) {
        this.contacts = contacts;
    }
}
