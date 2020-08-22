package com.binance.dex.api.client.domain.stake.sidechain;

import com.binance.dex.api.client.domain.stake.Commission;
import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Fitz.Lu
 **/
public class CreateSideChainValidator {

    private Description description;

    private Commission commission;

    private String delegatorAddr;

    private String validatorAddr;

    private Token delegation;

    private String sideChainId;

    private String sideConsAddr;

    private String sideFeeAddr;

    public CreateSideChainValidator() {
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public String getDelegatorAddr() {
        return delegatorAddr;
    }

    public void setDelegatorAddr(String delegatorAddr) {
        this.delegatorAddr = delegatorAddr;
    }

    public Token getDelegation() {
        return delegation;
    }

    public void setDelegation(Token delegation) {
        this.delegation = delegation;
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

    public String getValidatorAddr() {
        return validatorAddr;
    }

    public void setValidatorAddr(String validatorAddr) {
        this.validatorAddr = validatorAddr;
    }

    @Override
    public String toString() {
        return "CreateSideChainValidator{" + '\n' +
                "description=" + description + '\n' +
                ", commission=" + commission + '\n' +
                ", delegatorAddr='" + delegatorAddr + '\'' + '\n' +
                ", validatorAddr='" + validatorAddr + '\'' + '\n' +
                ", delegation=" + delegation + '\n' +
                ", sideChainId='" + sideChainId + '\'' + '\n' +
                ", sideConsAddr='" + sideConsAddr + '\'' + '\n' +
                ", sideFeeAddr='" + sideFeeAddr + '\'' + '\n' +
                '}';
    }
}
