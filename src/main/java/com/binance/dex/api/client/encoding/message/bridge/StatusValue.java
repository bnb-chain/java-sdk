package com.binance.dex.api.client.encoding.message.bridge;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
public class StatusValue implements AminoSerializable {

    private int text;

    private String finalClaim;

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public String getFinalClaim() {
        return finalClaim;
    }

    public void setFinalClaim(String finalClaim) {
        this.finalClaim = finalClaim;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new StatusValue();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Integer.class, text, false)
                .addField(String.class, finalClaim, StringUtils.isEmpty(finalClaim))
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                text = ((Integer) value);
                break;
            case 2:
                finalClaim = ((String) value);
                break;
        }
    }
}
