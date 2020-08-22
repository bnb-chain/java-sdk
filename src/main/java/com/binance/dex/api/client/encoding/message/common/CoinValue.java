package com.binance.dex.api.client.encoding.message.common;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.Token;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class CoinValue implements AminoSerializable {

    @JsonProperty(value = "denom")
    private String denom;

    @JsonProperty(value = "amount")
    private long amount;

    public CoinValue() {
    }

    public CoinValue(String denom, Long amount) {
        this.denom = denom;
        this.amount = amount;
    }

    public Token toToken(){
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(amount);
        return token;
    }

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
        return new CoinValueStr();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(String.class, denom, StringUtils.isEmpty(denom))
                .addField(Long.class, amount, amount == 0)
                .build();
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

}
