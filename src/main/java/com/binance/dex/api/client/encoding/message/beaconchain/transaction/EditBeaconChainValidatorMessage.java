package com.binance.dex.api.client.encoding.message.beaconchain.transaction;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.Dec;
import com.binance.dex.api.client.encoding.message.sidechain.value.DescriptionValue;
import com.binance.dex.api.client.encoding.serializer.DecToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @author Francis.Liu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class EditBeaconChainValidatorMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "description")
    private DescriptionValue description;

    @JsonProperty(value = "address")
    private Bech32AddressValue validatorOperatorAddress;

    @JsonProperty(value = "commission_rate")
    @JsonSerialize(using = DecToStringSerializer.class)
    private Dec commissionRate;

    @JsonProperty(value = "pubkey")
    private String pubKey;


    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                description = ((DescriptionValue) value);
                break;
            case 2:
                validatorOperatorAddress = ((Bech32AddressValue) value);
                break;
            case 3:
                commissionRate = ((Dec) value);
                break;
            case 4:
                pubKey = ((String) value);
                break;
            default:
                break;
        }
    }

    public EditBeaconChainValidatorMessage() {
    }

    public DescriptionValue getDescription() {
        return description;
    }

    public void setDescription(DescriptionValue description) {
        this.description = description;
    }

    public Bech32AddressValue getValidatorOperatorAddress() {
        return validatorOperatorAddress;
    }

    public void setValidatorOperatorAddress(Bech32AddressValue validatorOperatorAddress) {
        this.validatorOperatorAddress = validatorOperatorAddress;
    }

    public Dec getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Dec commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    @Override
    public boolean useAminoJson() {
        return true;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new EditBeaconChainValidatorMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(DescriptionValue.class, description, description == null)
                .addField(Bech32AddressValue.class, validatorOperatorAddress, validatorOperatorAddress == null || validatorOperatorAddress.isDefaultOrEmpty())
                .addField(Dec.class, commissionRate, commissionRate == null || commissionRate.isDefaultOrEmpty())
//                .addField(Dec.class, commissionRate, false)
                .addField(String.class, pubKey, StringUtils.isEmpty(pubKey))
                .build();
    }

}
