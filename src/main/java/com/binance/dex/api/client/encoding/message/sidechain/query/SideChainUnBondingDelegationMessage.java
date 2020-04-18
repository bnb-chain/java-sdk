package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.sidechain.value.CoinValue;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
public class SideChainUnBondingDelegationMessage implements AminoSerializable {

    private byte[] delegatorAddress;

    private byte[] validatorAddress;

    private long createHeight;

    private long minTimeInMs;

    private CoinValue initialBalance = new CoinValue();

    private CoinValue balance = new CoinValue();

    @Override
    public AminoSerializable newAminoMessage() {
        return new SideChainUnBondingDelegationMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(byte[].class, delegatorAddress, ByteUtil.isEmpty(delegatorAddress))
                .addField(byte[].class, validatorAddress, ByteUtil.isEmpty(validatorAddress))
                .addField(Long.class, createHeight, createHeight == 0)
                .addField(Long.class, minTimeInMs, minTimeInMs == 0)
                .addField(CoinValue.class, initialBalance, initialBalance == null)
                .addField(CoinValue.class, balance, balance == null)
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                delegatorAddress = ((byte[]) value);
                break;
            case 2:
                validatorAddress = ((byte[]) value);
                break;
            case 3:
                createHeight = ((Integer) value);
                break;
            case 4:
                minTimeInMs = ((Long) value);
                break;
            case 5:
                initialBalance = ((CoinValue) value);
                break;
            case 6:
                balance = ((CoinValue) value);
                break;
            default:
                break;
        }
    }
}
