package com.binance.dex.api.client.encoding.message.bridge;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
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
public class UpdateBindClaimMessage implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "status")
    private int status;

    @JsonProperty(value = "symbol")
    private String symbol;

    @JsonProperty(value = "contract_address")
    private EthAddressValue contractAddress;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public EthAddressValue getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(EthAddressValue contractAddress) {
        this.contractAddress = contractAddress;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new UpdateBindClaimMessage();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(Integer.class, status, false)
                .addField(String.class, symbol, StringUtils.isEmpty(symbol))
                .addField(EthAddressValue.class, contractAddress, contractAddress ==  null || contractAddress.isDefaultOrEmpty())
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                status = ((int) value);
                break;
            case 2:
                symbol = ((String) value);
                break;
            case 3:
                contractAddress = ((EthAddressValue) value);
                break;
            default:
                break;
        }
    }
}
