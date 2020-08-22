package com.binance.dex.api.client.encoding.message.sidechain.value;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
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
public class CommissionValue implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "rate")
    @JsonSerialize(using = DecToStringSerializer.class)
    private Dec rate = Dec.newInstance(0L);

    @JsonProperty(value = "max_rate")
    @JsonSerialize(using = DecToStringSerializer.class)
    private Dec maxRate = Dec.newInstance(0L);

    @JsonProperty(value = "max_change_rate")
    @JsonSerialize(using = DecToStringSerializer.class)
    private Dec maxChangeRate = Dec.newInstance(0L);

    @JsonProperty(value = "update_time")
    private TimestampValue updateTime;

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

    public TimestampValue getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(TimestampValue updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Commission{" +
                "rate=" + rate +
                ", maxRate=" + maxRate +
                ", maxChangeRate=" + maxChangeRate +
                ", updateTime=" + updateTime +
                '}';
    }


    @Override
    public AminoSerializable newAminoMessage() {
        return new CommissionValue();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Dec.class, rate, rate == null || rate.isDefaultOrEmpty())
                .addField(Dec.class, maxRate, maxRate == null || maxRate.isDefaultOrEmpty())
                .addField(Dec.class, maxChangeRate, maxChangeRate == null || maxChangeRate.isDefaultOrEmpty())
                .addField(TimestampValue.class, updateTime, false)
                .build();
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
            case 4:
                updateTime = ((TimestampValue) value);
                break;
            default:
                break;
        }
    }
}
