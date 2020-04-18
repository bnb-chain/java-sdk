package com.binance.dex.api.client.encoding.message.sidechain.value;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
public class RedelegationValue implements AminoSerializable {

    private long creationHeight;

    private TimestampValue minTime = new TimestampValue();

    private CoinValue initialBalance = new CoinValue();

    private CoinValue balance = new CoinValue();

    private Dec sharesSrc = new Dec();

    private Dec sharesDst = new Dec();

    public RedelegationValue() {
    }

    public long getCreationHeight() {
        return creationHeight;
    }

    public void setCreationHeight(long creationHeight) {
        this.creationHeight = creationHeight;
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

    public Dec getSharesSrc() {
        return sharesSrc;
    }

    public void setSharesSrc(Dec sharesSrc) {
        this.sharesSrc = sharesSrc;
    }

    public Dec getSharesDst() {
        return sharesDst;
    }

    public void setSharesDst(Dec sharesDst) {
        this.sharesDst = sharesDst;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new RedelegationValue();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Long.class, creationHeight, creationHeight == 0)
                .addField(TimestampValue.class, minTime, minTime == null)
                .addField(CoinValue.class, initialBalance, initialBalance == null)
                .addField(CoinValue.class, balance, balance == null)
                .addField(Dec.class, sharesSrc, sharesSrc == null)
                .addField(Dec.class, sharesDst, sharesDst == null)
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                creationHeight = ((long) value);
                break;
            case 2:
                minTime = ((TimestampValue) value);
                break;
            case 3:
                initialBalance = ((CoinValue) value);
                break;
            case 4:
                balance = ((CoinValue) value);
                break;
            case 5:
                sharesSrc = ((Dec) value);
                break;
            case 6:
                sharesDst = ((Dec) value);
                break;
            default:
                break;
        }
    }
}
