package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.common.Dec;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
public class PoolMessage implements AminoSerializable {

    private Dec looseTokens;

    private Dec bondedTokens;

    public Dec getLooseTokens() {
        return looseTokens;
    }

    public void setLooseTokens(Dec looseTokens) {
        this.looseTokens = looseTokens;
    }

    public Dec getBondedTokens() {
        return bondedTokens;
    }

    public void setBondedTokens(Dec bondedTokens) {
        this.bondedTokens = bondedTokens;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new PoolMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Dec.class, looseTokens, looseTokens == null)
                .addField(Dec.class, bondedTokens, bondedTokens == null)
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                looseTokens = ((Dec) value);
                break;
            case 2:
                bondedTokens = ((Dec) value);
                break;
            default:
                break;
        }
    }
}
