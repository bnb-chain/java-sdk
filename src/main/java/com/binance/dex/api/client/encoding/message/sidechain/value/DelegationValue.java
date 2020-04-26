package com.binance.dex.api.client.encoding.message.sidechain.value;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.common.Dec;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
public class DelegationValue implements AminoSerializable {

    private Dec shares = new Dec();

    private long height;

    public DelegationValue() {
    }

    public Dec getShares() {
        return shares;
    }

    public void setShares(Dec shares) {
        this.shares = shares;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new DelegationValue();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Dec.class, shares, shares == null || shares.isDefaultOrEmpty())
                .addField(Long.class, height, height == 0)
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                shares = ((Dec) value);
                break;
            case 2:
                height = ((Long) value);
                break;
            default:
                break;
        }
    }
}
