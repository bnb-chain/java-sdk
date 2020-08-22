package com.binance.dex.api.client.encoding.message.bridge;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
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
public class BindMsgMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "from")
    private Bech32AddressValue from;

    @JsonProperty(value = "symbol")
    private String symbol;

    @JsonProperty(value = "amount")
    private long amount;

    @JsonProperty(value = "contract_address")
    private EthAddressValue contractAddress;

    @JsonProperty(value = "contract_decimals")
    private int contractDecimal;

    @JsonProperty(value = "expire_time")
    private long expireTime;

    public BindMsgMessage() {
    }

    public BindMsgMessage(Bech32AddressValue from, String symbol, long amount, EthAddressValue contractAddress, int contractDecimal, long expireTime) {
        this.from = from;
        this.symbol = symbol;
        this.amount = amount;
        this.contractAddress = contractAddress;
        this.contractDecimal = contractDecimal;
        this.expireTime = expireTime;
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public EthAddressValue getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(EthAddressValue contractAddress) {
        this.contractAddress = contractAddress;
    }

    public int getContractDecimal() {
        return contractDecimal;
    }

    public void setContractDecimal(int contractDecimal) {
        this.contractDecimal = contractDecimal;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public void validateBasic() {
        if (StringUtils.isEmpty(symbol)){
            throw new IllegalArgumentException("symbol should not be empty");
        }
        if (amount <= 0){
            throw new IllegalArgumentException("amount should be larger than 0");
        }
        if (contractAddress == null || contractAddress.isDefaultOrEmpty()){
            throw new IllegalArgumentException("contract address should not be empty");
        }
        if (contractDecimal < 0){
            throw new IllegalArgumentException("decimal should be no less than 0");
        }
        if (expireTime <= 0){
            throw new IllegalArgumentException("expire time should be larger than 0");
        }
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new BindMsgMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Bech32AddressValue.class, from, from == null || from.isDefaultOrEmpty())
                .addField(String.class, symbol, StringUtils.isEmpty(symbol))
                .addField(Long.class, amount, amount == 0)
                .addField(EthAddressValue.class, contractAddress, contractAddress == null || contractAddress.isDefaultOrEmpty())
                .addField(Integer.class, contractDecimal, contractDecimal == 0)
                .addField(Long.class, expireTime, expireTime == 0)
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
            case 3:
                amount = ((long) value);
                break;
            case 4:
                contractAddress = ((EthAddressValue) value);
                break;
            case 5:
                contractDecimal = ((int) value);
                break;
            case 6:
                expireTime = ((long) value);
                break;
        }
    }
}
