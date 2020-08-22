package com.binance.dex.api.client.encoding.message.sidechain.value;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
public class TimestampValue implements AminoSerializable {

    private long seconds;

    private int nano;

    public TimestampValue() {
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public int getNano() {
        return nano;
    }

    public void setNano(int nano) {
        this.nano = nano;
    }

    public long getTimeInMilliseconds(){
        return seconds * 1000;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new TimestampValue();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Long.class, seconds, false)
                .addField(Integer.class, nano, false)
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                seconds = ((Long) value);
                break;
            case 2:
                nano = ((Integer) value);
                break;
            default:
                break;
        }
    }
}
