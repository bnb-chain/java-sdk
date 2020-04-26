package com.binance.dex.api.client.encoding.message.bridge;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.serializer.Bech32AddressValueToStringSerializer;
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
public class ClaimMsgMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "claim_type")
    private int claimType;

    @JsonProperty(value = "sequence")
    private long sequence;

    @JsonProperty(value = "claim")
    private String claim;

    @JsonProperty(value = "validator_address")
    @JsonSerialize(using = Bech32AddressValueToStringSerializer.class)
    private Bech32AddressValue validatorAddress;

    public ClaimMsgMessage() {
    }

    public ClaimMsgMessage(int claimType, long sequence, String claim, Bech32AddressValue validatorAddress) {
        this.claimType = claimType;
        this.sequence = sequence;
        this.claim = claim;
        this.validatorAddress = validatorAddress;
    }

    public int getClaimType() {
        return claimType;
    }

    public void setClaimType(int claimType) {
        this.claimType = claimType;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public Bech32AddressValue getValidatorAddress() {
        return validatorAddress;
    }

    public void setValidatorAddress(Bech32AddressValue validatorAddress) {
        this.validatorAddress = validatorAddress;
    }

    @Override
    public void validateBasic() {
        if (!ClaimTypes.IsValidClaimType(claimType)){
            throw new IllegalArgumentException(String.format("claim type %s does not exist, see class ClaimTypes", claimType));
        }
        if (sequence < 0){
            throw new IllegalArgumentException("sequence should not be less than 0");
        }
        if (StringUtils.isEmpty(claim)){
            throw new IllegalArgumentException("claim should not be empty");
        }
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new ClaimMsgMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Integer.class, claimType, false)
                .addField(Long.class, sequence, false)
                .addField(String.class, claim, StringUtils.isEmpty(claim))
                .addField(Bech32AddressValue.class, validatorAddress, validatorAddress == null ||validatorAddress.isDefaultOrEmpty())
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                claimType = ((int) value);
                break;
            case 2:
                sequence = ((long) value);
                break;
            case 3:
                claim = ((String) value);
                break;
            case 4:
                validatorAddress = (Bech32AddressValue) value;
                break;
            default:
                break;
        }
    }
}
