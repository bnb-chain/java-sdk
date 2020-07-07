package com.binance.dex.api.client.encoding.message.bridge;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class UnbindMsgMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "from")
    private Bech32AddressValue from;

    @JsonProperty(value = "symbol")
    private String symbol;

    public UnbindMsgMessage() {
    }

    public UnbindMsgMessage(Bech32AddressValue from, String symbol) {
        this.from = from;
        this.symbol = symbol;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new UnbindMsgMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Bech32AddressValue.class, from, from == null || from.isDefaultOrEmpty())
                .addField(String.class, symbol, StringUtils.isEmpty(symbol))
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                from = ((Bech32AddressValue) value);
                break;
            case 2:
                symbol = ((String) value);
                break;
        }
    }

    @Override
    public void validateBasic() {
        if (StringUtils.isEmpty(symbol)){
            throw new IllegalArgumentException("symbol should not be empty");
        }
    }

    public Bech32AddressValue getFrom() {
        return from;
    }

    public void setFrom(Bech32AddressValue from) {
        this.from = from;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
