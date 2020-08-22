package com.binance.dex.api.client.encoding.message.sidechain.transaction;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.CoinValueStr;
import com.binance.dex.api.client.encoding.message.sidechain.value.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class CreateSideChainValidatorMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "description")
    private DescriptionValue description = new DescriptionValue();

    @JsonProperty(value = "commission")
    private CommissionMsgValue commission = new CommissionMsgValue();

    @JsonProperty(value = "delegator_address")
    private Bech32AddressValue delegatorAddr;

    @JsonProperty(value = "validator_address")
    private Bech32AddressValue validatorOperatorAddr;

    @JsonProperty(value = "delegation")
    private CoinValueStr delegation;

    @JsonProperty(value = "side_chain_id")
    private String sideChainId;

    @JsonProperty(value = "side_cons_addr")
    private byte[] sideConsAddr;

    @JsonProperty(value = "side_fee_addr")
    private byte[] sideFeeAddr;

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
                delegation = ((CoinValueStr) value);
                break;
            case 6:
                sideChainId = ((String) value);
                break;
            case 7:
                sideConsAddr = ((byte[]) value);
                break;
            case 8:
                sideFeeAddr = ((byte[]) value);
                break;
            default:
                break;
        }
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new CreateSideChainValidatorMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        ArrayList<AminoField<?>> fields = new ArrayList<>();
        fields.add(new AminoField<>(DescriptionValue.class, description, description == null));
        fields.add(new AminoField<>(CommissionMsgValue.class, commission, commission == null));
        fields.add(new AminoField<>(Bech32AddressValue.class, delegatorAddr, delegatorAddr == null || delegatorAddr.isDefaultOrEmpty()));
        fields.add(new AminoField<>(Bech32AddressValue.class, validatorOperatorAddr, validatorOperatorAddr == null || validatorOperatorAddr.isDefaultOrEmpty()));
        fields.add(new AminoField<>(CoinValueStr.class, delegation, delegation == null));
        fields.add(new AminoField<>(String.class, sideChainId, StringUtils.isEmpty(sideChainId)));
        fields.add(new AminoField<>(byte[].class, sideConsAddr, sideConsAddr == null || sideConsAddr.length == 0));
        fields.add(new AminoField<>(byte[].class, sideFeeAddr, sideFeeAddr == null || sideFeeAddr.length == 0));
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

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }

    public byte[] getSideConsAddr() {
        return sideConsAddr;
    }

    public void setSideConsAddr(byte[] sideConsAddr) {
        this.sideConsAddr = sideConsAddr;
    }

    public byte[] getSideFeeAddr() {
        return sideFeeAddr;
    }

    public void setSideFeeAddr(byte[] sideFeeAddr) {
        this.sideFeeAddr = sideFeeAddr;
    }

}