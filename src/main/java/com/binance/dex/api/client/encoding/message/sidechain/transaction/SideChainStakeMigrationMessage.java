package com.binance.dex.api.client.encoding.message.sidechain.transaction;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.CoinValueStr;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class SideChainStakeMigrationMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "validator_src_addr")
    private Bech32AddressValue validatorSrcAddr;

    @JsonProperty(value = "validator_dst_addr")
    private byte[] validatorDstAddr;

    @JsonProperty(value = "delegator_addr")
    private byte[] delegatorAddr;

    @JsonProperty(value = "refund_addr")
    private Bech32AddressValue refundAddr;

    @JsonProperty(value = "amount")
    private CoinValueStr amount;

    public SideChainStakeMigrationMessage() {
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new SideChainStakeMigrationMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Bech32AddressValue.class, validatorSrcAddr, validatorSrcAddr == null || validatorSrcAddr.isDefaultOrEmpty())
                .addField(byte[].class, validatorDstAddr, validatorDstAddr == null || validatorDstAddr.length == 0)
                .addField(byte[].class, delegatorAddr, delegatorAddr == null || delegatorAddr.length == 0)
                .addField(Bech32AddressValue.class, refundAddr, refundAddr == null || refundAddr.isDefaultOrEmpty())
                .addField(CoinValueStr.class, amount, amount == null)
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                validatorSrcAddr = ((Bech32AddressValue) value);
                break;
            case 2:
                validatorDstAddr = ((byte[]) value);
                break;
            case 3:
                delegatorAddr = ((byte[]) value);
                break;
            case 4:
                refundAddr = ((Bech32AddressValue) value);
                break;
            case 5:
                amount = ((CoinValueStr) value);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean useAminoJson() {
        return true;
    }

    public Bech32AddressValue getValidatorSrcAddr() {
        return validatorSrcAddr;
    }

    public void setValidatorSrcAddr(Bech32AddressValue validatorSrcAddr) {
        this.validatorSrcAddr = validatorSrcAddr;
    }

    public byte[] getValidatorDstAddr() {
        return validatorDstAddr;
    }

    public void setValidatorDstAddr(byte[] validatorDstAddr) {
        this.validatorDstAddr = validatorDstAddr;
    }

    public byte[] getDelegatorAddr() {
        return delegatorAddr;
    }

    public void setDelegatorAddr(byte[] delegatorAddr) {
        this.delegatorAddr = delegatorAddr;
    }

    public Bech32AddressValue getRefundAddr() {
        return refundAddr;
    }

    public void setRefundAddr(Bech32AddressValue refundAddr) {
        this.refundAddr = refundAddr;
    }

    public CoinValueStr getAmount() {
        return amount;
    }

    public void setAmount(CoinValueStr amount) {
        this.amount = amount;
    }
}
