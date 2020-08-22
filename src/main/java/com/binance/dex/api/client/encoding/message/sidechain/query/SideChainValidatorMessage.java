package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.sidechain.value.CommissionValue;
import com.binance.dex.api.client.encoding.message.common.Dec;
import com.binance.dex.api.client.encoding.message.sidechain.value.DescriptionValue;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Fitz.Lu
 **/
public class SideChainValidatorMessage implements AminoSerializable {

    private byte[] feeAddr;

    private byte[] operatorAddr;

    private byte[] consPubKey;

    private boolean jailed;

    private int status;

    private Dec tokens = Dec.newInstance(0L);

    private Dec delegatorShares = Dec.newInstance(0L);

    private DescriptionValue description = new DescriptionValue();

    private long bondHeight;

    private int bondIntraTxCounter;

    private long unBondingHeight;

    private long unBondingMinTime;

    private CommissionValue commission = new CommissionValue();

    private byte[] distributionAddr;

    private String sideChainId;

    private byte[] sideConsAddr;

    private byte[] sideFeeAddr;

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

    public Dec getTokens() {
        return tokens;
    }

    public void setTokens(Dec tokens) {
        this.tokens = tokens;
    }

    public Dec getDelegatorShares() {
        return delegatorShares;
    }

    public void setDelegatorShares(Dec delegatorShares) {
        this.delegatorShares = delegatorShares;
    }

    public DescriptionValue getDescription() {
        return description;
    }

    public void setDescription(DescriptionValue description) {
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

    public CommissionValue getCommission() {
        return commission;
    }

    public void setCommission(CommissionValue commission) {
        this.commission = commission;
    }

    public byte[] getDistributionAddr() {
        return distributionAddr;
    }

    public void setDistributionAddr(byte[] distributionAddr) {
        this.distributionAddr = distributionAddr;
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
    public AminoSerializable newAminoMessage() {
        return new SideChainValidatorMessage();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                feeAddr = (byte[]) value;
                break;
            case 2:
                operatorAddr = (byte[]) value;
                break;
            case 3:
                consPubKey = (byte[]) value;
                break;
            case 4:
                jailed = ((boolean) value);
                break;
            case 5:
                status = ((Integer) value);
                break;
            case 6:
                tokens = ((Dec) value);
                break;
            case 7:
                delegatorShares = ((Dec) value);
                break;
            case 8:
                description = ((DescriptionValue) value);
                break;
            case 9:
                bondHeight = ((long) value);
                break;
            case 10:
                bondIntraTxCounter = ((int) value);
                break;
            case 11:
                unBondingHeight = ((long) value);
                break;
            case 12:
                unBondingMinTime = ((long) value);
                break;
            case 13:
                commission = ((CommissionValue) value);
                break;
            case 14:
                distributionAddr = ((byte[]) value);
                break;
            case 15:
                sideChainId = ((String) value);
                break;
            case 16:
                sideConsAddr = ((byte[]) value);
                break;
            case 17:
                sideFeeAddr = ((byte[]) value);
                break;
            default:
                break;
        }
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(byte[].class, feeAddr, feeAddr == null || feeAddr.length == 0)
                .addField(byte[].class, operatorAddr, operatorAddr == null || operatorAddr.length == 0)
                .addField(byte[].class, consPubKey, consPubKey == null || consPubKey.length == 0)
                .addField(Boolean.class, jailed, !jailed)
                .addField(Integer.class, status, status == 0)
                .addField(Dec.class, tokens, tokens == null)
                .addField(Dec.class, delegatorShares, delegatorShares == null)
                .addField(DescriptionValue.class, description, description == null)
                .addField(Long.class, bondHeight, bondHeight == 0)
                .addField(Integer.class, bondIntraTxCounter, bondIntraTxCounter == 0)
                .addField(Long.class, unBondingHeight, unBondingHeight == 0)
                .addField(Long.class, unBondingMinTime, unBondingMinTime == 0)
                .addField(CommissionValue.class, commission, commission == null)
                .addField(byte[].class, distributionAddr, distributionAddr == null || distributionAddr.length == 0)
                .addField(String.class, sideChainId, StringUtils.isEmpty(sideChainId))
                .addField(byte[].class, sideConsAddr, sideConsAddr == null || sideConsAddr.length == 0)
                .addField(byte[].class, sideFeeAddr, sideFeeAddr == null || sideFeeAddr.length == 0)
                .build();
    }

    @Override
    public String toString() {
        return "SideChainValidatorMessage{" + '\n' +
                "feeAddr=" + Hex.toHexString(feeAddr)+ '\n' +
                ", operatorAddr=" + Hex.toHexString(operatorAddr) + '\n' +
                ", consPubKey=" + Hex.toHexString(consPubKey) + '\n' +
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
