package com.binance.dex.api.client.domain.bridge;

import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Fitz.Lu
 **/
public class TransferOut {

    private String from;

    private String toAddress;

    private Token amount;

    private long expireTime;

    public TransferOut() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public Token getAmount() {
        return amount;
    }

    public void setAmount(Token amount) {
        this.amount = amount;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "TransferOut{" +
                "from='" + from + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", amount=" + amount +
                ", expireTime=" + expireTime +
                '}';
    }
}
