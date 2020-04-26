package com.binance.dex.api.client.encoding.message.bridge;

import com.binance.dex.api.client.encoding.message.common.CoinValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class TransferInClaimMessage {

    @JsonProperty(value = "contract_address")
    private String contractAddress;

    @JsonProperty(value = "refund_addresses")
    private List<String> refundAddresses;

    @JsonProperty(value = "receiver_addresses")
    private List<String> receiverAddresses;

    @JsonProperty(value = "amounts")
    private List<Long> amounts;

    @JsonProperty(value = "symbol")
    private String symbol;

    @JsonProperty(value = "relay_fee")
    private CoinValue relayFee;

    @JsonProperty(value = "expire_time")
    private long expireTime;

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public List<String> getRefundAddresses() {
        return refundAddresses;
    }

    public void setRefundAddresses(List<String> refundAddresses) {
        this.refundAddresses = refundAddresses;
    }

    public List<String> getReceiverAddresses() {
        return receiverAddresses;
    }

    public void setReceiverAddresses(List<String> receiverAddresses) {
        this.receiverAddresses = receiverAddresses;
    }

    public List<Long> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<Long> amounts) {
        this.amounts = amounts;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public CoinValue getRelayFee() {
        return relayFee;
    }

    public void setRelayFee(CoinValue relayFee) {
        this.relayFee = relayFee;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
