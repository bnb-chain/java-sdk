package com.binance.dex.api.client.domain.bridge;

import com.binance.dex.api.client.encoding.message.Token;

import java.util.List;

/**
 * @author Fitz.Lu
 **/
public class TransferIn {

    private String contractAddress;

    private List<String> refundAddresses;

    private List<String> receiverAddresses;

    private List<Long> amounts;

    private String symbol;

    private Token relayFee;

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

    public Token getRelayFee() {
        return relayFee;
    }

    public void setRelayFee(Token relayFee) {
        this.relayFee = relayFee;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
