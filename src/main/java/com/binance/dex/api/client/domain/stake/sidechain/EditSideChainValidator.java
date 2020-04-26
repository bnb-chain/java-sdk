package com.binance.dex.api.client.domain.stake.sidechain;

import com.binance.dex.api.client.domain.stake.Description;

/**
 * @author Fitz.Lu
 **/
public class EditSideChainValidator {

    private Description description;

    private String validatorAddress;

    private long commissionRate;

    private String sideChainId;

    private String sideFeeAddr;

    public EditSideChainValidator() {
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getValidatorAddress() {
        return validatorAddress;
    }

    public void setValidatorAddress(String validatorAddress) {
        this.validatorAddress = validatorAddress;
    }

    public long getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(long commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }

    public String getSideFeeAddr() {
        return sideFeeAddr;
    }

    public void setSideFeeAddr(String sideFeeAddr) {
        this.sideFeeAddr = sideFeeAddr;
    }

    @Override
    public String toString() {
        return "EditSideChainValidator{" +
                "description=" + description + "\n" +
                ", validatorAddress='" + validatorAddress + '\'' + "\n" +
                ", commissionRate=" + commissionRate + "\n" +
                ", sideChainId='" + sideChainId + '\'' + "\n" +
                ", sideFeeAddr='" + sideFeeAddr + '\'' + "\n" +
                '}';
    }
}
