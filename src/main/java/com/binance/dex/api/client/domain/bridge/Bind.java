package com.binance.dex.api.client.domain.bridge;

/**
 * @author Fitz.Lu
 **/
public class Bind {

    private String from;

    private String symbol;

    private long amount;

    private String contractAddress;

    private int contractDecimal;

    private long expireTime;

    public Bind() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public int getContractDecimal() {
        return contractDecimal;
    }

    public void setContractDecimal(int contractDecimal) {
        this.contractDecimal = contractDecimal;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "Bind{" +
                "from='" + from + '\'' +
                ", symbol='" + symbol + '\'' +
                ", amount=" + amount +
                ", contractAddress='" + contractAddress + '\'' +
                ", contractDecimal=" + contractDecimal +
                ", expireTime=" + expireTime +
                '}';
    }
}
