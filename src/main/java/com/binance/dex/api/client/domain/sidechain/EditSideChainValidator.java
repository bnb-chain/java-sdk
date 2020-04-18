package com.binance.dex.api.client.domain.sidechain;

/**
 * @author Fitz.Lu
 **/
public class EditSideChainValidator {

    private Description description;

    private long commissionRate;

    private String sideChainId;

    private String sideConsAddr;

    private String sideFeeAddr;

    public EditSideChainValidator() {
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
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

    public String getSideConsAddr() {
        return sideConsAddr;
    }

    public void setSideConsAddr(String sideConsAddr) {
        this.sideConsAddr = sideConsAddr;
    }

    public String getSideFeeAddr() {
        return sideFeeAddr;
    }

    public void setSideFeeAddr(String sideFeeAddr) {
        this.sideFeeAddr = sideFeeAddr;
    }
}
