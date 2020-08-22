package com.binance.dex.api.client.encoding.message.bridge;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.CoinValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class UpdateTransferOutClaimMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "refund_address")
    private Bech32AddressValue refundAddress;

    @JsonProperty(value = "amount")
    private CoinValue amount;

    @JsonProperty(value = "refund_reason")
    private int refundReason;

    public Bech32AddressValue getRefundAddress() {
        return refundAddress;
    }

    public void setRefundAddress(Bech32AddressValue refundAddress) {
        this.refundAddress = refundAddress;
    }

    public CoinValue getAmount() {
        return amount;
    }

    public void setAmount(CoinValue amount) {
        this.amount = amount;
    }

    public int getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(int refundReason) {
        this.refundReason = refundReason;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new UpdateTransferOutClaimMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Bech32AddressValue.class, refundAddress, refundAddress == null || refundAddress.isDefaultOrEmpty())
                .addField(CoinValue.class, amount, amount == null)
                .addField(Integer.class, refundReason, false)
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                refundAddress = ((Bech32AddressValue) value);
                break;
            case 2:
                amount = ((CoinValue) value);
                break;
            case 3:
                refundReason = ((int) value);
                break;
            default:
                break;
        }
    }
}
