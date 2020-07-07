package com.binance.dex.api.client.encoding.message.bridge;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class ClaimMsgMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "chain_id")
    private int chainId;

    @JsonProperty(value = "sequence")
    private long sequence;

    @JsonProperty(value = "payload")
    private byte[] payload;

    @JsonProperty(value = "validator_address")
    private Bech32AddressValue validatorAddress;

    public ClaimMsgMessage() {
    }

    public ClaimMsgMessage(int chainId, long sequence, byte[] payload, Bech32AddressValue validatorAddress) {
        this.chainId = chainId;
        this.sequence = sequence;
        this.payload = payload;
        this.validatorAddress = validatorAddress;
    }

    public int getChainId() {
        return chainId;
    }

    public void setChainId(int chainId) {
        this.chainId = chainId;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public Bech32AddressValue getValidatorAddress() {
        return validatorAddress;
    }

    public void setValidatorAddress(Bech32AddressValue validatorAddress) {
        this.validatorAddress = validatorAddress;
    }

    @Override
    public void validateBasic() {
        if (!ClaimTypes.IsValidClaimType(chainId)) {
            throw new IllegalArgumentException(String.format("claim type %s does not exist, see class ClaimTypes", chainId));
        }
        if (sequence < 0) {
            throw new IllegalArgumentException("sequence should not be less than 0");
        }
        if (payload == null || payload.length == 0) {
            throw new IllegalArgumentException("payload should not be empty");
        }
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new ClaimMsgMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Integer.class, chainId, false)
                .addField(Long.class, sequence, false)
                .addField(byte[].class, payload, (payload == null || payload.length == 0))
                .addField(Bech32AddressValue.class, validatorAddress, validatorAddress == null || validatorAddress.isDefaultOrEmpty())
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                chainId = ((int) value);
                break;
            case 2:
                sequence = ((long) value);
                break;
            case 3:
                payload = (byte[]) value;
                break;
            case 4:
                validatorAddress = (Bech32AddressValue) value;
                break;
            default:
                break;
        }
    }
}
