package com.binance.dex.api.client.domain.stake.beaconchain;

import com.binance.dex.api.client.domain.stake.Commission;
import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.domain.stake.sidechain.SideChainValidator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

/**
 * @author Francis.Liu
 **/
public class BeaconChainValidator {

    private String feeAddr;
    private String operatorAddr;
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
    private String distributionAddr;

    private ArrayList<String> stakeSnapShots;

    private String accumulatedStake;


    public BeaconChainValidator() {
    }

    public static BeaconChainValidator createBySideChainValidator(SideChainValidator sideChainValidator) {
        BeaconChainValidator validator = new BeaconChainValidator();
        validator.setFeeAddr(sideChainValidator.getFeeAddr());
        validator.setOperatorAddr(sideChainValidator.getOperatorAddr());
        validator.setConsPubKey(sideChainValidator.getConsPubKey());
        validator.setJailed(sideChainValidator.isJailed());
        validator.setStatus(sideChainValidator.getStatus());
        validator.setTokens(sideChainValidator.getTokens());
        validator.setDelegatorShares(sideChainValidator.getDelegatorShares());
        validator.setDescription(sideChainValidator.getDescription());
        validator.setBondHeight(sideChainValidator.getBondHeight());
        validator.setBondIntraTxCounter(sideChainValidator.getBondIntraTxCounter());
        validator.setUnBondingHeight(sideChainValidator.getUnBondingHeight());
        validator.setCommission(sideChainValidator.getCommission());
        validator.setStakeSnapShots(sideChainValidator.getStakeSnapShots());
        validator.setAccumulatedStake(sideChainValidator.getAccumulatedStake());
        return validator;
    }

    public String getFeeAddr() {
        return feeAddr;
    }

    public void setFeeAddr(String feeAddr) {
        this.feeAddr = feeAddr;
    }

    public String getOperatorAddr() {
        return operatorAddr;
    }

    public void setOperatorAddr(String operatorAddr) {
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

    public String getDistributionAddr() {
        return distributionAddr;
    }

    public void setDistributionAddr(String distributionAddr) {
        this.distributionAddr = distributionAddr;
    }

    public ArrayList<String> getStakeSnapShots() {
        return stakeSnapShots;
    }

    public void setStakeSnapShots(ArrayList<String> stakeSnapShots) {
        this.stakeSnapShots = stakeSnapShots;
    }

    public String getAccumulatedStake() {
        return accumulatedStake;
    }

    public void setAccumulatedStake(String accumulatedStake) {
        this.accumulatedStake = accumulatedStake;
    }

    @Override
    public String toString() {
        return "SideChainValidator{" +
                "feeAddr=" + feeAddr + '\n' +
                ", operatorAddr=" + operatorAddr + '\n' +
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
                ", distributionAddr=" + distributionAddr + '\n' +
                '}';
    }

}
