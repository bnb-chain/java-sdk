package com.binance.dex.api.client.encoding.message.beaconchain.transaction;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.CoinValueStr;
import com.binance.dex.api.client.encoding.message.sidechain.value.CommissionMsgValue;
import com.binance.dex.api.client.encoding.message.sidechain.value.DescriptionValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @author Francis.Liu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class CreateBeaconChainValidatorMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "description")
    private DescriptionValue description = new DescriptionValue();

    @JsonProperty(value = "commission")
    private CommissionMsgValue commission = new CommissionMsgValue();

    @JsonProperty(value = "delegator_address")
    private Bech32AddressValue delegatorAddr;

    @JsonProperty(value = "validator_address")
    private Bech32AddressValue validatorOperatorAddr;

    @JsonProperty(value = "pubkey")
    private String pubKey;

    @JsonProperty(value = "delegation")
    private CoinValueStr delegation;


    @Override
    public boolean useAminoJson() {
        return true;
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                description = ((DescriptionValue) value);
                break;
            case 2:
                commission = ((CommissionMsgValue) value);
                break;
            case 3:
                delegatorAddr = ((Bech32AddressValue) value);
                break;
            case 4:
                validatorOperatorAddr = ((Bech32AddressValue) value);
                break;
            case 5:
                pubKey = ((String) value);
                break;
            case 6:
                delegation = ((CoinValueStr) value);
                break;
            default:
                break;
        }
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new CreateBeaconChainValidatorMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        ArrayList<AminoField<?>> fields = new ArrayList<>();
        fields.add(new AminoField<>(DescriptionValue.class, description, description == null));
        fields.add(new AminoField<>(CommissionMsgValue.class, commission, commission == null));
        fields.add(new AminoField<>(Bech32AddressValue.class, delegatorAddr, delegatorAddr == null || delegatorAddr.isDefaultOrEmpty()));
        fields.add(new AminoField<>(Bech32AddressValue.class, validatorOperatorAddr, validatorOperatorAddr == null || validatorOperatorAddr.isDefaultOrEmpty()));
        fields.add(new AminoField<>(String.class, pubKey, StringUtils.isEmpty(pubKey)));
        fields.add(new AminoField<>(CoinValueStr.class, delegation, delegation == null));
        return fields;
    }

    public DescriptionValue getDescription() {
        return description;
    }

    public void setDescription(DescriptionValue description) {
        this.description = description;
    }

    public CommissionMsgValue getCommission() {
        return commission;
    }

    public void setCommission(CommissionMsgValue commission) {
        this.commission = commission;
    }

    public Bech32AddressValue getDelegatorAddr() {
        return delegatorAddr;
    }

    public void setDelegatorAddr(Bech32AddressValue delegatorAddr) {
        this.delegatorAddr = delegatorAddr;
    }

    public Bech32AddressValue getValidatorOperatorAddr() {
        return validatorOperatorAddr;
    }

    public void setValidatorOperatorAddr(Bech32AddressValue validatorOperatorAddr) {
        this.validatorOperatorAddr = validatorOperatorAddr;
    }

    public CoinValueStr getDelegation() {
        return delegation;
    }

    public void setDelegation(CoinValueStr delegation) {
        this.delegation = delegation;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }
}