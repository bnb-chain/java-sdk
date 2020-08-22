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
public class SideChainUndelegateMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "delegator_addr")
    private Bech32AddressValue delegatorAddress;

    @JsonProperty(value = "validator_addr")
    private Bech32AddressValue validatorAddress;

    @JsonProperty(value = "amount")
    private CoinValueStr amount;

    @JsonProperty(value = "side_chain_id")
    private String sideChainId;

    public SideChainUndelegateMessage() {
    }

    @Override
    public boolean useAminoJson() {
        return true;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new SideChainUndelegateMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Bech32AddressValue.class, delegatorAddress, delegatorAddress == null || delegatorAddress.isDefaultOrEmpty())
                .addField(Bech32AddressValue.class, validatorAddress, validatorAddress == null || validatorAddress.isDefaultOrEmpty())
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
                validatorAddress = ((Bech32AddressValue) value);
                break;
            case 3:
                amount = ((CoinValueStr) value);
                break;
            case 4:
                sideChainId = ((String) value);
                break;
            default:
                break;
        }
    }

    public Bech32AddressValue getDelegatorAddress() {
        return delegatorAddress;
    }

    public void setDelegatorAddress(Bech32AddressValue delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public Bech32AddressValue getValidatorAddress() {
        return validatorAddress;
    }

    public void setValidatorAddress(Bech32AddressValue validatorAddress) {
        this.validatorAddress = validatorAddress;
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
