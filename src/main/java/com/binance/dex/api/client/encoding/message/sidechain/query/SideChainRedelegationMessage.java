package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.sidechain.value.CoinValue;
import com.binance.dex.api.client.encoding.message.sidechain.value.Dec;
import com.binance.dex.api.client.encoding.message.sidechain.value.TimestampValue;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
public class SideChainRedelegationMessage implements AminoSerializable {

    private byte[] delegatorAddress;

    private byte[] srcValidatorAddress;

    private byte[] dstValidatorAddress;

    private long createHeight;

    private TimestampValue minTime = new TimestampValue();

    private CoinValue initialBalance = new CoinValue();

    private CoinValue balance = new CoinValue();

    private Dec srcShares = new Dec();

    private Dec dstShare = new Dec();

    public SideChainRedelegationMessage() {
    }

    public byte[] getDelegatorAddress() {
        return delegatorAddress;
    }

    public void setDelegatorAddress(byte[] delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public byte[] getSrcValidatorAddress() {
        return srcValidatorAddress;
    }

    public void setSrcValidatorAddress(byte[] srcValidatorAddress) {
        this.srcValidatorAddress = srcValidatorAddress;
    }

    public byte[] getDstValidatorAddress() {
        return dstValidatorAddress;
    }

    public void setDstValidatorAddress(byte[] dstValidatorAddress) {
        this.dstValidatorAddress = dstValidatorAddress;
    }

    public long getCreateHeight() {
        return createHeight;
    }

    public void setCreateHeight(long createHeight) {
        this.createHeight = createHeight;
    }

    public TimestampValue getMinTime() {
        return minTime;
    }

    public void setMinTime(TimestampValue minTime) {
        this.minTime = minTime;
    }

    public CoinValue getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(CoinValue initialBalance) {
        this.initialBalance = initialBalance;
    }

    public CoinValue getBalance() {
        return balance;
    }

    public void setBalance(CoinValue balance) {
        this.balance = balance;
    }

    public Dec getSrcShares() {
        return srcShares;
    }

    public void setSrcShares(Dec srcShares) {
        this.srcShares = srcShares;
    }

    public Dec getDstShare() {
        return dstShare;
    }

    public void setDstShare(Dec dstShare) {
        this.dstShare = dstShare;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new SideChainRedelegationMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(byte[].class, delegatorAddress, delegatorAddress == null || delegatorAddress.length == 0)
                .addField(byte[].class, srcValidatorAddress, srcValidatorAddress == null || srcValidatorAddress.length == 0)
                .addField(byte[].class, dstValidatorAddress, dstValidatorAddress == null || dstValidatorAddress.length == 0)
                .addField(Long.class, createHeight, createHeight == 0)
                .addField(TimestampValue.class, minTime, minTime == null)
                .addField(CoinValue.class, initialBalance, initialBalance == null)
                .addField(CoinValue.class, balance, balance == null)
                .addField(Dec.class, srcShares, srcShares == null || srcShares.isDefaultOrEmpty())
                .addField(Dec.class, dstShare, dstShare == null || dstShare.isDefaultOrEmpty())
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                delegatorAddress = ((byte[]) value);
                break;
            case 2:
                srcValidatorAddress = ((byte[]) value);
                break;
            case 3:
                dstValidatorAddress = ((byte[]) value);
                break;
            case 4:
                createHeight = ((long) value);
                break;
            case 5:
                minTime = ((TimestampValue) value);
                break;
            case 6:
                initialBalance = ((CoinValue) value);
                break;
            case 7:
                balance = ((CoinValue) value);
                break;
            case 8:
                srcShares = ((Dec) value);
                break;
            case 9:
                dstShare = ((Dec) value);
                break;
            default:
                break;
        }
    }
}
