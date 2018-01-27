package com.xinyunlian.jinfu.shopkeeper.dto.card;

/**
 * Created by King on 2017/2/15.
 */
public class CardCheckDto {
    private boolean idAuthIsFull;
    private boolean baseIsFull;
    private boolean storeIsFull;
    private boolean linkmanIsFull;
    private boolean riskAuthIsFull;
    private boolean bankAuthIsFull;
    private boolean storeExtIsFull;
    private boolean hasCar;
    private String carId;
    private boolean hasHouse;
    private String houseId;
    private boolean hasBankAcc;
    private String bankAccId;

    public boolean isIdAuthIsFull() {
        return idAuthIsFull;
    }

    public void setIdAuthIsFull(boolean idAuthIsFull) {
        this.idAuthIsFull = idAuthIsFull;
    }

    public boolean isBaseIsFull() {
        return baseIsFull;
    }

    public void setBaseIsFull(boolean baseIsFull) {
        this.baseIsFull = baseIsFull;
    }

    public boolean isStoreIsFull() {
        return storeIsFull;
    }

    public void setStoreIsFull(boolean storeIsFull) {
        this.storeIsFull = storeIsFull;
    }

    public boolean isLinkmanIsFull() {
        return linkmanIsFull;
    }

    public void setLinkmanIsFull(boolean linkmanIsFull) {
        this.linkmanIsFull = linkmanIsFull;
    }

    public boolean isRiskAuthIsFull() {
        return riskAuthIsFull;
    }

    public void setRiskAuthIsFull(boolean riskAuthIsFull) {
        this.riskAuthIsFull = riskAuthIsFull;
    }

    public boolean isBankAuthIsFull() {
        return bankAuthIsFull;
    }

    public void setBankAuthIsFull(boolean bankAuthIsFull) {
        this.bankAuthIsFull = bankAuthIsFull;
    }

    public boolean isStoreExtIsFull() {
        return storeExtIsFull;
    }

    public void setStoreExtIsFull(boolean storeExtIsFull) {
        this.storeExtIsFull = storeExtIsFull;
    }

    public boolean isHasCar() {
        return hasCar;
    }

    public void setHasCar(boolean hasCar) {
        this.hasCar = hasCar;
    }

    public boolean isHasHouse() {
        return hasHouse;
    }

    public void setHasHouse(boolean hasHouse) {
        this.hasHouse = hasHouse;
    }

    public boolean isHasBankAcc() {
        return hasBankAcc;
    }

    public void setHasBankAcc(boolean hasBankAcc) {
        this.hasBankAcc = hasBankAcc;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getBankAccId() {
        return bankAccId;
    }

    public void setBankAccId(String bankAccId) {
        this.bankAccId = bankAccId;
    }
}
