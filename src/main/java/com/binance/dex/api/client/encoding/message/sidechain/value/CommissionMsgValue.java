package com.binance.dex.api.client.encoding.message.sidechain.value;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.common.Dec;
import com.binance.dex.api.client.encoding.serializer.DecToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class CommissionMsgValue implements AminoSerializable {

    @JsonProperty(value = "rate")
    @JsonSerialize(using = DecToStringSerializer.class)
    private Dec rate = new Dec();

    @JsonProperty(value = "max_rate")
    @JsonSerialize(using = DecToStringSerializer.class)
    private Dec maxRate = new Dec();

    @JsonProperty(value = "max_change_rate")
    @JsonSerialize(using = DecToStringSerializer.class)
    private Dec maxChangeRate = new Dec();

    public CommissionMsgValue() {
    }

    public Dec getRate() {
        return rate;
    }

    public void setRate(Dec rate) {
        this.rate = rate;
    }

    public Dec getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(Dec maxRate) {
        this.maxRate = maxRate;
    }

    public Dec getMaxChangeRate() {
        return maxChangeRate;
    }

    public void setMaxChangeRate(Dec maxChangeRate) {
        this.maxChangeRate = maxChangeRate;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new CommissionMsgValue();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        ArrayList<AminoField<?>> fields = new ArrayList<>();
        fields.add(new AminoField<Dec>(Dec.class, rate, rate == null || rate.isDefaultOrEmpty()));
        fields.add(new AminoField<Dec>(Dec.class, maxRate, rate == null || rate.isDefaultOrEmpty()));
        fields.add(new AminoField<Dec>(Dec.class, maxChangeRate, rate == null || rate.isDefaultOrEmpty()));
        return fields;
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                rate = ((Dec) value);
                break;
            case 2:
                maxRate = ((Dec) value);
                break;
            case 3:
                maxChangeRate = ((Dec) value);
                break;
            default:
                break;
        }
    }
}
