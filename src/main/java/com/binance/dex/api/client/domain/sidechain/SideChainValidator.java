package com.binance.dex.api.client.domain.sidechain;

import java.util.Arrays;

/**
 * @author Fitz.Lu
 **/
public class SideChainValidator {

    private byte[] feeAddr;

    private byte[] operatorAddr;

    private byte[] consPubKey;

    private boolean jailed;

    private int status;

    private long tokens;

    private long delegatorShares;

    private Description description;

    private long bondHeight;

    private int bondIntraTxCounter;

    private long unBondingHeight;

    private long unBondingMinTime;

    private Commission commission;

    private byte[] distributionAddr;

    private String sideChainId;

    private byte[] sideConsAddr;

    private byte[] sideFeeAddr;

    public SideChainValidator() {
    }

    public byte[] getFeeAddr() {
        return feeAddr;
    }

    public void setFeeAddr(byte[] feeAddr) {
        this.feeAddr = feeAddr;
    }

    public byte[] getOperatorAddr() {
        return operatorAddr;
    }

    public void setOperatorAddr(byte[] operatorAddr) {
        this.operatorAddr = operatorAddr;
    }

    public byte[] getConsPubKey() {
        return consPubKey;
    }

    public void setConsPubKey(byte[] consPubKey) {
        this.consPubKey = consPubKey;
    }

    public boolean isJailed() {
        return jailed;
    }

    public void setJailed(boolean jailed) {
        this.jailed = jailed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTokens() {
        return tokens;
    }

    public void setTokens(long tokens) {
        this.tokens = tokens;
    }

    public long getDelegatorShares() {
        return delegatorShares;
    }

    public void setDelegatorShares(long delegatorShares) {
        this.delegatorShares = delegatorShares;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public long getBondHeight() {
        return bondHeight;
    }

    public void setBondHeight(long bondHeight) {
        this.bondHeight = bondHeight;
    }

    public int getBondIntraTxCounter() {
        return bondIntraTxCounter;
    }

    public void setBondIntraTxCounter(int bondIntraTxCounter) {
        this.bondIntraTxCounter = bondIntraTxCounter;
    }

    public long getUnBondingHeight() {
        return unBondingHeight;
    }

    public void setUnBondingHeight(long unBondingHeight) {
        this.unBondingHeight = unBondingHeight;
    }

    public long getUnBondingMinTime() {
        return unBondingMinTime;
    }

    public void setUnBondingMinTime(long unBondingMinTime) {
        this.unBondingMinTime = unBondingMinTime;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public byte[] getDistributionAddr() {
        return distributionAddr;
    }

    public void setDistributionAddr(byte[] distributionAddr) {
        distributionAddr = distributionAddr;
    }

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }

    public byte[] getSideConsAddr() {
        return sideConsAddr;
    }

    public void setSideConsAddr(byte[] sideConsAddr) {
        this.sideConsAddr = sideConsAddr;
    }

    public byte[] getSideFeeAddr() {
        return sideFeeAddr;
    }

    public void setSideFeeAddr(byte[] sideFeeAddr) {
        this.sideFeeAddr = sideFeeAddr;
    }

    @Override
    public String toString() {
        return "SideChainValidator{" +
                "feeAddr=" + Arrays.toString(feeAddr) + '\n' +
                ", operatorAddr=" + Arrays.toString(operatorAddr) + '\n' +
                ", consPubKey='" + consPubKey + '\'' + '\n' +
                ", jailed=" + jailed + '\n' +
                ", status=" + status + '\n' +
                ", tokens=" + tokens + '\n' +
                ", delegatorShares=" + delegatorShares + '\n' +
                ", description=" + description + '\n' +
                ", bondHeight=" + bondHeight + '\n' +
                ", bondIntraTxCounter=" + bondIntraTxCounter + '\n' +
                ", unBondingHeight=" + unBondingHeight + '\n' +
                ", unBondingMinTime=" + unBondingMinTime + '\n' +
                ", commission=" + commission + '\n' +
                ", distributionAddr=" + Arrays.toString(distributionAddr) + '\n' +
                ", sideChainId='" + sideChainId + '\'' + '\n' +
                ", sideConsAddr=" + Arrays.toString(sideConsAddr) + '\n' +
                ", sideFeeAddr=" + Arrays.toString(sideFeeAddr) + '\n' +
                '}';
    }

}
