package com.binance.dex.api.client.domain.stake;

/**
 * @author Fitz.Lu
 **/
public class Commission {

    private long rate;

    private long maxRate;

    private long maxChangeRate;

    private long updateTimeInMs;

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public long getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(long maxRate) {
        this.maxRate = maxRate;
    }

    public long getMaxChangeRate() {
        return maxChangeRate;
    }

    public void setMaxChangeRate(long maxChangeRate) {
        this.maxChangeRate = maxChangeRate;
    }

    public long getUpdateTimeInMs() {
        return updateTimeInMs;
    }

    public void setUpdateTimeInMs(long updateTimeInMs) {
        this.updateTimeInMs = updateTimeInMs;
    }

    public Commission() {
    }

    public Commission(long rate, long maxRate, long maxChangeRate, long updateTimeInMs) {
        this.rate = rate;
        this.maxRate = maxRate;
        this.maxChangeRate = maxChangeRate;
        this.updateTimeInMs = updateTimeInMs;
    }

    @Override
    public String toString() {
        return "Commission{" +
                "rate=" + rate +
                ", maxRate=" + maxRate +
                ", maxChangeRate=" + maxChangeRate +
                ", updateTimeInMs=" + updateTimeInMs +
                '}';
    }
}
