package com.binance.dex.api.client.encoding.message.sidechain.value;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class CommissionValue implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "rate")
    private long rate;

    @JsonProperty(value = "max_rate")
    private long maxRate;

    @JsonProperty(value = "max_change_rate")
    private long maxChangeRate;

    @JsonProperty(value = "update_time")
    private TimestampValue updateTime;

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public long getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(long maxRate) {
        this.maxRate = maxRate;
    }

    public long getMaxChangeRate() {
        return maxChangeRate;
    }

    public void setMaxChangeRate(long maxChangeRate) {
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
                .addField(Long.class, rate, rate == 0)
                .addField(Long.class, maxRate, maxRate == 0)
                .addField(Long.class, maxChangeRate, maxChangeRate == 0)
                .addField(TimestampValue.class, updateTime, false)
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                rate = ((Long) value);
                break;
            case 2:
                maxRate = ((Long) value);
                break;
            case 3:
                maxChangeRate = ((Long) value);
                break;
            case 4:
                updateTime = ((TimestampValue) value);
                break;
            default:
                break;
        }
    }
}
