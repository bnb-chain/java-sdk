package com.binance.dex.api.client.encoding.message.sidechain.value;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.serializer.LongToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class TokenMessageValue implements AminoSerializable {

    @JsonProperty(value = "denom")
    private String denom;

    @JsonProperty(value = "amount")
    @JsonSerialize(using = LongToStringSerializer.class)
    private long amount;

    public TokenMessageValue() { }

    public String getDenom() {
        return denom;
    }

    public void setDenom(String denom) {
        this.denom = denom;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new TokenMessageValue();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                denom = ((String) value);
                break;
            case 2:
                amount = ((long) value);
                break;
            default:
                break;
        }
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        ArrayList<AminoField<?>> fields = new ArrayList<>();
        fields.add(new AminoField<>(String.class, denom, StringUtils.isEmpty(denom)));
        fields.add(new AminoField<>(Long.class, amount, amount == 0));
        return fields;
    }

}
