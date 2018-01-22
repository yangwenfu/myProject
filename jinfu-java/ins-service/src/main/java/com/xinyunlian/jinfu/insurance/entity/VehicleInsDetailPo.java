package com.xinyunlian.jinfu.insurance.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by dongfangchao on 2016/12/2/0002.
 */
@Entity
@Table(name = "vehicle_ins_detail")
public class VehicleInsDetailPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -4963486631892135406L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;
    @Column(name="PER_INSURANCE_ORDER_NO")
    private String perInsuranceOrderNo;
    @Column(name="SRC_BIZ_PREMIUM_INTERVAL")
    private Long srcBizPremiumInterval;
    @Column(name="SRC_COMPEL_PREMIUM_INTERVAL")
    private Long srcCompelPremiumInterval;
    @Column(name="SRC_TAX_PREIMUM")
    private Long srcTaxPreimum;
    @Column(name="BUSINESS_POLICY_NO")
    private String businessPolicyNo;
    @Column(name="BUSINESS_PREMIUM_INTERVAL")
    private Long businessPremiumInterval;
    @Column(name="VEHICLE_POLICY_EFFECTIVE_DATE")
    private String vehiclePolicyEffectiveDate;
    @Column(name="VEHICLE_POLICY_EXPIRY_DATE")
    private String vehiclePolicyExpiryDate;
    @Column(name="COMPEL_POLICY_NO")
    private String compelPolicyNo;
    @Column(name="COMPEL_PREMIUM_INTERVAL")
    private Long compelPremiumInterval;
    @Column(name="COMPEL_EFFECTIVE_DATE")
    private String compelEffectiveDate;
    @Column(name="COMPEL_EXPIRY_DATE")
    private String compelExpiryDate;
    @Column(name = "TAX_PREIMUM")
    private Long taxPreimum;
    @Column(name="VEHICLE_OWNER_NAME")
    private String vehicleOwnerName;
    @Column(name="POLICY_HOLDER_NAME")
    private String policyHolderName;
    @Column(name="INSURANT_NAME")
    private String insurantName;
    @Column(name="POLICY_HOLDER_PHONE_NO")
    private String policyHolderPhoneNo;
    @Column(name="VEHICLE_LICENCE_PLATE_NO")
    private String vehicleLicencePlateNo;
    @Column(name="VIN_NO")
    private String vinNo;
    @Column(name="VEHICLE_ENGINE_NO")
    private String vehicleEngineNo;
    @Column(name="RECEIVER_NAME")
    private String receiverName;
    @Column(name="RECEIVER_PHONE_NO")
    private String receiverPhoneNo;
    @Column(name="RECEIVER_ADDRESS")
    private String receiverAddress;
    @Column(name="CORRECT_VEHICLE_ENDO_NO")
    private String correctVehicleEndoNo;
    @Column(name="CORRECT_VEHICLE_DELTA_PREMIUM")
    private Long correctVehicleDeltaPremium;
    @Column(name="CORRECT_VEHICLE_APPLY_DATE")
    private String correctVehicleApplyDate;
    @Column(name="CORRECT_VEHICLE_EFFECTIVE_DATE")
    private String correctVehicleEffectiveDate;
    @Column(name="CORRECT_COMPEL_ENDO_NO")
    private String correctCompelEndoNo;
    @Column(name="CORRECT_COMPEL_DELTA_PREMIUM")
    private Long correctCompelDeltaPremium;
    @Column(name="CORRECT_COMPEL_APPLY_DATE")
    private String correctCompelApplyDate;
    @Column(name="CORRECT_COMPEL_EFFECTIVE_DATE")
    private String correctCompelEffectiveDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerInsuranceOrderNo() {
        return perInsuranceOrderNo;
    }

    public void setPerInsuranceOrderNo(String perInsuranceOrderNo) {
        this.perInsuranceOrderNo = perInsuranceOrderNo;
    }

    public String getBusinessPolicyNo() {
        return businessPolicyNo;
    }

    public void setBusinessPolicyNo(String businessPolicyNo) {
        this.businessPolicyNo = businessPolicyNo;
    }

    public Long getBusinessPremiumInterval() {
        return businessPremiumInterval;
    }

    public void setBusinessPremiumInterval(Long businessPremiumInterval) {
        this.businessPremiumInterval = businessPremiumInterval;
    }

    public String getVehiclePolicyEffectiveDate() {
        return vehiclePolicyEffectiveDate;
    }

    public void setVehiclePolicyEffectiveDate(String vehiclePolicyEffectiveDate) {
        this.vehiclePolicyEffectiveDate = vehiclePolicyEffectiveDate;
    }

    public String getVehiclePolicyExpiryDate() {
        return vehiclePolicyExpiryDate;
    }

    public void setVehiclePolicyExpiryDate(String vehiclePolicyExpiryDate) {
        this.vehiclePolicyExpiryDate = vehiclePolicyExpiryDate;
    }

    public String getCompelPolicyNo() {
        return compelPolicyNo;
    }

    public void setCompelPolicyNo(String compelPolicyNo) {
        this.compelPolicyNo = compelPolicyNo;
    }

    public Long getCompelPremiumInterval() {
        return compelPremiumInterval;
    }

    public void setCompelPremiumInterval(Long compelPremiumInterval) {
        this.compelPremiumInterval = compelPremiumInterval;
    }

    public String getCompelEffectiveDate() {
        return compelEffectiveDate;
    }

    public void setCompelEffectiveDate(String compelEffectiveDate) {
        this.compelEffectiveDate = compelEffectiveDate;
    }

    public String getCompelExpiryDate() {
        return compelExpiryDate;
    }

    public void setCompelExpiryDate(String compelExpiryDate) {
        this.compelExpiryDate = compelExpiryDate;
    }

    public String getVehicleOwnerName() {
        return vehicleOwnerName;
    }

    public void setVehicleOwnerName(String vehicleOwnerName) {
        this.vehicleOwnerName = vehicleOwnerName;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public String getInsurantName() {
        return insurantName;
    }

    public void setInsurantName(String insurantName) {
        this.insurantName = insurantName;
    }

    public String getPolicyHolderPhoneNo() {
        return policyHolderPhoneNo;
    }

    public void setPolicyHolderPhoneNo(String policyHolderPhoneNo) {
        this.policyHolderPhoneNo = policyHolderPhoneNo;
    }

    public String getVehicleLicencePlateNo() {
        return vehicleLicencePlateNo;
    }

    public void setVehicleLicencePlateNo(String vehicleLicencePlateNo) {
        this.vehicleLicencePlateNo = vehicleLicencePlateNo;
    }

    public String getVinNo() {
        return vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo;
    }

    public String getVehicleEngineNo() {
        return vehicleEngineNo;
    }

    public void setVehicleEngineNo(String vehicleEngineNo) {
        this.vehicleEngineNo = vehicleEngineNo;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhoneNo() {
        return receiverPhoneNo;
    }

    public void setReceiverPhoneNo(String receiverPhoneNo) {
        this.receiverPhoneNo = receiverPhoneNo;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getCorrectVehicleEndoNo() {
        return correctVehicleEndoNo;
    }

    public void setCorrectVehicleEndoNo(String correctVehicleEndoNo) {
        this.correctVehicleEndoNo = correctVehicleEndoNo;
    }

    public Long getCorrectVehicleDeltaPremium() {
        return correctVehicleDeltaPremium;
    }

    public void setCorrectVehicleDeltaPremium(Long correctVehicleDeltaPremium) {
        this.correctVehicleDeltaPremium = correctVehicleDeltaPremium;
    }

    public String getCorrectVehicleApplyDate() {
        return correctVehicleApplyDate;
    }

    public void setCorrectVehicleApplyDate(String correctVehicleApplyDate) {
        this.correctVehicleApplyDate = correctVehicleApplyDate;
    }

    public String getCorrectVehicleEffectiveDate() {
        return correctVehicleEffectiveDate;
    }

    public void setCorrectVehicleEffectiveDate(String correctVehicleEffectiveDate) {
        this.correctVehicleEffectiveDate = correctVehicleEffectiveDate;
    }

    public String getCorrectCompelEndoNo() {
        return correctCompelEndoNo;
    }

    public void setCorrectCompelEndoNo(String correctCompelEndoNo) {
        this.correctCompelEndoNo = correctCompelEndoNo;
    }

    public Long getCorrectCompelDeltaPremium() {
        return correctCompelDeltaPremium;
    }

    public void setCorrectCompelDeltaPremium(Long correctCompelDeltaPremium) {
        this.correctCompelDeltaPremium = correctCompelDeltaPremium;
    }

    public String getCorrectCompelApplyDate() {
        return correctCompelApplyDate;
    }

    public void setCorrectCompelApplyDate(String correctCompelApplyDate) {
        this.correctCompelApplyDate = correctCompelApplyDate;
    }

    public String getCorrectCompelEffectiveDate() {
        return correctCompelEffectiveDate;
    }

    public void setCorrectCompelEffectiveDate(String correctCompelEffectiveDate) {
        this.correctCompelEffectiveDate = correctCompelEffectiveDate;
    }

    public Long getTaxPreimum() {
        return taxPreimum;
    }

    public void setTaxPreimum(Long taxPreimum) {
        this.taxPreimum = taxPreimum;
    }

    public Long getSrcBizPremiumInterval() {
        return srcBizPremiumInterval;
    }

    public void setSrcBizPremiumInterval(Long srcBizPremiumInterval) {
        this.srcBizPremiumInterval = srcBizPremiumInterval;
    }

    public Long getSrcCompelPremiumInterval() {
        return srcCompelPremiumInterval;
    }

    public void setSrcCompelPremiumInterval(Long srcCompelPremiumInterval) {
        this.srcCompelPremiumInterval = srcCompelPremiumInterval;
    }

    public Long getSrcTaxPreimum() {
        return srcTaxPreimum;
    }

    public void setSrcTaxPreimum(Long srcTaxPreimum) {
        this.srcTaxPreimum = srcTaxPreimum;
    }
}
