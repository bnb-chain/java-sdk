package com.binance.dex.api.client.encoding.message.bridge;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.CoinValue;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class TransferOutMsgMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "from")
    private Bech32AddressValue from;

    @JsonProperty(value = "to")
    private EthAddressValue toAddress;

    @JsonProperty(value = "amount")
    private CoinValue amount;

    @JsonProperty(value = "expire_time")
    private long expireTime;

    public Bech32AddressValue getFrom() {
        return from;
    }

    public void setFrom(Bech32AddressValue from) {
        this.from = from;
    }

    public EthAddressValue getToAddress() {
        return toAddress;
    }

    public void setToAddress(EthAddressValue toAddress) {
        this.toAddress = toAddress;
    }

    public CoinValue getAmount() {
        return amount;
    }

    public void setAmount(CoinValue amount) {
        this.amount = amount;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public void validateBasic() {
        if (toAddress == null || toAddress.isDefaultOrEmpty()){
            throw new IllegalArgumentException("to address should not be empty");
        }
        if (amount == null || amount.getAmount() <= 0){
            throw new IllegalArgumentException("amount should be positive");
        }
        if (expireTime <= 0){
            throw new IllegalArgumentException("expire time should be larger than 0");
        }
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new TransferOutMsgMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Bech32AddressValue.class, from, from == null)
                .addField(EthAddressValue.class, toAddress, toAddress == null)
                .addField(CoinValue.class, amount, amount == null)
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
                toAddress = ((EthAddressValue) value);
                break;
            case 3:
                amount = ((CoinValue) value);
                break;
            case 4:
                expireTime = ((long) value);
                break;
        }
    }

}
