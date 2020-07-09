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

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class SideChainRedelegateMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "delegator_addr")
    private Bech32AddressValue delegatorAddress;

    @JsonProperty(value = "validator_src_addr")
    private Bech32AddressValue srcValidatorAddress;

    @JsonProperty(value = "validator_dst_addr")
    private Bech32AddressValue dstValidatorAddress;

    @JsonProperty(value = "amount")
    private CoinValueStr amount;

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
                .addField(Bech32AddressValue.class, delegatorAddress, delegatorAddress == null || delegatorAddress.isDefaultOrEmpty())
                .addField(Bech32AddressValue.class, srcValidatorAddress, srcValidatorAddress == null || srcValidatorAddress.isDefaultOrEmpty())
                .addField(Bech32AddressValue.class, dstValidatorAddress, dstValidatorAddress == null || dstValidatorAddress.isDefaultOrEmpty())
                .addField(CoinValueStr.class, amount, amount == null)
                .addField(String.class, sideChainId, StringUtils.isEmpty(sideChainId))
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                delegatorAddress = ((Bech32AddressValue) value);
                break;
            case 2:
                srcValidatorAddress = ((Bech32AddressValue) value);
                break;
            case 3:
                dstValidatorAddress = ((Bech32AddressValue) value);
                break;
            case 4:
                amount = ((CoinValueStr) value);
                break;
            case 5:
                sideChainId = ((String) value);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean useAminoJson() {
        return true;
    }

    public Bech32AddressValue getDelegatorAddress() {
        return delegatorAddress;
    }

    public void setDelegatorAddress(Bech32AddressValue delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public Bech32AddressValue getSrcValidatorAddress() {
        return srcValidatorAddress;
    }

    public void setSrcValidatorAddress(Bech32AddressValue srcValidatorAddress) {
        this.srcValidatorAddress = srcValidatorAddress;
    }

    public Bech32AddressValue getDstValidatorAddress() {
        return dstValidatorAddress;
    }

    public void setDstValidatorAddress(Bech32AddressValue dstValidatorAddress) {
        this.dstValidatorAddress = dstValidatorAddress;
    }

    public CoinValueStr getAmount() {
        return amount;
    }

    public void setAmount(CoinValueStr amount) {
        this.amount = amount;
    }

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }
}
