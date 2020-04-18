package com.binance.dex.api.client.encoding.message.sidechain.transaction;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.sidechain.value.AddressValue;
import com.binance.dex.api.client.encoding.message.sidechain.value.CoinValue;
import com.binance.dex.api.client.encoding.serializer.AccAddressValueToStringSerializer;
import com.binance.dex.api.client.encoding.serializer.ValAddressValueToStringSerializer;
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
public class SideChainRedelegateMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "delegator_addr")
    @JsonSerialize(using = AccAddressValueToStringSerializer.class)
    private AddressValue delegatorAddress;

    @JsonProperty(value = "validator_src_addr")
    @JsonSerialize(using = ValAddressValueToStringSerializer.class)
    private AddressValue srcValidatorAddress;

    @JsonProperty(value = "validator_dst_addr")
    @JsonSerialize(using = ValAddressValueToStringSerializer.class)
    private AddressValue dstValidatorAddress;

    @JsonProperty(value = "amount")
    private CoinValue amount;

    @JsonProperty(value = "side_chain_id")
    private String sideChainId;

    public SideChainRedelegateMessage() {
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new SideChainRedelegateMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(AddressValue.class, delegatorAddress, delegatorAddress == null || delegatorAddress.isDefaultOrEmpty())
                .addField(AddressValue.class, srcValidatorAddress, srcValidatorAddress == null || srcValidatorAddress.isDefaultOrEmpty())
                .addField(AddressValue.class, dstValidatorAddress, dstValidatorAddress == null || dstValidatorAddress.isDefaultOrEmpty())
                .addField(CoinValue.class, amount, amount == null)
                .addField(String.class, sideChainId, StringUtils.isEmpty(sideChainId))
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                delegatorAddress = ((AddressValue) value);
                break;
            case 2:
                srcValidatorAddress = ((AddressValue) value);
                break;
            case 3:
                dstValidatorAddress = ((AddressValue) value);
                break;
            case 4:
                amount = ((CoinValue) value);
                break;
            case 5:
                sideChainId = ((String) value);
                break;
            default:
                break;
        }
    }

    public AddressValue getDelegatorAddress() {
        return delegatorAddress;
    }

    public void setDelegatorAddress(AddressValue delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public AddressValue getSrcValidatorAddress() {
        return srcValidatorAddress;
    }

    public void setSrcValidatorAddress(AddressValue srcValidatorAddress) {
        this.srcValidatorAddress = srcValidatorAddress;
    }

    public AddressValue getDstValidatorAddress() {
        return dstValidatorAddress;
    }

    public void setDstValidatorAddress(AddressValue dstValidatorAddress) {
        this.dstValidatorAddress = dstValidatorAddress;
    }

    public CoinValue getAmount() {
        return amount;
    }

    public void setAmount(CoinValue amount) {
        this.amount = amount;
    }

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }
}
